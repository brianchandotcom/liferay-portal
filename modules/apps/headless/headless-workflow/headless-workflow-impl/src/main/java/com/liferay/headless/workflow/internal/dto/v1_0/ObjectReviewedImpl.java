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

package com.liferay.headless.workflow.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.workflow.dto.v1_0.ObjectReviewed;
import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ObjectReviewed")
@XmlRootElement(name = "ObjectReviewed")
public class ObjectReviewedImpl implements ObjectReviewed {

	public Long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@JsonIgnore
	public void setIdentifier(
		UnsafeSupplier<String, Throwable> identifierUnsafeSupplier) {

		try {
			identifier = identifierUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@JsonIgnore
	public void setResourceType(
		UnsafeSupplier<String, Throwable> resourceTypeUnsafeSupplier) {

		try {
			resourceType = resourceTypeUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String identifier;

	@GraphQLField
	@JsonProperty
	protected String resourceType;

}