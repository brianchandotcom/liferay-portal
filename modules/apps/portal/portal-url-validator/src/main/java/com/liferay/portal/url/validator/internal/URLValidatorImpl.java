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

package com.liferay.portal.url.validator.internal;

import com.liferay.portal.kernel.url.validator.URLValidator;

import org.apache.commons.validator.routines.UrlValidator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = URLValidator.class)
public class URLValidatorImpl implements URLValidator {

	@Override
	public boolean isValid(String url) {
		UrlValidator urlValidator = new UrlValidator();

		return urlValidator.isValid(url);
	}

}