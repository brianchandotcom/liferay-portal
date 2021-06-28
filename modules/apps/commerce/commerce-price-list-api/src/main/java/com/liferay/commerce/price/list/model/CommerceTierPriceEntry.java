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

package com.liferay.commerce.price.list.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceTierPriceEntry service. Represents a row in the &quot;CommerceTierPriceEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl"
)
@ProviderType
public interface CommerceTierPriceEntry
	extends CommerceTierPriceEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTierPriceEntry, Long>
		COMMERCE_TIER_PRICE_ENTRY_ID_ACCESSOR =
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

	public com.liferay.commerce.currency.model.CommerceMoney
			getPriceCommerceMoney(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getPromoPriceCommerceMoney(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException;

}