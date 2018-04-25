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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommercePriceListUserSegmentEntryRel service. Represents a row in the &quot;CPLUserSegmentEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelModel
 * @see com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelImpl
 * @see com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelImpl")
@ProviderType
public interface CommercePriceListUserSegmentEntryRel
	extends CommercePriceListUserSegmentEntryRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePriceListUserSegmentEntryRel, Long> COMMERCE_PRICE_LIST_USER_SEGMENT_ENTRY_REL_ID_ACCESSOR =
		new Accessor<CommercePriceListUserSegmentEntryRel, Long>() {
			@Override
			public Long get(
				CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
				return commercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommercePriceListUserSegmentEntryRel> getTypeClass() {
				return CommercePriceListUserSegmentEntryRel.class;
			}
		};
}