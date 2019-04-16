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

package com.liferay.headless.admin.user.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ContactInformation")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ContactInformation")
public class ContactInformation {

	@Schema(
		description = "A list of email addresses. One is optionally marked as primary."
	)
	public EmailAddress[] getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(EmailAddress[] emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	@JsonIgnore
	public void setEmailAddresses(
		UnsafeSupplier<EmailAddress[], Exception>
			emailAddressesUnsafeSupplier) {

		try {
			emailAddresses = emailAddressesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected EmailAddress[] emailAddresses;

	@Schema(description = "The facebook account of the UserAccount.")
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String facebook;

	@Schema(description = "The identifier of the resource.")
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema(description = "The jabber handle of the UserAccount.")
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String jabber;

	@Schema(
		description = "A list of postal addresses. One is optionally marked as primary."
	)
	public PostalAddress[] getPostalAddresses() {
		return postalAddresses;
	}

	public void setPostalAddresses(PostalAddress[] postalAddresses) {
		this.postalAddresses = postalAddresses;
	}

	@JsonIgnore
	public void setPostalAddresses(
		UnsafeSupplier<PostalAddress[], Exception>
			postalAddressesUnsafeSupplier) {

		try {
			postalAddresses = postalAddressesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected PostalAddress[] postalAddresses;

	@Schema(description = "The skype handle of the UserAccount.")
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String skype;

	@Schema(description = "The sms number of the UserAccount.")
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String sms;

	@Schema(
		description = "A list of telephones. One is optionally marked as primary."
	)
	public Phone[] getTelephones() {
		return telephones;
	}

	public void setTelephones(Phone[] telephones) {
		this.telephones = telephones;
	}

	@JsonIgnore
	public void setTelephones(
		UnsafeSupplier<Phone[], Exception> telephonesUnsafeSupplier) {

		try {
			telephones = telephonesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Phone[] telephones;

	@Schema(description = "The twitter handle of the UserAccount.")
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String twitter;

	@Schema(
		description = "A list of web urls associated with the UserAccount. One is optionally marked as primary."
	)
	public WebUrl[] getWebUrls() {
		return webUrls;
	}

	public void setWebUrls(WebUrl[] webUrls) {
		this.webUrls = webUrls;
	}

	@JsonIgnore
	public void setWebUrls(
		UnsafeSupplier<WebUrl[], Exception> webUrlsUnsafeSupplier) {

		try {
			webUrls = webUrlsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected WebUrl[] webUrls;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContactInformation)) {
			return false;
		}

		ContactInformation contactInformation = (ContactInformation)object;

		return Objects.equals(toString(), contactInformation.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"emailAddresses\": ");

		if (emailAddresses == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < emailAddresses.length; i++) {
				sb.append(emailAddresses[i]);

				if ((i + 1) < emailAddresses.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"facebook\": ");

		if (facebook == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(facebook);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"jabber\": ");

		if (jabber == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(jabber);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"postalAddresses\": ");

		if (postalAddresses == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < postalAddresses.length; i++) {
				sb.append(postalAddresses[i]);

				if ((i + 1) < postalAddresses.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"skype\": ");

		if (skype == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(skype);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"sms\": ");

		if (sms == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(sms);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"telephones\": ");

		if (telephones == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < telephones.length; i++) {
				sb.append(telephones[i]);

				if ((i + 1) < telephones.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"twitter\": ");

		if (twitter == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(twitter);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"webUrls\": ");

		if (webUrls == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < webUrls.length; i++) {
				sb.append(webUrls[i]);

				if ((i + 1) < webUrls.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

}