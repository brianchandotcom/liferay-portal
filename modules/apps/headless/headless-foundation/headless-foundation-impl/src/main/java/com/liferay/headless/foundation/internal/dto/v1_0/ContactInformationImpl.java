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

	public void setAddress(
			PostalAddress[] address) {

			this.address = (PostalAddressImpl[])address;
	}

	@JsonIgnore
	public void setAddress(
			UnsafeSupplier<PostalAddress[], Throwable>
				addressUnsafeSupplier) {

			try {
				address =
					(PostalAddressImpl[])addressUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected PostalAddressImpl[] address;
	public Long[] getAddressIds() {
			return addressIds;
	}

	public void setAddressIds(
			Long[] addressIds) {

			this.addressIds = (Long[])addressIds;
	}

	@JsonIgnore
	public void setAddressIds(
			UnsafeSupplier<Long[], Throwable>
				addressIdsUnsafeSupplier) {

			try {
				addressIds =
					(Long[])addressIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] addressIds;
	public Email[] getEmail() {
			return email;
	}

	public void setEmail(
			Email[] email) {

			this.email = (EmailImpl[])email;
	}

	@JsonIgnore
	public void setEmail(
			UnsafeSupplier<Email[], Throwable>
				emailUnsafeSupplier) {

			try {
				email =
					(EmailImpl[])emailUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected EmailImpl[] email;
	public Long[] getEmailIds() {
			return emailIds;
	}

	public void setEmailIds(
			Long[] emailIds) {

			this.emailIds = (Long[])emailIds;
	}

	@JsonIgnore
	public void setEmailIds(
			UnsafeSupplier<Long[], Throwable>
				emailIdsUnsafeSupplier) {

			try {
				emailIds =
					(Long[])emailIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] emailIds;
	public String getFacebook() {
			return facebook;
	}

	public void setFacebook(
			String facebook) {

			this.facebook = (String)facebook;
	}

	@JsonIgnore
	public void setFacebook(
			UnsafeSupplier<String, Throwable>
				facebookUnsafeSupplier) {

			try {
				facebook =
					(String)facebookUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String facebook;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					(Long)idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public String getJabber() {
			return jabber;
	}

	public void setJabber(
			String jabber) {

			this.jabber = (String)jabber;
	}

	@JsonIgnore
	public void setJabber(
			UnsafeSupplier<String, Throwable>
				jabberUnsafeSupplier) {

			try {
				jabber =
					(String)jabberUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String jabber;
	public String getSkype() {
			return skype;
	}

	public void setSkype(
			String skype) {

			this.skype = (String)skype;
	}

	@JsonIgnore
	public void setSkype(
			UnsafeSupplier<String, Throwable>
				skypeUnsafeSupplier) {

			try {
				skype =
					(String)skypeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String skype;
	public String getSms() {
			return sms;
	}

	public void setSms(
			String sms) {

			this.sms = (String)sms;
	}

	@JsonIgnore
	public void setSms(
			UnsafeSupplier<String, Throwable>
				smsUnsafeSupplier) {

			try {
				sms =
					(String)smsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String sms;
	public Phone[] getTelephone() {
			return telephone;
	}

	public void setTelephone(
			Phone[] telephone) {

			this.telephone = (PhoneImpl[])telephone;
	}

	@JsonIgnore
	public void setTelephone(
			UnsafeSupplier<Phone[], Throwable>
				telephoneUnsafeSupplier) {

			try {
				telephone =
					(PhoneImpl[])telephoneUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected PhoneImpl[] telephone;
	public Long[] getTelephoneIds() {
			return telephoneIds;
	}

	public void setTelephoneIds(
			Long[] telephoneIds) {

			this.telephoneIds = (Long[])telephoneIds;
	}

	@JsonIgnore
	public void setTelephoneIds(
			UnsafeSupplier<Long[], Throwable>
				telephoneIdsUnsafeSupplier) {

			try {
				telephoneIds =
					(Long[])telephoneIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] telephoneIds;
	public String getTwitter() {
			return twitter;
	}

	public void setTwitter(
			String twitter) {

			this.twitter = (String)twitter;
	}

	@JsonIgnore
	public void setTwitter(
			UnsafeSupplier<String, Throwable>
				twitterUnsafeSupplier) {

			try {
				twitter =
					(String)twitterUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String twitter;
	public WebUrl[] getWebUrl() {
			return webUrl;
	}

	public void setWebUrl(
			WebUrl[] webUrl) {

			this.webUrl = (WebUrlImpl[])webUrl;
	}

	@JsonIgnore
	public void setWebUrl(
			UnsafeSupplier<WebUrl[], Throwable>
				webUrlUnsafeSupplier) {

			try {
				webUrl =
					(WebUrlImpl[])webUrlUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected WebUrlImpl[] webUrl;
	public Long[] getWebUrlIds() {
			return webUrlIds;
	}

	public void setWebUrlIds(
			Long[] webUrlIds) {

			this.webUrlIds = (Long[])webUrlIds;
	}

	@JsonIgnore
	public void setWebUrlIds(
			UnsafeSupplier<Long[], Throwable>
				webUrlIdsUnsafeSupplier) {

			try {
				webUrlIds =
					(Long[])webUrlIdsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long[] webUrlIds;

}