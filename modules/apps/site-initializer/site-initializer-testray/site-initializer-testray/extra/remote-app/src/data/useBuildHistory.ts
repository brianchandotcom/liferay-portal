/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {TestrayBuild} from '../services/rest';
import {DATA_COLORS, Statuses} from '../util/constants';
import {getRandomMaximumValue} from '../util/mock';

const useBuildHistory = () => {
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
		colors: {
			[Statuses.BLOCKED]: DATA_COLORS['metrics.blocked'],
			[Statuses.FAILED]: DATA_COLORS['metrics.failed'],
			[Statuses.INCOMPLETE]: DATA_COLORS['metrics.incomplete'],
			[Statuses.PASSED]: DATA_COLORS['metrics.passed'],
			[Statuses.TEST_FIX]: DATA_COLORS['metrics.test-fix'],
		},
		getColumns: (builds: TestrayBuild[]) => [
			[
				Statuses.PASSED,
				...builds.map(({caseResultPassed = 0}) =>
					Number(caseResultPassed)
				),
			],
			[
				Statuses.FAILED,
				...builds.map(({caseResultFailed = 0}) =>
					Number(caseResultFailed)
				),
			],
			[
				Statuses.BLOCKED,
				...builds.map(({caseResultBlocked = 0}) =>
					Number(caseResultBlocked)
				),
			],
			[
				Statuses.TEST_FIX,
				...builds.map(({caseResultTestFix = 0}) =>
					Number(caseResultTestFix)
				),
			],
			[
				Statuses.INCOMPLETE,
				...builds.map(
					({caseResultInProgress = 0, caseResultUntested = 0}) =>
						Number(caseResultInProgress) +
						Number(caseResultUntested)
				),
			],
		],
		statuses: Object.values(Statuses),
	};
};

export default useBuildHistory;
