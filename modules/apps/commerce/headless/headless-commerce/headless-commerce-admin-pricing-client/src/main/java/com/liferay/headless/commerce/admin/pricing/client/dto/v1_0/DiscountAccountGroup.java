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

package com.liferay.headless.commerce.admin.pricing.client.dto.v1_0;

import com.liferay.headless.commerce.admin.pricing.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v1_0.DiscountAccountGroupSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class DiscountAccountGroup implements Cloneable {

	public static DiscountAccountGroup toDTO(String json) {
		return DiscountAccountGroupSerDes.toDTO(json);
	}

	public String getAccountGroupExternalReferenceCode() {
		return accountGroupExternalReferenceCode;
	}

	public void setAccountGroupExternalReferenceCode(
		String accountGroupExternalReferenceCode) {

		this.accountGroupExternalReferenceCode =
			accountGroupExternalReferenceCode;
	}

	public void setAccountGroupExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			accountGroupExternalReferenceCodeUnsafeSupplier) {

		try {
			accountGroupExternalReferenceCode =
				accountGroupExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String accountGroupExternalReferenceCode;

	public Long getAccountGroupId() {
		return accountGroupId;
	}

	public void setAccountGroupId(Long accountGroupId) {
		this.accountGroupId = accountGroupId;
	}

	public void setAccountGroupId(
		UnsafeSupplier<Long, Exception> accountGroupIdUnsafeSupplier) {

		try {
			accountGroupId = accountGroupIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long accountGroupId;

	public String getDiscountExternalReferenceCode() {
		return discountExternalReferenceCode;
	}

	public void setDiscountExternalReferenceCode(
		String discountExternalReferenceCode) {

		this.discountExternalReferenceCode = discountExternalReferenceCode;
	}

	public void setDiscountExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			discountExternalReferenceCodeUnsafeSupplier) {

		try {
			discountExternalReferenceCode =
				discountExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String discountExternalReferenceCode;

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public void setDiscountId(
		UnsafeSupplier<Long, Exception> discountIdUnsafeSupplier) {

		try {
			discountId = discountIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long discountId;

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

	@Override
	public DiscountAccountGroup clone() throws CloneNotSupportedException {
		return (DiscountAccountGroup)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DiscountAccountGroup)) {
			return false;
		}

		DiscountAccountGroup discountAccountGroup =
			(DiscountAccountGroup)object;

		return Objects.equals(toString(), discountAccountGroup.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DiscountAccountGroupSerDes.toJSON(this);
	}

}