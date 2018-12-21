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
import com.liferay.structured.content.apio.architect.model.StructuredContentLocation;
import com.liferay.structured.content.apio.architect.model.StructuredContentValue;

/**
 * @author Javier Gamarra
 */
public class StructuredContentValueForm implements StructuredContentValue {

	public static Form<StructuredContentValueForm> buildForm(
		Form.Builder<StructuredContentValueForm> builder) {

		return builder.title(
			__ -> "The structured content values form"
		).description(
			__ -> "This form is used to create the values of a structured form"
		).constructor(
			StructuredContentValueForm::new
		).addOptionalLinkedModel(
			"document", MediaObjectIdentifier.class,
			StructuredContentValueForm::setDocument
		).addOptionalLinkedModel(
			"structuredContent", StructuredContentIdentifier.class,
			StructuredContentValueForm::setStructuredContentId
		).addOptionalNestedModel(
			"geo", StructuredContentLocationForm::buildForm,
			StructuredContentValueForm::setStructuredContentLocationForm
		).addOptionalString(
			"name", StructuredContentValueForm::setName
		).addOptionalString(
			"value", StructuredContentValueForm::setValue
		).build();
	}

	@Override
	public Long getDocument() {
		return _document;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Long getStructuredContentId() {
		return _structuredContentId;
	}

	@Override
	public StructuredContentLocation getStructuredContentLocation() {
		return _structuredContentLocation;
	}

	@Override
	public String getValue() {
		return _value;
	}

	public void setDocument(Long document) {
		_document = document;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setStructuredContentId(Long structuredContentId) {
		_structuredContentId = structuredContentId;
	}

	public void setStructuredContentLocationForm(
		StructuredContentLocation structuredContentLocation) {

		_structuredContentLocation = structuredContentLocation;
	}

	public void setValue(String value) {
		_value = value;
	}

	private Long _document;
	private String _name;
	private Long _structuredContentId;
	private StructuredContentLocation _structuredContentLocation;
	private String _value;

}