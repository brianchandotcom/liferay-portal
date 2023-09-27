/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.client.dto.v1_0;

import com.liferay.headless.admin.taxonomy.client.function.UnsafeSupplier;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.ParentTaxonomyCategorySerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentTaxonomyCategory implements Cloneable, Serializable {

	public static ParentTaxonomyCategory toDTO(String json) {
		return ParentTaxonomyCategorySerDes.toDTO(json);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

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