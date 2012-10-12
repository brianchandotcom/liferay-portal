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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl;

import java.io.InputStream;

import java.util.Calendar;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class BlogsPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_entries
		};
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "blog-entries", true, _metadataControls)
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _wordpress
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "blog-entries", true, _metadataControls)
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return PropsValues.BLOGS_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				BlogsPortletDataHandlerImpl.class, "deleteData")) {

			BlogsEntryLocalServiceUtil.deleteEntries(
				portletDataContext.getScopeGroupId());

			BlogsStatsUserLocalServiceUtil.deleteStatsUserByGroupId(
				portletDataContext.getScopeGroupId());
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.blogs", portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("blogs-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element entriesElement = rootElement.addElement("entries");

		Element dlFileEntryTypesElement = entriesElement.addElement(
			"dl-file-entry-types");
		Element dlFoldersElement = entriesElement.addElement("dl-folders");
		Element dlFileEntriesElement = entriesElement.addElement(
			"dl-file-entries");
		Element dlFileRanksElement = entriesElement.addElement("dl-file-ranks");
		Element dlRepositoriesElement = entriesElement.addElement(
			"dl-repositories");
		Element dlRepositoryEntriesElement = entriesElement.addElement(
			"dl-repository-entries");

		List<BlogsEntry> entries = BlogsEntryUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (BlogsEntry entry : entries) {
			exportEntry(
				portletDataContext, entriesElement, dlFileEntryTypesElement,
				dlFoldersElement, dlFileEntriesElement, dlFileRanksElement,
				dlRepositoriesElement, dlRepositoryEntriesElement, entry);
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.blogs", portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element entriesElement = rootElement.element("entries");

		if (entriesElement != null) {
			JournalPortletDataHandlerImpl.importReferencedData(
				portletDataContext, entriesElement);
		}
		else {
			entriesElement = rootElement;
		}

		for (Element entryElement : entriesElement.elements("entry")) {
			String path = entryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			BlogsEntry entry =
				(BlogsEntry)portletDataContext.getZipEntryAsObject(path);

			importEntry(portletDataContext, entryElement, entry);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "wordpress")) {
			WordPressImporter.importData(portletDataContext);
		}

		return null;
	}

	protected void exportEntry(
			PortletDataContext portletDataContext, Element entriesElement,
			Element dlFileEntryTypesElement, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element dlRepositoriesElement, Element dlRepositoryEntriesElement,
			BlogsEntry entry)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		if (!entry.isApproved() && !entry.isInTrash()) {
			return;
		}

		String path = getEntryPath(portletDataContext, entry);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		// Clone this entry to make sure changes to its content are never
		// persisted

		entry = (BlogsEntry)entry.clone();

		Element entryElement = (Element)entriesElement.selectSingleNode(
			"//page[@path='".concat(path).concat("']"));

		if (entryElement == null) {
			entryElement = entriesElement.addElement("entry");
		}

		String content = JournalPortletDataHandlerImpl.exportReferencedContent(
			portletDataContext, dlFileEntryTypesElement, dlFoldersElement,
			dlFileEntriesElement, dlFileRanksElement, dlRepositoriesElement,
			dlRepositoryEntriesElement, entryElement, entry.getContent());

		entry.setContent(content);

		String imagePath = getEntryImagePath(portletDataContext, entry);

		entryElement.addAttribute("image-path", imagePath);

		Image smallImage = ImageUtil.fetchByPrimaryKey(entry.getSmallImageId());

		if (entry.isSmallImage() && (smallImage != null)) {
			String smallImagePath = getEntrySmallImagePath(
				portletDataContext, entry);

			entryElement.addAttribute("small-image-path", smallImagePath);

			entry.setSmallImageType(smallImage.getType());

			portletDataContext.addZipEntry(
				smallImagePath, smallImage.getTextObj());
		}

		portletDataContext.addClassedModel(
			entryElement, path, entry, _NAMESPACE);
	}

	protected String getEntryImagePath(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BLOGS));
		sb.append("/entry/");
		sb.append(entry.getUuid());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getEntryPath(
		PortletDataContext portletDataContext, BlogsEntry entry) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BLOGS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getEntrySmallImagePath(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BLOGS));
		sb.append("/entries/");
		sb.append(entry.getUuid());
		sb.append("/thumbnail");
		sb.append(StringPool.PERIOD);
		sb.append(entry.getSmallImageType());

		return sb.toString();
	}

	protected void importEntry(
			PortletDataContext portletDataContext, Element entryElement,
			BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		String content = JournalPortletDataHandlerImpl.importReferencedContent(
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
			String smallImagePath = entryElement.attributeValue(
				"small-image-path");

			if (entry.isSmallImage() && Validator.isNotNull(smallImagePath)) {
				smallImageFileName = String.valueOf(
					entry.getSmallImageId()).concat(
						StringPool.PERIOD).concat(entry.getSmallImageType());
				smallImageInputStream =
					portletDataContext.getZipEntryAsInputStream(smallImagePath);
			}

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(
					entryElement, entry, _NAMESPACE);

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
				entry, importedEntry, _NAMESPACE);
		}
		finally {
			StreamUtil.cleanUp(smallImageInputStream);
		}

	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "blogs";

	private static PortletDataHandlerBoolean _entries =
		new PortletDataHandlerBoolean(_NAMESPACE, "entries", true, true);

	private static PortletDataHandlerControl[] _metadataControls =
		new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(_NAMESPACE, "categories"),
			new PortletDataHandlerBoolean(_NAMESPACE, "comments"),
			new PortletDataHandlerBoolean(_NAMESPACE, "ratings"),
			new PortletDataHandlerBoolean(_NAMESPACE, "tags")
		};

	private static PortletDataHandlerBoolean _wordpress =
		new PortletDataHandlerBoolean(_NAMESPACE, "wordpress");

}