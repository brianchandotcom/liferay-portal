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

package com.liferay.portlet.amazonrankings;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.amazonrankings.model.AmazonRankings;
import com.liferay.portlet.amazonrankings.util.AmazonRankingsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class AmazonRankingsPreferencesValidator
	implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences preferences)
		throws ValidatorException {

		List<String> badIsbns = new ArrayList<String>();

		String[] isbns = preferences.getValues("isbns", new String[0]);

		for (String isbn : isbns) {
			AmazonRankings amazonRankings =
				AmazonRankingsUtil.getAmazonRankings(isbn);

			if (amazonRankings == null) {
				badIsbns.add(isbn);

				if (_log.isInfoEnabled()) {
					_log.info("Invalid ISBN " + isbn);
				}
			}
		}

		if (badIsbns.size() > 0) {
			throw new ValidatorException("Failed to retrieve ISBNs", badIsbns);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AmazonRankingsPreferencesValidator.class);

}