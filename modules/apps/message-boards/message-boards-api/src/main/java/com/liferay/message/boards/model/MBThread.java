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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the MBThread service. Represents a row in the &quot;MBThread&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadModel
 * @see com.liferay.message.boards.model.impl.MBThreadImpl
 * @see com.liferay.message.boards.model.impl.MBThreadModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.message.boards.model.impl.MBThreadImpl")
@ProviderType
public interface MBThread extends MBThreadModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.message.boards.model.impl.MBThreadImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBThread, Long> THREAD_ID_ACCESSOR = new Accessor<MBThread, Long>() {
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

	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.lock.Lock getLock();

	public long[] getParticipantUserIds();

	public boolean hasLock(long userId);

	public boolean isLocked();
}