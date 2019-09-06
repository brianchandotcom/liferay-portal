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

package com.liferay.mail.reader.service.impl;

import com.liferay.document.library.kernel.exception.DirectoryNameException;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.mail.reader.model.Attachment;
import com.liferay.mail.reader.model.Message;
import com.liferay.mail.reader.service.base.AttachmentLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Scott Lee
 */
@Component(
	property = "model.class.name=com.liferay.mail.reader.model.Attachment",
	service = AopService.class
)
public class AttachmentLocalServiceImpl extends AttachmentLocalServiceBaseImpl {

	@Override
	public Attachment addAttachment(
			long userId, long messageId, String contentPath, String fileName,
			long size, File file)
		throws PortalException {

		// Attachment

		User user = userLocalService.getUser(userId);
		Message message = messagePersistence.findByPrimaryKey(messageId);

		long attachmentId = counterLocalService.increment();

		Attachment attachment = attachmentPersistence.create(attachmentId);

		attachment.setCompanyId(user.getCompanyId());
		attachment.setUserId(user.getUserId());
		attachment.setAccountId(message.getAccountId());
		attachment.setFolderId(message.getFolderId());
		attachment.setMessageId(messageId);
		attachment.setContentPath(contentPath);
		attachment.setFileName(fileName);
		attachment.setSize(size);

		attachmentPersistence.update(attachment);

		// File

		if (file != null) {
			if (!file.exists()) {
				throw new PortalException(new FileNotFoundException());
			}

			String directoryPath = getDirectoryPath(attachment.getMessageId());

			try {
				DLStoreUtil.addDirectory(
					attachment.getCompanyId(), _REPOSITORY_ID, directoryPath);
			}
			catch (DirectoryNameException dne) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(dne, dne);
				}
			}

			String filePath = getFilePath(attachment.getMessageId(), fileName);

			try {
				DLStoreUtil.addFile(
					attachment.getCompanyId(), _REPOSITORY_ID, filePath, file);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return attachment;
	}

	@Override
	public Attachment deleteAttachment(long attachmentId)
		throws PortalException {

		// Attachment

		Attachment attachment = attachmentPersistence.findByPrimaryKey(
			attachmentId);

		attachmentPersistence.remove(attachmentId);

		// File

		String filePath = getFilePath(
			attachment.getMessageId(), attachment.getFileName());

		DLStoreUtil.deleteFile(
			attachment.getCompanyId(), _REPOSITORY_ID, filePath);

		return attachment;
	}

	@Override
	public void deleteAttachments(long companyId, long messageId)
		throws PortalException {

		// Attachments

		List<Attachment> attachments = attachmentPersistence.findByMessageId(
			messageId);

		for (Attachment attachment : attachments) {
			deleteAttachment(attachment);
		}

		// File

		DLStoreUtil.deleteDirectory(
			companyId, _REPOSITORY_ID, getDirectoryPath(messageId));
	}

	@Override
	public List<Attachment> getAttachments(long messageId) {
		return attachmentPersistence.findByMessageId(messageId);
	}

	@Override
	public File getFile(long attachmentId) throws PortalException {
		try {
			File file = FileUtil.createTempFile();

			FileUtil.write(file, getInputStream(attachmentId));

			return file;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public InputStream getInputStream(long attachmentId)
		throws PortalException {

		Attachment attachment = attachmentPersistence.findByPrimaryKey(
			attachmentId);

		String filePath = getFilePath(
			attachment.getMessageId(), attachment.getFileName());

		return DLStoreUtil.getFileAsStream(
			attachment.getCompanyId(), _REPOSITORY_ID, filePath);
	}

	protected String getDirectoryPath(long messageId) {
		return _DIRECTORY_PATH_PREFIX.concat(String.valueOf(messageId));
	}

	protected String getFilePath(long messageId, String filename) {
		return getDirectoryPath(
			messageId
		).concat(
			StringPool.SLASH
		).concat(
			filename
		);
	}

	private static final String _DIRECTORY_PATH_PREFIX = "mail/";

	private static final long _REPOSITORY_ID = CompanyConstants.SYSTEM;

	private static final Log _log = LogFactoryUtil.getLog(
		AttachmentLocalServiceImpl.class);

}