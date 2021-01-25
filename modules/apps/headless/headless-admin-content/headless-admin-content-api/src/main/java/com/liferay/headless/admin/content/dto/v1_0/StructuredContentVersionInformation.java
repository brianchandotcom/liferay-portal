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

package com.liferay.headless.admin.content.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

/**
 * @author Luis Miguel Barcos
 */
public class StructuredContentVersionInformation extends StructuredContent {

	@Schema(description = "The version information of the structured content.")
	public VersionInformation getVersionInformation() {
		return versionInformation;
	}

	@JsonIgnore
	public void setVersionInformation(
		UnsafeSupplier<VersionInformation, Exception> versionUnsafeSupplier) {

		try {
			versionInformation = versionUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setVersionInformation(VersionInformation versionInformation) {
		this.versionInformation = versionInformation;
	}

	@GraphQLField(description = "The version of the structured content.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected VersionInformation versionInformation;

}