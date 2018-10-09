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

package com.liferay.forms.apio.internal.architect.form;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.form.Form;
import com.liferay.folder.apio.architect.identifier.FolderIdentifier;

import java.util.Optional;

/**
 * Represents the values extracted from a media object creator form.
 *
 * @author Javier Gamarra
 */
public class MediaObjectCreatorForm {

	/**
	 * Builds a {@code Form} that generates a {@code MediaObjectCreatorForm}
	 * that depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a media object creator form
	 */
	public static Form<MediaObjectCreatorForm> buildForm(
		Form.Builder<MediaObjectCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The media object creator form"
		).description(
			__ -> "This form can be used to create a media object"
		).constructor(
			MediaObjectCreatorForm::new
		).addOptionalLinkedModel(
			"folder", FolderIdentifier.class,
			MediaObjectCreatorForm::setFolderId
		).addOptionalString(
			"description", MediaObjectCreatorForm::setDescription
		).addRequiredFile(
			"binaryFile", MediaObjectCreatorForm::setBinaryFile
		).addRequiredString(
			"name", MediaObjectCreatorForm::setName
		).addRequiredString(
			"title", MediaObjectCreatorForm::setTitle
		).build();
	}

	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	public String getDescription() {
		return _description;
	}

	public Optional<Long> getFolderIdOptional() {
		return Optional.ofNullable(_folderId);
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	public void setBinaryFile(BinaryFile binaryFile) {
		_binaryFile = binaryFile;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFolderId(Long folderId) {
		_folderId = folderId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private BinaryFile _binaryFile;
	private String _description;
	private Long _folderId;
	private String _name;
	private String _title;

}