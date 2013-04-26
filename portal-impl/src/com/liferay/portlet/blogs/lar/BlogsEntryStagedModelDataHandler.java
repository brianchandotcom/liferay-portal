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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandler;
import com.liferay.portlet.journal.lar.JournalPortletDataHandler;

import java.io.InputStream;

import java.util.Calendar;

/**
 * @author Zsolt Berentey
 */
public class BlogsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BlogsEntry> {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		if (!entry.isApproved() && !entry.isInTrash()) {
			return;
		}

		Element entryElement = portletDataContext.getExportDataElement(entry);

		if (entry.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				entry.getSmallImageId());

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				String smallImageURL =
					DDMPortletDataHandler.exportReferenceContent(
						portletDataContext, null, null, null, null, null, null,
						entryElement,
						entry.getSmallImageURL().concat(StringPool.SPACE));

				entry.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					entry, smallImage.getImageId() + StringPool.PERIOD +
						smallImage.getType());

				entryElement.addAttribute("small-image-path", smallImagePath);

				entry.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		String content = DDMPortletDataHandler.exportReferenceContent(
			portletDataContext, null, null, null, null, null, null,
			entryElement, entry.getContent());

		entry.setContent(content);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry,
			BlogsPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Element entryElement =
			portletDataContext.getImportDataStagedModelElement(entry);

		String content = JournalPortletDataHandler.importReferenceContent(
			portletDataContext, entryElement, entry.getContent());

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
		int status = entry.getStatus();

		String smallImageFileName = null;
		InputStream smallImageInputStream = null;

		try {
			if (entry.isSmallImage()) {
				String smallImagePath = entryElement.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(entry.getSmallImageURL())) {
					String smallImageURL =
						JournalPortletDataHandler.importReferenceContent(
							portletDataContext, entryElement,
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
				portletDataContext.createServiceContext(
					entry, BlogsPortletDataHandler.NAMESPACE);

			if ((status != WorkflowConstants.STATUS_APPROVED) &&
				(status != WorkflowConstants.STATUS_IN_TRASH)) {

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}

			BlogsEntry importedEntry = null;

			if (portletDataContext.isDataStrategyMirror()) {
				serviceContext.setAttribute("urlTitle", entry.getUrlTitle());

				BlogsEntry existingEntry = BlogsEntryUtil.fetchByUUID_G(
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

					if (status == WorkflowConstants.STATUS_IN_TRASH) {
						importedEntry =
							BlogsEntryLocalServiceUtil.moveEntryToTrash(
								userId, importedEntry);
					}
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

			portletDataContext.importClassedModel(
				entry, importedEntry, BlogsPortletDataHandler.NAMESPACE);
		}
		finally {
			StreamUtil.cleanUp(smallImageInputStream);
		}
	}

}