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

package com.liferay.portal.domainname;

import com.google.common.net.InternetDomainName;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DomainName;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Kong
 * @author Yuxing Wu
 */
@Component(
	immediate = true, service = DomainName.class
)
public class DomainNameImpl implements DomainName {

	@Override
	public String getCookieDomain(String domain) {

		// See LEP-4602 and LEP-4645.

		if (domain == null) {
			return null;
		}

		// See LEP-5595.

		if (Validator.isIPAddress(domain)) {
			return domain;
		}

		InternetDomainName internetDomainName = InternetDomainName.from(domain);

		if (internetDomainName.isPublicSuffix()) {
			return null;
		}

		if (internetDomainName.isTopPrivateDomain()) {
			return StringPool.PERIOD + internetDomainName.toString();
		}

		int x = domain.indexOf(CharPool.PERIOD);

		if (x <= 0) {
			return null;
		}

		int y = domain.indexOf(CharPool.PERIOD, x + 1);

		if (y <= 0) {
			return StringPool.PERIOD + domain;
		}

		return domain.substring(x);
	}

}