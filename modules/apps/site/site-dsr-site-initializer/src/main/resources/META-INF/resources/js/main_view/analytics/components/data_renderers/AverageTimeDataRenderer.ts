/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {TRoomDocumentsStatistics} from '../../../../common/utils/types';

export function AverageTimeDataRenderer({
	itemData,
}: {
	itemData: TRoomDocumentsStatistics;
}) {
	const {totalTimeViewingAsset = 0, totalViews = 0} = itemData;

	const averageTimeSeconds =
		totalViews > 0 ? Math.round(totalTimeViewingAsset / totalViews) : 0;

	const hours = Math.floor(averageTimeSeconds / 3600);
	const minutes = Math.floor((averageTimeSeconds % 3600) / 60);

	const hoursLanguageKey = sub(
		hours === 1
			? Liferay.Language.get('x-hour').toLowerCase()
			: Liferay.Language.get('x-hours').toLowerCase(),
		[hours]
	);

	const minutesLanguageKey = sub(
		minutes === 1
			? Liferay.Language.get('x-minute').toLowerCase()
			: Liferay.Language.get('x-minutes').toLowerCase(),
		[minutes]
	);

	return `${hoursLanguageKey} ${minutesLanguageKey}`;
}
