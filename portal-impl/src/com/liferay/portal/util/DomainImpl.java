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

package com.liferay.portal.util;

import com.google.common.net.InternetDomainName;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Domain;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Yuxing Wu
 */
public class DomainImpl implements Domain {

	@Override
	public String getDomain(String host) {

		// See LEP-4602 and LEP-4645.

		if (host == null) {
			return null;
		}

		// See LEP-5595.

		if (Validator.isIPAddress(host)) {
			return host;
		}

		InternetDomainName internetDomainName = InternetDomainName.from(host);

		if (internetDomainName.isPublicSuffix()) {
			return null;
		}

		if (internetDomainName.isTopPrivateDomain()) {
			String domain = internetDomainName.toString();

			return StringPool.PERIOD + domain;
		}

		int x = host.indexOf(CharPool.PERIOD);

		if (x <= 0) {
			return null;
		}

		return host.substring(x);
	}

}