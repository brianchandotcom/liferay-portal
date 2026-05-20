/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.RequestPortletDataHandlerSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class RequestPortletDataHandler implements Cloneable, Serializable {

	public static RequestPortletDataHandler toDTO(String json) {
		return RequestPortletDataHandlerSerDes.toDTO(json);
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

	@Override
	public RequestPortletDataHandler clone() throws CloneNotSupportedException {
		return (RequestPortletDataHandler)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RequestPortletDataHandler)) {
			return false;
		}

		RequestPortletDataHandler requestPortletDataHandler =
			(RequestPortletDataHandler)object;

		return Objects.equals(toString(), requestPortletDataHandler.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RequestPortletDataHandlerSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-727763548