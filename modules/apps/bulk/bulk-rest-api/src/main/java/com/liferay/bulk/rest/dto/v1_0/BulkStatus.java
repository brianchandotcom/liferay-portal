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

package com.liferay.bulk.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("BulkStatus")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "BulkStatus")
public class BulkStatus {

	public Boolean getBusy() {
		return busy;
	}

	public void setBusy(Boolean busy) {
		this.busy = busy;
	}

	@JsonIgnore
	public void setBusy(UnsafeSupplier<Boolean, Exception> busyUnsafeSupplier) {
		try {
			busy = busyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean busy;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"busy\": ");

		sb.append(busy);

		sb.append("}");

		return sb.toString();
	}

}