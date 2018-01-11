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
 * The extended model interface for the CommerceWarehouse service. Represents a row in the &quot;CommerceWarehouse&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseModel
 * @see com.liferay.commerce.model.impl.CommerceWarehouseImpl
 * @see com.liferay.commerce.model.impl.CommerceWarehouseModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceWarehouseImpl")
@ProviderType
public interface CommerceWarehouse extends CommerceWarehouseModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceWarehouseImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceWarehouse, Long> COMMERCE_WAREHOUSE_ID_ACCESSOR =
		new Accessor<CommerceWarehouse, Long>() {
			@Override
			public Long get(CommerceWarehouse commerceWarehouse) {
				return commerceWarehouse.getCommerceWarehouseId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceWarehouse> getTypeClass() {
				return CommerceWarehouse.class;
			}
		};

	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<CommerceWarehouseItem> getCommerceWarehouseItems();

	public boolean isGeolocated();
}