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

import {translate} from '../i18n';

type TableChartDataProps = {
	colors: string[][];
	columns: string[][];
	data: {value: number}[][];
};

const COLORS = {
	BLOCKED: 'blocked',
	DNR: 'dnr',
	FAILED: 'failed',
	PASSED: 'passed',
	TEST_FIX: 'test-fix',
};

const useTableChartData = (): TableChartDataProps => {
	const HORIZONTAL_COLUMNS = {
		B_BLOCKED: `B ${translate('blocked')}`,
		B_DNR: 'B DNR',
		B_FAILED: `B ${translate('failed')}`,
		B_PASSED: `B ${translate('passed')}`,
		B_TEST_FIX: `B ${translate('test-fix')}`,
	};

	const VERTICAL_COLUMNS = {
		A_BLOCKED: `A ${translate('blocked')}`,
		A_DNR: 'A DNR',
		A_FAILED: `A ${translate('failed')}`,
		A_PASSED: `A ${translate('passed')}`,
		A_TEST_FIX: `A ${translate('test-fix')}`,
	};

	return {
		colors: [
			[
				COLORS.PASSED,
				COLORS.FAILED,
				COLORS.BLOCKED,
				COLORS.TEST_FIX,
				COLORS.PASSED,
			],
			[
				COLORS.FAILED,
				COLORS.FAILED,
				COLORS.FAILED,
				COLORS.FAILED,
				COLORS.FAILED,
			],
			[
				COLORS.BLOCKED,
				COLORS.FAILED,
				COLORS.BLOCKED,
				COLORS.BLOCKED,
				COLORS.BLOCKED,
			],
			[
				COLORS.TEST_FIX,
				COLORS.FAILED,
				COLORS.BLOCKED,
				COLORS.TEST_FIX,
				COLORS.TEST_FIX,
			],
			[
				COLORS.PASSED,
				COLORS.FAILED,
				COLORS.BLOCKED,
				COLORS.TEST_FIX,
				COLORS.DNR,
			],
		],
		columns: [
			[
				HORIZONTAL_COLUMNS.B_PASSED,
				HORIZONTAL_COLUMNS.B_FAILED,
				HORIZONTAL_COLUMNS.B_BLOCKED,
				HORIZONTAL_COLUMNS.B_TEST_FIX,
				HORIZONTAL_COLUMNS.B_DNR,
			],
			[
				VERTICAL_COLUMNS.A_PASSED,
				VERTICAL_COLUMNS.A_FAILED,
				VERTICAL_COLUMNS.A_BLOCKED,
				VERTICAL_COLUMNS.A_TEST_FIX,
				VERTICAL_COLUMNS.A_DNR,
			],
		],
		data: [
			[{value: 20}, {value: 192}],
			[],
			[{value: 300}, {value: 1000}, {value: 5000}, {value: 3}],
			[],
			[],
		],
	};
};

export default useTableChartData;
