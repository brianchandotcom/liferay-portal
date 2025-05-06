/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0;

import com.liferay.portal.tools.rest.builder.test.client.function.UnsafeSupplier;
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0.ERCAssetLibraryTestEntitySerDes;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ERCAssetLibraryTestEntity implements Cloneable, Serializable {

	public static ERCAssetLibraryTestEntity toDTO(String json) {
		return ERCAssetLibraryTestEntitySerDes.toDTO(json);
	}

	public String getAssetLibraryExternalReferenceCode() {
		return assetLibraryExternalReferenceCode;
	}

	public void setAssetLibraryExternalReferenceCode(
		String assetLibraryExternalReferenceCode) {

		this.assetLibraryExternalReferenceCode =
			assetLibraryExternalReferenceCode;
	}

	public void setAssetLibraryExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			assetLibraryExternalReferenceCodeUnsafeSupplier) {

		try {
			assetLibraryExternalReferenceCode =
				assetLibraryExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String assetLibraryExternalReferenceCode;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

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

	public
		com.liferay.portal.tools.rest.builder.test.client.permission.
			Permission[] getPermissions() {

		return permissions;
	}

	public void setPermissions(
		com.liferay.portal.tools.rest.builder.test.client.permission.
			Permission[] permissions) {

		this.permissions = permissions;
	}

	public void setPermissions(
		UnsafeSupplier
			<com.liferay.portal.tools.rest.builder.test.client.permission.
				Permission[],
			 Exception> permissionsUnsafeSupplier) {

		try {
			permissions = permissionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected
		com.liferay.portal.tools.rest.builder.test.client.permission.
			Permission[] permissions;

	@Override
	public ERCAssetLibraryTestEntity clone() throws CloneNotSupportedException {
		return (ERCAssetLibraryTestEntity)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCAssetLibraryTestEntity)) {
			return false;
		}

		ERCAssetLibraryTestEntity ercAssetLibraryTestEntity =
			(ERCAssetLibraryTestEntity)object;

		return Objects.equals(toString(), ercAssetLibraryTestEntity.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ERCAssetLibraryTestEntitySerDes.toJSON(this);
	}

}