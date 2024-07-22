/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';

const modifiedDate = new Date().toISOString();

export async function createIndividuals({
	apiHelpers,
	individuals,
}: {
	apiHelpers: ApiHelpers;
	individuals: {
		birthDate?: string;
		dataSourceId?: number;
		familyName?: string;
		id: string;
		name: string;
	}[];
}) {
	const formattedIndividuals = individuals.map(
		({
			birthDate = '1970-01-01T00:00:00.000Z',
			dataSourceId = 0,
			familyName = 'Smith',
			id,
			name,
		}) => ({
			birthday: birthDate,
			emailAddress: `${name}@liferay.com`,
			fields: [
				{dataSourceId, name: 'birthday', value: birthDate},
				{
					dataSourceId,
					name: 'emailAddress',
					value: `${name}@liferay.com`,
				},
				{dataSourceId, name: 'firstName', value: name},
				{dataSourceId, name: 'lastName', value: familyName},
			],
			firstName: name,
			id,
			lastName: familyName,
			modifiedDate,
		})
	);

	await apiHelpers.jsonWebServicesOSBAsah.createIndividuals(
		formattedIndividuals
	);

	const individualIdentities = individuals.map(({id}) => ({
		createDate: modifiedDate,
		id,
		individualId: id,
	}));

	await apiHelpers.jsonWebServicesOSBAsah.createIdentities(
		individualIdentities
	);
}

export function generateIndividual({name}: {name: any}) {
	const id = getRandomString();

	return {
		id,
		name,
	};
}
