/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.discount.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceDiscountUsageEntry service. Represents a row in the &quot;CommerceDiscountUsageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryModel
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl")
@ProviderType
public interface CommerceDiscountUsageEntry
	extends CommerceDiscountUsageEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountUsageEntry, Long> COMMERCE_DISCOUNT_USAGE_ENTRY_ID_ACCESSOR =
		new Accessor<CommerceDiscountUsageEntry, Long>() {
			@Override
			public Long get(
				CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
				return commerceDiscountUsageEntry.getCommerceDiscountUsageEntryId();
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