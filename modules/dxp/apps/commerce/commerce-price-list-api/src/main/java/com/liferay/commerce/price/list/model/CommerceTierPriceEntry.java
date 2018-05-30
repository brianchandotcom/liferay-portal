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

package com.liferay.commerce.price.list.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceTierPriceEntry service. Represents a row in the &quot;CommerceTierPriceEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryModel
 * @see com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl
 * @see com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl")
@ProviderType
public interface CommerceTierPriceEntry extends CommerceTierPriceEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTierPriceEntry, Long> COMMERCE_TIER_PRICE_ENTRY_ID_ACCESSOR =
		new Accessor<CommerceTierPriceEntry, Long>() {
			@Override
			public Long get(CommerceTierPriceEntry commerceTierPriceEntry) {
				return commerceTierPriceEntry.getCommerceTierPriceEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceTierPriceEntry> getTypeClass() {
				return CommerceTierPriceEntry.class;
			}
		};

	public CommercePriceEntry getCommercePriceEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney getPriceMoney(
		long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney getPromoPriceMoney(
		long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException;
}