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

package com.liferay.portal.security.auth.bundle.screennamevalidatorfactory;

import com.liferay.portal.kernel.security.auth.ScreenNameValidator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestScreenNameValidator implements ScreenNameValidator {

	@Override
	public String getAUIValidatorJS() {
		return "function() {return true;}";
	}

	@Override
	public String getDescription(Locale locale) {
		return locale.toString();
	}

	@Override
	public boolean validate(long companyId, String screenName) {
		if (companyId == 1) {
			if (screenName.equals("lftest")) {
				return true;
			}
		}

		return false;
	}

}