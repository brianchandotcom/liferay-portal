/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DisplayPageNavigationMenuItemSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DisplayPageNavigationMenuItemSettings
	implements Cloneable, Serializable {

	public static DisplayPageNavigationMenuItemSettings toDTO(String json) {
		return DisplayPageNavigationMenuItemSettingsSerDes.toDTO(json);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassName(
		UnsafeSupplier<String, Exception> classNameUnsafeSupplier) {

		try {
			className = classNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String className;

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

	public String getScopeExternalReferenceCode() {
		return scopeExternalReferenceCode;
	}

	public void setScopeExternalReferenceCode(
		String scopeExternalReferenceCode) {

		this.scopeExternalReferenceCode = scopeExternalReferenceCode;
	}

	public void setScopeExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			scopeExternalReferenceCodeUnsafeSupplier) {

		try {
			scopeExternalReferenceCode =
				scopeExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String scopeExternalReferenceCode;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String title;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String type;

	@Override
	public DisplayPageNavigationMenuItemSettings clone()
		throws CloneNotSupportedException {

		return (DisplayPageNavigationMenuItemSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageNavigationMenuItemSettings)) {
			return false;
		}

		DisplayPageNavigationMenuItemSettings
			displayPageNavigationMenuItemSettings =
				(DisplayPageNavigationMenuItemSettings)object;

		return Objects.equals(
			toString(), displayPageNavigationMenuItemSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageNavigationMenuItemSettingsSerDes.toJSON(this);
	}

}