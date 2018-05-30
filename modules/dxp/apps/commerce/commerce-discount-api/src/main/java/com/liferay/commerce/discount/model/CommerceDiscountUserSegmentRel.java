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
 * The extended model interface for the CommerceDiscountUserSegmentRel service. Represents a row in the &quot;CommerceDiscountUserSegmentRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRelModel
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelImpl
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelImpl")
@ProviderType
public interface CommerceDiscountUserSegmentRel
	extends CommerceDiscountUserSegmentRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountUserSegmentRel, Long> COMMERCE_DISCOUNT_USER_SEGMENT_REL_ID_ACCESSOR =
		new Accessor<CommerceDiscountUserSegmentRel, Long>() {
			@Override
			public Long get(
				CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
				return commerceDiscountUserSegmentRel.getCommerceDiscountUserSegmentRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceDiscountUserSegmentRel> getTypeClass() {
				return CommerceDiscountUserSegmentRel.class;
			}
		};

	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry getCommerceUserSegmentEntry()
		throws com.liferay.portal.kernel.exception.PortalException;
}