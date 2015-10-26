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

package com.liferay.portlet.messageboards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MBMessage service. Represents a row in the &quot;MBMessage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageModel
 * @see com.liferay.portlet.messageboards.model.impl.MBMessageImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl
 * @generated
 */
@ProviderType
public interface MBMessage extends MBMessageModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.messageboards.model.impl.MBMessageImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBMessage, Long> MESSAGE_ID_ACCESSOR = new Accessor<MBMessage, Long>() {
			@Override
			public Long get(MBMessage mbMessage) {
				return mbMessage.getMessageId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MBMessage> getTypeClass() {
				return MBMessage.class;
			}
		};

	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String[] getAssetTagNames();

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getBody(boolean translate);

	public com.liferay.portlet.messageboards.model.MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.messageboards.model.MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getThreadAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getWorkflowClassName();

	public boolean isDiscussion();

	public boolean isFormatBBCode();

	public boolean isReply();

	public boolean isRoot();

	public void setAttachmentsFolderId(long attachmentsFolderId);
}