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

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface ContactInformation {

	public PostalAddress[] getAddress();

	public void setAddress(
			PostalAddress[] address);

	public void setAddress(
			UnsafeSupplier<PostalAddress[], Throwable>
				addressUnsafeSupplier);
	public Long[] getAddressIds();

	public void setAddressIds(
			Long[] addressIds);

	public void setAddressIds(
			UnsafeSupplier<Long[], Throwable>
				addressIdsUnsafeSupplier);
	public Email[] getEmail();

	public void setEmail(
			Email[] email);

	public void setEmail(
			UnsafeSupplier<Email[], Throwable>
				emailUnsafeSupplier);
	public Long[] getEmailIds();

	public void setEmailIds(
			Long[] emailIds);

	public void setEmailIds(
			UnsafeSupplier<Long[], Throwable>
				emailIdsUnsafeSupplier);
	public String getFacebook();

	public void setFacebook(
			String facebook);

	public void setFacebook(
			UnsafeSupplier<String, Throwable>
				facebookUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getJabber();

	public void setJabber(
			String jabber);

	public void setJabber(
			UnsafeSupplier<String, Throwable>
				jabberUnsafeSupplier);
	public String getSkype();

	public void setSkype(
			String skype);

	public void setSkype(
			UnsafeSupplier<String, Throwable>
				skypeUnsafeSupplier);
	public String getSms();

	public void setSms(
			String sms);

	public void setSms(
			UnsafeSupplier<String, Throwable>
				smsUnsafeSupplier);
	public Phone[] getTelephone();

	public void setTelephone(
			Phone[] telephone);

	public void setTelephone(
			UnsafeSupplier<Phone[], Throwable>
				telephoneUnsafeSupplier);
	public Long[] getTelephoneIds();

	public void setTelephoneIds(
			Long[] telephoneIds);

	public void setTelephoneIds(
			UnsafeSupplier<Long[], Throwable>
				telephoneIdsUnsafeSupplier);
	public String getTwitter();

	public void setTwitter(
			String twitter);

	public void setTwitter(
			UnsafeSupplier<String, Throwable>
				twitterUnsafeSupplier);
	public WebUrl[] getWebUrl();

	public void setWebUrl(
			WebUrl[] webUrl);

	public void setWebUrl(
			UnsafeSupplier<WebUrl[], Throwable>
				webUrlUnsafeSupplier);
	public Long[] getWebUrlIds();

	public void setWebUrlIds(
			Long[] webUrlIds);

	public void setWebUrlIds(
			UnsafeSupplier<Long[], Throwable>
				webUrlIdsUnsafeSupplier);

}