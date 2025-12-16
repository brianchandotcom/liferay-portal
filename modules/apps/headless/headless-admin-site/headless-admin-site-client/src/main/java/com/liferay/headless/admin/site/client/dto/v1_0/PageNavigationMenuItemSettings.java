/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.PageNavigationMenuItemSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class PageNavigationMenuItemSettings implements Cloneable, Serializable {

	public static PageNavigationMenuItemSettings toDTO(String json) {
		return PageNavigationMenuItemSettingsSerDes.toDTO(json);
	}

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public Boolean getPrivatePage() {
		return privatePage;
	}

	public void setPrivatePage(Boolean privatePage) {
		this.privatePage = privatePage;
	}

	public void setPrivatePage(
		UnsafeSupplier<Boolean, Exception> privatePageUnsafeSupplier) {

		try {
			privatePage = privatePageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean privatePage;

	@Override
	public PageNavigationMenuItemSettings clone()
		throws CloneNotSupportedException {

		return (PageNavigationMenuItemSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageNavigationMenuItemSettings)) {
			return false;
		}

		PageNavigationMenuItemSettings pageNavigationMenuItemSettings =
			(PageNavigationMenuItemSettings)object;

		return Objects.equals(
			toString(), pageNavigationMenuItemSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageNavigationMenuItemSettingsSerDes.toJSON(this);
	}

}