/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.production.readiness;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.server.admin.web.internal.configuration.ProductionReadinessConfiguration;

import java.util.Set;

/**
 * @author Lily Chi
 */
public class ProductionReadinessIgnoredRuleUtil {

	public static void addIgnoredRule(String ruleKey)
		throws ConfigurationException {

		Set<String> ignoredRules = getIgnoredRules();

		if (!ignoredRules.add(ruleKey)) {
			return;
		}

		_saveIgnoredRules(ignoredRules);
	}

	public static Set<String> getIgnoredRules() throws ConfigurationException {
		ProductionReadinessConfiguration productionReadinessConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				ProductionReadinessConfiguration.class);

		return SetUtil.fromArray(
			productionReadinessConfiguration.ignoredRules());
	}

	public static void removeIgnoredRule(String ruleKey)
		throws ConfigurationException {

		Set<String> ignoredRules = getIgnoredRules();

		if (!ignoredRules.remove(ruleKey)) {
			return;
		}

		if (ignoredRules.isEmpty()) {
			ConfigurationProviderUtil.deleteSystemConfiguration(
				ProductionReadinessConfiguration.class);
		}
		else {
			_saveIgnoredRules(ignoredRules);
		}
	}

	private static void _saveIgnoredRules(Set<String> ignoredRules)
		throws ConfigurationException {

		ConfigurationProviderUtil.saveSystemConfiguration(
			ProductionReadinessConfiguration.class,
			HashMapDictionaryBuilder.<String, Object>put(
				"ignoredRules", ignoredRules.toArray(new String[0])
			).build());
	}

}