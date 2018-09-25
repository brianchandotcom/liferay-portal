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
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Optional;

/**
 * Represents the values extracted from a media object form.
 *
 * @author Javier Gamarra
 */
public class MediaObjectCreatorForm {

	/**
	 * Builds a {@code Form} that generates a {@code MediaObjectCreatorForm}
	 * that depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return the folder form
	 */
	public static Form<MediaObjectCreatorForm> buildForm(
		Form.Builder<MediaObjectCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The media object creator form"
		).description(
			__ -> "This form can be used to create a media object"
		).constructor(
			MediaObjectCreatorForm::new
		).addOptionalLinkedModelList(
			"categories", CategoryIdentifier.class,
			MediaObjectCreatorForm::setCategories
		).addOptionalString(
			"description", MediaObjectCreatorForm::setDescription
		).addOptionalString(
			"title", MediaObjectCreatorForm::setTitle
		).addOptionalStringList(
			"keywords", MediaObjectCreatorForm::setKeywords
		).addRequiredFile(
			"binaryFile", MediaObjectCreatorForm::setBinaryFile
		).build();
	}

	/**
	 * Returns the media object's binaries.
	 *
	 * @return the binaries
	 */
	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	public List<Long> getCategories() {
		return _categories;
	}

	/**
	 * Returns the media object's description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return _description;
	}

	public List<String> getKeywords() {
		return _keywords;
	}

	/**
	 * Returns the service context related with this form
	 *
	 * @param  groupId the group ID
	 * @return the service context
	 */
	public ServiceContext getServiceContext(long groupId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (ListUtil.isNotEmpty(_keywords)) {
			serviceContext.setAssetTagNames(ArrayUtil.toStringArray(_keywords));
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	/**
	 * Returns the media object's title.
	 *
	 * @return the title
	 */
	public Optional<String> getTitleOptional() {
		return Optional.ofNullable(_title);
	}

	public void setBinaryFile(BinaryFile binaryFile) {
		_binaryFile = binaryFile;
	}

	public void setCategories(List<Long> categories) {
		_categories = categories;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private BinaryFile _binaryFile;
	private List<Long> _categories;
	private String _description;
	private List<String> _keywords;
	private String _title;

}