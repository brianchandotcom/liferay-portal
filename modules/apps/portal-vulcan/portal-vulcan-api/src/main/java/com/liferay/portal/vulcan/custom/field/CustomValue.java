/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.custom.field;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.function.Supplier;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carlos Correa
 */
@GraphQLName("CustomValue")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CustomValue")
public class CustomValue implements Serializable {

	public static CustomValue toDTO(String json) {
		return ObjectMapperUtil.readValue(CustomValue.class, json);
	}

	public static CustomValue unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(CustomValue.class, json);
	}

	@Valid
	public Object getData() {
		if (_dataSupplier != null) {
			data = _dataSupplier.get();

			_dataSupplier = null;
		}

		return data;
	}

	@Valid
	public Map<String, String> getData_i18n() {
		if (_data_i18nSupplier != null) {
			data_i18n = _data_i18nSupplier.get();

			_data_i18nSupplier = null;
		}

		return data_i18n;
	}

	@Valid
	public Geo getGeo() {
		if (_geoSupplier != null) {
			geo = _geoSupplier.get();

			_geoSupplier = null;
		}

		return geo;
	}

	public void setData(Object data) {
		this.data = data;

		_dataSupplier = null;
	}

	@JsonIgnore
	public void setData(UnsafeSupplier<Object, Exception> dataUnsafeSupplier) {
		_dataSupplier = () -> {
			try {
				return dataUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	public void setData_i18n(Map<String, String> data_i18n) {
		this.data_i18n = data_i18n;

		_data_i18nSupplier = null;
	}

	@JsonIgnore
	public void setData_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			data_i18nUnsafeSupplier) {

		_data_i18nSupplier = () -> {
			try {
				return data_i18nUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	public void setGeo(Geo geo) {
		this.geo = geo;

		_geoSupplier = null;
	}

	@JsonIgnore
	public void setGeo(UnsafeSupplier<Geo, Exception> geoUnsafeSupplier) {
		_geoSupplier = () -> {
			try {
				return geoUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object data;

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> data_i18n;

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Geo geo;

	@JsonIgnore
	private Supplier<Map<String, String>> _data_i18nSupplier;

	@JsonIgnore
	private Supplier<Object> _dataSupplier;

	@JsonIgnore
	private Supplier<Geo> _geoSupplier;

}