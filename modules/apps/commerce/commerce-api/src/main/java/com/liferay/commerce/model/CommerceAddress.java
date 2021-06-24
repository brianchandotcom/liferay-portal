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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceAddress service. Represents a row in the &quot;CommerceAddress&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressModel
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceAddressImpl")
@ProviderType
public interface CommerceAddress extends CommerceAddressModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceAddressImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAddress, Long>
		COMMERCE_ADDRESS_ID_ACCESSOR = new Accessor<CommerceAddress, Long>() {

			@Override
			public Long get(CommerceAddress commerceAddress) {
				return commerceAddress.getCommerceAddressId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceAddress> getTypeClass() {
				return CommerceAddress.class;
			}

		};

	public com.liferay.portal.kernel.model.Country fetchCountry();

	public com.liferay.portal.kernel.model.Country getCountry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.expando.kernel.model.ExpandoBridge getExpandoBridge();

	public com.liferay.portal.kernel.model.Region getRegion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isGeolocated();

	public boolean isSameAddress(CommerceAddress commerceAddress);

}