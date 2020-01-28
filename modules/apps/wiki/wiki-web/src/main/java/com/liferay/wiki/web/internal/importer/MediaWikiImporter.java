/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.web.internal.importer;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.ProgressTrackerThreadLocal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.exception.ImportFilesException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.importer.WikiImporter;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.validator.WikiPageTitleValidator;
import com.liferay.wiki.web.internal.translator.MediaWikiToCreoleTranslator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 */
@Component(
	property = {"importer=MediaWiki", "page=/wiki/import/mediawiki.jsp"},
	service = WikiImporter.class
)
public class MediaWikiImporter implements WikiImporter {

	public static final String FORMAT_CREOLE = "creole";

	public static final String OPTIONS_FRONT_PAGE = "OPTIONS_FRONT_PAGE";

	public static final String OPTIONS_IMPORT_LATEST_VERSION =
		"OPTIONS_IMPORT_LATEST_VERSION";

	public static final String OPTIONS_STRICT_IMPORT_MODE =
		"OPTIONS_STRICT_IMPORT_MODE";

	public static final String SHARED_IMAGES_CONTENT = "See attachments";

	public static final String SHARED_IMAGES_TITLE = "SharedImages";

	@Override
	public void importPages(
			long userId, WikiNode node, InputStream[] inputStreams,
			Map<String, String[]> options)
		throws PortalException {

		if ((inputStreams.length < 1) || (inputStreams[0] == null)) {
			throw new PortalException("The pages file is mandatory");
		}

		InputStream pagesInputStream = inputStreams[0];

		InputStream usersInputStream = null;

		if (inputStreams.length > 1) {
			usersInputStream = inputStreams[1];
		}

		InputStream imagesInputStream = null;

		if (inputStreams.length > 2) {
			imagesInputStream = inputStreams[2];
		}

		try {
			Document document = SAXReaderUtil.read(pagesInputStream);

			Map<String, String> usersMap = readUsersFile(usersInputStream);

			Element rootElement = document.getRootElement();

			List<String> specialNamespaces = readSpecialNamespaces(rootElement);

			processImages(userId, node, imagesInputStream);

			processSpecialPages(userId, node, rootElement, specialNamespaces);
			processRegularPages(
				userId, node, rootElement, specialNamespaces, usersMap,
				imagesInputStream, options);

			moveFrontPage(userId, node, options);
		}
		catch (DocumentException documentException) {
			throw new ImportFilesException(
				"Invalid XML file provided", documentException);
		}
		catch (IOException ioException) {
			throw new ImportFilesException(
				"Error reading the files provided", ioException);
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	protected String getCreoleRedirectContent(String redirectTitle) {
		return StringPool.DOUBLE_OPEN_BRACKET + redirectTitle +
			StringPool.DOUBLE_CLOSE_BRACKET;
	}

	protected long getUserId(
		long userId, WikiNode node, String author,
		Map<String, String> usersMap) {

		User user = null;

		String emailAddress = usersMap.get(author);

		if (Validator.isNotNull(emailAddress)) {
			user = _userLocalService.fetchUserByEmailAddress(
				node.getCompanyId(), emailAddress);
		}
		else {
			user = _userLocalService.fetchUserByScreenName(
				node.getCompanyId(), StringUtil.toLowerCase(author));
		}

		if (user != null) {
			return user.getUserId();
		}

		return userId;
	}

	protected void importPage(
			long userId, String author, WikiNode node, String title,
			String content, String summary, Map<String, String> usersMap,
			boolean strictImportMode)
		throws PortalException {

		try {
			long authorUserId = getUserId(userId, node, author, usersMap);

			String parentTitle = readParentTitle(content);

			String redirectTitle = readRedirectTitle(content);

			if (Validator.isNotNull(redirectTitle)) {
				content = getCreoleRedirectContent(redirectTitle);
			}
			else {
				content = translateMediaWikiToCreole(content, strictImportMode);
				content = translateMediaLinks(node, content);
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAssetTagNames(
				readAssetTagNames(userId, node, content));

			WikiPage page = null;

			try {
				page = _wikiPageLocalService.getPage(node.getNodeId(), title);
			}
			catch (NoSuchPageException noSuchPageException) {
				page = _wikiPageLocalService.addPage(
					authorUserId, node.getNodeId(), title,
					WikiPageConstants.NEW, null, true, serviceContext);
			}

			_wikiPageLocalService.updatePage(
				authorUserId, node.getNodeId(), title, page.getVersion(),
				content, summary, true, FORMAT_CREOLE, parentTitle,
				redirectTitle, serviceContext);
		}
		catch (Exception exception) {
			throw new PortalException(
				"Error importing page " + title, exception);
		}
	}

	protected boolean isSpecialMediaWikiPage(
		String title, List<String> specialNamespaces) {

		for (String namespace : specialNamespaces) {
			if (title.startsWith(namespace + StringPool.COLON)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidImage(String[] paths, InputStream inputStream) {
		if (_specialMediaWikiDirs.contains(paths[0])) {
			return false;
		}

		if ((paths.length > 1) && _specialMediaWikiDirs.contains(paths[1])) {
			return false;
		}

		String fileName = paths[paths.length - 1];

		try {
			DLStoreUtil.validate(fileName, true, inputStream);
		}
		catch (PortalException | SystemException exception) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return false;
		}

		return true;
	}

	protected void moveFrontPage(
		long userId, WikiNode node, Map<String, String[]> options) {

		String frontPageTitle = MapUtil.getString(options, OPTIONS_FRONT_PAGE);

		if (Validator.isNull(frontPageTitle)) {
			return;
		}

		frontPageTitle = _wikiPageTitleValidator.normalize(frontPageTitle);

		try {
			int count = _wikiPageLocalService.getPagesCount(
				node.getNodeId(), frontPageTitle, true);

			if (count > 0) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				_wikiPageLocalService.renamePage(
					userId, node.getNodeId(), frontPageTitle,
					_wikiGroupServiceConfiguration.frontPageName(), false,
					serviceContext);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Could not move ");
				sb.append(_wikiGroupServiceConfiguration.frontPageName());
				sb.append(" to the title provided: ");
				sb.append(frontPageTitle);

				_log.warn(sb.toString(), exception);
			}
		}
	}

	protected String normalize(String categoryName, int length) {
		categoryName = _toWord(categoryName.trim());

		return StringUtil.shorten(categoryName, length);
	}

	protected void processImages(
			long userId, WikiNode node, InputStream imagesInputStream)
		throws Exception {

		if (imagesInputStream == null) {
			return;
		}

		ProgressTracker progressTracker =
			ProgressTrackerThreadLocal.getProgressTracker();

		int count = 0;

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			imagesInputStream);

		List<String> entries = zipReader.getEntries();

		if (entries == null) {
			throw new ImportFilesException();
		}

		int total = entries.size();

		if (total > 0) {
			try {
				_wikiPageLocalService.getPage(
					node.getNodeId(), SHARED_IMAGES_TITLE);
			}
			catch (NoSuchPageException noSuchPageException) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				_wikiPageLocalService.addPage(
					userId, node.getNodeId(), SHARED_IMAGES_TITLE,
					SHARED_IMAGES_CONTENT, null, true, serviceContext);
			}
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();

		try {
			int percentage = 50;

			for (int i = 0; i < entries.size(); i++) {
				String entry = entries.get(i);

				String key = entry;

				InputStream inputStream = zipReader.getEntryAsInputStream(
					entry);

				String[] paths = StringUtil.split(key, CharPool.SLASH);

				if (!isValidImage(paths, inputStream)) {
					if (_log.isInfoEnabled()) {
						_log.info("Ignoring " + key);
					}

					continue;
				}

				String fileName = StringUtil.toLowerCase(
					paths[paths.length - 1]);

				ObjectValuePair<String, InputStream> inputStreamOVP =
					new ObjectValuePair<>(fileName, inputStream);

				inputStreamOVPs.add(inputStreamOVP);

				count++;

				if ((i % 5) == 0) {
					_wikiPageLocalService.addPageAttachments(
						userId, node.getNodeId(), SHARED_IMAGES_TITLE,
						inputStreamOVPs);

					inputStreamOVPs.clear();

					percentage = Math.min(50 + (i * 50) / total, 99);

					if (progressTracker != null) {
						progressTracker.setPercent(percentage);
					}
				}
			}

			if (!inputStreamOVPs.isEmpty()) {
				_wikiPageLocalService.addPageAttachments(
					userId, node.getNodeId(), SHARED_IMAGES_TITLE,
					inputStreamOVPs);
			}
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				try (InputStream inputStream = inputStreamOVP.getValue()) {
				}
				catch (IOException ioException) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioException, ioException);
					}
				}
			}
		}

