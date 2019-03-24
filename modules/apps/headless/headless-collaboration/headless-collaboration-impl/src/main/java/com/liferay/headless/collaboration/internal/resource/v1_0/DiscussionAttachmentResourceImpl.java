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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.DiscussionAttachment;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionAttachmentResource;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/discussion-attachment.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscussionAttachmentResource.class
)
public class DiscussionAttachmentResourceImpl
	extends BaseDiscussionAttachmentResourceImpl {

	@Override
	public void deleteDiscussionAttachment(Long discussionAttachmentId)
		throws Exception {

		_portletFileRepository.deletePortletFileEntry(discussionAttachmentId);
	}

	@Override
	public DiscussionAttachment getDiscussionAttachment(
			Long discussionAttachmentId)
		throws Exception {

		return _toDiscussionAttachment(
			_portletFileRepository.getPortletFileEntry(discussionAttachmentId));
	}

	@Override
	public Page<DiscussionAttachment>
			getDiscussionForumPostingDiscussionAttachmentsPage(
				Long discussionForumPostingId)
		throws Exception {

		return _getDiscussionAttachmentPage(discussionForumPostingId);
	}

	@Override
	public Page<DiscussionAttachment>
			getDiscussionThreadDiscussionAttachmentsPage(
				Long discussionThreadId)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			discussionThreadId);

		return _getDiscussionAttachmentPage(mbThread.getRootMessageId());
	}

	@Override
	public DiscussionAttachment postDiscussionForumPostingDiscussionAttachment(
			Long discussionForumPostingId, MultipartBody multipartBody)
		throws Exception {

		return _addDiscussionAttachment(
			discussionForumPostingId, multipartBody);
	}

	@Override
	public DiscussionAttachment postDiscussionThreadDiscussionAttachment(
			Long discussionThreadId, MultipartBody multipartBody)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			discussionThreadId);

		return _addDiscussionAttachment(
			mbThread.getRootMessageId(), multipartBody);
	}

	private DiscussionAttachment _addDiscussionAttachment(
			Long discussionForumPostingId, MultipartBody multipartBody)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			discussionForumPostingId);
		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		return _toDiscussionAttachment(
			_portletFileRepository.addPortletFileEntry(
				mbMessage.getGroupId(), _user.getUserId(),
				MBMessage.class.getName(), mbMessage.getClassPK(),
				MBConstants.SERVICE_NAME, mbMessage.getAttachmentsFolderId(),
				binaryFile.getInputStream(), binaryFile.getFileName(),
				binaryFile.getFileName(), false));
	}

	private Page<DiscussionAttachment> _getDiscussionAttachmentPage(
			Long discussionForumPostingId)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			discussionForumPostingId);

		return Page.of(
			transform(
				mbMessage.getAttachmentsFileEntries(),
				this::_toDiscussionAttachment));
	}

	private DiscussionAttachment _toDiscussionAttachment(FileEntry fileEntry)
		throws Exception {

		return new DiscussionAttachment() {
			{
				contentUrl = _dlurlHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Context
	private User _user;

}