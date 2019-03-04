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

package com.liferay.headless.foundation.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ContactInformation")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "ContactInformation")
public class ContactInformation {

	public Email[] getEmail() {
		return email;
	}

	public Long[] getEmailIds() {
		return emailIds;
	}

	public String getFacebook() {
		return facebook;
	}

	public Long getId() {
		return id;
	}

	public String getJabber() {
		return jabber;
	}

	public PostalAddress[] getPostalAddress() {
		return postalAddress;
	}

	public Long[] getPostalAddressIds() {
		return postalAddressIds;
	}

	public String getSkype() {
		return skype;
	}

	public String getSms() {
		return sms;
	}

	public Phone[] getTelephone() {
		return telephone;
	}

	public Long[] getTelephoneIds() {
		return telephoneIds;
	}

	public String getTwitter() {
		return twitter;
	}

	public WebUrl[] getWebUrl() {
		return webUrl;
	}

	public Long[] getWebUrlIds() {
		return webUrlIds;
	}

	public void setEmail(Email[] email) {
		this.email = email;
	}

	@JsonIgnore
	public void setEmail(
		UnsafeSupplier<Email[], Exception> emailUnsafeSupplier) {

		try {
			email = emailUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setEmailIds(Long[] emailIds) {
		this.emailIds = emailIds;
	}

	@JsonIgnore
	public void setEmailIds(
		UnsafeSupplier<Long[], Exception> emailIdsUnsafeSupplier) {

		try {
			emailIds = emailIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	@JsonIgnore
	public void setFacebook(
		UnsafeSupplier<String, Exception> facebookUnsafeSupplier) {

		try {
			facebook = facebookUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setJabber(String jabber) {
		this.jabber = jabber;
	}

	@JsonIgnore
	public void setJabber(
		UnsafeSupplier<String, Exception> jabberUnsafeSupplier) {

		try {
			jabber = jabberUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setPostalAddress(PostalAddress[] postalAddress) {
		this.postalAddress = postalAddress;
	}

	@JsonIgnore
	public void setPostalAddress(
		UnsafeSupplier<PostalAddress[], Exception>
			postalAddressUnsafeSupplier) {

		try {
			postalAddress = postalAddressUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setPostalAddressIds(Long[] postalAddressIds) {
		this.postalAddressIds = postalAddressIds;
	}

	@JsonIgnore
	public void setPostalAddressIds(
		UnsafeSupplier<Long[], Exception> postalAddressIdsUnsafeSupplier) {

		try {
			postalAddressIds = postalAddressIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	@JsonIgnore
	public void setSkype(
		UnsafeSupplier<String, Exception> skypeUnsafeSupplier) {

		try {
			skype = skypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	@JsonIgnore
	public void setSms(UnsafeSupplier<String, Exception> smsUnsafeSupplier) {
		try {
			sms = smsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTelephone(Phone[] telephone) {
		this.telephone = telephone;
	}

	@JsonIgnore
	public void setTelephone(
		UnsafeSupplier<Phone[], Exception> telephoneUnsafeSupplier) {

		try {
			telephone = telephoneUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTelephoneIds(Long[] telephoneIds) {
		this.telephoneIds = telephoneIds;
	}

	@JsonIgnore
	public void setTelephoneIds(
		UnsafeSupplier<Long[], Exception> telephoneIdsUnsafeSupplier) {

		try {
			telephoneIds = telephoneIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	@JsonIgnore
	public void setTwitter(
		UnsafeSupplier<String, Exception> twitterUnsafeSupplier) {

		try {
			twitter = twitterUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@JsonIgnore
	public void setWebUrl(
		UnsafeSupplier<WebUrl[], Exception> webUrlUnsafeSupplier) {

		try {
			webUrl = webUrlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setWebUrl(WebUrl[] webUrl) {
		this.webUrl = webUrl;
	}

	public void setWebUrlIds(Long[] webUrlIds) {
		this.webUrlIds = webUrlIds;
	}

	@JsonIgnore
	public void setWebUrlIds(
		UnsafeSupplier<Long[], Exception> webUrlIdsUnsafeSupplier) {

		try {
			webUrlIds = webUrlIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(69);

		sb.append("{");

		sb.append("\"email\": ");

		sb.append("\"");
		sb.append(email);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"emailIds\": ");

		sb.append("\"");
		sb.append(emailIds);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"facebook\": ");

		sb.append("\"");
		sb.append(facebook);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"jabber\": ");

		sb.append("\"");
		sb.append(jabber);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"postalAddress\": ");

		sb.append("\"");
		sb.append(postalAddress);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"postalAddressIds\": ");

		sb.append("\"");
		sb.append(postalAddressIds);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"skype\": ");

		sb.append("\"");
		sb.append(skype);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"sms\": ");

		sb.append("\"");
		sb.append(sms);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"telephone\": ");

		sb.append("\"");
		sb.append(telephone);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"telephoneIds\": ");

		sb.append("\"");
		sb.append(telephoneIds);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"twitter\": ");

		sb.append("\"");
		sb.append(twitter);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"webUrl\": ");

		sb.append("\"");
		sb.append(webUrl);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"webUrlIds\": ");

		sb.append("\"");
		sb.append(webUrlIds);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected Email[] email;

	@GraphQLField
	@JsonProperty
	protected Long[] emailIds;

	@GraphQLField
	@JsonProperty
	protected String facebook;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String jabber;

	@GraphQLField
	@JsonProperty
	protected PostalAddress[] postalAddress;

	@GraphQLField
	@JsonProperty
	protected Long[] postalAddressIds;

	@GraphQLField
	@JsonProperty
	protected String skype;

	@GraphQLField
	@JsonProperty
	protected String sms;

	@GraphQLField
	@JsonProperty
	protected Phone[] telephone;

	@GraphQLField
	@JsonProperty
	protected Long[] telephoneIds;

	@GraphQLField
	@JsonProperty
	protected String twitter;

	@GraphQLField
	@JsonProperty
	protected WebUrl[] webUrl;

	@GraphQLField
	@JsonProperty
	protected Long[] webUrlIds;

}