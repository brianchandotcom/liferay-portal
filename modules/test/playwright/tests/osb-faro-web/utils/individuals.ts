/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';

export async function createIndividuals({
	apiHelpers,
	individuals,
}: {
	apiHelpers: ApiHelpers;
	individuals: {dataSourceId?: number; id: string; name: string}[];
}) {
	const formattedIndividuals = individuals.map(
		({dataSourceId = 0, id, name}) => ({
			emailAddress: `${name}@liferay.com`,
			fields: [
				{dataSourceId, name: 'firstName', value: name},
				{dataSourceId, name: 'lastName', value: name},
				{
					dataSourceId,
					name: 'emailAddress',
					value: `${name}@liferay.com`,
				},
			],
			firstName: name,
			id,
			lastName: name,
		})
	);

	await apiHelpers.jsonWebServicesOSBAsah.createIndividuals(
		formattedIndividuals
	);
}
