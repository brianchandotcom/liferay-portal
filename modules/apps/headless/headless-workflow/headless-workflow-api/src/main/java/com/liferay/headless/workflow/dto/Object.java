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

package com.liferay.headless.workflow.dto;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Object")
@XmlRootElement(name = "Object")
public class Object {

	public Long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getResourceType() {
		return resourceType;
	}

	public String getSelf() {
		return self;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	@GraphQLField
	private Long id;

	@GraphQLField
	private String identifier;

	@GraphQLField
	private String resourceType;

	@GraphQLField
	private String self;

}