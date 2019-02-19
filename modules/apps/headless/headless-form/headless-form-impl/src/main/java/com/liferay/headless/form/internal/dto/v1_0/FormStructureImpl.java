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

package com.liferay.headless.form.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.FormPages;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.SuccessPage;
import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FormStructure")
@XmlRootElement(name = "FormStructure")
public class FormStructureImpl implements FormStructure {

	public String[] getAvailableLanguages() {
		return availableLanguages;
	}

	public Long getContentSpace() {
		return contentSpace;
	}

	public Creator getCreator() {
		return creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public String getDescription() {
		return description;
	}

	public FormPages[] getFormPages() {
		return formPages;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public SuccessPage getSuccessPage() {
		return successPage;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
		UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier) {

		try {
			availableLanguages = availableLanguagesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setContentSpace(Long contentSpace) {
		this.contentSpace = contentSpace;
	}

	@JsonIgnore
	public void setContentSpace(
		UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier) {

		try {
			contentSpace = contentSpaceUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setFormPages(FormPages[] formPages) {
		this.formPages = formPages;
	}

	@JsonIgnore
	public void setFormPages(
		UnsafeSupplier<FormPages[], Throwable> formPagesUnsafeSupplier) {

		try {
			formPages = formPagesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
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

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setSuccessPage(SuccessPage successPage) {
		this.successPage = successPage;
	}

	@JsonIgnore
	public void setSuccessPage(
		UnsafeSupplier<SuccessPage, Throwable> successPageUnsafeSupplier) {

		try {
			successPage = successPageUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;

	@GraphQLField
	@JsonProperty
	protected Long contentSpace;

	@GraphQLField
	@JsonProperty
	protected Creator creator;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected Date dateModified;

	@GraphQLField
	@JsonProperty
	protected String description;

	@GraphQLField
	@JsonProperty
	protected FormPages[] formPages;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String name;

	@GraphQLField
	@JsonProperty
	protected SuccessPage successPage;

}