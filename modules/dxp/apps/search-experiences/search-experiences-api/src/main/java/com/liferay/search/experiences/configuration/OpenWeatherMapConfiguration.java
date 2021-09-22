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

package com.liferay.search.experiences.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Petteri Karttunen
 */
@ExtendedObjectClassDefinition(
	category = "search-experiences",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.search.experiences.configuration.OpenWeatherMapConfiguration",
	localization = "content/Language",
	name = "openweathermap-configuration-name"
)
public interface OpenWeatherMapConfiguration {

	@Meta.AD(deflt = "false", name = "is-enabled", required = false)
	public boolean isEnabled();

	@Meta.AD(deflt = "", name = "api-key", required = false)
	public String apiKey();

	@Meta.AD(
		deflt = "http://api.openweathermap.org/data/2.5/weather",
		name = "api-url", required = false
	)
	public String apiURL();

	@Meta.AD(
		deflt = "604800", name = "cache-timeout-in-millis", required = false
	)
	public int cacheTimeout();

	@Meta.AD(deflt = "", name = "test-ip-address", required = false)
	public String testIpAddress();

}