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

package com.liferay.message.boards.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MBMessage service. Represents a row in the &quot;MBMessage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageModel
 * @generated
 */
@ImplementationClassName("com.liferay.message.boards.model.impl.MBMessageImpl")
@ProviderType
public interface MBMessage extends MBMessageModel, PersistedModel, TreeModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.message.boards.model.impl.MBMessageImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBMessage, Long> MESSAGE_ID_ACCESSOR =
		new Accessor<MBMessage, Long>() {

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

	public com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String[] getAssetTagNames();

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getBody(boolean translate);

	public MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getThreadAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getWorkflowClassName();

	public boolean isDiscussion();

	public boolean isFormatBBCode();

	public boolean isReply();

	public boolean isRoot();

	public void setAttachmentsFolderId(long attachmentsFolderId);

}