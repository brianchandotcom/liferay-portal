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
 * The extended model interface for the CommerceTermEntryRel service. Represents a row in the &quot;CommerceTermEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.term.model.impl.CommerceTermEntryRelImpl"
)
@ProviderType
public interface CommerceTermEntryRel
	extends CommerceTermEntryRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.term.model.impl.CommerceTermEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTermEntryRel, Long>
		COMMERCE_TERM_ENTRY_REL_ID_ACCESSOR =
			new Accessor<CommerceTermEntryRel, Long>() {

				@Override
				public Long get(CommerceTermEntryRel commerceTermEntryRel) {
					return commerceTermEntryRel.getCommerceTermEntryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceTermEntryRel> getTypeClass() {
					return CommerceTermEntryRel.class;
				}

			};

}