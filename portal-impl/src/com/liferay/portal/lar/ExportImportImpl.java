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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImport;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
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
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zsolt Berentey
 */
public class ExportImportImpl implements ExportImport {

	public String exportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		content = ExportImportUtil.exportLayoutReferences(
			portletDataContext, content);
		content = ExportImportUtil.exportLinksToLayouts(
			portletDataContext, content);

		content = ExportImportUtil.exportDLReferences(
			portletDataContext, entityElement, content);

		Element groupElement = entityElement.getParent();

		String groupElementName = groupElement.getName();

		if (!groupElementName.equals(JournalArticle.class.getSimpleName())) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	public String exportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
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

		String contextPath = PortalUtil.getPathContext();

		String[] dlRefs = new String[] {
			contextPath.concat("/c/document_library/get_file?"),
			contextPath.concat("/documents/"),
			contextPath.concat("/image/image_gallery?")
		};

		int endPos = content.length();
		int beginPos = -1;

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, dlRefs, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String[]> refParameters = getDLReferenceParameters(
				portletDataContext, content, beginPos + contextPath.length(),
				endPos);

			FileEntry fileEntry = getFileEntry(
				portletDataContext, refParameters);

			if (fileEntry == null) {
				endPos = beginPos - 1;

				continue;
			}

			endPos = MapUtil.getInteger(refParameters, "endPos");

			try {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileEntry);

				portletDataContext.addReferenceElement(
					entityElement, fileEntry, FileEntry.class);

				String path = ExportImportPathUtil.getModelPath(
					fileEntry.getGroupId(), FileEntry.class.getName(),
					fileEntry.getFileEntryId());

				sb.replace(beginPos, endPos, "[$dl-reference=" + path + "$]");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	public String exportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		StringBuilder sb = new StringBuilder(content);

		String[] patterns = new String[] {"href=", "[["};

