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
@XmlRootElement(name = "WebSite")
public class WebSite {

	public String[] getAvailableLanguages() {
		return _availableLanguages;
	}

	public ContentSpace getContentSpace() {
		return _contentSpace;
	}

	public UserAccount getCreator() {
		return _creator;
	}

	public String getDescription() {
		return _description;
	}

	public Integer getId() {
		return _id;
	}

	public UserAccount getMembers() {
		return _members;
	}

	public String getMembershipType() {
		return _membershipType;
	}

	public String getName() {
		return _name;
	}

	public String getPrivateUrl() {
		return _privateUrl;
	}

	public String getPublicUrl() {
		return _publicUrl;
	}

	public String getSelf() {
		return _self;
	}

	public WebSite getWebSite() {
		return _webSite;
	}

	public WebSite getWebSites() {
		return _webSites;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setContentSpace(ContentSpace contentSpace) {
		_contentSpace = contentSpace;
	}

	public void setCreator(UserAccount creator) {
		_creator = creator;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setMembers(UserAccount members) {
		_members = members;
	}

	public void setMembershipType(String membershipType) {
		_membershipType = membershipType;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPrivateUrl(String privateUrl) {
		_privateUrl = privateUrl;
	}

	public void setPublicUrl(String publicUrl) {
		_publicUrl = publicUrl;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setWebSite(WebSite webSite) {
		_webSite = webSite;
	}

	public void setWebSites(WebSite webSites) {
		_webSites = webSites;
	}

	private String[] _availableLanguages;
	private ContentSpace _contentSpace;
	private UserAccount _creator;
	private String _description;
	private Integer _id;
	private UserAccount _members;
	private String _membershipType;
	private String _name;
	private String _privateUrl;
	private String _publicUrl;
	private String _self;
	private WebSite _webSite;
	private WebSite _webSites;

}