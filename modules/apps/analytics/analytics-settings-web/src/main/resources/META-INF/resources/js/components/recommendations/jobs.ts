/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Job = {
	active: boolean;
	description: string;
	id: string;
	title: string;
	type: string;
};

export const jobs: Job[] = [
	{
		title: Liferay.Language.get('most-popular-content'),
		description: Liferay.Language.get(
			'recommends-content-based-on-popularity-among-all-users-without-considering-individual-user-behavior'
		),
		id: '1',
		type: Liferay.Language.get('content'),
		active: false,
	},
	{
		title: Liferay.Language.get(
			"user's-personalized-content-recommendations"
		),
		description: Liferay.Language.get(
			'recommends-content-based-on-individual-users-preferences-and-past-behavior'
		),
		id: '2',
		type: Liferay.Language.get('content'),
		active: false,
	},
];
