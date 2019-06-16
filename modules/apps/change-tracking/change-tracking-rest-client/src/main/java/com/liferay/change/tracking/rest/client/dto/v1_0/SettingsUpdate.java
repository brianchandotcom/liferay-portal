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

package com.liferay.change.tracking.rest.client.dto.v1_0;

import com.liferay.change.tracking.rest.client.function.UnsafeSupplier;
import com.liferay.change.tracking.rest.client.serdes.v1_0.SettingsUpdateSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class SettingsUpdate {

	public Boolean getChangeTrackingEnabled() {
		return changeTrackingEnabled;
	}

	public void setChangeTrackingEnabled(Boolean changeTrackingEnabled) {
		this.changeTrackingEnabled = changeTrackingEnabled;
	}

	public void setChangeTrackingEnabled(
		UnsafeSupplier<Boolean, Exception>
			changeTrackingEnabledUnsafeSupplier) {

		try {
			changeTrackingEnabled = changeTrackingEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean changeTrackingEnabled;

	public Boolean getCheckoutCTCollectionConfirmationEnabled() {
		return checkoutCTCollectionConfirmationEnabled;
	}

	public void setCheckoutCTCollectionConfirmationEnabled(
		Boolean checkoutCTCollectionConfirmationEnabled) {

		this.checkoutCTCollectionConfirmationEnabled =
			checkoutCTCollectionConfirmationEnabled;
	}

	public void setCheckoutCTCollectionConfirmationEnabled(
		UnsafeSupplier<Boolean, Exception>
			checkoutCTCollectionConfirmationEnabledUnsafeSupplier) {

		try {
			checkoutCTCollectionConfirmationEnabled =
				checkoutCTCollectionConfirmationEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean checkoutCTCollectionConfirmationEnabled;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SettingsUpdate)) {
			return false;
		}

		SettingsUpdate settingsUpdate = (SettingsUpdate)object;

		return Objects.equals(toString(), settingsUpdate.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SettingsUpdateSerDes.toJSON(this);
	}

}