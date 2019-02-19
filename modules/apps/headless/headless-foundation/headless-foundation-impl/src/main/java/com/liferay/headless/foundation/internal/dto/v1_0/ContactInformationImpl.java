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

package com.liferay.headless.foundation.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.foundation.dto.v1_0.ContactInformation;
import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.petra.function.UnsafeSupplier;

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
@XmlRootElement(name = "ContactInformation")
public class ContactInformationImpl implements ContactInformation {

	public PostalAddress[] getAddress() {
		return address;
	}

	public Long[] getAddressIds() {
		return addressIds;
	}

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

	public void setAddress(PostalAddress[] address) {
		this.address = address;
	}

	@JsonIgnore
	public void setAddress(
		UnsafeSupplier<PostalAddress[], Throwable> addressUnsafeSupplier) {

		try {
			address = addressUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setAddressIds(Long[] addressIds) {
		this.addressIds = addressIds;
	}

	@JsonIgnore
	public void setAddressIds(
		UnsafeSupplier<Long[], Throwable> addressIdsUnsafeSupplier) {

		try {
			addressIds = addressIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setEmail(Email[] email) {
		this.email = email;
	}

	@JsonIgnore
	public void setEmail(
		UnsafeSupplier<Email[], Throwable> emailUnsafeSupplier) {

		try {
			email = emailUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setEmailIds(Long[] emailIds) {
		this.emailIds = emailIds;
	}

	@JsonIgnore
	public void setEmailIds(
		UnsafeSupplier<Long[], Throwable> emailIdsUnsafeSupplier) {

		try {
			emailIds = emailIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	@JsonIgnore
	public void setFacebook(
		UnsafeSupplier<String, Throwable> facebookUnsafeSupplier) {

		try {
			facebook = facebookUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setJabber(String jabber) {
		this.jabber = jabber;
	}

	@JsonIgnore
	public void setJabber(
		UnsafeSupplier<String, Throwable> jabberUnsafeSupplier) {

		try {
			jabber = jabberUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	@JsonIgnore
	public void setSkype(
		UnsafeSupplier<String, Throwable> skypeUnsafeSupplier) {

		try {
			skype = skypeUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	@JsonIgnore
	public void setSms(UnsafeSupplier<String, Throwable> smsUnsafeSupplier) {
		try {
			sms = smsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setTelephone(Phone[] telephone) {
		this.telephone = telephone;
	}

	@JsonIgnore
	public void setTelephone(
		UnsafeSupplier<Phone[], Throwable> telephoneUnsafeSupplier) {

		try {
			telephone = telephoneUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setTelephoneIds(Long[] telephoneIds) {
		this.telephoneIds = telephoneIds;
	}

	@JsonIgnore
	public void setTelephoneIds(
		UnsafeSupplier<Long[], Throwable> telephoneIdsUnsafeSupplier) {

		try {
			telephoneIds = telephoneIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	@JsonIgnore
	public void setTwitter(
		UnsafeSupplier<String, Throwable> twitterUnsafeSupplier) {

		try {
			twitter = twitterUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@JsonIgnore
	public void setWebUrl(
		UnsafeSupplier<WebUrl[], Throwable> webUrlUnsafeSupplier) {

		try {
			webUrl = webUrlUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
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
		UnsafeSupplier<Long[], Throwable> webUrlIdsUnsafeSupplier) {

		try {
			webUrlIds = webUrlIdsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected PostalAddress[] address;

	@GraphQLField
	@JsonProperty
	protected Long[] addressIds;

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