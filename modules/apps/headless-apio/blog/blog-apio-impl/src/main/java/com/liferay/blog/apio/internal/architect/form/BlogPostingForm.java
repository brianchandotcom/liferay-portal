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
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Represents the values extracted from a blog posting form.
 *
 * @author Alejandro Hernández
 */
public class BlogPostingForm implements BlogPosting {

	/**
	 * Builds a {@code Form} that generates a {@code BlogPostingForm} that
	 * depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return the blog posting form
	 */
	public static Form<BlogPostingForm> buildForm(
		Form.Builder<BlogPostingForm> formBuilder) {

		return formBuilder.title(
			__ -> "The blog posting form"
		).description(
			__ -> "This form can be used to create or update a blog posting"
		).constructor(
			BlogPostingForm::new
		).addOptionalDate(
			"dateCreated", BlogPostingForm::setCreateDate
		).addOptionalDate(
			"dateModified", BlogPostingForm::setModifiedDate
		).addOptionalDate(
			"datePublished", BlogPostingForm::setPublishedDate
		).addOptionalLinkedModel(
			"creator", PersonIdentifier.class, BlogPostingForm::setCreatorId
		).addOptionalLinkedModel(
			"image", MediaObjectIdentifier.class, BlogPostingForm::setImageId
		).addOptionalLinkedModelList(
			"category", CategoryIdentifier.class, BlogPostingForm::setCategories
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
	 * Returns the blog posting's alternative headline.
	 *
	 * @return the alternative headline
	 */
	public String getAlternativeHeadline() {
		return _alternativeHeadline;
	}

	/**
	 * Returns the blog post's body.
	 *
	 * @return the blog post's body
	 */
	public String getArticleBody() {
		return _articleBody;
	}

	/**
	 * Returns the blog post's image caption.
	 *
	 * @return the image caption
	 */
	public String getCaption() {
		return _imageCaption;
	}

	@Override
	public List<Long> getCategories() {
		return _categories;
	}

	@Override
	public Date getCreatedDate() {
		return Optional.ofNullable(
			_createDate
		).orElseGet(
			Date::new
		);
	}

	/**
	 * Returns the blog post's creator ID
	 *
	 * @return the creator ID
	 */
	public Long getCreatorId() {
		return _creatorId;
	}

	/**
	 * Returns the blog post's description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Returns the blog post's friendly URL
	 *
	 * @return the friendly URL
	 */
	public String getFriendlyURLPath() {
		return _friendlyURLPath;
	}

	/**
	 * Returns the blog post's headline.
	 *
	 * @return the headline
	 */
	public String getHeadline() {
		return _headline;
	}

	@Override
	public Long getImageId() {
		return _imageId;
	}

	@Override
	public List<String> getKeywords() {
		return _keywords;
	}

	@Override
	public Date getModifiedDate() {
		return Optional.ofNullable(
			_modifiedDate
		).orElseGet(
			Date::new
		);
	}

	/**
	 * Returns the blog post's display date.
	 *
	 * @return the display date
	 */
	@Override
	public Date getPublishedDate() {
		return _publishedDate;

	}

	public void setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	public void setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	public void setCategories(List<Long> categories) {
		_categories = categories;
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

	public void setPublishedDate(Date publishedDate) {
		_publishedDate = publishedDate;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private List<Long> _categories;
	private Date _createDate;
	private Long _creatorId;
	private String _description;
	private String _friendlyURLPath;
	private String _headline;
	private String _imageCaption;
	private Long _imageId;
	private List<String> _keywords;
	private Date _modifiedDate;
	private Date _publishedDate;

}