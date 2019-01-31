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

package com.liferay.headless.web.experience.dto;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "StructuredContent")
public class StructuredContent {

	public AggregateRating getAggregateRating() {
		return _aggregateRating;
	}

	public String[] getAvailableLanguages() {
		return _availableLanguages;
	}

	public String getCategory() {
		return _category;
	}

	public Comment getComment() {
		return _comment;
	}

	public String getContentSpace() {
		return _contentSpace;
	}

	public ContentStructure getContentStructure() {
		return _contentStructure;
	}

	public String getCreator() {
		return _creator;
	}

	public String getDateCreated() {
		return _dateCreated;
	}

	public String getDateModified() {
		return _dateModified;
	}

	public String getDatePublished() {
		return _datePublished;
	}

	public String getDescription() {
		return _description;
	}

	public Integer getId() {
		return _id;
	}

	public String[] getKeywords() {
		return _keywords;
	}

	public String getLastReviewed() {
		return _lastReviewed;
	}

	public Object getRenderedContentsByTemplate() {
		return _renderedContentsByTemplate;
	}

	public String getSelf() {
		return _self;
	}

	public String getTitle() {
		return _title;
	}

	public Object getValues() {
		return _values;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		_aggregateRating = aggregateRating;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setCategory(String category) {
		_category = category;
	}

	public void setComment(Comment comment) {
		_comment = comment;
	}

	public void setContentSpace(String contentSpace) {
		_contentSpace = contentSpace;
	}

	public void setContentStructure(ContentStructure contentStructure) {
		_contentStructure = contentStructure;
	}

	public void setCreator(String creator) {
		_creator = creator;
	}

	public void setDateCreated(String dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(String dateModified) {
		_dateModified = dateModified;
	}

	public void setDatePublished(String datePublished) {
		_datePublished = datePublished;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setKeywords(String[] keywords) {
		_keywords = keywords;
	}

	public void setLastReviewed(String lastReviewed) {
		_lastReviewed = lastReviewed;
	}

	public void setRenderedContentsByTemplate(
		Object renderedContentsByTemplate) {

		_renderedContentsByTemplate = renderedContentsByTemplate;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setValues(Object values) {
		_values = values;
	}

	private AggregateRating _aggregateRating;
	private String[] _availableLanguages;
	private String _category;
	private Comment _comment;
	private String _contentSpace;
	private ContentStructure _contentStructure;
	private String _creator;
	private String _dateCreated;
	private String _dateModified;
	private String _datePublished;
	private String _description;
	private Integer _id;
	private String[] _keywords;
	private String _lastReviewed;
	private Object _renderedContentsByTemplate;
	private String _self;
	private String _title;
	private Object _values;

}