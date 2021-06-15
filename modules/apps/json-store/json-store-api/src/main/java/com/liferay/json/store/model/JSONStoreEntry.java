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

package com.liferay.json.store.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the JSONStoreEntry service. Represents a row in the &quot;JSONStoreEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Preston Crary
 * @see JSONStoreEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.json.store.model.impl.JSONStoreEntryImpl")
@ProviderType
public interface JSONStoreEntry extends JSONStoreEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.json.store.model.impl.JSONStoreEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JSONStoreEntry, Long>
		JSON_STORE_ENTRY_ID_ACCESSOR = new Accessor<JSONStoreEntry, Long>() {

			@Override
			public Long get(JSONStoreEntry jsonStoreEntry) {
				return jsonStoreEntry.getJsonStoreEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<JSONStoreEntry> getTypeClass() {
				return JSONStoreEntry.class;
			}

		};

	public Object getValue();

	public void setValue(Object value);

}