/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.rss;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSPreferencesValidator implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences preferences)
		throws ValidatorException {

		List<String> badURLs = new ArrayList<String>();

		String[] urls = preferences.getValues("urls", new String[0]);

		for (String url : urls) {
			if (!Validator.isUrl(url)) {
				badURLs.add(url);
			}
		}

		if (badURLs.size() > 0) {
			throw new ValidatorException("Failed to retrieve URLs", badURLs);
		}
	}

}