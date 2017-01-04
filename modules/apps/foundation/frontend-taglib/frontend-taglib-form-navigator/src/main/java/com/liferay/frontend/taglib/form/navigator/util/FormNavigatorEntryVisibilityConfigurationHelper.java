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

package com.liferay.frontend.taglib.form.navigator.util;

import com.liferay.frontend.taglib.form.navigator.configuration.FormNavigatorConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = FormNavigatorEntryVisibilityConfigurationHelper.class)
public class FormNavigatorEntryVisibilityConfigurationHelper {

	public boolean isHidden(
		String configurationPrefix, String formNavigatorId,
		String formNavigatorEntryClassName) {

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					"(service.factoryPid=" +
						FormNavigatorConfiguration.class.getName() + ")");

			if (ArrayUtil.isEmpty(configurations)) {
				return false;
			}

			return Arrays.stream(configurations).map(
				configuration ->
					ConfigurableUtil.createConfigurable(
						FormNavigatorConfiguration.class,
						configuration.getProperties())
				).filter(
					formNavigatorConfiguration ->
						formNavigatorId.equals(
							formNavigatorConfiguration.formNavigatorId())
				).map(
					formNavigatorConfiguration ->
						formNavigatorConfiguration.
							hiddenFormNavigatorEntryQueries()
				).anyMatch(
					hiddenFormNavigatorEntryQueries ->
						containsFormNavigatorEntryClassName(
							hiddenFormNavigatorEntryQueries,
							configurationPrefix, formNavigatorEntryClassName));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected boolean containsFormNavigatorEntryClassName(
		String[] hiddenFormNavigatorEntryQueries, String prefix,
		String formNavigatorEntryClassName) {

		return Arrays.stream(hiddenFormNavigatorEntryQueries).map(
				StringUtil::splitLines
			).flatMap(
				hiddenFormNavigatorEntryQueryLine ->
					Arrays.stream(hiddenFormNavigatorEntryQueryLine)
			).filter(
				hiddenFormNavigatorEntryQuery ->
					StringUtil.startsWith(hiddenFormNavigatorEntryQuery, prefix)
			).map(
				hiddenFormNavigatorEntryQuery ->
					hiddenFormNavigatorEntryQuery.substring(prefix.length() + 1)
			).map(
				StringUtil::split
			).anyMatch(
				hiddenFormNavigationEntryClassNames -> ArrayUtil.contains(
					hiddenFormNavigationEntryClassNames,
					formNavigatorEntryClassName));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormNavigatorEntryVisibilityConfigurationHelper.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}