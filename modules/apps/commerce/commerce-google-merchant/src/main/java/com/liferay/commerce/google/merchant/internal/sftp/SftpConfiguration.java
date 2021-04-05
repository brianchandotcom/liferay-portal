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

package com.liferay.commerce.google.merchant.internal.sftp;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.google.merchant.internal.constants.CommerceGoogleMerchantConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Thomas Stewart
 */
@ExtendedObjectClassDefinition(category = "google-merchant")
@Meta.OCD(
	id = "com.liferay.commerce.google.merchant.internal.sftp.SftpConfiguration",
	name = "sftp-upload-configuration-name"
)
public interface SftpConfiguration {

	@Meta.AD(
		deflt = CommerceGoogleMerchantConstants.COMMERCE_GOOGLE_PARTNER_UPLOAD_URL,
		name = "host", required = false
	)
	public String host();

	@Meta.AD(name = "feed-username", required = false)
	public String username();

	@Meta.AD(name = "feed-password", required = false)
	public String password();

	@Meta.AD(deflt = "19321", name = "port", required = false)
	public int port();

	@Meta.AD(name = "fingerprint", required = false)
	public String fingerprint();

}