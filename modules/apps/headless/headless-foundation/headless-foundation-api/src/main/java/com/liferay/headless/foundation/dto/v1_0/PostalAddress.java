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

package com.liferay.headless.foundation.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface PostalAddress {

	public String getAddressCountry();

	public void setAddressCountry(
			String addressCountry);

	public void setAddressCountry(
			UnsafeSupplier<String, Throwable>
				addressCountryUnsafeSupplier);
	public String getAddressLocality();

	public void setAddressLocality(
			String addressLocality);

	public void setAddressLocality(
			UnsafeSupplier<String, Throwable>
				addressLocalityUnsafeSupplier);
	public String getAddressRegion();

	public void setAddressRegion(
			String addressRegion);

	public void setAddressRegion(
			UnsafeSupplier<String, Throwable>
				addressRegionUnsafeSupplier);
	public String getAddressType();

	public void setAddressType(
			String addressType);

	public void setAddressType(
			UnsafeSupplier<String, Throwable>
				addressTypeUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getPostalCode();

	public void setPostalCode(
			String postalCode);

	public void setPostalCode(
			UnsafeSupplier<String, Throwable>
				postalCodeUnsafeSupplier);
	public String getStreetAddressLine1();

	public void setStreetAddressLine1(
			String streetAddressLine1);

	public void setStreetAddressLine1(
			UnsafeSupplier<String, Throwable>
				streetAddressLine1UnsafeSupplier);
	public String getStreetAddressLine2();

	public void setStreetAddressLine2(
			String streetAddressLine2);

	public void setStreetAddressLine2(
			UnsafeSupplier<String, Throwable>
				streetAddressLine2UnsafeSupplier);
	public String getStreetAddressLine3();

	public void setStreetAddressLine3(
			String streetAddressLine3);

	public void setStreetAddressLine3(
			UnsafeSupplier<String, Throwable>
				streetAddressLine3UnsafeSupplier);

}