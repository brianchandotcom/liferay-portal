/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {JobId} from '../../utils/api';

export type Job = {
	active: boolean;
	description: string;
	id: JobId;
	modalTitle: string;
	title: string;
	type: string;
};

export const jobs: Job[] = [
	{
		active: false,
		description: Liferay.Language.get(
			'recommends-content-based-on-popularity-among-all-users-without-considering-individual-user-behavior'
		),
		id: JobId.ContentRecommenderMostPopularItemsEnabled,
		modalTitle: Liferay.Language.get('most-popular-content'),
		title: Liferay.Language.get('most-popular-content'),
		type: Liferay.Language.get('content'),
	},
	{
		active: false,
		description: Liferay.Language.get(
			'recommends-content-based-on-individual-users-preferences-and-past-behavior'
		),
		id: JobId.ContentRecommenderUserPersonalizationEnabled,
		modalTitle: Liferay.Language.get("user's-personalized-content"),
		title: Liferay.Language.get(
			"user's-personalized-content-recommendations"
		),
		type: Liferay.Language.get('content'),
	},
];
