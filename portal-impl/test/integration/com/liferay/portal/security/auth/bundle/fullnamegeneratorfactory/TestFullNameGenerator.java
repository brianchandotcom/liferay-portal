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

package com.liferay.portal.security.auth.bundle.fullnamegeneratorfactory;

import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestFullNameGenerator implements FullNameGenerator {

	@Override
	public String getFullName(
		String firstName, String middleName, String lastName) {

		return StringBundler.concat(firstName, " ", middleName, " ", lastName);
	}

	@Override
	public String getLocalizedFullName(
		String firstName, String middleName, String lastName, Locale locale,
		long prefixId, long suffixId) {

		if (firstName.equals("James")) {
			if (locale.equals(Locale.FRENCH)) {
				return "Jacques";
			}
		}

		return "not Jacques";
	}

	@Override
	public String[] splitFullName(String fullName) {
		return new String[] {"firstName", "middleName", "lastName"};
	}

}