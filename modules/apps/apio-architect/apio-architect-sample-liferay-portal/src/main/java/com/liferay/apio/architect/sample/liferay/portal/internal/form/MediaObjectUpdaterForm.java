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

import java.util.Optional;

/**
 * Instances of this class represent the values extracted from a media object
 * form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class MediaObjectUpdaterForm {

	/**
	 * Builds a {@code Form} that generates {@code MediaObjectCreatorForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a folder form
	 * @review
	 */
	public static Form<MediaObjectUpdaterForm> buildForm(
		Builder<MediaObjectUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The media object form"
		).description(
			__ -> "This form can be used to create a media object"
		).constructor(
			MediaObjectUpdaterForm::new
		).addOptionalString(
			"changelog", MediaObjectUpdaterForm::_setChangelog
		).addOptionalBoolean(
			"majorVersion", MediaObjectUpdaterForm::_setMajorVersion
		).addRequiredFile(
			"contentStream", MediaObjectUpdaterForm::_setBinaryFile
		).addRequiredString(
			"text", MediaObjectUpdaterForm::_setDescription
		).addRequiredString(
			"name", MediaObjectUpdaterForm::_setName
		).addRequiredString(
			"headline", MediaObjectUpdaterForm::_setTitle
		).build();
	}

	/**
	 * Returns the media object's binary file
	 *
	 * @return the media object's binary file
	 * @review
	 */
	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	public String getChangelog() {
		return _changelog;
	}

	/**
	 * Returns the media object's description
	 *
	 * @return the media object's description
	 * @review
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Returns {@code true} if this change is a major version.
	 *
	 * @return {@code true} if this change is a major version
	 * @review
	 */
	public Boolean getMajorVersion() {
		return Optional.ofNullable(
			_majorVersion
		).orElse(
			true
		);
	}

	/**
	 * Returns the media object's name
	 *
	 * @return the media object's name
	 * @review
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the media object's title
	 *
	 * @return the media object's title
	 * @review
	 */
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

	private void _setMajorVersion(Boolean majorVersion) {
		_majorVersion = majorVersion;
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
	private Boolean _majorVersion;
	private String _name;
	private String _title;

}