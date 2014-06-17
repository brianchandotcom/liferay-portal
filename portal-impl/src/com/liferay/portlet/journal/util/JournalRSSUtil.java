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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class JournalRSSUtil {

	public static List<JournalArticle> getArticles(JournalFeed feed) {
		long companyId = feed.getCompanyId();
		long groupId = feed.getGroupId();
		List<Long> folderIds = Collections.emptyList();
		String articleId = null;
		Double version = null;
		String title = null;
		String description = null;
		String content = null;

		String type = feed.getType();

		if (Validator.isNull(type)) {
			type = null;
		}

		String structureId = feed.getStructureId();

		if (Validator.isNull(structureId)) {
			structureId = null;
		}

		String templateId = feed.getTemplateId();

		if (Validator.isNull(templateId)) {
			templateId = null;
		}

		Date displayDateGT = null;
		Date displayDateLT = new Date();
		int status = WorkflowConstants.STATUS_APPROVED;
		Date reviewDate = null;
		boolean andOperator = true;
		int start = 0;
		int end = feed.getDelta();

		String orderByCol = feed.getOrderByCol();
		String orderByType = feed.getOrderByType();
		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator obc = new ArticleModifiedDateComparator(orderByAsc);

		if (orderByCol.equals("display-date")) {
			obc = new ArticleDisplayDateComparator(orderByAsc);
		}

		return JournalArticleLocalServiceUtil.search(
			companyId, groupId, folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, articleId, version,
			title, description, content, type, structureId, templateId,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	public static List<SyndEnclosure> getDLEnclosures(
		String portalURL, String url) {

		List<SyndEnclosure> syndEnclosures = new ArrayList<SyndEnclosure>();

		FileEntry fileEntry = getFileEntry(url);

		if (fileEntry == null) {
			return syndEnclosures;
		}

		SyndEnclosure syndEnclosure = new SyndEnclosureImpl();

		syndEnclosure.setLength(fileEntry.getSize());
		syndEnclosure.setType(fileEntry.getMimeType());
		syndEnclosure.setUrl(portalURL + url);

		syndEnclosures.add(syndEnclosure);

		return syndEnclosures;
	}

	public static List<SyndLink> getDLLinks(String portalURL, String url) {
		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		FileEntry fileEntry = getFileEntry(url);

		if (fileEntry == null) {
			return syndLinks;
		}

		SyndLink syndLink = new SyndLinkImpl();

		syndLink.setHref(portalURL + url);
		syndLink.setLength(fileEntry.getSize());
		syndLink.setRel("enclosure");
		syndLink.setType(fileEntry.getMimeType());

		syndLinks.add(syndLink);

		return syndLinks;
	}

	public static FileEntry getFileEntry(String url) {
		FileEntry fileEntry = null;

		String queryString = HttpUtil.getQueryString(url);

		Map<String, String[]> parameters = HttpUtil.parameterMapFromString(
			queryString);

		if (url.startsWith("/documents/")) {
			String[] pathArray = StringUtil.split(url, CharPool.SLASH);

			String uuid = null;
			long groupId = GetterUtil.getLong(pathArray[2]);
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			String title = null;

			if (pathArray.length == 4) {
				uuid = pathArray[3];
			}
			else if (pathArray.length == 5) {
				folderId = GetterUtil.getLong(pathArray[3]);
				title = HttpUtil.decodeURL(pathArray[4]);
			}
			else if (pathArray.length > 5) {
				uuid = pathArray[5];
			}

			try {
				if (Validator.isNotNull(uuid)) {
					fileEntry =
						DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
							uuid, groupId);
				}
				else {
					fileEntry = DLAppLocalServiceUtil.getFileEntry(
						groupId, folderId, title);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
		else if (parameters.containsKey("folderId") &&
				 parameters.containsKey("name")) {

			try {
				long fileEntryId = GetterUtil.getLong(
					parameters.get("fileEntryId")[0]);

				fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
		else if (parameters.containsKey("uuid") &&
				 parameters.containsKey("groupId")) {

			try {
				String uuid = parameters.get("uuid")[0];
				long groupId = GetterUtil.getLong(parameters.get("groupId")[0]);

				fileEntry = DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return fileEntry;
	}

	public static List<SyndEnclosure> getIGEnclosures(
		String portalURL, String url) {

		List<SyndEnclosure> syndEnclosures = new ArrayList<SyndEnclosure>();

		Object[] imageProperties = getImageProperties(url);

		if (imageProperties == null) {
			return syndEnclosures;
		}

		SyndEnclosure syndEnclosure = new SyndEnclosureImpl();

		syndEnclosure.setLength((Long)imageProperties[1]);
		syndEnclosure.setType(
			MimeTypesUtil.getExtensionContentType(
				imageProperties[0].toString()));
		syndEnclosure.setUrl(portalURL + url);

		syndEnclosures.add(syndEnclosure);

		return syndEnclosures;
	}

	public static List<SyndLink> getIGLinks(String portalURL, String url) {
		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		Object[] imageProperties = getImageProperties(url);

		if (imageProperties == null) {
			return syndLinks;
		}

		SyndLink syndLink = new SyndLinkImpl();

		syndLink.setHref(portalURL + url);
		syndLink.setLength((Long)imageProperties[1]);
		syndLink.setRel("enclosure");
		syndLink.setType(
			MimeTypesUtil.getExtensionContentType(
				imageProperties[0].toString()));

		syndLinks.add(syndLink);

		return syndLinks;
	}

	public static Image getImage(String url) {
		Image image = null;

		String queryString = HttpUtil.getQueryString(url);

		Map<String, String[]> parameters = HttpUtil.parameterMapFromString(
			queryString);

		if (parameters.containsKey("image_id") ||
			parameters.containsKey("img_id") ||
			parameters.containsKey("i_id")) {

			try {
				long imageId = 0;

				if (parameters.containsKey("image_id")) {
					imageId = GetterUtil.getLong(parameters.get("image_id")[0]);
				}
				else if (parameters.containsKey("img_id")) {
					imageId = GetterUtil.getLong(parameters.get("img_id")[0]);
				}
				else if (parameters.containsKey("i_id")) {
					imageId = GetterUtil.getLong(parameters.get("i_id")[0]);
				}

				image = ImageLocalServiceUtil.getImage(imageId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return image;
	}

	protected static Object[] getImageProperties(String url) {
		String type = null;
		long size = 0;

		Image image = getImage(url);

		if (image != null) {
			type = image.getType();
			size = image.getSize();
		}
		else {
			FileEntry fileEntry = getFileEntry(url);

			Set<String> imageMimeTypes = ImageProcessorUtil.getImageMimeTypes();

			if ((fileEntry != null) &&
				imageMimeTypes.contains(fileEntry.getMimeType())) {

				type = fileEntry.getExtension();
				size = fileEntry.getSize();
			}
		}

		if (Validator.isNotNull(type)) {
			return new Object[] {type, size};
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalRSSUtil.class);

}