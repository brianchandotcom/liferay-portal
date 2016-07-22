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

package com.liferay.rss.web.internal.portlet.validator;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.rss.web.constants.RSSPortletKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = {"javax.portlet.name=" + RSSPortletKeys.RSS}
)
public class RSSPreferencesValidator implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences preferences)
		throws ValidatorException {

		List<String> badURLs = new ArrayList<>();

		String[] urls = preferences.getValues("urls", new String[0]);

		for (String url : urls) {
			if (!Validator.isUrl(url)) {
				badURLs.add(url);
			}
		}

		if (!badURLs.isEmpty()) {
			throw new ValidatorException("Failed to retrieve URLs", badURLs);
		}
	}

}