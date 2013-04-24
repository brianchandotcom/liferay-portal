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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureActionableDynamicQuery;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateActionableDynamicQuery;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class DDMPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "dynamic_data_mapping";

	public static String exportReferencedContent(
			PortletDataContext portletDataContext,
			Element dlFileEntryTypesElement, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element dlRepositoriesElement, Element dlRepositoryEntriesElement,
			Element entityElement, String content)
		throws Exception {

		content = exportDLFileEntries(
			portletDataContext, dlFileEntryTypesElement, dlFoldersElement,
			dlFileEntriesElement, dlFileRanksElement, dlRepositoriesElement,
			dlRepositoryEntriesElement, entityElement, content, false);
		content = exportLayoutFriendlyURLs(portletDataContext, content);
		content = exportLinksToLayout(portletDataContext, content);

		Element groupElement = entityElement.getParent();
		String groupElementName = groupElement.getName();

		if (!groupElementName.equals(JournalArticle.class.getSimpleName())) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	public DDMPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "structures", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "templates"));
	}

	protected static String exportDLFileEntries(
			PortletDataContext portletDataContext,
			Element dlFileEntryTypesElement, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element dlRepositoriesElement, Element dlRepositoryEntriesElement,
			Element entityElement, String content, boolean checkDateRange)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		int beginPos = content.length();
		int currentLocation = -1;

		boolean legacyURL = true;

		while (true) {
			String contextPath = PortalUtil.getPathContext();

			currentLocation = content.lastIndexOf(
				contextPath.concat("/c/document_library/get_file?"), beginPos);

			if (currentLocation == -1) {
				currentLocation = content.lastIndexOf(
					contextPath.concat("/image/image_gallery?"), beginPos);
			}

			if (currentLocation == -1) {
				currentLocation = content.lastIndexOf(
					contextPath.concat("/documents/"), beginPos);

				legacyURL = false;
			}

			if (currentLocation == -1) {
				return sb.toString();
			}

			beginPos = currentLocation + contextPath.length();

			int endPos1 = content.indexOf(CharPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(CharPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(CharPool.CLOSE_CURLY_BRACE, beginPos);
			int endPos4 = content.indexOf(CharPool.CLOSE_PARENTHESIS, beginPos);
			int endPos5 = content.indexOf(CharPool.LESS_THAN, beginPos);
			int endPos6 = content.indexOf(CharPool.QUESTION, beginPos);
			int endPos7 = content.indexOf(CharPool.QUOTE, beginPos);
			int endPos8 = content.indexOf(CharPool.SPACE, beginPos);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) ||
				((endPos6 != -1) && (endPos6 < endPos) && !legacyURL)) {

				endPos = endPos6;
			}

			if ((endPos == -1) || ((endPos7 != -1) && (endPos7 < endPos))) {
				endPos = endPos7;
			}

			if ((endPos == -1) || ((endPos8 != -1) && (endPos8 < endPos))) {
				endPos = endPos8;
			}

			if ((beginPos == -1) || (endPos == -1)) {
				break;
			}

			try {
				String oldParameters = content.substring(beginPos, endPos);

				while (oldParameters.contains(StringPool.AMPERSAND_ENCODED)) {
					oldParameters = oldParameters.replace(
						StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
				}

				Map<String, String[]> map = new HashMap<String, String[]>();

				if (oldParameters.startsWith("/documents/")) {
					String[] pathArray = oldParameters.split(StringPool.SLASH);

					map.put("groupId", new String[] {pathArray[2]});

					if (pathArray.length == 4) {
						map.put("uuid", new String[] {pathArray[3]});
					}
					else if (pathArray.length == 5) {
						map.put("folderId", new String[] {pathArray[3]});

						String title = HttpUtil.decodeURL(pathArray[4]);

						int pos = title.indexOf(StringPool.QUESTION);

						if (pos != -1) {
							title = title.substring(0, pos);
						}

						map.put("title", new String[] {title});
					}
					else if (pathArray.length > 5) {
						String uuid = pathArray[5];

						int pos = uuid.indexOf(StringPool.QUESTION);

						if (pos != -1) {
							uuid = uuid.substring(0, pos);
						}

						map.put("uuid", new String[] {uuid});
					}
				}
				else {
					oldParameters = oldParameters.substring(
						oldParameters.indexOf(CharPool.QUESTION) + 1);

					map = HttpUtil.parameterMapFromString(oldParameters);
				}

				FileEntry fileEntry = null;

				String uuid = MapUtil.getString(map, "uuid");

				if (Validator.isNotNull(uuid)) {
					String groupIdString = MapUtil.getString(map, "groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = portletDataContext.getScopeGroupId();
					}

					fileEntry =
						DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
							uuid, groupId);
				}
				else {
					String folderIdString = MapUtil.getString(map, "folderId");

					if (Validator.isNotNull(folderIdString)) {
						long folderId = GetterUtil.getLong(folderIdString);
						String name = MapUtil.getString(map, "name");
						String title = MapUtil.getString(map, "title");

						String groupIdString = MapUtil.getString(
							map, "groupId");

						long groupId = GetterUtil.getLong(groupIdString);

						if (groupIdString.equals("@group_id@")) {
							groupId = portletDataContext.getScopeGroupId();
						}

						if (Validator.isNotNull(title)) {
							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								groupId, folderId, title);
						}
						else {
							DLFileEntry dlFileEntry =
								DLFileEntryLocalServiceUtil.getFileEntryByName(
									groupId, folderId, name);

							fileEntry = new LiferayFileEntry(dlFileEntry);
						}
					}
					else if (map.containsKey("image_id") ||
							 map.containsKey("img_id") ||
							 map.containsKey("i_id")) {

						long imageId = MapUtil.getLong(map, "image_id");

						if (imageId <= 0) {
							imageId = MapUtil.getLong(map, "img_id");

							if (imageId <= 0) {
								imageId = MapUtil.getLong(map, "i_id");
							}
						}

						DLFileEntry dlFileEntry =
							DLFileEntryLocalServiceUtil.
								fetchFileEntryByAnyImageId(imageId);

						if (dlFileEntry != null) {
							fileEntry = new LiferayFileEntry(dlFileEntry);
						}
					}
				}

				if (fileEntry == null) {
					beginPos--;

					continue;
				}

				beginPos = currentLocation;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileEntry);

				Element dlReferenceElement = entityElement.addElement(
					"dl-reference");

				dlReferenceElement.addAttribute(
					"default-repository",
					String.valueOf(fileEntry.isDefaultRepository()));

				String path = ExportImportPathUtil.getModelPath(
					fileEntry.getGroupId(), FileEntry.class.getName(),
					fileEntry.getFileEntryId());

				dlReferenceElement.addAttribute("path", path);

				String dlReference = "[$dl-reference=" + path + "$]";

				sb.replace(beginPos, endPos, dlReference);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			beginPos--;
		}

		return sb.toString();
	}

	protected static String exportLayoutFriendlyURLs(
		PortletDataContext portletDataContext, String content) {

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(
				portletDataContext.getScopeGroupId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

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

		String href = "href=";

		int beginPos = content.length();

		while (true) {
			int hrefLength = href.length();

			beginPos = content.lastIndexOf(href, beginPos);

			if (beginPos == -1) {
				break;
			}

			char c = content.charAt(beginPos + hrefLength);

			if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
				hrefLength++;
			}

			int endPos1 = content.indexOf(
				CharPool.APOSTROPHE, beginPos + hrefLength);
			int endPos2 = content.indexOf(
				CharPool.CLOSE_BRACKET, beginPos + hrefLength);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_CURLY_BRACE, beginPos + hrefLength);
			int endPos4 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos + hrefLength);
			int endPos5 = content.indexOf(
				CharPool.LESS_THAN, beginPos + hrefLength);
			int endPos6 = content.indexOf(
				CharPool.QUESTION, beginPos + hrefLength);
			int endPos7 = content.indexOf(
				CharPool.QUOTE, beginPos + hrefLength);
			int endPos8 = content.indexOf(
				CharPool.SPACE, beginPos + hrefLength);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if ((endPos == -1) || ((endPos7 != -1) && (endPos7 < endPos))) {
				endPos = endPos7;
			}

			if ((endPos == -1) || ((endPos8 != -1) && (endPos8 < endPos))) {
				endPos = endPos8;
			}

			if (endPos == -1) {
				beginPos--;

				continue;
			}

			String url = content.substring(beginPos + hrefLength, endPos);

			if (!url.startsWith(privateGroupServletMapping) &&
				!url.startsWith(privateUserServletMapping) &&
				!url.startsWith(publicServletMapping)) {

				beginPos--;

				continue;
			}

			int contextLength = 0;

			if (Validator.isNotNull(portalContextPath)) {
				contextLength = portalContextPath.length();
			}

			int beginGroupPos = content.indexOf(
				CharPool.SLASH, beginPos + hrefLength + contextLength + 1);

			if (beginGroupPos == -1) {
				beginPos--;

				continue;
			}

			int endGroupPos = content.indexOf(
				CharPool.SLASH, beginGroupPos + 1);

			if (endGroupPos == -1) {
				beginPos--;

				continue;
			}

			String groupFriendlyURL = content.substring(
				beginGroupPos, endGroupPos);

			if (groupFriendlyURL.equals(group.getFriendlyURL())) {
				sb.replace(
					beginGroupPos, endGroupPos,
					"@data_handler_group_friendly_url@");
			}

			String dataHandlerServletMapping = StringPool.BLANK;

			if (url.startsWith(privateGroupServletMapping)) {
				dataHandlerServletMapping =
					"@data_handler_private_group_servlet_mapping@";
			}
			else if (url.startsWith(privateUserServletMapping)) {
				dataHandlerServletMapping =
					"@data_handler_private_user_servlet_mapping@";
			}
			else {
				dataHandlerServletMapping =
					"@data_handler_public_servlet_mapping@";
			}

			sb.replace(
				beginPos + hrefLength, beginGroupPos,
				dataHandlerServletMapping);

			beginPos--;
		}

		return sb.toString();
	}

	protected static String exportLinksToLayout(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _exportLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long layoutId = GetterUtil.getLong(matcher.group(1));

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					portletDataContext.getScopeGroupId(), privateLayout,
					layoutId);

				String oldLinkToLayout = matcher.group(0);

				StringBundler sb = new StringBundler(5);

				sb.append(type);
				sb.append(StringPool.AT);
				sb.append(layout.getUuid());
				sb.append(StringPool.AT);
				sb.append(layout.getFriendlyURL());

				String newLinkToLayout = StringUtil.replace(
					oldLinkToLayout, type, sb.toString());

				oldLinksToLayout.add(oldLinkToLayout);
				newLinksToLayout.add(newLinkToLayout);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled() || _log.isWarnEnabled()) {
					String message =
						"Unable to get layout with ID " + layoutId +
							" in group " + portletDataContext.getScopeGroupId();

					if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
					else {
						_log.debug(message, e);
					}
				}
			}
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
				DDMPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

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
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery structureActionableDynamicQuery =
			new DDMStructureActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				DDMStructure structure = (DDMStructure)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, structure);
			}

		};

		structureActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		structureActionableDynamicQuery.performActions();

		ActionableDynamicQuery templateActionableDynamicQuery =
			new DDMTemplateActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				DDMTemplate template = (DDMTemplate)object;

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, template);
			}

		};

		templateActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		templateActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element structuresElement =
			portletDataContext.getImportDataGroupElement(DDMStructure.class);

		List<Element> structureElements = structuresElement.elements();

		for (Element structureElement : structureElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, structureElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "templates")) {
			Element templatesElement =
				portletDataContext.getImportDataGroupElement(DDMTemplate.class);

			List<Element> templateElements = templatesElement.elements();

			for (Element templateElement : templateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, templateElement);
			}
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMPortletDataHandler.class);

	private static Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");

}