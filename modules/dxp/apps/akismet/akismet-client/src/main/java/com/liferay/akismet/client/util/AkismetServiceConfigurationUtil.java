/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.client.util;

import com.liferay.akismet.configuration.AkismetServiceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Jamie Sammons
 */
@Component(
	configurationPid = "com.liferay.akismet.configuration.AkismetServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class AkismetServiceConfigurationUtil {

	public static int getAkismetCheckThreshold() {
		return _akismetServiceConfiguration.akismetCheckThreshold();
	}

	public static AkismetServiceConfiguration getAkismetServiceConfiguration() {
		return _akismetServiceConfiguration;
	}

	public static String getAPIKey() {
		String apiKey = null;

		try {
			apiKey = _akismetServiceConfiguration.akismetApiKey();
		}
		catch (NullPointerException nullPointerException) {
		}

		return apiKey;
	}

	public static Date getReportableTime(long companyId) {
		int reportableTime =
			_akismetServiceConfiguration.akismetReportableTime();

		return new Date(
			System.currentTimeMillis() - (reportableTime * Time.DAY));
	}

	public static Date getRetainSpamTime() {
		long retainSpamTime =
			_akismetServiceConfiguration.akismetRetainSpamTime() * Time.DAY;

		return new Date(System.currentTimeMillis() - retainSpamTime);
	}

	public static boolean isMessageBoardsEnabled() {
		if (Validator.isNull(getAPIKey())) {
			return false;
		}

		return _akismetServiceConfiguration.messageBoardsEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_akismetServiceConfiguration = ConfigurableUtil.createConfigurable(
			AkismetServiceConfiguration.class, properties);
	}

	private static volatile AkismetServiceConfiguration
		_akismetServiceConfiguration;

}