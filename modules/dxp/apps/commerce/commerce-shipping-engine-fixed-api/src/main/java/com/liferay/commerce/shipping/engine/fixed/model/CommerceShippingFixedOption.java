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
 * The extended model interface for the CommerceShippingFixedOption service. Represents a row in the &quot;CommerceShippingFixedOption&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionModel
 * @see com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl
 * @see com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl")
@ProviderType
public interface CommerceShippingFixedOption
	extends CommerceShippingFixedOptionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShippingFixedOption, Long> COMMERCE_SHIPPING_FIXED_OPTION_ID_ACCESSOR =
		new Accessor<CommerceShippingFixedOption, Long>() {
			@Override
			public Long get(
				CommerceShippingFixedOption commerceShippingFixedOption) {
				return commerceShippingFixedOption.getCommerceShippingFixedOptionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceShippingFixedOption> getTypeClass() {
				return CommerceShippingFixedOption.class;
			}
		};
}