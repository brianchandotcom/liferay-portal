/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.dto.v1_0;

import com.liferay.ai.hub.rest.client.function.UnsafeSupplier;
import com.liferay.ai.hub.rest.client.serdes.v1_0.AgentInstanceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class AgentInstance implements Cloneable, Serializable {

	public static AgentInstance toDTO(String json) {
		return AgentInstanceSerDes.toDTO(json);
	}

	public Map<String, ?> getContext() {
		return context;
	}

	public void setContext(Map<String, ?> context) {
		this.context = context;
	}

	public void setContext(
		UnsafeSupplier<Map<String, ?>, Exception> contextUnsafeSupplier) {

		try {
			context = contextUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ?> context;

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

	public String getSseEventSinkKey() {
		return sseEventSinkKey;
	}

	public void setSseEventSinkKey(String sseEventSinkKey) {
		this.sseEventSinkKey = sseEventSinkKey;
	}

	public void setSseEventSinkKey(
		UnsafeSupplier<String, Exception> sseEventSinkKeyUnsafeSupplier) {

		try {
			sseEventSinkKey = sseEventSinkKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String sseEventSinkKey;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String type;

	@Override
	public AgentInstance clone() throws CloneNotSupportedException {
		return (AgentInstance)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AgentInstance)) {
			return false;
		}

		AgentInstance agentInstance = (AgentInstance)object;

		return Objects.equals(toString(), agentInstance.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AgentInstanceSerDes.toJSON(this);
	}

}