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

package com.liferay.apio.architect.sample.liferay.portal.internal.form;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

/**
 * Represents the values extracted from a media object form.
 *
 * @author Alejandro Hernández
 */
public class MediaObjectCreatorForm {

	/**
	 * Builds and returns a {@code Form} that generates a {@code
	 * MediaObjectCreatorForm} that depends on the HTTP body.
	 *
	 * @param  formBuilder the form builder
	 * @return the form
	 */
	public static Form<MediaObjectCreatorForm> buildForm(
		Builder<MediaObjectCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The media object form"
		).description(
			__ -> "This form can be used to create a media object"
		).constructor(
			MediaObjectCreatorForm::new
		).addRequiredFile(
			"contentStream", MediaObjectCreatorForm::_setBinaryFile
		).addRequiredString(
			"text", MediaObjectCreatorForm::_setDescription
		).addRequiredString(
			"name", MediaObjectCreatorForm::_setName
		).addRequiredString(
			"headline", MediaObjectCreatorForm::_setTitle
		).build();
	}

	/**
	 * Returns the media object's binary file.
	 *
	 * @return the binary file
	 */
	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	/**
	 * Returns the media object's description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Returns the media object's name.
	 *
	 * @return the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the media object's title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return _title;
	}

	private void _setBinaryFile(BinaryFile binaryFile) {
		_binaryFile = binaryFile;
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setName(String name) {
		_name = name;
	}

	private void _setTitle(String title) {
		_title = title;
	}

	private BinaryFile _binaryFile;
	private String _description;
	private String _name;
	private String _title;

}