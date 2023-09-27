/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.graphql.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.io.Serializable;

/**
 * @author Javier de Arcos
 */
@GraphQLName(
	description = "Represents the user who created/authored the content. Properties follow the [creator](https://schema.org/creator) specification.",
	value = "Creator"
)
public class Creator implements Serializable {

	public String getAdditionalName() {
		return additionalName;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public Long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public String getProfileURL() {
		return profileURL;
	}

	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	@GraphQLField(
		description = "The user's additional name, which can be used as a middle name."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String additionalName;

	@GraphQLField(description = "The type of the content.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String contentType;

	@GraphQLField(description = "The user's surname (last name).")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String familyName;

	@GraphQLField(description = "The user's first name.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String givenName;

	@GraphQLField(description = "The user's ID.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@GraphQLField(description = "A relative URL to the user's profile image.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String image;

	@GraphQLField(description = "The user's full name.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String name;

	@GraphQLField(description = "A relative URL to the user's profile.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String profileURL;

}