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

package com.liferay.portal.search.tuning.blueprints.openweathermap.internal.configuration;

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
	id = "com.liferay.portal.search.tuning.blueprints.openweathermap.internal.configuration.OpenWeatherMapConfiguration",
	localization = "content/Language",
	name = "openweathermap-configuration-name"
)
public interface OpenWeatherMapConfiguration {

	@Meta.AD(
		deflt = "false", description = "is-enabled-desc",
		name = "is-enabled-name", required = false
	)
	public boolean isEnabled();

	@Meta.AD(
		deflt = "", description = "api-key-desc", name = "api-key-name",
		required = false
	)
	public String apiKey();

	@Meta.AD(
		deflt = "http://api.openweathermap.org/data/2.5/weather",
		description = "api-url-desc", name = "api-url-name", required = false
	)
	public String apiURL();

	@Meta.AD(
		deflt = "604800", description = "cache-timeout-desc",
		name = "cache-timeout-name", required = false
	)
	public int cacheTimeout();

	@Meta.AD(
		deflt = "", description = "test-ip-desc", name = "test-ip-name",
		required = false
	)
	public String testIpAddress();

}