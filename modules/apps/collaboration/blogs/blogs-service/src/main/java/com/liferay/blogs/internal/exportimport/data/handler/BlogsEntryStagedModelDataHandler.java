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

package com.liferay.blogs.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionStagingHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.ratings.kernel.model.RatingsEntry;

import java.io.InputStream;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class BlogsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BlogsEntry> {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	@Override
	public void deleteStagedModel(BlogsEntry entry) throws PortalException {
		_blogsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BlogsEntry entry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (entry != null) {
			deleteStagedModel(entry);
		}
	}

	@Override
	public BlogsEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<BlogsEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<BlogsEntry>());
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
			if (entry.getSmallImageFileEntryId() > 0) {
				FileEntry fileEntry =
					PortletFileRepositoryUtil.getPortletFileEntry(
						entry.getSmallImageFileEntryId());

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, entry, fileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}
			else if (entry.getSmallImageId() > 0) {
				Image smallImage = _imageLocalService.fetchImage(
					entry.getSmallImageId());

				if ((smallImage != null) && (smallImage.getTextObj() != null)) {
					String smallImagePath = ExportImportPathUtil.getModelPath(
						entry,
						smallImage.getImageId() + StringPool.PERIOD +
							smallImage.getType());

					entryElement.addAttribute(
						"small-image-path", smallImagePath);

					entry.setSmallImageType(smallImage.getType());

					portletDataContext.addZipEntry(
						smallImagePath, smallImage.getTextObj());
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(4);

						sb.append("Unable to export small image ");
						sb.append(entry.getSmallImageId());
						sb.append(" to blogs entry ");
						sb.append(entry.getEntryId());

						_log.warn(sb.toString());
					}

					entry.setSmallImage(false);
					entry.setSmallImageId(0);
				}
			}
		}

		if (entry.getCoverImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getCoverImageFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, entry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		_exportFriendlyURLEntries(portletDataContext, entry);

		String content =
			_exportImportContentProcessor.replaceExportContentReferences(
				portletDataContext, entry, entry.getContent(),
				portletDataContext.getBooleanParameter(
					"blogs", "referenced-content"),
				true);

		entry.setContent(content);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long entryId)
		throws Exception {

		BlogsEntry existingEntry = fetchMissingReference(uuid, groupId);

		if (existingEntry == null) {
			return;
		}

		Map<Long, Long> entryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BlogsEntry.class);

		entryIds.put(entryId, existingEntry.getEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Element entryElement =
			portletDataContext.getImportDataStagedModelElement(entry);

		String content =
			_exportImportContentProcessor.replaceImportContentReferences(
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

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry);

		BlogsEntry importedEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BlogsEntry existingEntry = fetchStagedModelByUuidAndGroupId(
				entry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = _blogsEntryLocalService.addEntry(
					userId, entry.getTitle(), entry.getSubtitle(),
					entry.getDescription(), entry.getContent(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, allowPingbacks,
					allowTrackbacks, trackbacks, entry.getCoverImageCaption(),
					null, null, serviceContext);
			}
			else {
				importedEntry = _blogsEntryLocalService.updateEntry(
					userId, existingEntry.getEntryId(), entry.getTitle(),
					entry.getSubtitle(), entry.getDescription(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					allowPingbacks, allowTrackbacks, trackbacks,
					entry.getCoverImageCaption(), new ImageSelector(),
					new ImageSelector(), serviceContext);
			}
		}
		else {
			importedEntry = _blogsEntryLocalService.addEntry(
				userId, entry.getTitle(), entry.getSubtitle(),
				entry.getDescription(), entry.getContent(), displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				entry.getCoverImageCaption(), null, null, serviceContext);
		}

		// Cover image

		ImageSelector coverImageSelector = null;

		List<Element> attachmentElements =
			portletDataContext.getReferenceDataElements(
				entry, DLFileEntry.class,
				PortletDataContext.REFERENCE_TYPE_WEAK);

		if (Validator.isNotNull(entry.getCoverImageURL())) {
			coverImageSelector = new ImageSelector(entry.getCoverImageURL());
		}
		else if (entry.getCoverImageFileEntryId() != 0) {
			coverImageSelector = _getImageSelector(
				portletDataContext, entry.getCoverImageFileEntryId(),
				attachmentElements);
		}

		if (coverImageSelector != null) {
			_blogsEntryLocalService.addCoverImage(
				importedEntry.getEntryId(), coverImageSelector);
		}

		// Small image

		ImageSelector smallImageSelector = null;

		if (entry.isSmallImage()) {
			String smallImagePath = entryElement.attributeValue(
				"small-image-path");

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				smallImageSelector = new ImageSelector(
					entry.getSmallImageURL());
			}
			else if (Validator.isNotNull(smallImagePath)) {
				String smallImageFileName =
					entry.getSmallImageId() + StringPool.PERIOD +
						entry.getSmallImageType();

				InputStream inputStream = null;

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						smallImagePath);

					smallImageSelector = new ImageSelector(
						FileUtil.getBytes(inputStream), smallImageFileName,
						MimeTypesUtil.getContentType(smallImageFileName), null);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
			else if (entry.getSmallImageFileEntryId() != 0) {
				smallImageSelector = _getImageSelector(
					portletDataContext, entry.getSmallImageFileEntryId(),
					attachmentElements);
			}
		}

		if (smallImageSelector != null) {
			_blogsEntryLocalService.addSmallImage(
				importedEntry.getEntryId(), smallImageSelector);
		}

		if ((coverImageSelector != null) || (smallImageSelector != null)) {
			importedEntry = _blogsEntryLocalService.getEntry(
				importedEntry.getEntryId());
		}

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BlogsEntry.class);

		newPrimaryKeysMap.put(entry.getEntryId(), importedEntry.getEntryId());

		_importFriendlyURLEntries(portletDataContext, entry, importedEntry);

		portletDataContext.importClassedModel(entry, importedEntry);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		BlogsEntry existingEntry = fetchStagedModelByUuidAndGroupId(
			entry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingEntry == null) || !existingEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			BlogsEntry.class.getName());

		if (trashHandler.isRestorable(existingEntry.getEntryId())) {
			trashHandler.restoreTrashEntry(userId, existingEntry.getEntryId());
		}
	}

	protected InputStream getSmallImageInputStream(
		PortletDataContext portletDataContext, Element attachmentElement) {

		InputStream inputStream = null;

		String path = attachmentElement.attributeValue("path");

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		String binPath = attachmentElement.attributeValue("bin-path");

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				inputStream = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception e) {
			}
		}
		else {
			inputStream = portletDataContext.getZipEntryAsInputStream(binPath);
		}

		if (inputStream == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import small image file entry " +
						fileEntry.getFileEntryId());
			}
		}

		return inputStream;
	}

	@Override
	protected void importReferenceStagedModels(
			PortletDataContext portletDataContext, BlogsEntry stagedModel)
		throws PortletDataException {

		Element stagedModelElement =
			portletDataContext.getImportDataStagedModelElement(stagedModel);

		Element referencesElement = stagedModelElement.element("references");

		if (referencesElement == null) {
			return;
		}

		DiscussionStagingHandler discussionStagingHandler =
			_commentManager.getDiscussionStagingHandler();

		String stagedModelClassName = null;

		if (discussionStagingHandler != null) {
			stagedModelClassName = discussionStagingHandler.getClassName();
		}

		List<Element> referenceElements = referencesElement.elements();

		for (Element referenceElement : referenceElements) {
			String className = referenceElement.attributeValue("class-name");

			if (className.equals(FriendlyURLEntry.class.getName()) ||
				className.equals(AssetCategory.class.getName()) ||
				className.equals(RatingsEntry.class.getName()) ||
				className.equals(stagedModelClassName)) {

				continue;
			}

			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, stagedModel, className, classPK);
		}
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.blogs.kernel.model.BlogsEntry)",
		unbind = "-"
	)
	protected void setExportImportContentProcessor(
		ExportImportContentProcessor<String> exportImportContentProcessor) {

		_exportImportContentProcessor = exportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setFriendlyURLEntryLocalService(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	private void _exportFriendlyURLEntries(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortletDataException {

		long classNameId = _portal.getClassNameId(BlogsEntry.class);

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				blogsEntry.getGroupId(), blogsEntry.getCompanyId(), classNameId,
				blogsEntry.getEntryId());

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, blogsEntry, friendlyURLEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private ImageSelector _getImageSelector(
			PortletDataContext portletDataContext, long fileEntryId,
			List<Element> attachmentElements)
		throws Exception {

		for (Element attachmentElement : attachmentElements) {
			String path = attachmentElement.attributeValue("path");

			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(path);

			if (fileEntryId == fileEntry.getFileEntryId()) {
				InputStream inputStream = null;

				try {
					String binPath = attachmentElement.attributeValue(
						"bin-path");

					if (Validator.isNull(binPath) &&
						portletDataContext.isPerformDirectBinaryImport()) {

						try {
							inputStream = FileEntryUtil.getContentStream(
								fileEntry);
						}
						catch (NoSuchFileException nsfe) {

							// LPS-52675

							if (_log.isDebugEnabled()) {
								_log.debug(nsfe, nsfe);
							}
						}
					}
					else {
						inputStream =
							portletDataContext.getZipEntryAsInputStream(
								binPath);
					}

					if (inputStream == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to import attachment for file entry " +
									fileEntry.getFileEntryId());
						}

						continue;
					}

					return new ImageSelector(
						FileUtil.getBytes(inputStream), fileEntry.getFileName(),
						fileEntry.getMimeType(), null);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
		}

		return null;
	}

	private void _importFriendlyURLEntries(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry,
			BlogsEntry importedBlogsEntry)
		throws PortletDataException {

		List<Element> friendlyURLEntryElements =
			portletDataContext.getReferenceDataElements(
				blogsEntry, FriendlyURLEntry.class);

		for (Element friendlyURLEntryElement : friendlyURLEntryElements) {
			String path = friendlyURLEntryElement.attributeValue("path");

			FriendlyURLEntry friendlyURLEntry =
				(FriendlyURLEntry)portletDataContext.getZipEntryAsObject(path);

			friendlyURLEntry.setClassNameId(
				_portal.getClassNameId(BlogsEntry.class));

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, friendlyURLEntry);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryStagedModelDataHandler.class);

	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private CommentManager _commentManager;

	private ExportImportContentProcessor<String> _exportImportContentProcessor;
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;
	private ImageLocalService _imageLocalService;

	@Reference
	private Portal _portal;

}