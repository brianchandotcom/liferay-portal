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
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
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
			"dateDisplayed", BlogPostingForm::setDisplayDate
		).addOptionalDate(
			"dateCreated", BlogPostingForm::setCreateDate
		).addOptionalDate(
			"dateModified", BlogPostingForm::setModifiedDate
		).addOptionalLinkedModel(
			"creator", PersonIdentifier.class, BlogPostingForm::setCreatorId
		).addOptionalLinkedModel(
			"image", MediaObjectIdentifier.class, BlogPostingForm::setImageId
		).addOptionalString(
			"alternativeHeadline", BlogPostingForm::setAlternativeHeadline
		).addOptionalString(
			"caption", BlogPostingForm::setImageCaption
		).addOptionalString(
			"description", BlogPostingForm::setDescription
		).addOptionalString(
			"friendlyUrlPath", BlogPostingForm::setFriendlyURLPath
		).addOptionalStringList(
			"keywords", BlogPostingForm::setKeywords
		).addRequiredString(
			"articleBody", BlogPostingForm::setArticleBody
		).addRequiredString(
			"headline", BlogPostingForm::setHeadline
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
	 * Returns the blog posting's creator ID if present. Returns the provided
	 * default ID otherwise.
	 *
	 * @param  defaultCreatorId the default creator ID
	 * @return the blog posting's creator ID, if present; the provided default
	 *         ID otherwise.
	 * @review
	 */
	public long getCreatorId(long defaultCreatorId) {
		return Optional.ofNullable(
			_creatorId
		).orElse(
			defaultCreatorId
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
	 * Returns the blog posting's friendly URL if present. Returns an empty
	 * {@code String} otherwise.
	 *
	 * @return the blog posting's friendly URL if present; an empty {@code
	 *         String} otherwise
	 * @review
	 */
	public String getFriendlyURLPath() {
		return Optional.ofNullable(
			_friendlyURLPath
		).orElse(
			""
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

	public void setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	public void setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setCreatorId(long creatorId) {
		_creatorId = creatorId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public void setFriendlyURLPath(String friendlyURLPath) {
		_friendlyURLPath = friendlyURLPath;
	}

	public void setHeadline(String headline) {
		_headline = headline;
	}

	public void setImageCaption(String imageCaption) {
		_imageCaption = imageCaption;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private Date _createDate;
	private Long _creatorId;
	private String _description;
	private Date _displayDate;
	private String _friendlyURLPath;
	private String _headline;
	private String _imageCaption;
	private Long _imageId;
	private List<String> _keywords;
	private Date _modifiedDate;

}