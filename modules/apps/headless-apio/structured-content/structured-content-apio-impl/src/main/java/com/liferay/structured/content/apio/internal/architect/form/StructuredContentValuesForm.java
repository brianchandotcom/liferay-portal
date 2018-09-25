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

package com.liferay.structured.content.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.structured.content.apio.architect.identifier.StructuredContentIdentifier;

/**
 * @author Javier Gamarra
 */
public class StructuredContentValuesForm {

	public static Form<StructuredContentValuesForm> buildValuesForm(
		Form.Builder<StructuredContentValuesForm> builder) {

		return builder.title(
			__ -> "The structured content values form"
		).description(
			__ -> "This form is used to create the values of a structured form"
		).constructor(
			StructuredContentValuesForm::new
		).addOptionalNestedModel(
			"geo", StructuredContentLocationForm::buildValuesForm,
			StructuredContentValuesForm::setStructuredContentLocationForm
		).addOptionalLinkedModel(
			"document", MediaObjectIdentifier.class,
			StructuredContentValuesForm::setDocument
		).addOptionalLinkedModel(
			"structuredContent", StructuredContentIdentifier.class,
			StructuredContentValuesForm::setStructuredContent
		).addOptionalString(
			"name", StructuredContentValuesForm::setName
		).addOptionalString(
			"value", StructuredContentValuesForm::setValue
		).build();
	}

	public Long getDocument() {
		return _document;
	}

	public String getName() {
		return _name;
	}

	public Long getStructuredContent() {
		return _structuredContent;
	}

	public StructuredContentLocationForm getStructuredContentLocationForm() {
		return _structuredContentLocationForm;
	}

	public String getValue() {
		return _value;
	}

	public void setDocument(Long document) {
		_document = document;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setStructuredContent(Long structuredContent) {
		_structuredContent = structuredContent;
	}

	public void setStructuredContentLocationForm(
		StructuredContentLocationForm structuredContentLocationForm) {

		_structuredContentLocationForm = structuredContentLocationForm;
	}

	public void setValue(String value) {
		_value = value;
	}

	private Long _document;
	private String _name;
	private Long _structuredContent;
	private StructuredContentLocationForm _structuredContentLocationForm;
	private String _value;

}