		zipReader.close();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Imported ", count, " images into ", node.getName()));
		}
	}

	protected void processRegularPages(
		long userId, WikiNode node, Element rootElement,
		List<String> specialNamespaces, Map<String, String> usersMap,
		InputStream imagesInputStream, Map<String, String[]> options) {

		boolean importLatestVersion = MapUtil.getBoolean(
			options, OPTIONS_IMPORT_LATEST_VERSION);
		boolean strictImportMode = MapUtil.getBoolean(
			options, OPTIONS_STRICT_IMPORT_MODE);

		ProgressTracker progressTracker =
			ProgressTrackerThreadLocal.getProgressTracker();

		int count = 0;

		int percentage = 10;

		int maxPercentage = 50;

		if (imagesInputStream == null) {
			maxPercentage = 99;
		}

		List<Element> pageElements = rootElement.elements("page");

		for (int i = 0; i < pageElements.size(); i++) {
			Element pageElement = pageElements.get(i);

			String title = pageElement.elementText("title");

			if (isSpecialMediaWikiPage(title, specialNamespaces)) {
				continue;
			}

			title = _wikiPageTitleValidator.normalize(title);

			percentage = Math.min(
				10 + (i * (maxPercentage - percentage)) / pageElements.size(),
				maxPercentage);

			progressTracker.setPercent(percentage);

			List<Element> revisionElements = pageElement.elements("revision");

			if (importLatestVersion) {
				Element lastRevisionElement = revisionElements.get(
					revisionElements.size() - 1);

				revisionElements = new ArrayList<>();

				revisionElements.add(lastRevisionElement);
			}

			for (Element revisionElement : revisionElements) {
				Element contributorElement = revisionElement.element(
					"contributor");

				String author = contributorElement.elementText("username");

				String content = revisionElement.elementText("text");
				String summary = revisionElement.elementText("comment");

				try {
					importPage(
						userId, author, node, title, content, summary, usersMap,
						strictImportMode);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Page with title " + title +
								" could not be imported",
							exception);
					}
				}
			}

			count++;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Imported ", count, " pages into ", node.getName()));
		}
	}

	protected void processSpecialPages(
			long userId, WikiNode node, Element rootElement,
			List<String> specialNamespaces)
		throws PortalException {

		ProgressTracker progressTracker =
			ProgressTrackerThreadLocal.getProgressTracker();

		List<Element> pageElements = rootElement.elements("page");

		for (int i = 0; i < pageElements.size(); i++) {
			Element pageElement = pageElements.get(i);

			String title = pageElement.elementText("title");

			if (!title.startsWith("Category:")) {
				if (isSpecialMediaWikiPage(title, specialNamespaces)) {
					rootElement.remove(pageElement);
				}

				continue;
			}

			String categoryName = title.substring("Category:".length());

			categoryName = normalize(categoryName, 75);

			_assetTagLocalService.checkTags(
				userId, node.getGroupId(), new String[] {categoryName});

			if ((i % 5) == 0) {
				progressTracker.setPercent((i * 10) / pageElements.size());
			}
		}
	}

	protected String[] readAssetTagNames(
			long userId, WikiNode node, String content)
		throws PortalException {

		Matcher matcher = _categoriesPattern.matcher(content);

		List<String> assetTagNames = new ArrayList<>();

		while (matcher.find()) {
			String categoryName = matcher.group(1);

			categoryName = normalize(categoryName, 75);

			List<AssetTag> assetTags = _assetTagLocalService.checkTags(
				userId, node.getGroupId(), new String[] {categoryName});

			assetTagNames.addAll(
				ListUtil.toList(assetTags, AssetTag.NAME_ACCESSOR));
		}

		if (content.contains(_WORK_IN_PROGRESS)) {
			assetTagNames.add(_WORK_IN_PROGRESS_TAG);
		}

		return assetTagNames.toArray(new String[0]);
	}

	protected String readParentTitle(String content) {
		Matcher matcher = _parentPattern.matcher(content);

		String redirectTitle = StringPool.BLANK;

		if (matcher.find()) {
			redirectTitle = matcher.group(1);

			redirectTitle = _wikiPageTitleValidator.normalize(redirectTitle);

			redirectTitle += " (disambiguation)";
		}

		return redirectTitle;
	}

	protected String readRedirectTitle(String content) {
		Matcher matcher = _redirectPattern.matcher(content);

		String redirectTitle = StringPool.BLANK;

		if (matcher.find()) {
			redirectTitle = matcher.group(1);

			redirectTitle = _wikiPageTitleValidator.normalize(redirectTitle);
		}

		return redirectTitle;
	}

	protected List<String> readSpecialNamespaces(Element root)
		throws ImportFilesException {

		List<String> namespaces = new ArrayList<>();

		Element siteinfoElement = root.element("siteinfo");

		if (siteinfoElement == null) {
			throw new ImportFilesException("Invalid pages XML file");
		}

		Element namespacesElement = siteinfoElement.element("namespaces");

		List<Element> namespaceElements = namespacesElement.elements(
			"namespace");

		for (Element namespaceElement : namespaceElements) {
			Attribute attribute = namespaceElement.attribute("key");

			String value = attribute.getValue();

			if (!value.equals("0")) {
				namespaces.add(namespaceElement.getText());
			}
		}

		return namespaces;
	}

	protected Map<String, String> readUsersFile(InputStream usersInputStream)
		throws IOException {

		if (usersInputStream == null) {
			return Collections.emptyMap();
		}

		Map<String, String> usersMap = new HashMap<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(usersInputStream));

		String line = unsyncBufferedReader.readLine();

		while (line != null) {
			String[] array = StringUtil.split(line);

			if ((array.length == 2) && Validator.isNotNull(array[0]) &&
				Validator.isNotNull(array[1])) {

				usersMap.put(array[0], array[1]);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Ignoring line " + line +
							" because it does not contain exactly 2 columns");
				}
			}

			line = unsyncBufferedReader.readLine();
		}

		return usersMap;
	}

	protected String translateMediaLinks(WikiNode node, String content) {
		try {
			StringBuffer sb = new StringBuffer();

			WikiPage sharedImagesPage = _wikiPageLocalService.getPage(
				node.getNodeId(), SHARED_IMAGES_TITLE);

			Company company = _companyLocalService.getCompany(
				node.getCompanyId());

			String portalURL = company.getPortalURL(node.getGroupId());

			Matcher matcher = _mediaLinkPattern.matcher(content);

			while (matcher.find()) {
				String fileName = matcher.group(2);

				FileEntry fileEntry =
					_portletFileRepository.fetchPortletFileEntry(
						node.getGroupId(),
						sharedImagesPage.getAttachmentsFolderId(), fileName);

				if (fileEntry == null) {
					matcher.appendReplacement(sb, matcher.group());

					continue;
				}

				String fileEntryURL =
					_portletFileRepository.getPortletFileEntryURL(
						null, fileEntry, StringPool.BLANK);

				String linkLabel = matcher.group(3);

				if (linkLabel == null) {
					linkLabel = StringPool.PIPE + fileName;
				}

				matcher.appendReplacement(
					sb,
					StringBundler.concat(
						"[[", portalURL, fileEntryURL, linkLabel, "]]"));
			}

			matcher.appendTail(sb);

			return sb.toString();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}

			return content;
		}
	}

	protected String translateMediaWikiToCreole(
		String content, boolean strictImportMode) {

		_translator.setStrictImportMode(strictImportMode);

		return _translator.translate(content);
	}

	private String _toWord(String text) {
		if (Validator.isNull(text)) {
			return text;
		}

		char[] textCharArray = text.toCharArray();

		for (int i = 0; i < textCharArray.length; i++) {
			char c = textCharArray[i];

			for (char invalidChar : AssetHelper.INVALID_CHARACTERS) {
				if (c == invalidChar) {
					textCharArray[i] = CharPool.SPACE;

					break;
				}
			}
		}

		return new String(textCharArray);
	}

	private static final String _WORK_IN_PROGRESS = "{{Work in progress}}";

	private static final String _WORK_IN_PROGRESS_TAG = "work in progress";

	private static final Log _log = LogFactoryUtil.getLog(
		MediaWikiImporter.class);

	private static final Pattern _categoriesPattern = Pattern.compile(
		"\\[\\[[Cc]ategory:([^\\]]*)\\]\\][\\n]*");
	private static final Pattern _mediaLinkPattern = Pattern.compile(
		"\\[\\[(Media:)([^\\]\\|]*)(\\|[^\\]]*)?\\]\\]", Pattern.DOTALL);
	private static final Pattern _parentPattern = Pattern.compile(
		"\\{{2}OtherTopics\\|([^\\}]*)\\}{2}");
	private static final Pattern _redirectPattern = Pattern.compile(
		"#REDIRECT \\[\\[([^\\]]*)\\]\\]");
	private static final Set<String> _specialMediaWikiDirs = SetUtil.fromArray(
		new String[] {"archive", "temp", "thumb"});

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	private final MediaWikiToCreoleTranslator _translator =
		new MediaWikiToCreoleTranslator();

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

	@Reference
	private WikiPageTitleValidator _wikiPageTitleValidator;

}