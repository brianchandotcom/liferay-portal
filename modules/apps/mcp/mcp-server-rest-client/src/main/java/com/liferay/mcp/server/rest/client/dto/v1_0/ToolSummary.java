/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.client.dto.v1_0;

import com.liferay.mcp.server.rest.client.function.UnsafeSupplier;
import com.liferay.mcp.server.rest.client.serdes.v1_0.ToolSummarySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ToolSummary implements Cloneable, Serializable {

	public static ToolSummary toDTO(String json) {
		return ToolSummarySerDes.toDTO(json);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

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

	@Override
	public ToolSummary clone() throws CloneNotSupportedException {
		return (ToolSummary)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ToolSummary)) {
			return false;
		}

		ToolSummary toolSummary = (ToolSummary)object;

		return Objects.equals(toString(), toolSummary.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ToolSummarySerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-978640542