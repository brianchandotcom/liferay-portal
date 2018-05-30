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
 * The extended model interface for the CommerceDiscountRel service. Represents a row in the &quot;CommerceDiscountRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountRelModel
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountRelImpl
 * @see com.liferay.commerce.discount.model.impl.CommerceDiscountRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.discount.model.impl.CommerceDiscountRelImpl")
@ProviderType
public interface CommerceDiscountRel extends CommerceDiscountRelModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.discount.model.impl.CommerceDiscountRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountRel, Long> COMMERCE_DISCOUNT_REL_ID_ACCESSOR =
		new Accessor<CommerceDiscountRel, Long>() {
			@Override
			public Long get(CommerceDiscountRel commerceDiscountRel) {
				return commerceDiscountRel.getCommerceDiscountRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceDiscountRel> getTypeClass() {
				return CommerceDiscountRel.class;
			}
		};
}