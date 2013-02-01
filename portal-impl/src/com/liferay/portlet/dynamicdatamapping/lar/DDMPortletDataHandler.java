/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandler;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateUtil;
import com.liferay.portlet.journal.lar.JournalPortletDataHandler;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class DDMPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "ddm";

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

		String entityElementName = entityElement.getName();

		if (!entityElementName.equals("article")) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	public static void exportStructure(
			PortletDataContext portletDataContext, Element structuresElement,
			DDMStructure structure)
		throws Exception {

		String path = getStructurePath(portletDataContext, structure);

		exportStructure(portletDataContext, structuresElement, path, structure);
	}

	public static void exportStructure(
			PortletDataContext portletDataContext, Element structuresElement,
			String path, DDMStructure structure)
		throws Exception {

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element structureElement = structuresElement.addElement("structure");

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			structure.getCompanyId());

		if (defaultUserId == structure.getUserId()) {
			structureElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			structureElement, path, structure, NAMESPACE);
	}

	public static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			DDMTemplate template)
		throws Exception {

		String path = getTemplatePath(portletDataContext, template);

		exportTemplate(portletDataContext, templatesElement, path, template);
	}

	public static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			Element dlFileEntryTypesElement, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element dlRepositoriesElement, Element dlRepositoryEntriesElement,
			String path, DDMTemplate template)
		throws Exception {

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		// Clone this template to make sure changes to its content are never
		// persisted

		template = (DDMTemplate)template.clone();

		Element templateElement = templatesElement.addElement("template");

		if (template.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL = exportReferencedContent(
					portletDataContext, dlFileEntryTypesElement,
					dlFoldersElement, dlFileEntriesElement, dlFileRanksElement,
					dlRepositoriesElement, dlRepositoryEntriesElement,
					templateElement,
					template.getSmallImageURL().concat(StringPool.SPACE));

				template.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = getTemplateSmallImagePath(
					portletDataContext, template);

				templateElement.addAttribute(
					"small-image-path", smallImagePath);

				template.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "embedded-assets")) {

			String content = exportReferencedContent(
				portletDataContext, dlFileEntryTypesElement, dlFoldersElement,
				dlFileEntriesElement, dlFileRanksElement, dlRepositoriesElement,
				dlRepositoryEntriesElement, templateElement,
				template.getScript());

			template.setScript(content);
		}

		portletDataContext.addClassedModel(
			templateElement, path, template, NAMESPACE);
	}

	public static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			String path, DDMTemplate template)
		throws Exception {

		exportTemplate(
			portletDataContext, templatesElement, null, null, null, null, null,
			null, path, template);
	}

	public static void importStructure(
			PortletDataContext portletDataContext, Element structureElement)
		throws Exception {

		String path = structureElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDMStructure structure =
			(DDMStructure)portletDataContext.getZipEntryAsObject(
				structureElement, path);

		prepareLanguagesForImport(structure);

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structureElement, structure, NAMESPACE);

		DDMStructure importedStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			boolean preloaded = GetterUtil.getBoolean(
				structureElement.attributeValue("preloaded"));

			DDMStructure existingStructure = null;

			if (!preloaded) {
				existingStructure = DDMStructureUtil.fetchByUUID_G(
					structure.getUuid(), portletDataContext.getScopeGroupId());
			}
			else {
				existingStructure = DDMStructureUtil.fetchByG_S(
					portletDataContext.getScopeGroupId(),
					structure.getStructureKey());
			}

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				importedStructure = DDMStructureLocalServiceUtil.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					structure.getParentStructureId(),
					structure.getClassNameId(), structure.getStructureKey(),
					structure.getNameMap(), structure.getDescriptionMap(),
					structure.getXsd(), structure.getStorageType(),
					structure.getType(), serviceContext);
			}
			else {
				importedStructure =
					DDMStructureLocalServiceUtil.updateStructure(
						existingStructure.getStructureId(),
						structure.getParentStructureId(),
						structure.getNameMap(), structure.getDescriptionMap(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			importedStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(),
				structure.getParentStructureId(), structure.getClassNameId(),
				structure.getStructureKey(), structure.getNameMap(),
				structure.getDescriptionMap(), structure.getXsd(),
				structure.getStorageType(), structure.getType(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			structure, importedStructure, NAMESPACE);

		structureIds.put(
			structure.getStructureId(), importedStructure.getStructureId());
	}

	public static void importTemplate(
			PortletDataContext portletDataContext, Element templateElement)
		throws Exception {

		String path = templateElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDMTemplate template =
			(DDMTemplate)portletDataContext.getZipEntryAsObject(
				templateElement, path);

		long userId = portletDataContext.getUserId(template.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long classPK = MapUtil.getLong(
			structureIds, template.getClassPK(), template.getClassPK());

		File smallFile = null;

		if (template.isSmallImage()) {
			String smallImagePath = templateElement.attributeValue(
				"small-image-path");

			if (Validator.isNotNull(template.getSmallImageURL())) {
				String smallImageURL =
					JournalPortletDataHandler.importReferencedContent(
						portletDataContext, templateElement,
						template.getSmallImageURL());

				template.setSmallImageURL(smallImageURL);
			}
			else if (Validator.isNotNull(smallImagePath)) {
				byte[] bytes = portletDataContext.getZipEntryAsByteArray(
					smallImagePath);

				if (bytes != null) {
					smallFile = FileUtil.createTempFile(
						template.getSmallImageType());

					FileUtil.write(smallFile, bytes);
				}
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			templateElement, template, NAMESPACE);

		DDMTemplate importedTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDMTemplate existingTemplate = DDMTemplateUtil.fetchByUUID_G(
				template.getUuid(), portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				importedTemplate = addTemplate(
					userId, portletDataContext.getScopeGroupId(), template,
					classPK, smallFile, serviceContext);
			}
			else {
				importedTemplate = DDMTemplateLocalServiceUtil.updateTemplate(
					existingTemplate.getTemplateId(), template.getNameMap(),
					template.getDescriptionMap(), template.getType(),
					template.getMode(), template.getLanguage(),
					template.getScript(), template.isCacheable(),
					template.isSmallImage(), template.getSmallImageURL(),
					smallFile, serviceContext);
			}
		}
		else {
			importedTemplate = addTemplate(
				userId, portletDataContext.getScopeGroupId(), template, classPK,
				smallFile, serviceContext);
		}

		portletDataContext.importClassedModel(
			template, importedTemplate, NAMESPACE);
	}

	public DDMPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "structures", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "templates"));
	}

	protected static DDMTemplate addTemplate(
			long userId, long groupId, DDMTemplate template, long classPK,
			File smallFile, ServiceContext serviceContext)
		throws Exception {

		DDMTemplate newTemplate = null;

		try {
			return DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK,
				template.getTemplateKey(), template.getNameMap(),
				template.getDescriptionMap(), template.getType(),
				template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);
		}
		catch (TemplateDuplicateTemplateKeyException tdtke) {
			newTemplate = DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK, null,
				template.getNameMap(), template.getDescriptionMap(),
				template.getType(), template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"A template with the key " + template.getTemplateKey() +
						" already exists. The new generated key is " +
							newTemplate.getTemplateKey());
			}
		}

		return newTemplate;
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

				DLPortletDataHandler.exportFileEntry(
					portletDataContext, dlFileEntryTypesElement,
					dlFoldersElement, dlFileEntriesElement, dlFileRanksElement,
					dlRepositoriesElement, dlRepositoryEntriesElement,
					fileEntry, checkDateRange);

				Element dlReferenceElement = entityElement.addElement(
					"dl-reference");

				dlReferenceElement.addAttribute(
					"default-repository",
					String.valueOf(fileEntry.isDefaultRepository()));

				String path = null;

				if (fileEntry.isDefaultRepository()) {
					path = DLPortletDataHandler.getFileEntryPath(
						portletDataContext, fileEntry);

				}
				else {
					path = DLPortletDataHandler.getRepositoryEntryPath(
						portletDataContext, fileEntry.getFileEntryId());
				}

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

	protected static String getStructurePath(
		PortletDataContext portletDataContext, DDMStructure structure) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.DYNAMIC_DATA_MAPPING));
		sb.append("/structures/");
		sb.append(structure.getStructureId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplatePath(
		PortletDataContext portletDataContext, DDMTemplate template) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.DYNAMIC_DATA_MAPPING));
		sb.append("/templates/");
		sb.append(template.getTemplateId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplateSmallImagePath(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.DYNAMIC_DATA_MAPPING));
		sb.append("/templates/thumbnail-");
		sb.append(template.getTemplateId());
		sb.append(StringPool.PERIOD);
		sb.append(template.getSmallImageType());

		return sb.toString();
	}

	protected static void prepareLanguagesForImport(DDMStructure structure)
		throws PortalException {

		Locale structureDefaultLocale = LocaleUtil.fromLanguageId(
			structure.getDefaultLanguageId());

		Locale[] structureAvailableLocales = LocaleUtil.fromLanguageIds(
			structure.getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			DDMStructure.class.getName(), structure.getPrimaryKey(),
			structureDefaultLocale, structureAvailableLocales);

		structure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				DDMPortletDataHandler.class, "deleteData")) {

			DDMTemplateLocalServiceUtil.deleteTemplates(
				portletDataContext.getScopeGroupId());

			DDMStructureLocalServiceUtil.deleteStructures(
				portletDataContext.getScopeGroupId());
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("ddm-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element structuresElement = rootElement.addElement("structures");

		List<DDMStructure> ddmStructures = DDMStructureUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (DDMStructure structure : ddmStructures) {
			if (portletDataContext.isWithinDateRange(
					structure.getModifiedDate())) {

				exportStructure(
					portletDataContext, structuresElement, structure);
			}
		}

		Element templatesElement = rootElement.addElement("templates");

		if (portletDataContext.getBooleanParameter(NAMESPACE, "templates")) {
			List<DDMTemplate> templates = DDMTemplateUtil.findByG_C(
				portletDataContext.getScopeGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class));

			for (DDMTemplate template : templates) {
				if (portletDataContext.isWithinDateRange(
						template.getModifiedDate())) {

					exportTemplate(
						portletDataContext, templatesElement, template);
				}
			}
		}

		return document.formattedString();
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

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element structuresElement = rootElement.element("structures");

		List<Element> structureElements = structuresElement.elements(
			"structure");

		for (Element structureElement : structureElements) {
			importStructure(portletDataContext, structureElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "templates")) {
			Element templatesElement = rootElement.element("templates");

			List<Element> templateElements = templatesElement.elements(
				"template");

			for (Element templateElement : templateElements) {
				importTemplate(portletDataContext, templateElement);
			}
		}

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMPortletDataHandler.class);

	private static Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");

}