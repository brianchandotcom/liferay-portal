/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceTermEntry service. Represents a row in the &quot;CommerceTermEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.term.model.impl.CommerceTermEntryImpl"
)
@ProviderType
public interface CommerceTermEntry
	extends CommerceTermEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.term.model.impl.CommerceTermEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTermEntry, Long>
		COMMERCE_TERM_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceTermEntry, Long>() {

				@Override
				public Long get(CommerceTermEntry commerceTermEntry) {
					return commerceTermEntry.getCommerceTermEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceTermEntry> getTypeClass() {
					return CommerceTermEntry.class;
				}

			};

	public void setTypeSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties);

}