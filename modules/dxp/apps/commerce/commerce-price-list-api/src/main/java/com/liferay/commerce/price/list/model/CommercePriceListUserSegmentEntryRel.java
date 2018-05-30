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