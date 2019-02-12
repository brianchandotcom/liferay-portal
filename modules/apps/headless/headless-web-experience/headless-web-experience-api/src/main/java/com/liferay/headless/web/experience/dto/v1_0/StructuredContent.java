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

package com.liferay.headless.web.experience.dto.v1_0;

import java.util.Date;

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

	public Long[] getCategory() {
		return _category;
	}

	public Comment[] getComment() {
		return _comment;
	}

	public Long getContentSpace() {
		return _contentSpace;
	}

	public ContentStructure getContentStructure() {
		return _contentStructure;
	}

	public Long getContentStructureId() {
		return _contentStructureId;
	}

	public Creator getCreator() {
		return _creator;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public Date getDatePublished() {
		return _datePublished;
	}

	public String getDescription() {
		return _description;
	}

	public Fields[] getFields() {
		return _fields;
	}

	public Long getId() {
		return _id;
	}

	public String[] getKeywords() {
		return _keywords;
	}

	public Date getLastReviewed() {
		return _lastReviewed;
	}

	public RenderedContentsByTemplate[] getRenderedContentsByTemplate() {
		return _renderedContentsByTemplate;
	}

	public String getTitle() {
		return _title;
	}

	public Values[] getValues() {
		return _values;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		_aggregateRating = aggregateRating;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setCategory(Long[] category) {
		_category = category;
	}

	public void setComment(Comment[] comment) {
		_comment = comment;
	}

	public void setContentSpace(Long contentSpace) {
		_contentSpace = contentSpace;
	}

	public void setContentStructure(ContentStructure contentStructure) {
		_contentStructure = contentStructure;
	}

	public void setContentStructureId(Long contentStructureId) {
		_contentStructureId = contentStructureId;
	}

	public void setCreator(Creator creator) {
		_creator = creator;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDatePublished(Date datePublished) {
		_datePublished = datePublished;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setFields(Fields[] fields) {
		_fields = fields;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setKeywords(String[] keywords) {
		_keywords = keywords;
	}

	public void setLastReviewed(Date lastReviewed) {
		_lastReviewed = lastReviewed;
	}

	public void setRenderedContentsByTemplate(
		RenderedContentsByTemplate[] renderedContentsByTemplate) {

		_renderedContentsByTemplate = renderedContentsByTemplate;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setValues(Values[] values) {
		_values = values;
	}

	private AggregateRating _aggregateRating;
	private String[] _availableLanguages;
	private Long[] _category;
	private Comment[] _comment;
	private Long _contentSpace;
	private ContentStructure _contentStructure;
	private Long _contentStructureId;
	private Creator _creator;
	private Date _dateCreated;
	private Date _dateModified;
	private Date _datePublished;
	private String _description;
	private Fields[] _fields;
	private Long _id;
	private String[] _keywords;
	private Date _lastReviewed;
	private RenderedContentsByTemplate[] _renderedContentsByTemplate;
	private String _title;
	private Values[] _values;

}