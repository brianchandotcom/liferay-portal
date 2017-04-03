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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceProductDefintionOptionRel service. Represents a row in the &quot;CommerceProductDefintionOptionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionRelModel
 * @see com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelImpl
 * @see com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelImpl")
@ProviderType
public interface CommerceProductDefintionOptionRel
	extends CommerceProductDefintionOptionRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CommerceProductDefintionOptionRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceProductDefintionOptionRel, Long> COMMERCE_PRODUCT_DEFINTION_OPTION_REL_ID_ACCESSOR =
		new Accessor<CommerceProductDefintionOptionRel, Long>() {
			@Override
			public Long get(
				CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
				return commerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceProductDefintionOptionRel> getTypeClass() {
				return CommerceProductDefintionOptionRel.class;
			}
		};
}