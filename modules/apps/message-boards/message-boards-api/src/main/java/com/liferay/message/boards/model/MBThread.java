/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MBThread service. Represents a row in the &quot;MBThread&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadModel
 * @generated
 */
@ImplementationClassName("com.liferay.message.boards.model.impl.MBThreadImpl")
@ProviderType
public interface MBThread extends MBThreadModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.message.boards.model.impl.MBThreadImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBThread, Long> THREAD_ID_ACCESSOR =
		new Accessor<MBThread, Long>() {

			@Override
			public Long get(MBThread mbThread) {
				return mbThread.getThreadId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MBThread> getTypeClass() {
				return MBThread.class;
			}

		};

	public com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.lock.Lock getLock();

	public int getMessageCount();

	public long[] getParticipantUserIds();

	public long getViewCount();

	public boolean hasLock(long userId);

	public boolean isLocked();

}