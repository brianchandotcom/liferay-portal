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

package com.liferay.amazon.rankings.web.portlet.validator;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.amazon.rankings.web.configuration.AmazonRankingsConfiguration;
import com.liferay.amazon.rankings.web.constants.AmazonRankingsPortletKeys;
import com.liferay.amazon.rankings.web.model.AmazonRankings;
import com.liferay.amazon.rankings.web.util.AmazonRankingsUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

import org.apache.commons.validator.routines.ISBNValidator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.amazon.rankings.web.configuration.AmazonRankingsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"javax.portlet.name=" + AmazonRankingsPortletKeys.AMAZON_RANKINGS
	}
)
public class AmazonRankingsPreferencesValidator
	implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences portletPreferences)
		throws ValidatorException {

		List<String> badIsbns = new ArrayList<>();

		ISBNValidator isbnValidator = new ISBNValidator();

		String[] isbns = portletPreferences.getValues(
			"isbns", StringPool.EMPTY_ARRAY);

		for (String isbn : isbns) {
			if (!isbnValidator.isValid(isbn)) {
				badIsbns.add(isbn);

				if (_log.isInfoEnabled()) {
					_log.info("Invalid ISBN " + isbn);
				}

				continue;
			}

			AmazonRankings amazonRankings =
				AmazonRankingsUtil.getAmazonRankings(
					_amazonRankingsConfiguration, isbn);

			if (amazonRankings == null) {
				badIsbns.add(isbn);

				if (_log.isInfoEnabled()) {
					_log.info("Invalid ISBN " + isbn);
				}
			}
		}

		if (!badIsbns.isEmpty()) {
			throw new ValidatorException("Unable to retrieve ISBNs", badIsbns);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_amazonRankingsConfiguration = Configurable.createConfigurable(
			AmazonRankingsConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AmazonRankingsPreferencesValidator.class);

	private volatile AmazonRankingsConfiguration _amazonRankingsConfiguration;

}