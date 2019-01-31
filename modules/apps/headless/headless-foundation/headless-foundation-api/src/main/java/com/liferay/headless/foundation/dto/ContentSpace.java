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

package com.liferay.headless.foundation.dto;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "ContentSpace")
public class ContentSpace {

	public String[] getAvailableLanguages() {
		return _availableLanguages;
	}

	public String getBlogPosts() {
		return _blogPosts;
	}

	public String getContentStructures() {
		return _contentStructures;
	}

	public UserAccount getCreator() {
		return _creator;
	}

	public String getDescription() {
		return _description;
	}

	public String getDocumentsRepository() {
		return _documentsRepository;
	}

	public String getForms() {
		return _forms;
	}

	public String getFormStructures() {
		return _formStructures;
	}

	public Integer getId() {
		return _id;
	}

	public Keyword getKeywords() {
		return _keywords;
	}

	public String getName() {
		return _name;
	}

	public String getSelf() {
		return _self;
	}

	public String getStructuredContents() {
		return _structuredContents;
	}

	public Vocabulary getVocabularies() {
		return _vocabularies;
	}

	public WebSite getWebSite() {
		return _webSite;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setBlogPosts(String blogPosts) {
		_blogPosts = blogPosts;
	}

	public void setContentStructures(String contentStructures) {
		_contentStructures = contentStructures;
	}

	public void setCreator(UserAccount creator) {
		_creator = creator;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDocumentsRepository(String documentsRepository) {
		_documentsRepository = documentsRepository;
	}

	public void setForms(String forms) {
		_forms = forms;
	}

	public void setFormStructures(String formStructures) {
		_formStructures = formStructures;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setKeywords(Keyword keywords) {
		_keywords = keywords;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setStructuredContents(String structuredContents) {
		_structuredContents = structuredContents;
	}

	public void setVocabularies(Vocabulary vocabularies) {
		_vocabularies = vocabularies;
	}

	public void setWebSite(WebSite webSite) {
		_webSite = webSite;
	}

	private String[] _availableLanguages;
	private String _blogPosts;
	private String _contentStructures;
	private UserAccount _creator;
	private String _description;
	private String _documentsRepository;
	private String _forms;
	private String _formStructures;
	private Integer _id;
	private Keyword _keywords;
	private String _name;
	private String _self;
	private String _structuredContents;
	private Vocabulary _vocabularies;
	private WebSite _webSite;

}