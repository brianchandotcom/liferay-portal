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

package com.liferay.commerce.products.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceProductInstance service. Represents a row in the &quot;CommerceProductInstance&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceProductInstanceModel
 * @see com.liferay.commerce.products.model.impl.CommerceProductInstanceImpl
 * @see com.liferay.commerce.products.model.impl.CommerceProductInstanceModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.products.model.impl.CommerceProductInstanceImpl")
@ProviderType
public interface CommerceProductInstance extends CommerceProductInstanceModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.products.model.impl.CommerceProductInstanceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceProductInstance, Long> COMMERCE_PRODUCT_INSTANCE_ID_ACCESSOR =
		new Accessor<CommerceProductInstance, Long>() {
			@Override
			public Long get(CommerceProductInstance commerceProductInstance) {
				return commerceProductInstance.getCommerceProductInstanceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceProductInstance> getTypeClass() {
				return CommerceProductInstance.class;
			}
		};
}