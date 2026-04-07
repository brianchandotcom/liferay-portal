/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.client.dto.v1_0;

import com.liferay.headless.admin.configuration.client.function.UnsafeSupplier;
import com.liferay.headless.admin.configuration.client.serdes.v1_0.InstanceConfigurationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Thiago Buarque
 * @generated
 */
@Generated("")
public class InstanceConfiguration implements Cloneable, Serializable {

	public static InstanceConfiguration toDTO(String json) {
		return InstanceConfigurationSerDes.toDTO(json);
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

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setProperties(
		UnsafeSupplier<Map<String, Object>, Exception>
			propertiesUnsafeSupplier) {

		try {
			properties = propertiesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> properties;

	@Override
	public InstanceConfiguration clone() throws CloneNotSupportedException {
		return (InstanceConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InstanceConfiguration)) {
			return false;
		}

		InstanceConfiguration instanceConfiguration =
			(InstanceConfiguration)object;

		return Objects.equals(toString(), instanceConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return InstanceConfigurationSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:595403568