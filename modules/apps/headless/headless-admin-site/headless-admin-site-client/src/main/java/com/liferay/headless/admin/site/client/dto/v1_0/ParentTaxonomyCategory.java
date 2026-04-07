/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ParentTaxonomyCategorySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ParentTaxonomyCategory implements Cloneable, Serializable {

	public static ParentTaxonomyCategory toDTO(String json) {
		return ParentTaxonomyCategorySerDes.toDTO(json);
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

	@Override
	public ParentTaxonomyCategory clone() throws CloneNotSupportedException {
		return (ParentTaxonomyCategory)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ParentTaxonomyCategory)) {
			return false;
		}

		ParentTaxonomyCategory parentTaxonomyCategory =
			(ParentTaxonomyCategory)object;

		return Objects.equals(toString(), parentTaxonomyCategory.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ParentTaxonomyCategorySerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:862797448