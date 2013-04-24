/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureActionableDynamicQuery;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleActionableDynamicQuery;
import com.liferay.portlet.journal.service.persistence.JournalFeedActionableDynamicQuery;
import com.liferay.portlet.journal.service.persistence.JournalFolderActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior to
 * import.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Wesley Gong
 * @author Hugo Huijser
 * @author Daniel Kocsis
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalContentPortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
public class JournalPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "journal";

	public static String importReferencedContent(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		content = importDLFileEntries(
			portletDataContext, parentElement, content);
		content = importLayoutFriendlyURLs(portletDataContext, content);
		content = importLinksToLayout(portletDataContext, content);

		return content;
	}

	public static void importReferencedData(
			PortletDataContext portletDataContext, Element entityElement)
		throws Exception {

		Element dlRepositoriesElement =
			portletDataContext.getImportDataGroupElement(Repository.class);

		List<Element> dlRepositoryElements = dlRepositoriesElement.elements();

		for (Element dlRepositoryElement : dlRepositoryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, dlRepositoryElement);
		}

		Element dlRepositoryEntriesElement =
			portletDataContext.getImportDataGroupElement(RepositoryEntry.class);

		List<Element> dlRepositoryEntryElements =
			dlRepositoryEntriesElement.elements();

		for (Element dlRepositoryEntryElement : dlRepositoryEntryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, dlRepositoryEntryElement);
		}

		Element dlFoldersElement = portletDataContext.getImportDataGroupElement(
			Folder.class);

		List<Element> dlFolderElements = dlFoldersElement.elements();

		for (Element folderElement : dlFolderElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, folderElement);
		}

		Element dlFileEntriesElement =
			portletDataContext.getImportDataGroupElement(FileEntry.class);

		List<Element> dlFileEntryElements = dlFileEntriesElement.elements();

		for (Element dlFileEntryElement : dlFileEntryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, dlFileEntryElement);
		}

		Element dlFileRanksElement =
			portletDataContext.getImportDataGroupElement(DLFileRank.class);

		List<Element> dlFileRankElements = dlFileRanksElement.elements();

		for (Element dlFileRankElement : dlFileRankElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, dlFileRankElement);
		}
	}

	public JournalPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "web-content"),
			new PortletDataHandlerBoolean(
				NAMESPACE, "structures-templates-and-feeds", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "embedded-assets"),
			new PortletDataHandlerBoolean(
				NAMESPACE, "version-history",
				PropsValues.JOURNAL_PUBLISH_VERSION_HISTORY_BY_DEFAULT));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "web-content", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "images"),
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "comments"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
		setImportControls(getExportControls()[0], getExportControls()[1]);
		setPublishToLiveByDefault(
			PropsValues.JOURNAL_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	protected static String importDLFileEntries(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		List<Element> dlReferenceElements = parentElement.elements(
			"dl-reference");

		for (Element dlReferenceElement : dlReferenceElements) {
			String dlReferencePath = dlReferenceElement.attributeValue("path");

			String fileEntryUUID = null;

			try {
				Object zipEntryObject = portletDataContext.getZipEntryAsObject(
					dlReferencePath);

				if (zipEntryObject == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to reference " + dlReferencePath);
					}

					continue;
				}

				boolean defaultRepository = GetterUtil.getBoolean(
					dlReferenceElement.attributeValue("default-repository"));

				if (defaultRepository) {
					FileEntry fileEntry = (FileEntry)zipEntryObject;

					fileEntryUUID = fileEntry.getUuid();
				}
				else {
					RepositoryEntry repositoryEntry =
						(RepositoryEntry)zipEntryObject;

					fileEntryUUID = repositoryEntry.getUuid();
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			if (fileEntryUUID == null) {
				continue;
			}

			FileEntry fileEntry = null;

			try {
				fileEntry =
					DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
						fileEntryUUID, portletDataContext.getScopeGroupId());
			}
			catch (NoSuchFileEntryException nsfee) {
				continue;
			}

			String dlReference = "[$dl-reference=" + dlReferencePath + "$]";

			String url = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			content = StringUtil.replace(content, dlReference, url);
		}

		return content;
	}

	protected static String importLayoutFriendlyURLs(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		String privateGroupServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String privateUserServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String publicServletMapping =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		String portalContextPath = PortalUtil.getPathContext();

		if (Validator.isNotNull(portalContextPath)) {
			privateGroupServletMapping = portalContextPath.concat(
				privateGroupServletMapping);
			privateUserServletMapping = portalContextPath.concat(
				privateUserServletMapping);
			publicServletMapping = portalContextPath.concat(
				publicServletMapping);
		}

		content = StringUtil.replace(
			content, "@data_handler_private_group_servlet_mapping@",
			privateGroupServletMapping);
		content = StringUtil.replace(
			content, "@data_handler_private_user_servlet_mapping@",
			privateUserServletMapping);
		content = StringUtil.replace(
			content, "@data_handler_public_servlet_mapping@",
			publicServletMapping);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		return content;
	}

	protected static String importLinksToLayout(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long oldLayoutId = GetterUtil.getLong(matcher.group(1));

			long newLayoutId = oldLayoutId;

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			String layoutUuid = matcher.group(3);

			String friendlyURL = matcher.group(4);

			try {
				Layout layout = LayoutUtil.fetchByUUID_G_P(
					layoutUuid, portletDataContext.getScopeGroupId(),
					privateLayout);

				if (layout == null) {
					layout = LayoutUtil.fetchByG_P_F(
						portletDataContext.getScopeGroupId(), privateLayout,
						friendlyURL);
				}

				if (layout == null) {
					layout = LayoutUtil.fetchByG_P_L(
						portletDataContext.getScopeGroupId(), privateLayout,
						oldLayoutId);
				}

				if (layout == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(9);

						sb.append("Unable to get layout with UUID ");
						sb.append(layoutUuid);
						sb.append(", friendly URL ");
						sb.append(friendlyURL);
						sb.append(", or ");
						sb.append("layoutId ");
						sb.append(oldLayoutId);
						sb.append(" in group ");
						sb.append(portletDataContext.getScopeGroupId());

						_log.warn(sb.toString());
					}
				}
				else {
					newLayoutId = layout.getLayoutId();
				}
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get layout in group " +
							portletDataContext.getScopeGroupId(), se);
				}
			}

			String oldLinkToLayout = matcher.group(0);

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.AT);
			sb.append(layoutUuid);
			sb.append(StringPool.AT);
			sb.append(friendlyURL);

			String newLinkToLayout = StringUtil.replace(
				oldLinkToLayout,
				new String[] {sb.toString(), String.valueOf(oldLayoutId)},
				new String[] {StringPool.BLANK, String.valueOf(newLayoutId)});

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				JournalPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		JournalArticleLocalServiceUtil.deleteArticles(
			portletDataContext.getScopeGroupId());

		DDMTemplateLocalServiceUtil.deleteTemplates(
			portletDataContext.getScopeGroupId());

		DDMStructureLocalServiceUtil.deleteStructures(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		// Structures & Templates

		ActionableDynamicQuery structureActionableDynamicQuery =
			new DDMStructureActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long classNameId = PortalUtil.getClassNameId(
					JournalArticle.class);

				dynamicQuery.add(classNameIdProperty.eq(classNameId));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				DDMStructure ddmStructure = (DDMStructure)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmStructure);

				List<DDMTemplate> ddmTemplates = Collections.emptyList();

				try {
					ddmTemplates = ddmStructure.getTemplates();
				}
				catch (SystemException se) {
				}

				for (DDMTemplate ddmTemplate : ddmTemplates) {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmTemplate);
				}
			}

		};

		structureActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		structureActionableDynamicQuery.performActions();

		// Feeds

		ActionableDynamicQuery feedActionableDynamicQuery =
			new JournalFeedActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				JournalFeed feed = (JournalFeed)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, feed);
			}

		};

		feedActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		feedActionableDynamicQuery.performActions();

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			getExportDataRootElementString(rootElement);
		}

		// Folders

		ActionableDynamicQuery folderActionableDynamicQuery =
			new JournalFolderActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				JournalFolder folder = (JournalFolder)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, folder);
			}

		};

		folderActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		folderActionableDynamicQuery.performActions();

		// Articles

		ActionableDynamicQuery articleActionableDynamicQuery =
			new JournalArticleActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new Integer[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_EXPIRED
						}));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				JournalArticle article = (JournalArticle)object;

				boolean latestVersion = false;

				try {
					latestVersion =
						JournalArticleLocalServiceUtil.isLatestVersion(
							article.getGroupId(), article.getArticleId(),
							article.getVersion(),
							WorkflowConstants.STATUS_APPROVED);
				}
				catch (Exception e) {
				}

				if (portletDataContext.getBooleanParameter(
						NAMESPACE, "version-history") || latestVersion) {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, article);
				}
			}

		};

		articleActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		articleActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element rootElement = portletDataContext.getImportDataRootElement();

		importReferencedData(portletDataContext, rootElement);

		// Structures

		Element ddmStructuresElement =
			portletDataContext.getImportDataGroupElement(DDMStructure.class);

		List<Element> ddmStructureElements = ddmStructuresElement.elements();

		for (Element ddmStructureElement : ddmStructureElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmStructureElement);
		}

		// Templates

		Element ddmTemplatesElement =
			portletDataContext.getImportDataGroupElement(DDMTemplate.class);

		List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

		for (Element ddmTemplateElement : ddmTemplateElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmTemplateElement);
		}

		// Feeds

		Element feedsElement = portletDataContext.getImportDataGroupElement(
			JournalFeed.class);

		List<Element> feedElements = feedsElement.elements();

		for (Element feedElement : feedElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, feedElement);
		}

		// Folders & Articles

		if (portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(
					JournalFolder.class);

			List<Element> folderElements = foldersElement.elements();

			for (Element folderElement : folderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}

			Element articlesElement =
				portletDataContext.getImportDataGroupElement(
					JournalArticle.class);

			List<Element> articleElements = articlesElement.elements();

			for (Element articleElement : articleElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, articleElement);
			}
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalPortletDataHandler.class);

	private static Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@(\\p{XDigit}{8}\\-" +
		"(?:\\p{XDigit}{4}\\-){3}\\p{XDigit}{12})@([^\\]]*)\\]");

}