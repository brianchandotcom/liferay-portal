/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function filterScriptOption(
	allowScriptContentToBeExecutedOrIncluded,
	hadGroovyScriptBefore,
	options
) {
	if (
		Liferay.FeatureFlags['LPD-11179'] &&
		!allowScriptContentToBeExecutedOrIncluded &&
		!hadGroovyScriptBefore
	) {
		return options.filter(
			(option) => option.value !== 'groovy' && option.value !== 'java'
		);
	}

	return options;
}
