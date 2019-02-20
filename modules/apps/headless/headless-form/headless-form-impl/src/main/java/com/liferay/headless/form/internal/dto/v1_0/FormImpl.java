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
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormStructure;
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
@GraphQLName("Form")
@XmlRootElement(name = "Form")
public class FormImpl implements Form {

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
	public Date getDatePublished() {
			return datePublished;
	}

	public void setDatePublished(
			Date datePublished) {

			this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
			UnsafeSupplier<Date, Throwable>
				datePublishedUnsafeSupplier) {

			try {
				datePublished =
					datePublishedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date datePublished;
	public String getDefaultLanguage() {
			return defaultLanguage;
	}

	public void setDefaultLanguage(
			String defaultLanguage) {

			this.defaultLanguage = defaultLanguage;
	}

	@JsonIgnore
	public void setDefaultLanguage(
			UnsafeSupplier<String, Throwable>
				defaultLanguageUnsafeSupplier) {

			try {
				defaultLanguage =
					defaultLanguageUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String defaultLanguage;
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
	public FormRecord[] getFormRecords() {
			return formRecords;
	}

	public void setFormRecords(
			FormRecord[] formRecords) {

			this.formRecords = formRecords;
	}

	@JsonIgnore
	public void setFormRecords(
			UnsafeSupplier<FormRecord[], Throwable>
				formRecordsUnsafeSupplier) {

			try {
				formRecords =
					formRecordsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected FormRecord[] formRecords;
	public Long[] getFormRecordsIds() {
			return formRecordsIds;
	}

	public void setFormRecordsIds(
			Long[] formRecordsIds) {

			this.formRecordsIds = formRecordsIds;
	}

	@JsonIgnore
	public void setFormRecordsIds(
			UnsafeSupplier<Long[], Throwable>
				formRecordsIdsUnsafeSupplier) {

			try {
				formRecordsIds =
					formRecordsIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] formRecordsIds;
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
	public FormStructure getStructure() {
			return structure;
	}

	public void setStructure(
			FormStructure structure) {

			this.structure = structure;
	}

	@JsonIgnore
	public void setStructure(
			UnsafeSupplier<FormStructure, Throwable>
				structureUnsafeSupplier) {

			try {
				structure =
					structureUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected FormStructure structure;
	public Long getStructureId() {
			return structureId;
	}

	public void setStructureId(
			Long structureId) {

			this.structureId = structureId;
	}

	@JsonIgnore
	public void setStructureId(
			UnsafeSupplier<Long, Throwable>
				structureIdUnsafeSupplier) {

			try {
				structureId =
					structureIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long structureId;

}