		int beginPos = -1;
		int endPos = content.length();
		int offset = 0;

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			if (content.startsWith("href=", beginPos)) {
				offset = 5;

				char c = content.charAt(beginPos + offset);

				if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
					offset++;
				}
			}
			else if (content.charAt(beginPos) == CharPool.OPEN_BRACKET) {
				offset = 2;
			}

			endPos = StringUtil.indexOfAny(
				content, _LAYOUT_REF_STOP_CHARS, beginPos + offset, endPos);

			if (endPos == -1) {
				endPos = beginPos - 1;

				continue;
			}

			String url = content.substring(beginPos + offset, endPos);

			String servletMapping;
			String servletMappingParam;

			if (url.startsWith(PortalUtil.getPathFriendlyURLPrivateGroup())) {
				servletMapping = PortalUtil.getPathFriendlyURLPrivateGroup();
				servletMappingParam =
					"@data_handler_private_group_servlet_mapping@";
			}
			else if (url.startsWith(
						PortalUtil.getPathFriendlyURLPrivateUser())) {

				servletMapping = PortalUtil.getPathFriendlyURLPrivateUser();
				servletMappingParam =
					"@data_handler_private_user_servlet_mapping@";
			}
			else if (url.startsWith(PortalUtil.getPathFriendlyURLPublic())) {
				servletMapping = PortalUtil.getPathFriendlyURLPublic();
				servletMappingParam = "@data_handler_public_servlet_mapping@";
			}
			else {
				endPos = beginPos - 1;

				continue;
			}

			int beginGroupPos = beginPos + offset + servletMapping.length();

			if (content.charAt(beginGroupPos) == CharPool.SLASH) {
				int endGroupPos = url.indexOf(
					CharPool.SLASH, servletMapping.length() + 1);

				if (endGroupPos == -1) {
					endGroupPos = endPos;
				}
				else {
					endGroupPos = endGroupPos + beginPos + offset;
				}

				String groupFriendlyURL = content.substring(
					beginGroupPos, endGroupPos);

				if (groupFriendlyURL.equals(group.getFriendlyURL())) {
					sb.replace(
						beginGroupPos, endGroupPos,
						"@data_handler_group_friendly_url@");
				}
			}

			sb.replace(beginPos + offset, beginGroupPos, servletMappingParam);

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	public String exportLinksToLayouts(
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

	public String importContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		content = ExportImportUtil.importLayoutReferences(
			portletDataContext, content);
		content = ExportImportUtil.importLinksToLayouts(
			portletDataContext, content);

		content = ExportImportUtil.importDLReferences(
			portletDataContext, entityElement, content);

		return content;
	}

	public String importDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				entityElement, FileEntry.class);

		for (Element referenceDataElement : referenceDataElements) {
			String fileEntryUUID = referenceDataElement.attributeValue("uuid");

			if (fileEntryUUID == null) {
				continue;
			}

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceDataElement);

			String path = referenceDataElement.attributeValue("path");

			FileEntry fileEntry = null;

			try {
				long groupId = portletDataContext.getScopeGroupId();

				long fileEntryGroupId = GetterUtil.getLong(
					referenceDataElement.attributeValue("group-id"));

				if (fileEntryGroupId ==
						portletDataContext.getSourceCompanyGroupId()) {

					groupId = portletDataContext.getSourceCompanyGroupId();
				}

				fileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					fileEntryUUID, groupId);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to reference " + path);
				}

				continue;
			}

			String url = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			content = StringUtil.replace(
				content, "[$dl-reference=" + path + "$]", url);
		}

		return content;
	}

	public String importLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		content = StringUtil.replace(
			content, "@data_handler_private_group_servlet_mapping@",
			PortalUtil.getPathFriendlyURLPrivateGroup());
		content = StringUtil.replace(
			content, "@data_handler_private_user_servlet_mapping@",
			PortalUtil.getPathFriendlyURLPrivateUser());
		content = StringUtil.replace(
			content, "@data_handler_public_servlet_mapping@",
			PortalUtil.getPathFriendlyURLPublic());

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		return content;
	}

	public String importLinksToLayouts(
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

	protected Map<String, String[]> getDLReferenceParameters(
		PortletDataContext portletDataContext, String content, int beginPos,
		int endPos) {

		boolean legacyURL = true;
		char[] stopChars = _DL_REF_STOP_CHARS_LEGACY;

		if (content.startsWith("/documents/", beginPos)) {
			legacyURL = false;
			stopChars = _DL_REF_STOP_CHARS;
		}

		endPos = StringUtil.indexOfAny(content, stopChars, beginPos, endPos);

		if (endPos == -1) {
			return null;
		}

		Map<String, String[]> map = new HashMap<String, String[]>();

		String dlReference = content.substring(beginPos, endPos);

		while (dlReference.contains(StringPool.AMPERSAND_ENCODED)) {
			dlReference = dlReference.replace(
				StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		if (!legacyURL) {
			String[] pathArray = dlReference.split(StringPool.SLASH);

			map.put("groupId", new String[] {pathArray[2]});

			if (pathArray.length == 4) {
				map.put("uuid", new String[] {pathArray[3]});
			}
			else if (pathArray.length == 5) {
				map.put("folderId", new String[] {pathArray[3]});
				map.put(
					"title", new String[] {HttpUtil.decodeURL(pathArray[4])});
			}
			else if (pathArray.length > 5) {
				map.put("uuid", new String[] {pathArray[5]});
			}
		}
		else {
			dlReference = dlReference.substring(
				dlReference.indexOf(CharPool.QUESTION) + 1);

			map = HttpUtil.parameterMapFromString(dlReference);

			if (map.containsKey("img_id")) {
				map.put("image_id", map.get("img_id"));
			}
			else if (map.containsKey("i_id")) {
				map.put("image_id", map.get("i_id"));
			}
		}

		map.put("endPos", new String[] {String.valueOf(endPos)});

		String groupIdString = MapUtil.getString(map, "groupId");

		if (groupIdString.equals("@group_id@")) {
			groupIdString = String.valueOf(
				portletDataContext.getScopeGroupId());

			map.put("groupId", new String[] {groupIdString});
		}

		return map;
	}

	protected FileEntry getFileEntry(
		PortletDataContext portletDataContext, Map<String, String[]> args) {

		if (args == null) {
			return null;
		}

		FileEntry fileEntry = null;

		try {
			String uuid = MapUtil.getString(args, "uuid");
			long groupId = MapUtil.getLong(args, "groupId");

			if (Validator.isNotNull(uuid)) {
				fileEntry =
					DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
						uuid, groupId);
			}
			else {
				if (args.containsKey("folderId")) {
					long folderId = MapUtil.getLong(args, "folderId");
					String name = MapUtil.getString(args, "name");
					String title = MapUtil.getString(args, "title");

					if (Validator.isNotNull(title)) {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							groupId, folderId, title);
					}
					else {
						DLFileEntry dlFileEntry =
							DLFileEntryLocalServiceUtil.fetchFileEntryByName(
								groupId, folderId, name);

						if (dlFileEntry != null) {
							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								dlFileEntry.getFileEntryId());
						}
					}
				}
				else if (args.containsKey("image_id")) {
					DLFileEntry dlFileEntry =
						DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(
							MapUtil.getLong(args, "image_id"));

					if (dlFileEntry != null) {
						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							dlFileEntry.getFileEntryId());
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();

			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return fileEntry;
	}

	private static final char[] _DL_REF_STOP_CHARS = new char[] {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	private static final char[] _DL_REF_STOP_CHARS_LEGACY = new char[] {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUOTE, CharPool.SPACE
	};

	private static final char[] _LAYOUT_REF_STOP_CHARS = new char[] {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	private static Log _log = LogFactoryUtil.getLog(ExportImportImpl.class);

	private Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");
	private Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@(\\p{XDigit}{8}\\-" +
		"(?:\\p{XDigit}{4}\\-){3}\\p{XDigit}{12})@([^\\]]*)\\]");

}