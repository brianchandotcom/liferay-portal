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

package com.liferay.blog.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;
import java.util.Optional;

/**
 * Instances of this class represent the values extracted from a blog posting
 * form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class BlogPostingForm {

	/**
	 * Builds a {@code Form} that generates {@code BlogPostingForm} depending on
	 * the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a blog posting form
	 * @review
	 */
	public static Form<BlogPostingForm> buildForm(
		Builder<BlogPostingForm> formBuilder) {

		return formBuilder.title(
			__ -> "The blog posting form"
		).description(
			__ -> "This form can be used to create or update a blog posting"
		).constructor(
			BlogPostingForm::new
		).addOptionalDate(
			"dateDisplayed", BlogPostingForm::_setDisplayDate
		).addOptionalDate(
			"dateCreated", BlogPostingForm::_setCreateDate
		).addOptionalDate(
			"dateModified", BlogPostingForm::_setModifiedDate
		).addOptionalString(
			"alternativeHeadline", BlogPostingForm::_setAlternativeHeadline
		).addOptionalString(
			"description", BlogPostingForm::_setDescription
		).addRequiredString(
			"articleBody", BlogPostingForm::_setArticleBody
		).addRequiredString(
			"headline", BlogPostingForm::_setHeadline
		).build();
	}

	/**
	 * Returns the blog posting's alternative headline
	 *
	 * @return the blog posting's alternative headline
	 * @review
	 */
	public String getAlternativeHeadline() {
		return Optional.ofNullable(
			_alternativeHeadline
		).orElse(
			""
		);
	}

	/**
	 * Returns the blog posting's body
	 *
	 * @return the blog posting's body
	 * @review
	 */
	public String getArticleBody() {
		return _articleBody;
	}

	/**
	 * Returns the blog posting's description
	 *
	 * @return the blog posting's description
	 * @review
	 */
	public String getDescription() {
		return Optional.ofNullable(
			_description
		).orElse(
			""
		);
	}

	/**
	 * Returns the blog posting's display date
	 *
	 * @return the blog posting's display date
	 * @review
	 */
	public Date getDisplayDate() {
		return Optional.ofNullable(
			_displayDate
		).orElseGet(
			Date::new
		);
	}

	/**
	 * Returns the blog posting's headline
	 *
	 * @return the blog posting's headline
	 * @review
	 */
	public String getHeadline() {
		return _headline;
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
		serviceContext.setScopeGroupId(groupId);

		if (_createDate != null) {
			serviceContext.setCreateDate(_createDate);
		}

		if (_modifiedDate != null) {
			serviceContext.setModifiedDate(_modifiedDate);
		}

		return serviceContext;
	}

	private void _setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	private void _setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	private void _setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	private void _setHeadline(String headline) {
		_headline = headline;
	}

	private void _setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private Date _createDate;
	private String _description;
	private Date _displayDate;
	private String _headline;
	private Date _modifiedDate;

}