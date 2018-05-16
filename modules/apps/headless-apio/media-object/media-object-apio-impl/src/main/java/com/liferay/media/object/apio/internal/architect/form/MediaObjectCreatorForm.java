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

package com.liferay.media.object.apio.internal.architect.form;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

/**
 * @author Javier Gamarra
 */
public class MediaObjectCreatorForm {

	public static Form<MediaObjectCreatorForm> buildForm(
		Builder<MediaObjectCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The media object creator form"
		).description(
			__ -> "This form can be used to create a media object"
		).constructor(
			MediaObjectCreatorForm::new
		).addOptionalString(
			"changeLog", MediaObjectCreatorForm::_setChangelog
		).addOptionalString(
			"description", MediaObjectCreatorForm::_setDescription
		).addRequiredFile(
			"binaryFile", MediaObjectCreatorForm::_setBinaryFile
		).addRequiredString(
			"name", MediaObjectCreatorForm::_setName
		).addRequiredString(
			"title", MediaObjectCreatorForm::_setTitle
		).build();
	}

	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	public String getChangelog() {
		return _changelog;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	private void _setBinaryFile(BinaryFile binaryFile) {
		_binaryFile = binaryFile;
	}

	private void _setChangelog(String changelog) {
		_changelog = changelog;
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
	private String _changelog;
	private String _description;
	private String _name;
	private String _title;

}