/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';

export async function createIndividuals({
	apiHelpers,
	names,
}: {
	apiHelpers: ApiHelpers;
	names: string[];
}) {
	const individuals = names.map((name) => ({
		emailAddress: `${name}@liferay.com`,
		fields: [
			{dataSourceId: 0, name: 'givenName', value: name},
			{dataSourceId: 0, name: 'familyName', value: name},
			{dataSourceId: 0, name: 'email', value: `${name}@liferay.com`},
		],
		firstName: name,
		id: `${name}@liferay.com`,
		lastName: name,
	}));

	await apiHelpers.jsonWebServicesOSBAsah.createIndividuals(individuals);
}
