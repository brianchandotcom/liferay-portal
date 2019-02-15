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
public class ContactInformation {

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

	public void setTwitter(
		UnsafeSupplier<String, Throwable> twitterUnsafeSupplier) {

			try {
				twitter = twitterUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

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
	protected PostalAddress[] address;

	@GraphQLField
	protected Long[] addressIds;

	@GraphQLField
	protected Email[] email;

	@GraphQLField
	protected Long[] emailIds;

	@GraphQLField
	protected String facebook;

	@GraphQLField
	protected Long id;

	@GraphQLField
	protected String jabber;

	@GraphQLField
	protected String skype;

	@GraphQLField
	protected String sms;

	@GraphQLField
	protected Phone[] telephone;

	@GraphQLField
	protected Long[] telephoneIds;

	@GraphQLField
	protected String twitter;

	@GraphQLField
	protected WebUrl[] webUrl;

	@GraphQLField
	protected Long[] webUrlIds;

}