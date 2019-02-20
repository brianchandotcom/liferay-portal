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

package com.liferay.headless.document.library.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Creator;
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
@GraphQLName("Comment")
@XmlRootElement(name = "Comment")
public class CommentImpl implements Comment {

	public Comment[] getComments() {
			return comments;
	}

	public void setComments(
			Comment[] comments) {

			this.comments = comments;
	}

	@JsonIgnore
	public void setComments(
			UnsafeSupplier<Comment[], Throwable>
				commentsUnsafeSupplier) {

			try {
				comments =
					commentsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Comment[] comments;
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
	public Boolean getHasComments() {
			return hasComments;
	}

	public void setHasComments(
			Boolean hasComments) {

			this.hasComments = hasComments;
	}

	@JsonIgnore
	public void setHasComments(
			UnsafeSupplier<Boolean, Throwable>
				hasCommentsUnsafeSupplier) {

			try {
				hasComments =
					hasCommentsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean hasComments;
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
	public String getText() {
			return text;
	}

	public void setText(
			String text) {

			this.text = text;
	}

	@JsonIgnore
	public void setText(
			UnsafeSupplier<String, Throwable>
				textUnsafeSupplier) {

			try {
				text =
					textUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String text;

}