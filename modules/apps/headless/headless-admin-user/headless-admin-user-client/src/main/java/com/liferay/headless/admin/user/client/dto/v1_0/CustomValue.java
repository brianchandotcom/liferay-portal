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

package com.liferay.headless.admin.user.client.dto.v1_0;

import com.liferay.headless.admin.user.client.function.UnsafeSupplier;
import com.liferay.headless.admin.user.client.serdes.v1_0.CustomValueSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CustomValue {

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setData(UnsafeSupplier<Object, Exception> dataUnsafeSupplier) {
		try {
			data = dataUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object data;

	public Map<String, String> getData_i18n() {
		return data_i18n;
	}

	public void setData_i18n(Map<String, String> data_i18n) {
		this.data_i18n = data_i18n;
	}

	public void setData_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			data_i18nUnsafeSupplier) {

		try {
			data_i18n = data_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> data_i18n;

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public void setGeo(UnsafeSupplier<Geo, Exception> geoUnsafeSupplier) {
		try {
			geo = geoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Geo geo;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CustomValue)) {
			return false;
		}

		CustomValue customValue = (CustomValue)object;

		return Objects.equals(toString(), customValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CustomValueSerDes.toJSON(this);
	}

}