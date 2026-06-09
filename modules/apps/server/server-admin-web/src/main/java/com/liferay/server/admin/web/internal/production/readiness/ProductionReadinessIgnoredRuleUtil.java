/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.production.readiness;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.server.admin.web.internal.configuration.ProductionReadinessConfiguration;

import java.util.List;

/**
 * @author Lily Chi
 */
public class ProductionReadinessIgnoredRuleUtil {

	public static void addIgnoredRule(String ruleKey)
		throws ConfigurationException {

		List<String> ignoredRules = getIgnoredRules();

		if (ignoredRules.contains(ruleKey)) {
			return;
		}

		ignoredRules.add(ruleKey);

		_saveIgnoredRules(ignoredRules);
	}

	public static List<String> getIgnoredRules() throws ConfigurationException {
		ProductionReadinessConfiguration productionReadinessConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				ProductionReadinessConfiguration.class);

		return ListUtil.fromArray(
			productionReadinessConfiguration.ignoredRules());
	}

	public static void removeIgnoredRule(String ruleKey)
		throws ConfigurationException {

		List<String> ignoredRules = getIgnoredRules();

		ignoredRules.remove(ruleKey);

		if (ignoredRules.isEmpty()) {
			ConfigurationProviderUtil.deleteSystemConfiguration(
				ProductionReadinessConfiguration.class);
		}
		else {
			_saveIgnoredRules(ignoredRules);
		}
	}

	private static void _saveIgnoredRules(List<String> ignoredRules)
		throws ConfigurationException {

		ConfigurationProviderUtil.saveSystemConfiguration(
			ProductionReadinessConfiguration.class,
			HashMapDictionaryBuilder.<String, Object>put(
				"ignoredRules", ignoredRules.toArray(new String[0])
			).build());
	}

}