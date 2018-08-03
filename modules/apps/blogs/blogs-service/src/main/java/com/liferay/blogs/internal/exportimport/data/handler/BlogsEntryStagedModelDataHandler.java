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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
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
			new StagedModelModifiedDateComparator<>());
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
	public int[] getExportableStatuses() {
		return new int[] {
			WorkflowConstants.STATUS_APPROVED,
			WorkflowConstants.STATUS_SCHEDULED
		};
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
		long entryId) {

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

		String content =
			_exportImportContentProcessor.replaceImportContentReferences(
				portletDataContext, entry, entry.getContent());

		entry.setContent(content);

		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry);

		BlogsEntry existingEntry = fetchStagedModelByUuidAndGroupId(
			entry.getUuid(), portletDataContext.getScopeGroupId());

		long existingCoverImageFileEntryId = 0;
		long existingSmallImageFileEntryId = 0;

		if (existingEntry != null) {
			existingCoverImageFileEntryId =
				existingEntry.getCoverImageFileEntryId();
			existingSmallImageFileEntryId =
				existingEntry.getSmallImageFileEntryId();
		}

		if (portletDataContext.isDataStrategyMirror() &&
			(existingEntry == null)) {

			serviceContext.setUuid(entry.getUuid());
		}

		BlogsEntry importedEntry = null;

		if (existingEntry == null) {
			importedEntry = _blogsEntryLocalService.addEntry(
				userId, entry.getTitle(), entry.getSubtitle(),
				entry.getDescription(), entry.getContent(),
				entry.getDisplayDate(), entry.isAllowPingbacks(),
				entry.isAllowTrackbacks(), trackbacks,
				entry.getCoverImageCaption(), null, null, serviceContext);
		}
		else {
			importedEntry = _blogsEntryLocalService.updateEntry(
				userId, existingEntry.getEntryId(), entry.getTitle(),
				entry.getSubtitle(), entry.getDescription(), entry.getContent(),
				entry.getDisplayDate(), entry.isAllowPingbacks(),
				entry.isAllowTrackbacks(), trackbacks,
				entry.getCoverImageCaption(), null, null, serviceContext);
		}

		serviceContext.setModifiedDate(importedEntry.getModifiedDate());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {

			// Cover image

			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			long coverImageFileEntryId = MapUtil.getLong(
				fileEntryIds, entry.getCoverImageFileEntryId(), 0);

			importedEntry.setCoverImageFileEntryId(coverImageFileEntryId);

			importedEntry = _blogsEntryLocalService.updateBlogsEntry(
				importedEntry);

			if ((existingCoverImageFileEntryId != 0) &&
				(entry.getCoverImageFileEntryId() == 0)) {

				PortletFileRepositoryUtil.deletePortletFileEntry(
					existingCoverImageFileEntryId);
			}

			// Small image

			if (entry.isSmallImage()) {
				long smallImageFileEntryId = MapUtil.getLong(
					fileEntryIds, entry.getSmallImageFileEntryId(), 0);

				importedEntry.setSmallImageFileEntryId(smallImageFileEntryId);

				if (smallImageFileEntryId == 0) {
					importedEntry.setSmallImage(false);
				}

				importedEntry = _blogsEntryLocalService.updateBlogsEntry(
					importedEntry);

				if ((existingSmallImageFileEntryId != 0) &&
					(entry.getSmallImageFileEntryId() == 0)) {

					PortletFileRepositoryUtil.deletePortletFileEntry(
						existingSmallImageFileEntryId);
				}
			}

			Map<Long, Long> newPrimaryKeysMap =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					BlogsEntry.class);

			newPrimaryKeysMap.put(
				entry.getEntryId(), importedEntry.getEntryId());

			_importFriendlyURLEntries(portletDataContext, entry, importedEntry);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

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

	@Override
	protected String[] getSkipImportReferenceStagedModelNames() {
		return _SKIP_IMPORT_REFERENCE_STAGED_MODEL_NAMES;
	}

	private void _exportFriendlyURLEntries(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortletDataException {

		long classNameId = _portal.getClassNameId(BlogsEntry.class);

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				blogsEntry.getGroupId(), classNameId, blogsEntry.getEntryId());

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, blogsEntry, friendlyURLEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _importFriendlyURLEntries(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry,
			BlogsEntry importedBlogsEntry)
		throws PortalException {

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

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				BlogsEntry.class, importedBlogsEntry.getEntryId());

		importedBlogsEntry.setUrlTitle(mainFriendlyURLEntry.getUrlTitle());

		_blogsEntryLocalService.updateBlogsEntry(importedBlogsEntry);
	}

	private static final String[] _SKIP_IMPORT_REFERENCE_STAGED_MODEL_NAMES =
		{FriendlyURLEntry.class.getName()};

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryStagedModelDataHandler.class);

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)"
	)
	private volatile ExportImportContentProcessor<String>
		_exportImportContentProcessor;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private Portal _portal;

}