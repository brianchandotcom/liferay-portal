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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;

import java.util.Calendar;

/**
 * @author Zsolt Berentey
 */
public class BlogsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BlogsEntry> {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		BlogsEntry entry =
			BlogsEntryLocalServiceUtil.fetchBlogsEntryByUuidAndGroupId(
				uuid, groupId);

		if (entry != null) {
			BlogsEntryLocalServiceUtil.deleteEntry(entry);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(BlogsEntry entry) {
		return entry.getTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(entry);

		if (entry.isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.fetchImage(
				entry.getSmallImageId());

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				String smallImageURL =
					ExportImportHelperUtil.replaceExportContentReferences(
						portletDataContext, entry,
						entry.getSmallImageURL().concat(StringPool.SPACE),
						true);

				entry.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					entry,
					smallImage.getImageId() + StringPool.PERIOD +
						smallImage.getType());

				entryElement.addAttribute("small-image-path", smallImagePath);

				entry.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		String content = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContext, entry, entry.getContent(),
			portletDataContext.getBooleanParameter(
				BlogsPortletDataHandler.NAMESPACE, "referenced-content"));

		entry.setContent(content);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Element entryElement =
			portletDataContext.getImportDataStagedModelElement(entry);

		String content = ExportImportHelperUtil.replaceImportContentReferences(
			portletDataContext, entry, entry.getContent());

		entry.setContent(content);

		Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

		displayDateCal.setTime(entry.getDisplayDate());

		int displayDateMonth = displayDateCal.get(Calendar.MONTH);
		int displayDateDay = displayDateCal.get(Calendar.DATE);
		int displayDateYear = displayDateCal.get(Calendar.YEAR);
		int displayDateHour = displayDateCal.get(Calendar.HOUR);
		int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

		if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = entry.isAllowPingbacks();
		boolean allowTrackbacks = entry.isAllowTrackbacks();
		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		String smallImageFileName = null;
		InputStream smallImageInputStream = null;

		try {
			if (entry.isSmallImage()) {
				String smallImagePath = entryElement.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(entry.getSmallImageURL())) {
					String smallImageURL =
						ExportImportHelperUtil.replaceImportContentReferences(
							portletDataContext, entry,
							entry.getSmallImageURL());

					entry.setSmallImageURL(smallImageURL);
				}
				else if (Validator.isNotNull(smallImagePath)) {
					smallImageFileName = String.valueOf(
						entry.getSmallImageId()).concat(
							StringPool.PERIOD).concat(
								entry.getSmallImageType());

					smallImageInputStream =
						portletDataContext.getZipEntryAsInputStream(
							smallImagePath);
				}
			}

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(entry);

			BlogsEntry importedEntry = null;

			if (portletDataContext.isDataStrategyMirror()) {
				serviceContext.setAttribute("urlTitle", entry.getUrlTitle());

				BlogsEntry existingEntry =
					BlogsEntryLocalServiceUtil.fetchBlogsEntryByUuidAndGroupId(
						entry.getUuid(), portletDataContext.getScopeGroupId());

				if (existingEntry == null) {
					serviceContext.setUuid(entry.getUuid());

					importedEntry = BlogsEntryLocalServiceUtil.addEntry(
						userId, entry.getTitle(), entry.getDescription(),
						entry.getContent(), displayDateMonth, displayDateDay,
						displayDateYear, displayDateHour, displayDateMinute,
						allowPingbacks, allowTrackbacks, trackbacks,
						entry.isSmallImage(), entry.getSmallImageURL(),
						smallImageFileName, smallImageInputStream,
						serviceContext);
				}
				else {
					importedEntry = BlogsEntryLocalServiceUtil.updateEntry(
						userId, existingEntry.getEntryId(), entry.getTitle(),
						entry.getDescription(), entry.getContent(),
						displayDateMonth, displayDateDay, displayDateYear,
						displayDateHour, displayDateMinute, allowPingbacks,
						allowTrackbacks, trackbacks, entry.getSmallImage(),
						entry.getSmallImageURL(), smallImageFileName,
						smallImageInputStream, serviceContext);
				}
			}
			else {
				importedEntry = BlogsEntryLocalServiceUtil.addEntry(
					userId, entry.getTitle(), entry.getDescription(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					allowPingbacks, allowTrackbacks, trackbacks,
					entry.getSmallImage(), entry.getSmallImageURL(),
					smallImageFileName, smallImageInputStream, serviceContext);
			}

			portletDataContext.importClassedModel(entry, importedEntry);
		}
		finally {
			StreamUtil.cleanUp(smallImageInputStream);
		}
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		BlogsEntry existingEntry =
			BlogsEntryLocalServiceUtil.fetchBlogsEntryByUuidAndGroupId(
				entry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingEntry == null) || !existingEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingEntry.getTrashHandler();

		if (trashHandler.isRestorable(existingEntry.getEntryId())) {
			trashHandler.restoreTrashEntry(userId, existingEntry.getEntryId());
		}
	}

}