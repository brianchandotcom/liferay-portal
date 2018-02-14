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

package com.liferay.wsrp.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Base64;

import java.security.Key;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = WSRPURLUtil.class)
public class WSRPURLUtil {

	public String encodeWSRPAuth(long companyId, String wsrpAuth)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		Key key = company.getKeyObj();

		byte[] hmacSHA = getHMACSha(
			key.getEncoded(), wsrpAuth.getBytes(StringPool.UTF8));

		return Base64.encodeToURL(hmacSHA);
	}

	protected byte[] getHMACSha(byte[] key, byte[] data) throws Exception {
		Mac mac = Mac.getInstance(_ALGORITHM);

		SecretKeySpec secretKeySpec = new SecretKeySpec(key, _ALGORITHM);

		mac.init(secretKeySpec);

		return mac.doFinal(data);
	}

	private static final String _ALGORITHM = "HmacSHA1";

	@Reference
	private CompanyLocalService _companyLocalService;

}