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

import java.util.function.Supplier;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carlos Correa
 */
@GraphQLName("CustomField")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CustomField")
public class CustomField implements Serializable {

	public static CustomField toDTO(String json) {
		return ObjectMapperUtil.readValue(CustomField.class, json);
	}

	public static CustomField unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(CustomField.class, json);
	}

	@Valid
	public CustomValue getCustomValue() {
		if (_customValueSupplier != null) {
			customValue = _customValueSupplier.get();

			_customValueSupplier = null;
		}

		return customValue;
	}

	public String getDataType() {
		if (_dataTypeSupplier != null) {
			dataType = _dataTypeSupplier.get();

			_dataTypeSupplier = null;
		}

		return dataType;
	}

	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setCustomValue(CustomValue customValue) {
		this.customValue = customValue;

		_customValueSupplier = null;
	}

	@JsonIgnore
	public void setCustomValue(
		UnsafeSupplier<CustomValue, Exception> customValueUnsafeSupplier) {

		_customValueSupplier = () -> {
			try {
				return customValueUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;

		_dataTypeSupplier = null;
	}

	@JsonIgnore
	public void setDataType(
		UnsafeSupplier<String, Exception> dataTypeUnsafeSupplier) {

		_dataTypeSupplier = () -> {
			try {
				return dataTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
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
	protected CustomValue customValue;

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String dataType;

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@JsonIgnore
	private Supplier<CustomValue> _customValueSupplier;

	@JsonIgnore
	private Supplier<String> _dataTypeSupplier;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

}