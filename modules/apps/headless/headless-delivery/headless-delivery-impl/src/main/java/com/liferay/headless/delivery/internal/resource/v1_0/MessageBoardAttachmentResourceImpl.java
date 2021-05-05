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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.dto.v1_0.util.ContentValueUtil;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardAttachmentResource;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message-board-attachment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = MessageBoardAttachmentResource.class
)
public class MessageBoardAttachmentResourceImpl
	extends BaseMessageBoardAttachmentResourceImpl {

	@Override
	public void deleteMessageBoardAttachment(Long messageBoardAttachmentId)
		throws Exception {

		_portletFileRepository.deletePortletFileEntry(messageBoardAttachmentId);
	}

	@Override
	public MessageBoardAttachment getMessageBoardAttachment(
			Long messageBoardAttachmentId)
		throws Exception {

		return _toMessageBoardAttachment(
			_portletFileRepository.getPortletFileEntry(
				messageBoardAttachmentId));
	}

	@Override
	public Page<MessageBoardAttachment>
			getMessageBoardMessageMessageBoardAttachmentsPage(
				Long messageBoardMessageId)
		throws Exception {

		return _getMessageBoardAttachmentsPage(messageBoardMessageId);
	}

	@Override
	public Page<MessageBoardAttachment>
			getMessageBoardThreadMessageBoardAttachmentsPage(
				Long messageBoardThreadId)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		return _getMessageBoardAttachmentsPage(mbThread.getRootMessageId());
	}

	@Override
	public MessageBoardAttachment postMessageBoardMessageMessageBoardAttachment(
			Long messageBoardMessageId, MultipartBody multipartBody)
		throws Exception {

		return _addMessageBoardAttachment(messageBoardMessageId, multipartBody);
	}

	@Override
	public MessageBoardAttachment postMessageBoardThreadMessageBoardAttachment(
			Long messageBoardThreadId, MultipartBody multipartBody)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		return _addMessageBoardAttachment(
			mbThread.getRootMessageId(), multipartBody);
	}

	private MessageBoardAttachment _addMessageBoardAttachment(
			Long messageBoardMessageId, MultipartBody multipartBody)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			messageBoardMessageId);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		if (binaryFile == null) {
			throw new BadRequestException("No file found in body");
		}

		Folder folder = mbMessage.addAttachmentsFolder();

		return _toMessageBoardAttachment(
			_portletFileRepository.addPortletFileEntry(
				mbMessage.getGroupId(), contextUser.getUserId(),
				MBMessage.class.getName(), mbMessage.getClassPK(),
				MBConstants.SERVICE_NAME, folder.getFolderId(),
				binaryFile.getInputStream(), binaryFile.getFileName(),
				binaryFile.getFileName(), false));
	}

	private Page<MessageBoardAttachment> _getMessageBoardAttachmentsPage(
			Long messageBoardMessageId)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			messageBoardMessageId);

		return Page.of(
			transform(
				mbMessage.getAttachmentsFileEntries(),
				this::_toMessageBoardAttachment));
	}

	private MessageBoardAttachment _toMessageBoardAttachment(
			FileEntry fileEntry)
		throws Exception {

		return new MessageBoardAttachment() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				contentValue = ContentValueUtil.toContentValue(
					"contentValue", fileEntry::getContentStream,
					Optional.of(contextUriInfo));
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}