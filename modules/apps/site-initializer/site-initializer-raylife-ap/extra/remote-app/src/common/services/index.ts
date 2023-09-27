/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Parameters = {
	[key: string]: string | string[] | number;
};

export function parametersFormater(
	parametersList: string[],
	parameters: Parameters
) {
	const parametersContainer: String[] = [];

	parametersList.forEach((item) => {
		parametersContainer.push(`${item}=${parameters[item]}`);
	});

	const parametersString = parametersContainer.join('&');

	return parametersString;
}

export * from './Application';
export * from './Claim';
export * from './Policy';
export * from './SalesGoal';
