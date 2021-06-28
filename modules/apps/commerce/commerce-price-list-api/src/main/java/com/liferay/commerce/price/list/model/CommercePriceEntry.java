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
 * The extended model interface for the CommercePriceEntry service. Represents a row in the &quot;CommercePriceEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.price.list.model.impl.CommercePriceEntryImpl"
)
@ProviderType
public interface CommercePriceEntry
	extends CommercePriceEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.price.list.model.impl.CommercePriceEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePriceEntry, Long>
		COMMERCE_PRICE_ENTRY_ID_ACCESSOR =
			new Accessor<CommercePriceEntry, Long>() {

				@Override
				public Long get(CommercePriceEntry commercePriceEntry) {
					return commercePriceEntry.getCommercePriceEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePriceEntry> getTypeClass() {
					return CommercePriceEntry.class;
				}

			};

	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPInstance getCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getPriceCommerceMoney(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getPromoPriceCommerceMoney(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException;

}