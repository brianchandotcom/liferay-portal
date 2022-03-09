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

package com.liferay.headless.commerce.admin.channel.client.dto.v1_0;

import com.liferay.headless.commerce.admin.channel.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.channel.client.serdes.v1_0.ShippingMethodSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class ShippingMethod implements Cloneable, Serializable {

	public static ShippingMethod toDTO(String json) {
		return ShippingMethodSerDes.toDTO(json);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean active;

	public Map<String, String> getDescription() {
		return description;
	}

	public void setDescription(Map<String, String> description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<Map<String, String>, Exception>
			descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> description;

	public String getEngineKey() {
		return engineKey;
	}

	public void setEngineKey(String engineKey) {
		this.engineKey = engineKey;
	}

	public void setEngineKey(
		UnsafeSupplier<String, Exception> engineKeyUnsafeSupplier) {

		try {
			engineKey = engineKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String engineKey;

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

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public void setPriority(
		UnsafeSupplier<Double, Exception> priorityUnsafeSupplier) {

		try {
			priority = priorityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double priority;

	public ShippingOption[] getShippingOptions() {
		return shippingOptions;
	}

	public void setShippingOptions(ShippingOption[] shippingOptions) {
		this.shippingOptions = shippingOptions;
	}

	public void setShippingOptions(
		UnsafeSupplier<ShippingOption[], Exception>
			shippingOptionsUnsafeSupplier) {

		try {
			shippingOptions = shippingOptionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ShippingOption[] shippingOptions;

	@Override
	public ShippingMethod clone() throws CloneNotSupportedException {
		return (ShippingMethod)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ShippingMethod)) {
			return false;
		}

		ShippingMethod shippingMethod = (ShippingMethod)object;

		return Objects.equals(toString(), shippingMethod.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ShippingMethodSerDes.toJSON(this);
	}

}