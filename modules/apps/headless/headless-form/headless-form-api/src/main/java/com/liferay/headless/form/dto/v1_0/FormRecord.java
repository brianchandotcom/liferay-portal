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

package com.liferay.headless.form.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface FormRecord {

	public Creator getCreator();

	public Date getDateCreated();

	public Date getDateModified();

	public Date getDatePublished();

	public Boolean getDraft();

	public FieldValues[] getFieldValues();

	public Form getForm();

	public Long getFormId();

	public Long getId();

	public void setCreator(Creator creator);

	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier);

	public void setDateCreated(Date dateCreated);

	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier);

	public void setDateModified(Date dateModified);

	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier);

	public void setDatePublished(Date datePublished);

	public void setDatePublished(
		UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier);

	public void setDraft(Boolean draft);

	public void setDraft(
		UnsafeSupplier<Boolean, Throwable> draftUnsafeSupplier);

	public void setFieldValues(FieldValues[] fieldValues);

	public void setFieldValues(
		UnsafeSupplier<FieldValues[], Throwable> fieldValuesUnsafeSupplier);

	public void setForm(Form form);

	public void setForm(UnsafeSupplier<Form, Throwable> formUnsafeSupplier);

	public void setFormId(Long formId);

	public void setFormId(UnsafeSupplier<Long, Throwable> formIdUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

}