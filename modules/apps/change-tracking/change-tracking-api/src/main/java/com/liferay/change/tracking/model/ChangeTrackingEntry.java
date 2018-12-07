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

package com.liferay.change.tracking.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ChangeTrackingEntry service. Represents a row in the &quot;ChangeTrackingEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntryModel
 * @see com.liferay.change.tracking.model.impl.ChangeTrackingEntryImpl
 * @see com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.change.tracking.model.impl.ChangeTrackingEntryImpl")
@ProviderType
public interface ChangeTrackingEntry extends ChangeTrackingEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ChangeTrackingEntry, Long> CHANGE_TRACKING_ENTRY_ID_ACCESSOR =
		new Accessor<ChangeTrackingEntry, Long>() {
			@Override
			public Long get(ChangeTrackingEntry changeTrackingEntry) {
				return changeTrackingEntry.getChangeTrackingEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ChangeTrackingEntry> getTypeClass() {
				return ChangeTrackingEntry.class;
			}
		};
}