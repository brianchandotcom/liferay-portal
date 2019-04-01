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

package com.liferay.headless.collaboration.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("KnowledgeBaseFolder")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "KnowledgeBaseFolder")
public class KnowledgeBaseFolder {

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Creator creator;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	public Number getNumberOfKnowledgeBaseArticles() {
		return numberOfKnowledgeBaseArticles;
	}

	public void setNumberOfKnowledgeBaseArticles(
		Number numberOfKnowledgeBaseArticles) {

		this.numberOfKnowledgeBaseArticles = numberOfKnowledgeBaseArticles;
	}

	@JsonIgnore
	public void setNumberOfKnowledgeBaseArticles(
		UnsafeSupplier<Number, Exception>
			numberOfKnowledgeBaseArticlesUnsafeSupplier) {

		try {
			numberOfKnowledgeBaseArticles =
				numberOfKnowledgeBaseArticlesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Number numberOfKnowledgeBaseArticles;

	public Number getNumberOfKnowledgeBaseFolders() {
		return numberOfKnowledgeBaseFolders;
	}

	public void setNumberOfKnowledgeBaseFolders(
		Number numberOfKnowledgeBaseFolders) {

		this.numberOfKnowledgeBaseFolders = numberOfKnowledgeBaseFolders;
	}

	@JsonIgnore
	public void setNumberOfKnowledgeBaseFolders(
		UnsafeSupplier<Number, Exception>
			numberOfKnowledgeBaseFoldersUnsafeSupplier) {

		try {
			numberOfKnowledgeBaseFolders =
				numberOfKnowledgeBaseFoldersUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Number numberOfKnowledgeBaseFolders;

	public ParentKnowledgeBaseFolder getParentKnowledgeBaseFolder() {
		return parentKnowledgeBaseFolder;
	}

	public void setParentKnowledgeBaseFolder(
		ParentKnowledgeBaseFolder parentKnowledgeBaseFolder) {

		this.parentKnowledgeBaseFolder = parentKnowledgeBaseFolder;
	}

	@JsonIgnore
	public void setParentKnowledgeBaseFolder(
		UnsafeSupplier<ParentKnowledgeBaseFolder, Exception>
			parentKnowledgeBaseFolderUnsafeSupplier) {

		try {
			parentKnowledgeBaseFolder =
				parentKnowledgeBaseFolderUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ParentKnowledgeBaseFolder parentKnowledgeBaseFolder;

	public Long getParentKnowledgeBaseFolderId() {
		return parentKnowledgeBaseFolderId;
	}

	public void setParentKnowledgeBaseFolderId(
		Long parentKnowledgeBaseFolderId) {

		this.parentKnowledgeBaseFolderId = parentKnowledgeBaseFolderId;
	}

	@JsonIgnore
	public void setParentKnowledgeBaseFolderId(
		UnsafeSupplier<Long, Exception>
			parentKnowledgeBaseFolderIdUnsafeSupplier) {

		try {
			parentKnowledgeBaseFolderId =
				parentKnowledgeBaseFolderIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Long parentKnowledgeBaseFolderId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KnowledgeBaseFolder)) {
			return false;
		}

		KnowledgeBaseFolder knowledgeBaseFolder = (KnowledgeBaseFolder)object;

		return Objects.equals(toString(), knowledgeBaseFolder.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"creator\": ");

		sb.append(creator);
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(dateCreated);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(dateModified);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(description);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseArticles\": ");

		sb.append(numberOfKnowledgeBaseArticles);
		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseFolders\": ");

		sb.append(numberOfKnowledgeBaseFolders);
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolder\": ");

		sb.append(parentKnowledgeBaseFolder);
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolderId\": ");

		sb.append(parentKnowledgeBaseFolderId);

		sb.append("}");

		return sb.toString();
	}

}