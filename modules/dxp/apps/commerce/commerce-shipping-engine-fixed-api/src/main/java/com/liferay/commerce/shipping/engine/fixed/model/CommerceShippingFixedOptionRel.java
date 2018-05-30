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

package com.liferay.commerce.shipping.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceShippingFixedOptionRel service. Represents a row in the &quot;CShippingFixedOptionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelModel
 * @see com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl
 * @see com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl")
@ProviderType
public interface CommerceShippingFixedOptionRel
	extends CommerceShippingFixedOptionRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShippingFixedOptionRel, Long> COMMERCE_SHIPPING_FIXED_OPTION_REL_ID_ACCESSOR =
		new Accessor<CommerceShippingFixedOptionRel, Long>() {
			@Override
			public Long get(
				CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {
				return commerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceShippingFixedOptionRel> getTypeClass() {
				return CommerceShippingFixedOptionRel.class;
			}
		};

	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceShippingFixedOption getCommerceShippingFixedOption()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException;
}