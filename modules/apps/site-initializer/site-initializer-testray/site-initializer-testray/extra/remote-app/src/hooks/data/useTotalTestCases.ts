/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Statuses, chartColors} from '../../util/constants';
import {getRandomMaximumValue} from '../../util/mock';

const useTotalTestCases = () => {
	const donutColumns = [
		[Statuses.PASSED, 30529],
		[Statuses.FAILED, 5374],
		[Statuses.BLOCKED, 0],
		[Statuses.TEST_FIX, 0],
		[Statuses.INCOMPLETE, 21],
	];

	return {
		barChart: {
			columns: [
				[Statuses.PASSED, ...getRandomMaximumValue(20, 1000)],
				[Statuses.FAILED, ...getRandomMaximumValue(20, 500)],
				[Statuses.BLOCKED, ...getRandomMaximumValue(20, 100)],
				[Statuses.TEST_FIX, ...getRandomMaximumValue(20, 100)],
				[Statuses.INCOMPLETE, ...getRandomMaximumValue(20, 100)],
			],
		},
		colors: chartColors,
		donut: {
			columns: donutColumns,
			total: donutColumns
				.map(([, totalCase]) => totalCase)
				.reduce(
					(prevValue, currentValue) =>
						Number(prevValue) + Number(currentValue)
				),
		},
		statuses: Object.values(Statuses),
	};
};

export default useTotalTestCases;
