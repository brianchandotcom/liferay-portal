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

package com.liferay.trash.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the TrashEntry service. Represents a row in the &quot;TrashEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryModel
 * @see com.liferay.portlet.trash.model.impl.TrashEntryImpl
 * @see com.liferay.portlet.trash.model.impl.TrashEntryModelImpl
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.trash.model.impl.TrashEntryImpl}
 * @generated
 */
@Deprecated
@ImplementationClassName("com.liferay.portlet.trash.model.impl.TrashEntryImpl")
@ProviderType
public interface TrashEntry extends TrashEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.trash.model.impl.TrashEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TrashEntry, Long> ENTRY_ID_ACCESSOR = new Accessor<TrashEntry, Long>() {
			@Override
			public Long get(TrashEntry trashEntry) {
				return trashEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TrashEntry> getTypeClass() {
				return TrashEntry.class;
			}
		};

	public TrashEntry getRootEntry();

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public java.lang.String getTypeSettingsProperty(java.lang.String key);

	public java.lang.String getTypeSettingsProperty(java.lang.String key,
		java.lang.String defaultValue);

	public boolean isTrashEntry(java.lang.Class<?> clazz, long classPK);

	public boolean isTrashEntry(java.lang.String className, long classPK);

	public void setRootEntry(TrashEntry rootEntry);

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);
}