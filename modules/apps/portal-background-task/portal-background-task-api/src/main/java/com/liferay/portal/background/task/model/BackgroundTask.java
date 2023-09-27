/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BackgroundTask service. Represents a row in the &quot;BackgroundTask&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.background.task.model.impl.BackgroundTaskImpl"
)
@ProviderType
public interface BackgroundTask extends BackgroundTaskModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.background.task.model.impl.BackgroundTaskImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BackgroundTask, Long>
		BACKGROUND_TASK_ID_ACCESSOR = new Accessor<BackgroundTask, Long>() {

			@Override
			public Long get(BackgroundTask backgroundTask) {
				return backgroundTask.getBackgroundTaskId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BackgroundTask> getTypeClass() {
				return BackgroundTask.class;
			}

		};

	public void addAttachment(long userId, String fileName, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addAttachment(
			long userId, String fileName, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public String getStatusLabel();

	public boolean isInProgress();

}