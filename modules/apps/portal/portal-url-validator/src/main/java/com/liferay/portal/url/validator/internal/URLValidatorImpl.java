/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.validator.internal;

import com.liferay.portal.kernel.url.validator.URLValidator;

import org.apache.commons.validator.routines.UrlValidator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(service = URLValidator.class)
public class URLValidatorImpl implements URLValidator {

	@Override
	public boolean isValid(String url) {
		UrlValidator urlValidator = new UrlValidator();

		return urlValidator.isValid(url);
	}

}