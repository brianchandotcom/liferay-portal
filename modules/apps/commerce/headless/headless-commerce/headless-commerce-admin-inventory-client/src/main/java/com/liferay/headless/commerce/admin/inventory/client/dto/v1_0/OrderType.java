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

package com.liferay.headless.commerce.admin.inventory.client.dto.v1_0;

import com.liferay.headless.commerce.admin.inventory.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.inventory.client.serdes.v1_0.OrderTypeSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class OrderType implements Cloneable, Serializable {

	public static OrderType toDTO(String json) {
		return OrderTypeSerDes.toDTO(json);
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

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public void setName(
		UnsafeSupplier<Map<String, String>, Exception> nameUnsafeSupplier) {

		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> name;

	@Override
	public OrderType clone() throws CloneNotSupportedException {
		return (OrderType)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OrderType)) {
			return false;
		}

		OrderType orderType = (OrderType)object;

		return Objects.equals(toString(), orderType.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return OrderTypeSerDes.toJSON(this);
	}

}