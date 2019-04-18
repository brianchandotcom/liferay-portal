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

package com.liferay.headless.form.client.dto.v1_0;

import com.liferay.headless.form.client.function.UnsafeSupplier;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormRecord {

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Creator creator;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public void setDatePublished(
		UnsafeSupplier<Date, Exception> datePublishedUnsafeSupplier) {

		try {
			datePublished = datePublishedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date datePublished;

	public Boolean getDraft() {
		return draft;
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	public void setDraft(
		UnsafeSupplier<Boolean, Exception> draftUnsafeSupplier) {

		try {
			draft = draftUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean draft;

	public FieldValue[] getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(FieldValue[] fieldValues) {
		this.fieldValues = fieldValues;
	}

	public void setFieldValues(
		UnsafeSupplier<FieldValue[], Exception> fieldValuesUnsafeSupplier) {

		try {
			fieldValues = fieldValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FieldValue[] fieldValues;

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public void setForm(UnsafeSupplier<Form, Exception> formUnsafeSupplier) {
		try {
			form = formUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Form form;

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public void setFormId(
		UnsafeSupplier<Long, Exception> formIdUnsafeSupplier) {

		try {
			formId = formIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long formId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormRecord)) {
			return false;
		}

		FormRecord formRecord = (FormRecord)object;

		return Objects.equals(toString(), formRecord.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"creator\": ");

		if (creator == null) {
			sb.append("null");
		}
		else {
			sb.append(creator);
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (dateCreated == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateCreated);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (dateModified == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateModified);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"datePublished\": ");

		if (datePublished == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(datePublished);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"draft\": ");

		if (draft == null) {
			sb.append("null");
		}
		else {
			sb.append(draft);
		}

		sb.append(", ");

		sb.append("\"fieldValues\": ");

		if (fieldValues == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < fieldValues.length; i++) {
				sb.append(fieldValues[i]);

				if ((i + 1) < fieldValues.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"form\": ");

		if (form == null) {
			sb.append("null");
		}
		else {
			sb.append(form);
		}

		sb.append(", ");

		sb.append("\"formId\": ");

		if (formId == null) {
			sb.append("null");
		}
		else {
			sb.append(formId);
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append("}");

		return sb.toString();
	}

}