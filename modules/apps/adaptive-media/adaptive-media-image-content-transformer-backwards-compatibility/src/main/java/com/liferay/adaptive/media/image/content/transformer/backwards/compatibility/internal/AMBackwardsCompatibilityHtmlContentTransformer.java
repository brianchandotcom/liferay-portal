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

package com.liferay.adaptive.media.image.content.transformer.backwards.compatibility.internal;

import com.liferay.adaptive.media.content.transformer.BaseRegexStringContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.friendly.url.resolver.FileEntryFriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = "content.transformer.content.type=html",
	service = ContentTransformer.class
)
public class AMBackwardsCompatibilityHtmlContentTransformer
	extends BaseRegexStringContentTransformer {

	@Override
	public ContentTransformerContentType<String>
		getContentTransformerContentType() {

		return ContentTransformerContentTypes.HTML;
	}

	@Override
	public String transform(String html) throws PortalException {
		if (html == null) {
			return null;
		}

		if (!html.contains("<img") || !html.contains("/documents/")) {
			return html;
		}

		Document document = _parseDocument(html);

		for (Element imgElement : document.select("img:not(picture > img)")) {
			String imgElementString = imgElement.toString();

			String replacement = _transform(
				imgElementString, imgElement.attr("src"));

			imgElement.replaceWith(_parseNode(replacement));
		}

		Element body = document.body();

		return body.html();
	}

	@Override
	protected FileEntry getFileEntry(Matcher matcher) throws PortalException {
		if (Objects.equals(
				FriendlyURLResolverConstants.URL_SEPARATOR_Y_FILE_ENTRY,
				matcher.group(7))) {

			Optional<FileEntry> fileEntryOptional = _resolveFileEntry(
				matcher.group(9), matcher.group(8));

			return fileEntryOptional.orElseThrow(
				() -> new PortalException(
					"No file entry found for friendly URL " +
						matcher.group(0)));
		}

		if (matcher.group(5) != null) {
			long groupId = Long.valueOf(matcher.group(2));

			String uuid = matcher.group(5);

			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				uuid, groupId);
		}

		long groupId = Long.valueOf(matcher.group(2));
		long folderId = Long.valueOf(matcher.group(3));
		String title = matcher.group(4);

		try {
			return _dlAppLocalService.getFileEntry(groupId, folderId, title);
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException);
			}

			return _dlAppLocalService.getFileEntryByFileName(
				groupId, folderId, title);
		}
	}

	@Override
	protected Pattern getPattern() {
		return _pattern;
	}

	@Override
	protected String getReplacement(String originalImgTag, FileEntry fileEntry)
		throws PortalException {

		if (fileEntry == null) {
			return originalImgTag;
		}

		return _amImageHTMLTagFactory.create(originalImgTag, fileEntry);
	}

	private Group _getGroup(long companyId, String name)
		throws PortalException {

		Group group = _groupLocalService.fetchFriendlyURLGroup(
			companyId, StringPool.SLASH + name);

		if (group != null) {
			return group;
		}

		User user = _userLocalService.getUserByScreenName(companyId, name);

		return user.getGroup();
	}

	private Document _parseDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		document.outputSettings(outputSettings);

		return document;
	}

	private Node _parseNode(String tag) {
		Document document = _parseDocument(tag);

		Node bodyNode = document.body();

		return bodyNode.childNode(0);
	}

	private Optional<FileEntry> _resolveFileEntry(
			String friendlyURL, String groupName)
		throws PortalException {

		if (_fileEntryFriendlyURLResolver == null) {
			return Optional.empty();
		}

		Group group = _getGroup(CompanyThreadLocal.getCompanyId(), groupName);

		return _fileEntryFriendlyURLResolver.resolveFriendlyURL(
			group.getGroupId(), friendlyURL);
	}

	private String _transform(String imgElementString, String src)
		throws PortalException {

		String replacement = imgElementString;

		StringBuffer sb = null;

		Pattern pattern = getPattern();

		Matcher matcher = pattern.matcher(src);

		while (matcher.find()) {
			if (sb == null) {
				sb = new StringBuffer(imgElementString.length());
			}

			FileEntry fileEntry = null;

			if (!imgElementString.contains(
					AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID)) {

				fileEntry = getFileEntry(matcher);
			}

			replacement = getReplacement(imgElementString, fileEntry);

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(replacement));
		}

		if (sb != null) {
			matcher.appendTail(sb);

			replacement = sb.toString();
		}

		return replacement;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMBackwardsCompatibilityHtmlContentTransformer.class);

	private static volatile FileEntryFriendlyURLResolver
		_fileEntryFriendlyURLResolver =
			ServiceProxyFactory.newServiceTrackedInstance(
				FileEntryFriendlyURLResolver.class,
				AMBackwardsCompatibilityHtmlContentTransformer.class,
				"_fileEntryFriendlyURLResolver", false, true);
	private static final Pattern _pattern = Pattern.compile(
		"((?:/?[^\\s]*)/documents/(\\d+)/(\\d+)/([^/?]+)(?:/([-0-9a-fA-F]+))?" +
			"(?:\\?t=\\d+)?)|((?:/?[^\\s]*)/documents/(d)/(.*)/" +
				"([_A-Za-z0-9-]+)?)");

	@Reference
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}