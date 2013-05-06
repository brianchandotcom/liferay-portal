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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
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
public class ExportImportUtil {

	public static String exportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		content = exportDLReferences(
			portletDataContext, entityElement, content);

		content = exportLayoutReferences(portletDataContext, content);
		content = exportLinksToLayouts(portletDataContext, content);

		Element groupElement = entityElement.getParent();

		String groupElementName = groupElement.getName();

		if (!groupElementName.equals(JournalArticle.class.getSimpleName())) {
			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
		}

		return content;
	}

	public static String importContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		content = importDLReferences(
			portletDataContext, entityElement, content);

		content = importLayoutReferences(portletDataContext, content);
		content = importLinksToLayouts(portletDataContext, content);

		return content;
	}

	protected static String exportDLReferences(
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

							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								dlFileEntry.getFileEntryId());
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
							fileEntry = DLAppLocalServiceUtil.getFileEntry(
								dlFileEntry.getFileEntryId());
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

			beginPos--;
		}

		return sb.toString();
	}

	protected static String exportLayoutReferences(
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

		String privateGroupServletMapping = _privateGroupServletMapping;
		String privateUserServletMapping = _privateUserServletMapping;
		String publicServletMapping = _publicServletMapping;

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

	protected static String exportLinksToLayouts(
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

	protected static String importDLReferences(
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

	protected static String importLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		String privateGroupServletMapping = _privateGroupServletMapping;
		String privateUserServletMapping = _privateUserServletMapping;
		String publicServletMapping = _publicServletMapping;

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

	protected static String importLinksToLayouts(
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

	private static Log _log = LogFactoryUtil.getLog(ExportImportUtil.class);

	private static Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");

	private static Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@(\\p{XDigit}{8}\\-" +
		"(?:\\p{XDigit}{4}\\-){3}\\p{XDigit}{12})@([^\\]]*)\\]");

	private static String _privateGroupServletMapping = PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);

	private static String _privateUserServletMapping = PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);

	private static String _publicServletMapping = PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

}