/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.client.dto.v1_0;

import com.liferay.mcp.server.rest.client.function.UnsafeSupplier;
import com.liferay.mcp.server.rest.client.serdes.v1_0.ToolSearchResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ToolSearchResult implements Cloneable, Serializable {

	public static ToolSearchResult toDTO(String json) {
		return ToolSearchResultSerDes.toDTO(json);
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

	public Prerequisite[] getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(Prerequisite[] prerequisites) {
		this.prerequisites = prerequisites;
	}

	public void setPrerequisites(
		UnsafeSupplier<Prerequisite[], Exception> prerequisitesUnsafeSupplier) {

		try {
			prerequisites = prerequisitesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Prerequisite[] prerequisites;

	public Map<String, ?> getRequiredInputSchema() {
		return requiredInputSchema;
	}

	public void setRequiredInputSchema(Map<String, ?> requiredInputSchema) {
		this.requiredInputSchema = requiredInputSchema;
	}

	public void setRequiredInputSchema(
		UnsafeSupplier<Map<String, ?>, Exception>
			requiredInputSchemaUnsafeSupplier) {

		try {
			requiredInputSchema = requiredInputSchemaUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ?> requiredInputSchema;

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
	public ToolSearchResult clone() throws CloneNotSupportedException {
		return (ToolSearchResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ToolSearchResult)) {
			return false;
		}

		ToolSearchResult toolSearchResult = (ToolSearchResult)object;

		return Objects.equals(toString(), toolSearchResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ToolSearchResultSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1098910185