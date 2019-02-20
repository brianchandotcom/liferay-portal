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

	public void setCreator(
			Creator creator);

	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier);
	public Date getDateCreated();

	public void setDateCreated(
			Date dateCreated);

	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier);
	public Date getDateModified();

	public void setDateModified(
			Date dateModified);

	public void setDateModified(
			UnsafeSupplier<Date, Throwable>
				dateModifiedUnsafeSupplier);
	public Date getDatePublished();

	public void setDatePublished(
			Date datePublished);

	public void setDatePublished(
			UnsafeSupplier<Date, Throwable>
				datePublishedUnsafeSupplier);
	public Boolean getDraft();

	public void setDraft(
			Boolean draft);

	public void setDraft(
			UnsafeSupplier<Boolean, Throwable>
				draftUnsafeSupplier);
	public FieldValues[] getFieldValues();

	public void setFieldValues(
			FieldValues[] fieldValues);

	public void setFieldValues(
			UnsafeSupplier<FieldValues[], Throwable>
				fieldValuesUnsafeSupplier);
	public Form getForm();

	public void setForm(
			Form form);

	public void setForm(
			UnsafeSupplier<Form, Throwable>
				formUnsafeSupplier);
	public Long getFormId();

	public void setFormId(
			Long formId);

	public void setFormId(
			UnsafeSupplier<Long, Throwable>
				formIdUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);

}