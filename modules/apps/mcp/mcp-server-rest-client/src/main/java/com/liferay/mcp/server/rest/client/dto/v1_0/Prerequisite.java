/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.client.dto.v1_0;

import com.liferay.mcp.server.rest.client.function.UnsafeSupplier;
import com.liferay.mcp.server.rest.client.serdes.v1_0.PrerequisiteSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class Prerequisite implements Cloneable, Serializable {

	public static Prerequisite toDTO(String json) {
		return PrerequisiteSerDes.toDTO(json);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setNote(UnsafeSupplier<String, Exception> noteUnsafeSupplier) {
		try {
			note = noteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String note;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setParameter(
		UnsafeSupplier<String, Exception> parameterUnsafeSupplier) {

		try {
			parameter = parameterUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String parameter;

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public void setToolName(
		UnsafeSupplier<String, Exception> toolNameUnsafeSupplier) {

		try {
			toolName = toolNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String toolName;

	public String getToolSetName() {
		return toolSetName;
	}

	public void setToolSetName(String toolSetName) {
		this.toolSetName = toolSetName;
	}

	public void setToolSetName(
		UnsafeSupplier<String, Exception> toolSetNameUnsafeSupplier) {

		try {
			toolSetName = toolSetNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String toolSetName;

	@Override
	public Prerequisite clone() throws CloneNotSupportedException {
		return (Prerequisite)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Prerequisite)) {
			return false;
		}

		Prerequisite prerequisite = (Prerequisite)object;

		return Objects.equals(toString(), prerequisite.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PrerequisiteSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-323965352