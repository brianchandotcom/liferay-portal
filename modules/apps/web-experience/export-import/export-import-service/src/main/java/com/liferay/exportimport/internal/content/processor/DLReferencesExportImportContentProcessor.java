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

package com.liferay.exportimport.internal.content.processor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, property = "content.processor.type=DLReferences",
	service = ExportImportContentProcessor.class
)
public class DLReferencesExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return replaceExportDLReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return replaceImportDLReferences(
			portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		validateDLReferences(groupId, content);
	}

	protected void deleteTimestampParameters(StringBuilder sb, int beginPos) {
		beginPos = sb.indexOf(StringPool.CLOSE_BRACKET, beginPos);

		if ((beginPos == -1) || (beginPos == (sb.length() - 1)) ||
			(sb.charAt(beginPos + 1) != CharPool.QUESTION)) {

			return;
		}

		int endPos = StringUtil.indexOfAny(
			sb.toString(), _DL_REFERENCE_LEGACY_STOP_STRINGS, beginPos + 2);

		if (endPos == -1) {
			return;
		}

		String urlParams = sb.substring(beginPos + 1, endPos);

		urlParams = _http.removeParameter(urlParams, "t");

		sb.replace(beginPos + 1, endPos, urlParams);
	}

	protected Map<String, String[]> getDLReferenceParameters(
		long groupId, String content, int beginPos, int endPos) {

		boolean legacyURL = true;
		String[] stopStrings = _DL_REFERENCE_LEGACY_STOP_STRINGS;

		if (content.startsWith("/documents/", beginPos)) {
			legacyURL = false;
			stopStrings = _DL_REFERENCE_STOP_STRINGS;
		}

		endPos = StringUtil.indexOfAny(content, stopStrings, beginPos, endPos);

		if (endPos == -1) {
			return null;
		}

		Map<String, String[]> map = new HashMap<>();

		String dlReference = content.substring(beginPos, endPos);

		while (dlReference.contains(StringPool.AMPERSAND_ENCODED)) {
			dlReference = dlReference.replace(
				StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		if (!legacyURL) {
			String[] pathArray = dlReference.split(StringPool.SLASH);

			if (pathArray.length < 3) {
				return map;
			}

			if ("portlet_file_entry".equals(pathArray[2])) {
				map.put("groupId", new String[] {pathArray[3]});
				map.put("title", new String[] {_http.decodeURL(pathArray[4])});
				map.put("uuid", new String[] {pathArray[5]});
			}
			else {
				map.put("groupId", new String[] {pathArray[2]});

				if (pathArray.length == 4) {
					map.put("uuid", new String[] {pathArray[3]});
				}
				else if (pathArray.length == 5) {
					map.put("folderId", new String[] {pathArray[3]});
					map.put(
						"title", new String[] {_http.decodeURL(pathArray[4])});
				}
				else if (pathArray.length > 5) {
					map.put("uuid", new String[] {pathArray[5]});
				}
			}
		}
		else {
			dlReference = dlReference.substring(
				dlReference.indexOf(CharPool.QUESTION) + 1);

			map = _http.parameterMapFromString(dlReference);

			String[] imageIds = null;

			if (map.containsKey("img_id")) {
				imageIds = map.get("img_id");
			}
			else if (map.containsKey("i_id")) {
				imageIds = map.get("i_id");
			}

			imageIds = ArrayUtil.filter(imageIds, Validator::isNotNull);

			if (ArrayUtil.isNotEmpty(imageIds)) {
				map.put("image_id", imageIds);
			}
		}

		map.put("endPos", new String[] {String.valueOf(endPos)});

		String groupIdString = MapUtil.getString(map, "groupId");

		if (groupIdString.equals("@group_id@")) {
			groupIdString = String.valueOf(groupId);

			map.put("groupId", new String[] {groupIdString});
		}

		return map;
	}

	protected FileEntry getFileEntry(Map<String, String[]> map) {
		if (MapUtil.isEmpty(map)) {
			return null;
		}

		FileEntry fileEntry = null;

		try {
			String uuid = MapUtil.getString(map, "uuid");
			long groupId = MapUtil.getLong(map, "groupId");

			if (Validator.isNotNull(uuid)) {
				fileEntry = _dlAppLocalService.getFileEntryByUuidAndGroupId(
					uuid, groupId);
			}
			else {
				if (map.containsKey("folderId")) {
					long folderId = MapUtil.getLong(map, "folderId");
					String name = MapUtil.getString(map, "name");
					String title = MapUtil.getString(map, "title");

					if (Validator.isNotNull(title)) {
						fileEntry = _dlAppLocalService.getFileEntry(
							groupId, folderId, title);
					}
					else {
						DLFileEntry dlFileEntry =
							_dlFileEntryLocalService.fetchFileEntryByName(
								groupId, folderId, name);

						if (dlFileEntry != null) {
							fileEntry = _dlAppLocalService.getFileEntry(
								dlFileEntry.getFileEntryId());
						}
					}
				}
				else if (map.containsKey("image_id")) {
					DLFileEntry dlFileEntry =
						_dlFileEntryLocalService.fetchFileEntryByAnyImageId(
							MapUtil.getLong(map, "image_id"));

					if (dlFileEntry != null) {
						fileEntry = _dlAppLocalService.getFileEntry(
							dlFileEntry.getFileEntryId());
					}
				}
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

		return fileEntry;
	}

	protected String replaceExportDLReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String contextPath = _portal.getPathContext();

		String[] patterns = {
			contextPath.concat("/c/document_library/get_file?"),
			contextPath.concat("/documents/"),
			contextPath.concat("/image/image_gallery?")
		};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String[]> dlReferenceParameters =
				getDLReferenceParameters(
					portletDataContext.getScopeGroupId(), content,
					beginPos + contextPath.length(), endPos);

			FileEntry fileEntry = getFileEntry(dlReferenceParameters);

			if (fileEntry == null) {
				endPos = beginPos - 1;

				continue;
			}

			endPos = MapUtil.getInteger(dlReferenceParameters, "endPos");

			try {
				if (exportReferencedContent && !fileEntry.isInTrash()) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, stagedModel, fileEntry,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				else {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					String referenceType =
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY;

					if (fileEntry.isInTrash()) {
						referenceType =
							PortletDataContext.
								REFERENCE_TYPE_DEPENDENCY_DISPOSABLE;
					}

					portletDataContext.addReferenceElement(
						stagedModel, entityElement, fileEntry, referenceType,
						true);
				}

				String path = ExportImportPathUtil.getModelPath(fileEntry);

				StringBundler exportedReferenceSB = new StringBundler(6);

				exportedReferenceSB.append("[$dl-reference=");
				exportedReferenceSB.append(path);
				exportedReferenceSB.append("$]");

				if (fileEntry.isInTrash()) {
					String originalReference = sb.substring(beginPos, endPos);

					exportedReferenceSB.append("[#dl-reference=");
					exportedReferenceSB.append(originalReference);
					exportedReferenceSB.append("#]");
				}

				sb.replace(beginPos, endPos, exportedReferenceSB.toString());

				int deleteTimestampParametersOffset = beginPos;

				if (fileEntry.isInTrash()) {
					deleteTimestampParametersOffset = sb.indexOf(
						"[#dl-reference=", beginPos);
				}

				deleteTimestampParameters(sb, deleteTimestampParametersOffset);
			}
			catch (Exception e) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process file entry ");
				exceptionSB.append(fileEntry.getFileEntryId());
				exceptionSB.append(" for staged model ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException eicpe =
					new ExportImportContentProcessorException(
						exceptionSB.toString(), e);

				if (_log.isDebugEnabled()) {
					_log.debug(exceptionSB.toString(), eicpe);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	protected String replaceImportDLReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				stagedModel, DLFileEntry.class);

		for (Element referenceElement : referenceElements) {
			Long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Element referenceDataElement =
				portletDataContext.getReferenceDataElement(
					stagedModel, DLFileEntry.class, classPK);

			String path = null;

			if (referenceDataElement != null) {
				path = referenceDataElement.attributeValue("path");
			}

			long groupId = GetterUtil.getLong(
				referenceElement.attributeValue("group-id"));

			if (Validator.isNull(path)) {
				String className = referenceElement.attributeValue(
					"class-name");

				path = ExportImportPathUtil.getModelPath(
					groupId, className, classPK);
			}

			if (!content.contains("[$dl-reference=" + path + "$]")) {
				continue;
			}

			try {
				StagedModelDataHandlerUtil.importReferenceStagedModel(
					portletDataContext, stagedModel, DLFileEntry.class,
					classPK);
			}
			catch (Exception e) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process file entry ");
				exceptionSB.append(classPK);
				exceptionSB.append(" for ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException eicpe =
					new ExportImportContentProcessorException(
						exceptionSB.toString(), e);

				if (_log.isDebugEnabled()) {
					_log.debug(exceptionSB.toString(), eicpe);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}

			Map<Long, Long> dlFileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntry.class);

			long fileEntryId = MapUtil.getLong(
				dlFileEntryIds, classPK, classPK);

			int beginPos = content.indexOf("[$dl-reference=" + path);

			int endPos = content.indexOf("$]", beginPos) + 2;

			FileEntry importedFileEntry = null;

			try {
				importedFileEntry = _dlAppLocalService.getFileEntry(
					fileEntryId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(pe.getMessage());
				}

				if (content.startsWith("[#dl-reference=", endPos)) {
					int prefixPos = endPos + "[#dl-reference=".length();

					int postfixPos = content.indexOf("#]", prefixPos);

					String originalReference = content.substring(
						prefixPos, postfixPos);

					String exportedReference = content.substring(
						beginPos, postfixPos + 2);

					content = StringUtil.replace(
						content, exportedReference, originalReference);
				}

				continue;
			}

			String url = DLUtil.getPreviewURL(
				importedFileEntry, importedFileEntry.getFileVersion(), null,
				StringPool.BLANK, false, false);

			if (url.contains(StringPool.QUESTION)) {
				content = StringUtil.replace(content, "$]?", "$]&");
			}

			String exportedReference = "[$dl-reference=" + path + "$]";

			if (content.startsWith("[#dl-reference=", endPos)) {
				endPos = content.indexOf("#]", beginPos) + 2;

				exportedReference = content.substring(beginPos, endPos);
			}

			content = StringUtil.replace(content, exportedReference, url);
		}

		return content;
	}

	protected void validateDLReferences(long groupId, String content)
		throws PortalException {

		String portalURL = _portal.getPathContext();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext != null) &&
			(serviceContext.getThemeDisplay() != null)) {

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			portalURL =
				_portal.getPortalURL(themeDisplay) + _portal.getPathContext();
		}

		String[] patterns = {
			portalURL.concat("/c/document_library/get_file?"),
			portalURL.concat("/documents/"),
			portalURL.concat("/image/image_gallery?")
		};

		String[] completePatterns = new String[patterns.length];

		long[] companyIds = _portal.getCompanyIds();

		for (long companyId : companyIds) {
			Company company = _companyLocalService.getCompany(companyId);

			String webId = company.getWebId();

			int i = 0;

			for (String pattern : patterns) {
				completePatterns[i] = webId.concat(pattern);

				i++;
			}

			int beginPos = -1;
			int endPos = content.length();

			while (true) {
				beginPos = StringUtil.lastIndexOfAny(
					content, completePatterns, endPos);

				if (beginPos == -1) {
					break;
				}

				Map<String, String[]> dlReferenceParameters =
					getDLReferenceParameters(
						groupId, content,
						beginPos + portalURL.length() + webId.length(), endPos);

				FileEntry fileEntry = getFileEntry(dlReferenceParameters);

				if (fileEntry == null) {
					ExportImportContentValidationException eicve =
						new ExportImportContentValidationException(
							DLReferencesExportImportContentProcessor.class.
								getName());

					eicve.setDlReferenceParameters(dlReferenceParameters);
					eicve.setType(
						ExportImportContentValidationException.
							FILE_ENTRY_NOT_FOUND);

					throw eicve;
				}

				endPos = beginPos - 1;
			}
		}
	}

	private static final String[] _DL_REFERENCE_LEGACY_STOP_STRINGS = {
		StringPool.APOSTROPHE, StringPool.APOSTROPHE_ENCODED,
		StringPool.CLOSE_BRACKET, StringPool.CLOSE_CURLY_BRACE,
		StringPool.CLOSE_PARENTHESIS, StringPool.GREATER_THAN,
		StringPool.LESS_THAN, StringPool.PIPE, StringPool.QUOTE,
		StringPool.QUOTE_ENCODED, StringPool.SPACE
	};

	private static final String[] _DL_REFERENCE_STOP_STRINGS = {
		StringPool.APOSTROPHE, StringPool.APOSTROPHE_ENCODED,
		StringPool.CLOSE_BRACKET, StringPool.CLOSE_CURLY_BRACE,
		StringPool.CLOSE_PARENTHESIS, StringPool.GREATER_THAN,
		StringPool.LESS_THAN, StringPool.PIPE, StringPool.QUESTION,
		StringPool.QUOTE, StringPool.QUOTE_ENCODED, StringPool.SPACE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DLReferencesExportImportContentProcessor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}