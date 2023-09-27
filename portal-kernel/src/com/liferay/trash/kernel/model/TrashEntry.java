/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.kernel.model;

import org.osgi.annotation.versioning.ProviderType;

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
 * @deprecated As of Judson (7.1.x), replaced by {@link
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