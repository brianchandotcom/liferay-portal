/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.json.storage.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the JSONStorageEntry service. Represents a row in the &quot;JSONStorageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Preston Crary
 * @see JSONStorageEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.json.storage.model.impl.JSONStorageEntryImpl"
)
@ProviderType
public interface JSONStorageEntry
	extends JSONStorageEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.json.storage.model.impl.JSONStorageEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JSONStorageEntry, Long>
		JSON_STORAGE_ENTRY_ID_ACCESSOR =
			new Accessor<JSONStorageEntry, Long>() {

				@Override
				public Long get(JSONStorageEntry jsonStorageEntry) {
					return jsonStorageEntry.getJsonStorageEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<JSONStorageEntry> getTypeClass() {
					return JSONStorageEntry.class;
				}

			};

	public Object getValue();

	public void setValue(Object value);

}