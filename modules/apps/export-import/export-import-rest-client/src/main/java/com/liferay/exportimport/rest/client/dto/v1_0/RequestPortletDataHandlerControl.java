/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.RequestPortletDataHandlerControlSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class RequestPortletDataHandlerControl
	implements Cloneable, Serializable {

	public static RequestPortletDataHandlerControl toDTO(String json) {
		return RequestPortletDataHandlerControlSerDes.toDTO(json);
	}

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

	public RequestPortletDataHandlerControl[]
		getRequestPortletDataHandlerControls() {

		return requestPortletDataHandlerControls;
	}

	public void setRequestPortletDataHandlerControls(
		RequestPortletDataHandlerControl[] requestPortletDataHandlerControls) {

		this.requestPortletDataHandlerControls =
			requestPortletDataHandlerControls;
	}

	public void setRequestPortletDataHandlerControls(
		UnsafeSupplier<RequestPortletDataHandlerControl[], Exception>
			requestPortletDataHandlerControlsUnsafeSupplier) {

		try {
			requestPortletDataHandlerControls =
				requestPortletDataHandlerControlsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RequestPortletDataHandlerControl[]
		requestPortletDataHandlerControls;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String value;

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public void setValues(
		UnsafeSupplier<String[], Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] values;

	@Override
	public RequestPortletDataHandlerControl clone()
		throws CloneNotSupportedException {

		return (RequestPortletDataHandlerControl)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RequestPortletDataHandlerControl)) {
			return false;
		}

		RequestPortletDataHandlerControl requestPortletDataHandlerControl =
			(RequestPortletDataHandlerControl)object;

		return Objects.equals(
			toString(), requestPortletDataHandlerControl.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RequestPortletDataHandlerControlSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1392321019