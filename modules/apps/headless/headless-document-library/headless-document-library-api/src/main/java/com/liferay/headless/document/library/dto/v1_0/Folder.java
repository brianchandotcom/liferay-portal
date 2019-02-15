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

package com.liferay.headless.document.library.dto.v1_0;

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
@GraphQLName("Folder")
@XmlRootElement(name = "Folder")
public class Folder {

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public String getDescription() {
		return description;
	}

	public Document[] getDocuments() {
		return documents;
	}

	public Long[] getDocumentsIds() {
		return documentsIds;
	}

	public Folder getDocumentsRepository() {
		return documentsRepository;
	}

	public Long getDocumentsRepositoryId() {
		return documentsRepositoryId;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Folder[] getSubFolders() {
		return subFolders;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

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

	public void setDescription(
		UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

			try {
				description = descriptionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setDocuments(Document[] documents) {
		this.documents = documents;
	}

	public void setDocuments(
		UnsafeSupplier<Document[], Throwable> documentsUnsafeSupplier) {

			try {
				documents = documentsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setDocumentsIds(Long[] documentsIds) {
		this.documentsIds = documentsIds;
	}

	public void setDocumentsIds(
		UnsafeSupplier<Long[], Throwable> documentsIdsUnsafeSupplier) {

			try {
				documentsIds = documentsIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setDocumentsRepository(Folder documentsRepository) {
		this.documentsRepository = documentsRepository;
	}

	public void setDocumentsRepository(
		UnsafeSupplier<Folder, Throwable> documentsRepositoryUnsafeSupplier) {

			try {
				documentsRepository = documentsRepositoryUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	public void setDocumentsRepositoryId(Long documentsRepositoryId) {
		this.documentsRepositoryId = documentsRepositoryId;
	}

	public void setDocumentsRepositoryId(
		UnsafeSupplier<Long, Throwable> documentsRepositoryIdUnsafeSupplier) {

			try {
				documentsRepositoryId =
					documentsRepositoryIdUnsafeSupplier.get();
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

	public void setSubFolders(Folder[] subFolders) {
		this.subFolders = subFolders;
	}

	public void setSubFolders(
		UnsafeSupplier<Folder[], Throwable> subFoldersUnsafeSupplier) {

			try {
				subFolders = subFoldersUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	protected Date dateCreated;

	@GraphQLField
	protected Date dateModified;

	@GraphQLField
	protected String description;

	@GraphQLField
	protected Document[] documents;

	@GraphQLField
	protected Long[] documentsIds;

	@GraphQLField
	protected Folder documentsRepository;

	@GraphQLField
	protected Long documentsRepositoryId;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String name;

	@GraphQLField
	protected Folder[] subFolders;

}