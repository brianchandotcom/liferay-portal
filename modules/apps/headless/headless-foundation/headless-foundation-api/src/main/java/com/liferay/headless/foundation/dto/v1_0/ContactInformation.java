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
//@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ContactInformation")
public class ContactInformation {

	public Email[] getEmail() {
		return email;
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

	@GraphQLField
	@JsonProperty
	protected Email[] email;

	public Long[] getEmailIds() {
		return emailIds;
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

	@GraphQLField
	@JsonProperty
	protected Long[] emailIds;

	public String getFacebook() {
		return facebook;
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

	@GraphQLField
	@JsonProperty
	protected String facebook;

	public Long getId() {
		return id;
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

	@GraphQLField
	@JsonProperty
	protected Long id;

	public String getJabber() {
		return jabber;
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

	@GraphQLField
	@JsonProperty
	protected String jabber;

	public PostalAddress[] getPostalAddress() {
		return postalAddress;
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

	@GraphQLField
	@JsonProperty
	protected PostalAddress[] postalAddress;

	public Long[] getPostalAddressIds() {
		return postalAddressIds;
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

	@GraphQLField
	@JsonProperty
	protected Long[] postalAddressIds;

	public String getSkype() {
		return skype;
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

	@GraphQLField
	@JsonProperty
	protected String skype;

	public String getSms() {
		return sms;
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

	@GraphQLField
	@JsonProperty
	protected String sms;

	public Phone[] getTelephone() {
		return telephone;
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

	@GraphQLField
	@JsonProperty
	protected Phone[] telephone;

	public Long[] getTelephoneIds() {
		return telephoneIds;
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

	@GraphQLField
	@JsonProperty
	protected Long[] telephoneIds;

	public String getTwitter() {
		return twitter;
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

	@GraphQLField
	@JsonProperty
	protected String twitter;

	public WebUrl[] getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(WebUrl[] webUrl) {
		this.webUrl = webUrl;
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

	@GraphQLField
	@JsonProperty
	protected WebUrl[] webUrl;

	public Long[] getWebUrlIds() {
		return webUrlIds;
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

	@GraphQLField
	@JsonProperty
	protected Long[] webUrlIds;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"email\": ");

		if (email == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < email.length; i++) {
				sb.append(email[i]);

				if ((i + 1) < email.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"emailIds\": ");

		if (emailIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < emailIds.length; i++) {
				sb.append(emailIds[i]);

				if ((i + 1) < emailIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

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

		if (postalAddress == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < postalAddress.length; i++) {
				sb.append(postalAddress[i]);

				if ((i + 1) < postalAddress.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"postalAddressIds\": ");

		if (postalAddressIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < postalAddressIds.length; i++) {
				sb.append(postalAddressIds[i]);

				if ((i + 1) < postalAddressIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

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

		if (telephone == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < telephone.length; i++) {
				sb.append(telephone[i]);

				if ((i + 1) < telephone.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"telephoneIds\": ");

		if (telephoneIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < telephoneIds.length; i++) {
				sb.append(telephoneIds[i]);

				if ((i + 1) < telephoneIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"twitter\": ");

		sb.append("\"");
		sb.append(twitter);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"webUrl\": ");

		if (webUrl == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < webUrl.length; i++) {
				sb.append(webUrl[i]);

				if ((i + 1) < webUrl.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"webUrlIds\": ");

		if (webUrlIds == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < webUrlIds.length; i++) {
				sb.append(webUrlIds[i]);

				if ((i + 1) < webUrlIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}