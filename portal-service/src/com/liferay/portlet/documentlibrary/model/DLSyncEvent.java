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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DLSyncEvent service. Represents a row in the &quot;DLSyncEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLSyncEventImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl
 * @generated
 */
@ProviderType
public interface DLSyncEvent extends DLSyncEventModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLSyncEventImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLSyncEvent, Long> SYNC_EVENT_ID_ACCESSOR = new Accessor<DLSyncEvent, Long>() {
			@Override
			public Long get(DLSyncEvent dlSyncEvent) {
				return dlSyncEvent.getSyncEventId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLSyncEvent> getTypeClass() {
				return DLSyncEvent.class;
			}
		};
}