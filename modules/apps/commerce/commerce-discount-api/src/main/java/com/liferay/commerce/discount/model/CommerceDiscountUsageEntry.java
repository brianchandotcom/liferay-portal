/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceDiscountUsageEntry service. Represents a row in the &quot;CommerceDiscountUsageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl"
)
@ProviderType
public interface CommerceDiscountUsageEntry
	extends CommerceDiscountUsageEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountUsageEntry, Long>
		COMMERCE_DISCOUNT_USAGE_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceDiscountUsageEntry, Long>() {

				@Override
				public Long get(
					CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

					return commerceDiscountUsageEntry.
						getCommerceDiscountUsageEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceDiscountUsageEntry> getTypeClass() {
					return CommerceDiscountUsageEntry.class;
				}

			};

}