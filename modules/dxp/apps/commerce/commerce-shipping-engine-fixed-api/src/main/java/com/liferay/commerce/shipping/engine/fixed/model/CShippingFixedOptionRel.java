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

package com.liferay.commerce.shipping.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CShippingFixedOptionRel service. Represents a row in the &quot;CShippingFixedOptionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelModel
 * @see com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelImpl
 * @see com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelImpl")
@ProviderType
public interface CShippingFixedOptionRel extends CShippingFixedOptionRelModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CShippingFixedOptionRel, Long> C_SHIPPING_FIXED_OPTION_REL_ID_ACCESSOR =
		new Accessor<CShippingFixedOptionRel, Long>() {
			@Override
			public Long get(CShippingFixedOptionRel cShippingFixedOptionRel) {
				return cShippingFixedOptionRel.getCShippingFixedOptionRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CShippingFixedOptionRel> getTypeClass() {
				return CShippingFixedOptionRel.class;
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