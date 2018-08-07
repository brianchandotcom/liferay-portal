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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * Instances of this class represent the values extracted from a media object
 * form.
 *
 * @author Javier Gamarra
 * @review
 */
public class MediaObjectCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code MediaObjectCreatorForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a folder form
	 * @review
	 */
	public static Form<MediaObjectCreatorForm> buildForm(
		Builder<MediaObjectCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The media object creator form"
		).description(
			__ -> "This form can be used to create a media object"
		).constructor(
			MediaObjectCreatorForm::new
		).addOptionalString(
			"changeLog", MediaObjectCreatorForm::setChangelog
		).addOptionalString(
			"description", MediaObjectCreatorForm::setDescription
		).addOptionalString(
			"headline", MediaObjectCreatorForm::setTitle
		).addOptionalStringList(
			"keywords", MediaObjectCreatorForm::setKeywords
		).addRequiredFile(
			"binaryFile", MediaObjectCreatorForm::setBinaryFile
		).addRequiredString(
			"name", MediaObjectCreatorForm::setName
		).build();
	}

	/**
	 * Returns the media object's binaries
	 *
	 * @return the media object's binaries
	 * @review
	 */
	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	/**
	 * Returns the media object's changelog
	 *
	 * @return the media object's changelog
	 * @review
	 */
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
	 * Returns the media object's name
	 *
	 * @return the media object's name
	 * @review
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns the service context related with this form
	 *
	 * @param  groupId the group ID
	 * @return the service context
	 * @review
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
	 * Returns the media object's title
	 *
	 * @return the media object's title
	 * @review
	 */
	public String getTitle() {
		return _title;
	}

	public void setBinaryFile(BinaryFile binaryFile) {
		_binaryFile = binaryFile;
	}

	public void setChangelog(String changelog) {
		_changelog = changelog;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private BinaryFile _binaryFile;
	private String _changelog;
	private String _description;
	private List<String> _keywords;
	private String _name;
	private String _title;

}