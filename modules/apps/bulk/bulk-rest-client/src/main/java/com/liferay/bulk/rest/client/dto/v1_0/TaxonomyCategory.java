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

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.TaxonomyCategorySerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class TaxonomyCategory implements Cloneable {

	public Long getTaxonomyCategoryId() {
		return taxonomyCategoryId;
	}

	public void setTaxonomyCategoryId(Long taxonomyCategoryId) {
		this.taxonomyCategoryId = taxonomyCategoryId;
	}

	public void setTaxonomyCategoryId(
		UnsafeSupplier<Long, Exception> taxonomyCategoryIdUnsafeSupplier) {

		try {
			taxonomyCategoryId = taxonomyCategoryIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long taxonomyCategoryId;

	public String getTaxonomyCategoryName() {
		return taxonomyCategoryName;
	}

	public void setTaxonomyCategoryName(String taxonomyCategoryName) {
		this.taxonomyCategoryName = taxonomyCategoryName;
	}

	public void setTaxonomyCategoryName(
		UnsafeSupplier<String, Exception> taxonomyCategoryNameUnsafeSupplier) {

		try {
			taxonomyCategoryName = taxonomyCategoryNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String taxonomyCategoryName;

	@Override
	public TaxonomyCategory clone() throws CloneNotSupportedException {
		return (TaxonomyCategory)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TaxonomyCategory)) {
			return false;
		}

		TaxonomyCategory taxonomyCategory = (TaxonomyCategory)object;

		return Objects.equals(toString(), taxonomyCategory.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TaxonomyCategorySerDes.toJSON(this);
	}

}