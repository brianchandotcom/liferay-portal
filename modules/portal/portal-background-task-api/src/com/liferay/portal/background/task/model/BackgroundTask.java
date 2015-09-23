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

package com.liferay.portal.background.task.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the BackgroundTask service. Represents a row in the &quot;BackgroundTask&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskModel
 * @see com.liferay.portal.background.task.model.impl.BackgroundTaskImpl
 * @see com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl
 * @generated
 */
@ProviderType
public interface BackgroundTask extends BackgroundTaskModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.background.task.model.impl.BackgroundTaskImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addAttachment(long userId, java.lang.String fileName,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addAttachment(long userId, java.lang.String fileName,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public java.lang.String getStatusLabel();

	public boolean isInProgress();
}