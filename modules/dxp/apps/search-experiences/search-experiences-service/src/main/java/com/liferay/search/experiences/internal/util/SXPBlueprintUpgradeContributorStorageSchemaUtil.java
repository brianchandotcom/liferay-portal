/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;

import java.util.Objects;

/**
 * @author Joshua Cords
 */
public class SXPBlueprintUpgradeContributorStorageSchemaUtil {

	public static SXPBlueprint upgradeContributorStorageSchema(
		SXPBlueprint sxpBlueprint) {

		if (!Objects.equals(sxpBlueprint.getSchemaVersion(), "1.0")) {
			return sxpBlueprint;
		}

		sxpBlueprint.setSchemaVersion("1.1");

		Configuration configuration = ConfigurationUtil.toConfiguration(
			sxpBlueprint.getConfigurationJSON());

		GeneralConfiguration generalConfiguration =
			configuration.getGeneralConfiguration();

		String[] clauseContributorsExcludes =
			generalConfiguration.getClauseContributorsExcludes();
		String[] clauseContributorsIncludes =
			generalConfiguration.getClauseContributorsIncludes();

		if (clauseContributorsExcludes.length == 0) {
			generalConfiguration.setClauseContributorsIncludes(() -> _WILDCARD);
		}
		else if (clauseContributorsIncludes.length == 0) {
			generalConfiguration.setClauseContributorsExcludes(() -> _WILDCARD);
		}
		else {
			generalConfiguration.setClauseContributorsExcludes(
				() -> new String[0]);
		}

		sxpBlueprint.setConfigurationJSON(configuration.toString());

		return sxpBlueprint;
	}

	private static final String[] _WILDCARD = {StringPool.STAR};

}