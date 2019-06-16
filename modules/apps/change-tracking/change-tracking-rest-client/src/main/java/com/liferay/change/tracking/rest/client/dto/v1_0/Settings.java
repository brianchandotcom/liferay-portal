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
import com.liferay.change.tracking.rest.client.serdes.v1_0.SettingsSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class Settings {

	public Boolean getChangeTrackingAllowed() {
		return changeTrackingAllowed;
	}

	public void setChangeTrackingAllowed(Boolean changeTrackingAllowed) {
		this.changeTrackingAllowed = changeTrackingAllowed;
	}

	public void setChangeTrackingAllowed(
		UnsafeSupplier<Boolean, Exception>
			changeTrackingAllowedUnsafeSupplier) {

		try {
			changeTrackingAllowed = changeTrackingAllowedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean changeTrackingAllowed;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setCompanyId(
		UnsafeSupplier<Long, Exception> companyIdUnsafeSupplier) {

		try {
			companyId = companyIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long companyId;

	public String[] getSupportedContentTypeLanguageKeys() {
		return supportedContentTypeLanguageKeys;
	}

	public void setSupportedContentTypeLanguageKeys(
		String[] supportedContentTypeLanguageKeys) {

		this.supportedContentTypeLanguageKeys =
			supportedContentTypeLanguageKeys;
	}

	public void setSupportedContentTypeLanguageKeys(
		UnsafeSupplier<String[], Exception>
			supportedContentTypeLanguageKeysUnsafeSupplier) {

		try {
			supportedContentTypeLanguageKeys =
				supportedContentTypeLanguageKeysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] supportedContentTypeLanguageKeys;

	public String[] getSupportedContentTypes() {
		return supportedContentTypes;
	}

	public void setSupportedContentTypes(String[] supportedContentTypes) {
		this.supportedContentTypes = supportedContentTypes;
	}

	public void setSupportedContentTypes(
		UnsafeSupplier<String[], Exception>
			supportedContentTypesUnsafeSupplier) {

		try {
			supportedContentTypes = supportedContentTypesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] supportedContentTypes;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		try {
			userId = userIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long userId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Settings)) {
			return false;
		}

		Settings settings = (Settings)object;

		return Objects.equals(toString(), settings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SettingsSerDes.toJSON(this);
	}

}