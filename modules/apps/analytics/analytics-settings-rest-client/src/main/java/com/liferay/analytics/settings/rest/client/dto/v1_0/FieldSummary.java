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

package com.liferay.analytics.settings.rest.client.dto.v1_0;

import com.liferay.analytics.settings.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.settings.rest.client.serdes.v1_0.FieldSummarySerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class FieldSummary implements Cloneable, Serializable {

	public static FieldSummary toDTO(String json) {
		return FieldSummarySerDes.toDTO(json);
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public void setAccount(
		UnsafeSupplier<Integer, Exception> accountUnsafeSupplier) {

		try {
			account = accountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer account;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setOrder(
		UnsafeSupplier<Integer, Exception> orderUnsafeSupplier) {

		try {
			order = orderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer order;

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public void setPeople(
		UnsafeSupplier<Integer, Exception> peopleUnsafeSupplier) {

		try {
			people = peopleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer people;

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public void setProduct(
		UnsafeSupplier<Integer, Exception> productUnsafeSupplier) {

		try {
			product = productUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer product;

	@Override
	public FieldSummary clone() throws CloneNotSupportedException {
		return (FieldSummary)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FieldSummary)) {
			return false;
		}

		FieldSummary fieldSummary = (FieldSummary)object;

		return Objects.equals(toString(), fieldSummary.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FieldSummarySerDes.toJSON(this);
	}

}