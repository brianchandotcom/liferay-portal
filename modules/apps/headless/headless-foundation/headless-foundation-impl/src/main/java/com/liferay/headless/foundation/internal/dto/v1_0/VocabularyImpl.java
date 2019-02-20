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

import com.liferay.headless.foundation.dto.v1_0.Creator;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
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
@GraphQLName("Vocabulary")
@XmlRootElement(name = "Vocabulary")
public class VocabularyImpl implements Vocabulary {

	public String[] getAvailableLanguages() {
			return availableLanguages;
	}

	public void setAvailableLanguages(
			String[] availableLanguages) {

			this.availableLanguages = availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
			UnsafeSupplier<String[], Throwable>
				availableLanguagesUnsafeSupplier) {

			try {
				availableLanguages =
					availableLanguagesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;
	public Long getContentSpace() {
			return contentSpace;
	}

	public void setContentSpace(
			Long contentSpace) {

			this.contentSpace = contentSpace;
	}

	@JsonIgnore
	public void setContentSpace(
			UnsafeSupplier<Long, Throwable>
				contentSpaceUnsafeSupplier) {

			try {
				contentSpace =
					contentSpaceUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long contentSpace;
	public Creator getCreator() {
			return creator;
	}

	public void setCreator(
			Creator creator) {

			this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier) {

			try {
				creator =
					creatorUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Creator creator;
	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(
			Date dateCreated) {

			this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier) {

			try {
				dateCreated =
					dateCreatedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
			return dateModified;
	}

	public void setDateModified(
			Date dateModified) {

			this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
			UnsafeSupplier<Date, Throwable>
				dateModifiedUnsafeSupplier) {

			try {
				dateModified =
					dateModifiedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateModified;
	public String getDescription() {
			return description;
	}

	public void setDescription(
			String description) {

			this.description = description;
	}

	@JsonIgnore
	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier) {

			try {
				description =
					descriptionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String description;
	public Boolean getHasCategories() {
			return hasCategories;
	}

	public void setHasCategories(
			Boolean hasCategories) {

			this.hasCategories = hasCategories;
	}

	@JsonIgnore
	public void setHasCategories(
			UnsafeSupplier<Boolean, Throwable>
				hasCategoriesUnsafeSupplier) {

			try {
				hasCategories =
					hasCategoriesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean hasCategories;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;

}