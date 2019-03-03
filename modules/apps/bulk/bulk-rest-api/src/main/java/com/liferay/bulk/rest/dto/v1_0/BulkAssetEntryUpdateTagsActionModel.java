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

package com.liferay.bulk.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("BulkAssetEntryUpdateTagsActionModel")
@XmlRootElement(name = "BulkAssetEntryUpdateTagsActionModel")
public class BulkAssetEntryUpdateTagsActionModel {

	public Boolean getAppend() {
		return append;
	}

	public Long getFolderId() {
		return folderId;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public Boolean getSelectAll() {
		return selectAll;
	}

	public String[] getSelection() {
		return selection;
	}

	public String[] getToAddTagNames() {
		return toAddTagNames;
	}

	public String[] getToRemoveTagNames() {
		return toRemoveTagNames;
	}

	public void setAppend(Boolean append) {
		this.append = append;
	}

	@JsonIgnore
	public void setAppend(
		UnsafeSupplier<Boolean, Exception> appendUnsafeSupplier) {

		try {
			append = appendUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	@JsonIgnore
	public void setFolderId(
		UnsafeSupplier<Long, Exception> folderIdUnsafeSupplier) {

		try {
			folderId = folderIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	@JsonIgnore
	public void setRepositoryId(
		UnsafeSupplier<Long, Exception> repositoryIdUnsafeSupplier) {

		try {
			repositoryId = repositoryIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	@JsonIgnore
	public void setSelectAll(
		UnsafeSupplier<Boolean, Exception> selectAllUnsafeSupplier) {

		try {
			selectAll = selectAllUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSelection(String[] selection) {
		this.selection = selection;
	}

	@JsonIgnore
	public void setSelection(
		UnsafeSupplier<String[], Exception> selectionUnsafeSupplier) {

		try {
			selection = selectionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setToAddTagNames(String[] toAddTagNames) {
		this.toAddTagNames = toAddTagNames;
	}

	@JsonIgnore
	public void setToAddTagNames(
		UnsafeSupplier<String[], Exception> toAddTagNamesUnsafeSupplier) {

		try {
			toAddTagNames = toAddTagNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setToRemoveTagNames(String[] toRemoveTagNames) {
		this.toRemoveTagNames = toRemoveTagNames;
	}

	@JsonIgnore
	public void setToRemoveTagNames(
		UnsafeSupplier<String[], Exception> toRemoveTagNamesUnsafeSupplier) {

		try {
			toRemoveTagNames = toRemoveTagNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(28);

		sb.append("{");

		sb.append("\"append\": ");

		sb.append(append);
		sb.append(", ");

		sb.append("\"toAddTagNames\": ");

		sb.append("\"");
		sb.append(toAddTagNames);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"toRemoveTagNames\": ");

		sb.append("\"");
		sb.append(toRemoveTagNames);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"folderId\": ");

		sb.append(folderId);
		sb.append(", ");

		sb.append("\"repositoryId\": ");

		sb.append(repositoryId);
		sb.append(", ");

		sb.append("\"selectAll\": ");

		sb.append(selectAll);
		sb.append(", ");

		sb.append("\"selection\": ");

		sb.append("\"");
		sb.append(selection);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected Boolean append;

	@GraphQLField
	@JsonProperty
	protected Long folderId;

	@GraphQLField
	@JsonProperty
	protected Long repositoryId;

	@GraphQLField
	@JsonProperty
	protected Boolean selectAll;

	@GraphQLField
	@JsonProperty
	protected String[] selection;

	@GraphQLField
	@JsonProperty
	protected String[] toAddTagNames;

	@GraphQLField
	@JsonProperty
	protected String[] toRemoveTagNames;

}