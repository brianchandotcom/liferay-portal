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

package com.liferay.message.boards.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MBMessageStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBMessage> {

	public static final String[] CLASS_NAMES = {MBMessage.class.getName()};

	@Override
	public void deleteStagedModel(MBMessage message) throws PortalException {
		_mbMessageLocalService.deleteMessage(message);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MBMessage message = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (message != null) {
			deleteStagedModel(message);
		}
	}

	@Override
	public MBMessage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mbMessageLocalService.fetchMBMessageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<MBMessage> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mbMessageLocalService.getMBMessagesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MBMessage message) {
		return message.getSubject();
	}

	@Override
	public void importStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws PortletDataException {

		if (message.isDiscussion()) {
			Map<Long, Long> relatedClassPKs =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					message.getClassName());

			if (!relatedClassPKs.containsKey(message.getClassPK())) {
				return;
			}
		}

		super.importStagedModel(portletDataContext, message);
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		if (message.isDiscussion()) {
			MBDiscussion discussion = _mbDiscussionLocalService.getDiscussion(
				message.getClassName(), message.getClassPK());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, discussion,
				PortletDataContext.REFERENCE_TYPE_PARENT);

			// Ratings that belong to discussion messages cannot be exported
			// automatically because of the special class name and class PK pair

			List<RatingsEntry> ratingsEntries =
				_ratingsEntryLocalService.getEntries(
					MBDiscussion.class.getName(), message.getMessageId());

			for (RatingsEntry ratingsEntry : ratingsEntries) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, message, ratingsEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}
		}
		else if (message.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, message.getCategory(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		if (!message.isRoot()) {
			MBMessage parentMessage = _mbMessageLocalService.getMessage(
				message.getParentMessageId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, parentMessage,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		message.setPriority(message.getPriority());

		MBThread thread = message.getThread();

		Element messageElement = portletDataContext.getExportDataElement(
			message);

		messageElement.addAttribute(
			"question", String.valueOf(thread.isQuestion()));
		messageElement.addAttribute("threadUuid", thread.getUuid());

		boolean hasAttachmentsFileEntries = false;

		if (message.getAttachmentsFileEntriesCount() > 0) {
			hasAttachmentsFileEntries = true;
		}

		messageElement.addAttribute(
			"hasAttachmentsFileEntries",
			String.valueOf(hasAttachmentsFileEntries));

		if (hasAttachmentsFileEntries) {
			for (FileEntry fileEntry : message.getAttachmentsFileEntries()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, message, fileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}

			long folderId = message.getAttachmentsFolderId();

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				message.setAttachmentsFolderId(folderId);
			}
		}

		portletDataContext.addClassedModel(
			messageElement, ExportImportPathUtil.getModelPath(message),
			message);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long messageId) {

		MBMessage existingMessage = fetchMissingReference(uuid, groupId);

		if (existingMessage == null) {
			return;
		}

		Map<Long, Long> messageIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBMessage.class);

		messageIds.put(messageId, existingMessage.getMessageId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		if (!message.isRoot()) {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, message, MBMessage.class,
				(Serializable)message.getParentMessageId());
		}

		long userId = portletDataContext.getUserId(message.getUserUuid());

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, message.getCategoryId(), message.getCategoryId());

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		long threadId = MapUtil.getLong(threadIds, message.getThreadId(), 0);

		Element messageElement =
			portletDataContext.getImportDataStagedModelElement(message);

		if (threadId == 0) {
			String threadUuid = messageElement.attributeValue("threadUuid");

			MBThread thread =
				_mbThreadLocalService.fetchMBThreadByUuidAndGroupId(
					threadUuid, portletDataContext.getScopeGroupId());

			if (thread != null) {
				threadId = thread.getThreadId();
			}
		}

		Map<Long, Long> messageIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBMessage.class);

		long parentMessageId = MapUtil.getLong(
			messageIds, message.getParentMessageId(),
			message.getParentMessageId());

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			_getAttachments(portletDataContext, messageElement, message);

		try {
			ServiceContext serviceContext =
				portletDataContext.createServiceContext(message);

			MBMessage importedMessage = null;

			if (portletDataContext.isDataStrategyMirror()) {
				MBMessage existingMessage = fetchStagedModelByUuidAndGroupId(
					message.getUuid(), portletDataContext.getScopeGroupId());

				if (existingMessage == null) {
					serviceContext.setUuid(message.getUuid());

					if (message.isDiscussion()) {
						importedMessage = _addDiscussionMessage(
							portletDataContext, userId, threadId,
							parentMessageId, message, serviceContext);
					}
					else {
						importedMessage = _mbMessageLocalService.addMessage(
							userId, message.getUserName(),
							portletDataContext.getScopeGroupId(),
							parentCategoryId, threadId, parentMessageId,
							message.getSubject(), message.getBody(),
							message.getFormat(), inputStreamOVPs,
							message.isAnonymous(), message.getPriority(),
							message.isAllowPingbacks(), serviceContext);
					}
				}
				else {
					if (!message.isRoot() && message.isDiscussion()) {
						MBDiscussion discussion =
							_mbDiscussionLocalService.getThreadDiscussion(
								threadId);

						importedMessage =
							_mbMessageLocalService.updateDiscussionMessage(
								userId, existingMessage.getMessageId(),
								discussion.getClassName(),
								discussion.getClassPK(), message.getSubject(),
								message.getBody(), serviceContext);
					}
					else {
						List<FileEntry> fileEntries =
							PortletFileRepositoryUtil.getPortletFileEntries(
								existingMessage.getGroupId(),
								existingMessage.getAttachmentsFolderId());

						for (FileEntry fileEntry : fileEntries) {
							PortletFileRepositoryUtil.deletePortletFileEntry(
								fileEntry.getFileEntryId());
						}

						importedMessage = _mbMessageLocalService.updateMessage(
							userId, existingMessage.getMessageId(),
							message.getSubject(), message.getBody(),
							inputStreamOVPs, message.getPriority(),
							message.isAllowPingbacks(), serviceContext);
					}
				}
			}
			else {
				if (message.isDiscussion()) {
					importedMessage = _addDiscussionMessage(
						portletDataContext, userId, threadId, parentMessageId,
						message, serviceContext);
				}
				else {
					importedMessage = _mbMessageLocalService.addMessage(
						userId, message.getUserName(),
						portletDataContext.getScopeGroupId(), parentCategoryId,
						threadId, parentMessageId, message.getSubject(),
						message.getBody(), message.getFormat(), inputStreamOVPs,
						message.isAnonymous(), message.getPriority(),
						message.isAllowPingbacks(), serviceContext);
				}
			}

			importedMessage = _updateAnswer(message, importedMessage);

			if (importedMessage.isRoot() && !importedMessage.isDiscussion()) {
				_mbThreadLocalService.updateQuestion(
					importedMessage.getThreadId(),
					GetterUtil.getBoolean(
						messageElement.attributeValue("question")));
			}

			if (message.isDiscussion()) {
				Map<Long, Long> discussionIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						MBDiscussion.class);

				discussionIds.put(
					message.getMessageId(), importedMessage.getMessageId());
			}

			threadIds.put(message.getThreadId(), importedMessage.getThreadId());

			// Keep thread UUID

			MBThread thread = importedMessage.getThread();

			thread.setUuid(messageElement.attributeValue("threadUuid"));

			_mbThreadLocalService.updateMBThread(thread);

			if (importedMessage.getCategoryId() != parentCategoryId) {
				_mbThreadLocalService.moveThread(
					thread.getGroupId(), parentCategoryId,
					thread.getThreadId());
			}

			portletDataContext.importClassedModel(message, importedMessage);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				try (InputStream inputStream = inputStreamOVP.getValue()) {
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe, ioe);
					}
				}
			}
		}
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		MBMessage existingMessage = fetchStagedModelByUuidAndGroupId(
			message.getUuid(), portletDataContext.getScopeGroupId());

		if (existingMessage == null) {
			return;
		}

		long userId = portletDataContext.getUserId(message.getUserUuid());

		if (existingMessage.isInTrash()) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					MBMessage.class.getName());

			if (trashHandler.isRestorable(existingMessage.getMessageId())) {
				trashHandler.restoreTrashEntry(
					userId, existingMessage.getMessageId());
			}
		}

		if (existingMessage.isInTrashContainer()) {
			MBThread existingThread = existingMessage.getThread();

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					MBThread.class.getName());

			if (trashHandler.isRestorable(existingThread.getThreadId())) {
				trashHandler.restoreTrashEntry(
					userId, existingThread.getThreadId());
			}
		}
	}

	private MBMessage _addDiscussionMessage(
			PortletDataContext portletDataContext, long userId, long threadId,
			long parentMessageId, MBMessage message,
			ServiceContext serviceContext)
		throws PortalException {

		if (!message.isRoot()) {
			MBDiscussion discussion =
				_mbDiscussionLocalService.getThreadDiscussion(threadId);

			return _mbMessageLocalService.addDiscussionMessage(
				userId, message.getUserName(),
				portletDataContext.getScopeGroupId(), discussion.getClassName(),
				discussion.getClassPK(), threadId, parentMessageId,
				message.getSubject(), message.getBody(), serviceContext);
		}

		MBThread thread = _mbThreadLocalService.getThread(threadId);

		return _mbMessageLocalService.getMBMessage(thread.getRootMessageId());
	}

	private List<ObjectValuePair<String, InputStream>> _getAttachments(
		PortletDataContext portletDataContext, Element messageElement,
		MBMessage message) {

		boolean hasAttachmentsFileEntries = GetterUtil.getBoolean(
			messageElement.attributeValue("hasAttachmentsFileEntries"));

		if (!hasAttachmentsFileEntries) {
			return Collections.emptyList();
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();

		List<Element> attachmentElements =
			portletDataContext.getReferenceDataElements(
				messageElement, DLFileEntry.class,
				PortletDataContext.REFERENCE_TYPE_WEAK);

		for (Element attachmentElement : attachmentElements) {
			String path = attachmentElement.attributeValue("path");

			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(path);

			InputStream inputStream = null;

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
				inputStream = portletDataContext.getZipEntryAsInputStream(
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

			ObjectValuePair<String, InputStream> inputStreamOVP =
				new ObjectValuePair<>(fileEntry.getTitle(), inputStream);

			inputStreamOVPs.add(inputStreamOVP);
		}

		if (inputStreamOVPs.isEmpty()) {
			_log.error(
				"Could not find attachments for message " +
					message.getMessageId());
		}

		return inputStreamOVPs;
	}

	private MBMessage _updateAnswer(
			MBMessage message, MBMessage importedMessage)
		throws PortalException {

		if (importedMessage.isAnswer() == message.isAnswer()) {
			return importedMessage;
		}

		Date modifiedDate = importedMessage.getModifiedDate();

		_mbMessageLocalService.updateAnswer(
			importedMessage, message.isAnswer(), false);

		importedMessage = _mbMessageLocalService.fetchMBMessage(
			importedMessage.getMessageId());

		importedMessage.setModifiedDate(modifiedDate);

		_mbMessageLocalService.updateMBMessage(importedMessage);

		return importedMessage;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageStagedModelDataHandler.class);

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

}