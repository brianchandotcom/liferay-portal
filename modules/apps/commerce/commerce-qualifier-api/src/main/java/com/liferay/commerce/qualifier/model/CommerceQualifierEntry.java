/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceQualifierEntry service. Represents a row in the &quot;CommerceQualifierEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Riccardo Alberti
 * @see CommerceQualifierEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.qualifier.model.impl.CommerceQualifierEntryImpl"
)
@ProviderType
public interface CommerceQualifierEntry
	extends CommerceQualifierEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.qualifier.model.impl.CommerceQualifierEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceQualifierEntry, Long>
		COMMERCE_QUALIFIER_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceQualifierEntry, Long>() {

				@Override
				public Long get(CommerceQualifierEntry commerceQualifierEntry) {
					return commerceQualifierEntry.getCommerceQualifierEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceQualifierEntry> getTypeClass() {
					return CommerceQualifierEntry.class;
				}

			};

}