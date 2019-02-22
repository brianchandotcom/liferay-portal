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

package com.liferay.headless.foundation.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.foundation.dto.v1_0.Phone;
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
@GraphQLName("Phone")
@XmlRootElement(name = "Phone")
public class PhoneImpl implements Phone {

	public String getExtension() {
			return extension;
	}

	public void setExtension(
			String extension) {

			this.extension = (String)extension;
	}

	@JsonIgnore
	public void setExtension(
			UnsafeSupplier<String, Throwable>
				extensionUnsafeSupplier) {

			try {
				extension =
					(String)extensionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String extension;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					(Long)idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public String getPhoneNumber() {
			return phoneNumber;
	}

	public void setPhoneNumber(
			String phoneNumber) {

			this.phoneNumber = (String)phoneNumber;
	}

	@JsonIgnore
	public void setPhoneNumber(
			UnsafeSupplier<String, Throwable>
				phoneNumberUnsafeSupplier) {

			try {
				phoneNumber =
					(String)phoneNumberUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String phoneNumber;
	public String getPhoneType() {
			return phoneType;
	}

	public void setPhoneType(
			String phoneType) {

			this.phoneType = (String)phoneType;
	}

	@JsonIgnore
	public void setPhoneType(
			UnsafeSupplier<String, Throwable>
				phoneTypeUnsafeSupplier) {

			try {
				phoneType =
					(String)phoneTypeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String phoneType;

}