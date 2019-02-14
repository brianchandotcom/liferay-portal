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

package com.liferay.headless.web.experience.dto.v1_0;

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
@GraphQLName("Values")
@XmlRootElement(name = "Values")
public class Values {

	public String getDataType() {
		return dataType;
	}

	public String getFilterAndSortIdentifier() {
		return filterAndSortIdentifier;
	}

	public Long getId() {
		return id;
	}

	public String getInputControl() {
		return inputControl;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDataType(
		UnsafeSupplier<String, Throwable> dataTypeUnsafeSupplier) {

		try {
			dataType = dataTypeUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setFilterAndSortIdentifier(String filterAndSortIdentifier) {
		this.filterAndSortIdentifier = filterAndSortIdentifier;
	}

	public void setFilterAndSortIdentifier(
		UnsafeSupplier<String, Throwable>
			filterAndSortIdentifierUnsafeSupplier) {

		try {
			filterAndSortIdentifier =
				filterAndSortIdentifierUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setInputControl(String inputControl) {
		this.inputControl = inputControl;
	}

	public void setInputControl(
		UnsafeSupplier<String, Throwable> inputControlUnsafeSupplier) {

		try {
			inputControl = inputControlUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<String, Throwable> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<Object, Throwable> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
	}
		catch (Throwable t) {
			throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected String dataType;

	@GraphQLField
	protected String filterAndSortIdentifier;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String inputControl;

	@GraphQLField
	protected String label;

	@GraphQLField
	protected String name;

	@GraphQLField
	protected Object value;

}