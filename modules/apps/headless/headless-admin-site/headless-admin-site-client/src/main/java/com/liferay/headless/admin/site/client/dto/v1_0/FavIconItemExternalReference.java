/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FavIconItemExternalReferenceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FavIconItemExternalReference
	extends FavIcon implements Cloneable, Serializable {

	public static FavIconItemExternalReference toDTO(String json) {
		return FavIconItemExternalReferenceSerDes.toDTO(json);
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

	public com.liferay.headless.admin.site.client.scope.Scope getScope() {
		return scope;
	}

	public void setScope(
		com.liferay.headless.admin.site.client.scope.Scope scope) {

		this.scope = scope;
	}

	public void setScope(
		UnsafeSupplier
			<com.liferay.headless.admin.site.client.scope.Scope, Exception>
				scopeUnsafeSupplier) {

		try {
			scope = scopeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected com.liferay.headless.admin.site.client.scope.Scope scope;

	@Override
	public FavIconItemExternalReference clone()
		throws CloneNotSupportedException {

		return (FavIconItemExternalReference)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FavIconItemExternalReference)) {
			return false;
		}

		FavIconItemExternalReference favIconItemExternalReference =
			(FavIconItemExternalReference)object;

		return Objects.equals(
			toString(), favIconItemExternalReference.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FavIconItemExternalReferenceSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1127812259