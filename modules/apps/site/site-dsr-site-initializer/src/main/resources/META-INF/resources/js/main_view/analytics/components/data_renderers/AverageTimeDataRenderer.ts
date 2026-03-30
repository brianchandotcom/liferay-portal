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

	const averageTimeSeconds = Math.round(totalTimeViewingAsset / totalViews);

	const hours = Math.floor(averageTimeSeconds / 3600);
	const minutes = Math.floor((averageTimeSeconds % 3600) / 60);

	return sub(`${Liferay.Language.get('x-h-x-min')}`, hours, minutes);
}
