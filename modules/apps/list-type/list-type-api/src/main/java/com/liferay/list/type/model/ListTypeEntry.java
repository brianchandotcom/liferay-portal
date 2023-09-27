/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ListTypeEntry service. Represents a row in the &quot;ListTypeEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see ListTypeEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.list.type.model.impl.ListTypeEntryImpl")
@ProviderType
public interface ListTypeEntry extends ListTypeEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.list.type.model.impl.ListTypeEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ListTypeEntry, Long>
		LIST_TYPE_ENTRY_ID_ACCESSOR = new Accessor<ListTypeEntry, Long>() {

			@Override
			public Long get(ListTypeEntry listTypeEntry) {
				return listTypeEntry.getListTypeEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ListTypeEntry> getTypeClass() {
				return ListTypeEntry.class;
			}

		};

}