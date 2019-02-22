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

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.Creator;
import com.liferay.headless.foundation.dto.v1_0.ParentCategory;
import com.liferay.headless.foundation.dto.v1_0.ParentVocabulary;
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
@GraphQLName("Category")
@XmlRootElement(name = "Category")
public class CategoryImpl implements Category {

	public String[] getAvailableLanguages() {
			return availableLanguages;
	}

	public void setAvailableLanguages(
			String[] availableLanguages) {

			this.availableLanguages = (String[])availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
			UnsafeSupplier<String[], Throwable>
				availableLanguagesUnsafeSupplier) {

			try {
				availableLanguages =
					(String[])availableLanguagesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String[] availableLanguages;
	public ParentCategory getParentCategory() {
			return parentCategory;
	}

	public void setParentCategory(
			ParentCategory parentCategory) {

			this.parentCategory = (ParentCategoryImpl)parentCategory;
	}

	@JsonIgnore
	public void setParentCategory(
			UnsafeSupplier<ParentCategory, Throwable>
				parentCategoryUnsafeSupplier) {

			try {
				parentCategory =
					(ParentCategoryImpl)parentCategoryUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected ParentCategoryImpl parentCategory;
	public Creator getCreator() {
			return creator;
	}

	public void setCreator(
			Creator creator) {

			this.creator = (CreatorImpl)creator;
	}

	@JsonIgnore
	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier) {

			try {
				creator =
					(CreatorImpl)creatorUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected CreatorImpl creator;
	public Long getCreatorId() {
			return creatorId;
	}

	public void setCreatorId(
			Long creatorId) {

			this.creatorId = (Long)creatorId;
	}

	@JsonIgnore
	public void setCreatorId(
			UnsafeSupplier<Long, Throwable>
				creatorIdUnsafeSupplier) {

			try {
				creatorId =
					(Long)creatorIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long creatorId;
	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(
			Date dateCreated) {

			this.dateCreated = (Date)dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier) {

			try {
				dateCreated =
					(Date)dateCreatedUnsafeSupplier.get();
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

			this.dateModified = (Date)dateModified;
	}

	@JsonIgnore
	public void setDateModified(
			UnsafeSupplier<Date, Throwable>
				dateModifiedUnsafeSupplier) {

			try {
				dateModified =
					(Date)dateModifiedUnsafeSupplier.get();
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

			this.description = (String)description;
	}

	@JsonIgnore
	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier) {

			try {
				description =
					(String)descriptionUnsafeSupplier.get();
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

			this.hasCategories = (Boolean)hasCategories;
	}

	@JsonIgnore
	public void setHasCategories(
			UnsafeSupplier<Boolean, Throwable>
				hasCategoriesUnsafeSupplier) {

			try {
				hasCategories =
					(Boolean)hasCategoriesUnsafeSupplier.get();
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
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = (String)name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					(String)nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public ParentVocabulary getParentVocabulary() {
			return parentVocabulary;
	}

	public void setParentVocabulary(
			ParentVocabulary parentVocabulary) {

			this.parentVocabulary = (ParentVocabularyImpl)parentVocabulary;
	}

	@JsonIgnore
	public void setParentVocabulary(
			UnsafeSupplier<ParentVocabulary, Throwable>
				parentVocabularyUnsafeSupplier) {

			try {
				parentVocabulary =
					(ParentVocabularyImpl)parentVocabularyUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected ParentVocabularyImpl parentVocabulary;
	public Long getParentVocabularyId() {
			return parentVocabularyId;
	}

	public void setParentVocabularyId(
			Long parentVocabularyId) {

			this.parentVocabularyId = (Long)parentVocabularyId;
	}

	@JsonIgnore
	public void setParentVocabularyId(
			UnsafeSupplier<Long, Throwable>
				parentVocabularyIdUnsafeSupplier) {

			try {
				parentVocabularyId =
					(Long)parentVocabularyIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long parentVocabularyId;

}