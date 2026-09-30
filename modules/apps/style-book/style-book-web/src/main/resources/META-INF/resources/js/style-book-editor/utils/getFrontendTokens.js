/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getFrontendTokens(frontendTokenDefinitions) {
	const frontendTokens = {};

	for (const {frontendTokenCategories, id} of frontendTokenDefinitions) {
		for (const frontendTokenCategory of frontendTokenCategories ?? []) {
			for (const frontendTokenSet of frontendTokenCategory.frontendTokenSets) {
				for (const frontendToken of frontendTokenSet.frontendTokens) {
					const namespacedName = `${id}:${frontendToken.name}`;

					frontendTokens[namespacedName] = {
						...frontendToken,
						name: namespacedName,
						tokenCategoryLabel: frontendTokenCategory.label,
						tokenSetLabel: frontendTokenSet.label,
						value: frontendToken.defaultValue,
					};
				}
			}
		}
	}

	return frontendTokens;
}
