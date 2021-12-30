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

package com.liferay.exportimport.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

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

		return _replaceExportDLReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return _replaceImportDLReferences(
			portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		if (_isValidateDLReferences()) {
			_validateDLReferences(groupId, content);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private ObjectValuePair<String, Integer>
		_getDLReferenceEndPosObjectValuePair(
			String content, int beginPos, int endPos) {

		String[] stopStrings = _DL_REFERENCE_LEGACY_STOP_STRINGS;

		if (!_isLegacyURL(content, beginPos)) {
			stopStrings = _DL_REFERENCE_STOP_STRINGS;
		}

		int urlPatternPos = StringUtil.indexOfAny(
			content, stopStrings, beginPos, endPos);

		if (urlPatternPos == -1) {
			if (endPos != content.length()) {
				return null;
			}

			urlPatternPos = endPos;
		}

		return new ObjectValuePair<>(
			content.substring(beginPos, urlPatternPos), urlPatternPos);
	}

	private Map<String, String[]> _getDLReferenceParameters(
		long groupId, String content, int beginPos, int endPos) {

		ObjectValuePair<String, Integer> dlReferenceEndPosObjectValuePair =
			_getDLReferenceEndPosObjectValuePair(content, beginPos, endPos);

		if (dlReferenceEndPosObjectValuePair == null) {
			return null;
		}

		boolean legacyURL = _isLegacyURL(content, beginPos);

		Map<String, String[]> map = new HashMap<>();

		String dlReference = dlReferenceEndPosObjectValuePair.getKey();

		endPos = dlReferenceEndPosObjectValuePair.getValue();

		while (dlReference.contains(StringPool.AMPERSAND_ENCODED)) {
			dlReference = StringUtil.replace(
				dlReference, StringPool.AMPERSAND_ENCODED,
				StringPool.AMPERSAND);
		}

		if (!legacyURL) {
			String[] pathArray = dlReference.split(StringPool.SLASH);

			if (pathArray.length < 3) {
				return map;
			}

			if (Objects.equals(pathArray[2], "portlet_file_entry")) {
				map.put("groupId", new String[] {pathArray[3]});
				map.put(
					"title",
					new String[] {
						StringUtils.substringBefore(
							_http.decodeURL(pathArray[4]), StringPool.POUND)
					});
			}
			else {
				map.put("groupId", new String[] {pathArray[2]});

				if (pathArray.length == 5) {
					map.put("folderId", new String[] {pathArray[3]});
					map.put(
						"title",
						new String[] {
							StringUtils.substringBefore(
								_http.decodeURL(pathArray[4]), StringPool.POUND)
						});
				}
			}

			String uuid = _getUuid(dlReference);

			if (Validator.isNotNull(uuid)) {
				map.put("uuid", new String[] {uuid});
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

	private FileEntry _getFileEntry(Map<String, String[]> map) {
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
						try {
							fileEntry =
								_dlAppLocalService.getFileEntryByFileName(
									groupId, folderId, title);
						}
						catch (NoSuchFileEntryException
									noSuchFileEntryException) {

							if (_log.isDebugEnabled()) {
								_log.debug(
									noSuchFileEntryException,
									noSuchFileEntryException);
							}

							fileEntry = _dlAppLocalService.getFileEntry(
								groupId, folderId, title);
						}
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(exception.getMessage());
			}
		}

		return fileEntry;
	}

	private String _getUuid(String s) {
		Matcher matcher = _uuidPattern.matcher(s);

		String uuid = StringPool.BLANK;

		while (matcher.find()) {
			uuid = matcher.group(0);
		}

		return uuid;
	}

	private boolean _isLegacyURL(String content, int beginPos) {
		if (content.startsWith("/documents/", beginPos)) {
			return false;
		}

		return true;
	}

	private boolean _isValidateDLReferences() {
		try {
			ExportImportServiceConfiguration configuration =
				_configurationProvider.getCompanyConfiguration(
					ExportImportServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return configuration.validateFileEntryReferences();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return true;
	}

	private String _replaceExportDLReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY) &&
			ExportImportThreadLocal.isStagingInProcess()) {

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
				_getDLReferenceParameters(
					portletDataContext.getScopeGroupId(), content,
					beginPos + contextPath.length(), endPos);

			FileEntry fileEntry = _getFileEntry(dlReferenceParameters);

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

				StringBundler exportedReferenceSB = new StringBundler(10);

				exportedReferenceSB.append("[$dl-reference=");
				exportedReferenceSB.append(path);
				exportedReferenceSB.append("$,$include-uuid=");
				exportedReferenceSB.append(
					dlReferenceParameters.containsKey("uuid"));
				exportedReferenceSB.append("$]");

				if (fileEntry.isInTrash()) {
					String originalReference = sb.substring(beginPos, endPos);

					exportedReferenceSB.append("[#dl-reference=");
					exportedReferenceSB.append(originalReference);
					exportedReferenceSB.append("#,#include-uuid=");
					exportedReferenceSB.append(
						dlReferenceParameters.containsKey("uuid"));
					exportedReferenceSB.append("#]");
				}

				sb.replace(beginPos, endPos, exportedReferenceSB.toString());
			}
			catch (Exception exception) {
				StringBundler exceptionSB = new StringBundler(6);

				exceptionSB.append("Unable to process file entry ");
				exceptionSB.append(fileEntry.getFileEntryId());
				exceptionSB.append(" for staged model ");
				exceptionSB.append(stagedModel.getModelClassName());
				exceptionSB.append(" with primary key ");
				exceptionSB.append(stagedModel.getPrimaryKeyObj());

				ExportImportContentProcessorException
					exportImportContentProcessorException =
						new ExportImportContentProcessorException(
							exceptionSB.toString(), exception);

				if (_log.isDebugEnabled()) {
					_log.debug(
						exceptionSB.toString(),
						exportImportContentProcessorException);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(exceptionSB.toString());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	private String _replaceImportDLReferences(
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

			if (Validator.isNull(path)) {
				long groupId = GetterUtil.getLong(
					referenceElement.attributeValue("group-id"));
				String className = referenceElement.attributeValue(
					"class-name");

				path = ExportImportPathUtil.getModelPath(
					groupId, className, classPK);
			}

			while (content.contains(
						"[$dl-reference=" + path + "$,$include-uuid=false$]") ||
				   content.contains(
					   "[$dl-reference=" + path + "$,$include-uuid=true$]")) {

				try {
					StagedModelDataHandlerUtil.importReferenceStagedModel(
						portletDataContext, stagedModel, DLFileEntry.class,
						classPK);
				}
				catch (Exception exception) {
					StringBundler exceptionSB = new StringBundler(6);

					exceptionSB.append("Unable to process file entry ");
					exceptionSB.append(classPK);
					exceptionSB.append(" for ");
					exceptionSB.append(stagedModel.getModelClassName());
					exceptionSB.append(" with primary key ");
					exceptionSB.append(stagedModel.getPrimaryKeyObj());

					ExportImportContentProcessorException
						exportImportContentProcessorException =
							new ExportImportContentProcessorException(
								exceptionSB.toString(), exception);

					if (_log.isDebugEnabled()) {
						_log.debug(
							exceptionSB.toString(),
							exportImportContentProcessorException);
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
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException, portalException);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(portalException.getMessage());
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
					else {
						throw portalException;
					}

					continue;
				}

				String url = _dlURLHelper.getPreviewURL(
					importedFileEntry, importedFileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false);

				if (url.contains(StringPool.QUESTION)) {
					url = url.substring(
						0, url.lastIndexOf(StringPool.QUESTION));
				}

				String urlWithoutUUID = url.substring(
					0, url.lastIndexOf(StringPool.SLASH));

				String exportedReferenceWithoutUUID =
					"[$dl-reference=" + path + "$,$include-uuid=false$]";
				String exportedReferenceWithUUID =
					"[$dl-reference=" + path + "$,$include-uuid=true$]";

				if (content.startsWith("[#dl-reference=", endPos)) {
					endPos = content.indexOf("#,#include-uuid=", beginPos) + 2;

					exportedReferenceWithoutUUID =
						content.substring(beginPos, endPos) +
							"#include-uuid=false#]";
					exportedReferenceWithUUID =
						content.substring(beginPos, endPos) +
							"#include-uuid=true#]";
				}

				content = StringUtil.replace(
					content, exportedReferenceWithUUID, url);
				content = StringUtil.replace(
					content, exportedReferenceWithoutUUID, urlWithoutUUID);
			}
		}

		return content;
	}

	private void _validateDLReferences(long groupId, String content)
		throws PortalException {

		String pathContext = _portal.getPathContext();

		String[] patterns = {
			pathContext.concat("/c/document_library/get_file?"),
			pathContext.concat("/documents/"),
			pathContext.concat("/image/image_gallery?")
		};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String[]> dlReferenceParameters =
				_getDLReferenceParameters(
					groupId, content, beginPos + pathContext.length(), endPos);

			if (dlReferenceParameters == null) {
				endPos = beginPos - 1;

				continue;
			}

			FileEntry fileEntry = _getFileEntry(dlReferenceParameters);

			if (fileEntry == null) {
				boolean absolutePortalURL = false;

				boolean relativePortalURL = false;

				if (content.regionMatches(
						true, beginPos - _OFFSET_HREF_ATTRIBUTE, "href=", 0,
						5) ||
					content.regionMatches(
						true, beginPos - _OFFSET_SRC_ATTRIBUTE, "src=", 0, 4)) {

					relativePortalURL = true;
				}

				if (!relativePortalURL) {
					String portalURL = pathContext;

					if (Validator.isNull(portalURL)) {
						ServiceContext serviceContext =
							ServiceContextThreadLocal.getServiceContext();

						if ((serviceContext != null) &&
							(serviceContext.getThemeDisplay() != null)) {

							portalURL = _portal.getPortalURL(
								serviceContext.getThemeDisplay());
						}
					}

					Set<String> hostNames = new HashSet<>();

					hostNames.add(portalURL);

					_companyLocalService.forEachCompany(
						company -> {
							String virtualHostname =
								company.getVirtualHostname();

							hostNames.add(
								Http.HTTP_WITH_SLASH + virtualHostname);
							hostNames.add(
								Http.HTTPS_WITH_SLASH + virtualHostname);
							hostNames.add(virtualHostname);
						});

					for (String hostName : hostNames) {
						int curBeginPos = beginPos - hostName.length();

						String substring = content.substring(
							curBeginPos, endPos);

						if (substring.startsWith(hostName) &&
							(content.regionMatches(
								true, curBeginPos - _OFFSET_HREF_ATTRIBUTE,
								"href=", 0, 5) ||
							 content.regionMatches(
								 true, curBeginPos - _OFFSET_SRC_ATTRIBUTE,
								 "src=", 0, 4))) {

							absolutePortalURL = true;
						}
					}
				}

				if (absolutePortalURL || relativePortalURL) {
					ExportImportContentValidationException
						exportImportContentValidationException =
							new ExportImportContentValidationException(
								DLReferencesExportImportContentProcessor.class.
									getName(),
								new NoSuchFileEntryException());

					exportImportContentValidationException.
						setDlReferenceParameters(dlReferenceParameters);

					ObjectValuePair<String, Integer>
						dlReferenceEndPosObjectValuePair =
							_getDLReferenceEndPosObjectValuePair(
								content, beginPos, endPos);

					exportImportContentValidationException.setDlReference(
						dlReferenceEndPosObjectValuePair.getKey());

					exportImportContentValidationException.setType(
						ExportImportContentValidationException.
							FILE_ENTRY_NOT_FOUND);

					throw exportImportContentValidationException;
				}
			}

			endPos = beginPos - 1;
		}
	}

	private static final String[] _DL_REFERENCE_LEGACY_STOP_STRINGS = {
		StringPool.APOSTROPHE, StringPool.APOSTROPHE_ENCODED,
		StringPool.BACK_SLASH + StringPool.APOSTROPHE,
		StringPool.BACK_SLASH + StringPool.QUOTE, StringPool.CLOSE_BRACKET,
		StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_PARENTHESIS,
		StringPool.GREATER_THAN, StringPool.LESS_THAN, StringPool.PIPE,
		StringPool.QUOTE, StringPool.QUOTE_ENCODED, StringPool.SPACE
	};

	private static final String[] _DL_REFERENCE_STOP_STRINGS = {
		StringPool.APOSTROPHE, StringPool.APOSTROPHE_ENCODED,
		StringPool.BACK_SLASH + StringPool.APOSTROPHE,
		StringPool.BACK_SLASH + StringPool.QUOTE, StringPool.CLOSE_BRACKET,
		StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_PARENTHESIS,
		StringPool.GREATER_THAN, StringPool.LESS_THAN, StringPool.NEW_LINE,
		StringPool.PIPE, StringPool.QUESTION, StringPool.QUOTE,
		StringPool.QUOTE_ENCODED, StringPool.SPACE
	};

	private static final int _OFFSET_HREF_ATTRIBUTE = 6;

	private static final int _OFFSET_SRC_ATTRIBUTE = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		DLReferencesExportImportContentProcessor.class);

	private static final Pattern _uuidPattern = Pattern.compile(
		"[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-" +
			"[a-fA-F0-9]{12}");

	@Reference
	private CompanyLocalService _companyLocalService;

	private ConfigurationProvider _configurationProvider;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}