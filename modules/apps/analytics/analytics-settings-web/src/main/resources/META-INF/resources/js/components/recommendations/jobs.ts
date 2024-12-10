/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {JobId, JobStatus} from '../../utils/api';

export type Job = {
	description: string;
	enabled: boolean;
	id: JobId;
	modalTitle: string;
	status: JobStatus;
	title: string;
	type: string;
};

export const jobs: Job[] = [
	{
		description: Liferay.Language.get(
			'recommends-content-based-on-popularity-among-all-users-without-considering-individual-user-behavior'
		),
		enabled: false,
		id: JobId.ContentRecommenderMostPopularItems,
		modalTitle: Liferay.Language.get('most-popular-content'),
		status: JobStatus.Disabled,
		title: Liferay.Language.get('most-popular-content'),
		type: Liferay.Language.get('content'),
	},
	{
		description: Liferay.Language.get(
			'recommends-content-based-on-individual-users-preferences-and-past-behavior'
		),
		enabled: false,
		id: JobId.ContentRecommenderUserPersonalization,
		modalTitle: Liferay.Language.get("user's-personalized-content"),
		status: JobStatus.Disabled,
		title: Liferay.Language.get(
			"user's-personalized-content-recommendations"
		),
		type: Liferay.Language.get('content'),
	},
];
