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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceTirePriceEntry service. Represents a row in the &quot;CommerceTirePriceEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryModel
 * @see com.liferay.commerce.model.impl.CommerceTirePriceEntryImpl
 * @see com.liferay.commerce.model.impl.CommerceTirePriceEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceTirePriceEntryImpl")
@ProviderType
public interface CommerceTirePriceEntry extends CommerceTirePriceEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceTirePriceEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTirePriceEntry, Long> COMMERCE_TIRE_PRICE_ENTRY_ID_ACCESSOR =
		new Accessor<CommerceTirePriceEntry, Long>() {
			@Override
			public Long get(CommerceTirePriceEntry commerceTirePriceEntry) {
				return commerceTirePriceEntry.getCommerceTirePriceEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceTirePriceEntry> getTypeClass() {
				return CommerceTirePriceEntry.class;
			}
		};

	public CommercePriceEntry getCommercePriceEntry()
		throws com.liferay.portal.kernel.exception.PortalException;
}