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
import com.liferay.apio.architect.function.throwable.ThrowableFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;

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
		).addOptionalLong(
			"creator", BlogPostingForm::_setAuthorId
		).addOptionalLong(
			"image", BlogPostingForm::_setImageId
		).addOptionalString(
			"alternativeHeadline", BlogPostingForm::_setAlternativeHeadline
		).addOptionalString(
			"caption", BlogPostingForm::_setImageCaption
		).addOptionalString(
			"description", BlogPostingForm::_setDescription
		).addOptionalString(
			"semanticUrl", BlogPostingForm::_setSemanticUrl
		).addOptionalStringList(
			"keywords", BlogPostingForm::_setKeywords
		).addRequiredString(
			"articleBody", BlogPostingForm::_setArticleBody
		).addRequiredString(
			"headline", BlogPostingForm::_setHeadline
		).build();
	}

	/**
	 * Returns the blog posting's alternative headline if present. Returns an
	 * empty {@code String} otherwise.
	 *
	 * @return the blog posting's alternative headline if present; an empty
	 *         {@code String} otherwise
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
	 * Returns the blog posting's author ID if present. Returns the provided
	 * default ID otherwise.
	 *
	 * @param  defaultAuthorId the default author ID
	 * @return the blog posting's author ID, if present; the provided default ID
	 *         otherwise.
	 * @review
	 */
	public long getAuthorId(long defaultAuthorId) {
		return Optional.ofNullable(
			_authorId
		).orElse(
			defaultAuthorId
		);
	}

	/**
	 * Returns the blog posting's description if present. Returns an empty
	 * {@code String} otherwise.
	 *
	 * @return the blog posting's description if present; an empty {@code
	 *         String} otherwise
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
	 * Returns the blog posting's display date if present. Returns today's date
	 * otherwise.
	 *
	 * @return the blog posting's display date if present; today's date
	 *         otherwise.
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
	 * Returns the blog posting's image caption if present. Returns empty {@code
	 * String} otherwise.
	 *
	 * @return the blog posting's image caption if present; empty {@code String}
	 *         otherwise
	 * @review
	 */
	public String getImageCaption() {
		return Optional.ofNullable(
			_imageCaption
		).orElse(
			""
		);
	}

	/**
	 * Returns the {@link ImageSelector} for the blog posting's image, if an
	 * image ID is present. Returns {@code null} otherwise.
	 *
	 * @param  function a function that transforms a file entry ID into a {@link
	 *         FileEntry}
	 * @return the {@link ImageSelector} for the blog posting's image, if an
	 *         image ID is present; {@code null} otherwise
	 * @review
	 */
	public ImageSelector getImageSelector(
		ThrowableFunction<Long, FileEntry> function) {

		if (_imageId == null) {
			return null;
		}

		return Try.fromFallible(
			() -> function.apply(_imageId)
		).map(
			fileEntry -> new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0,\"width\": 0,\"x\": 0,\"y\": 0}")
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to find file entry with id " + _imageId)
		);
	}

	/**
	 * Returns the blog posting's semantic URL if present. Returns an empty
	 * {@code String} otherwise.
	 *
	 * @return the blog posting's semantic URL if present; an empty {@code
	 *         String} otherwise
	 * @review
	 */
	public String getSemanticUrl() {
		return Optional.ofNullable(
			_semanticUrl
		).orElse(
			""
		);
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

		if (ListUtil.isNotEmpty(_keywords)) {
			serviceContext.setAssetTagNames(ArrayUtil.toStringArray(_keywords));
		}

		return serviceContext;
	}

	private void _setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	private void _setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	private void _setAuthorId(long authorId) {
		_authorId = authorId;
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

	private void _setImageCaption(String imageCaption) {
		_imageCaption = imageCaption;
	}

	private void _setImageId(long imageId) {
		_imageId = imageId;
	}

	private void _setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	private void _setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	private void _setSemanticUrl(String semanticUrl) {
		_semanticUrl = semanticUrl;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private Long _authorId;
	private Date _createDate;
	private String _description;
	private Date _displayDate;
	private String _headline;
	private String _imageCaption;
	private Long _imageId;
	private List<String> _keywords;
	private Date _modifiedDate;
	private String _semanticUrl;

}