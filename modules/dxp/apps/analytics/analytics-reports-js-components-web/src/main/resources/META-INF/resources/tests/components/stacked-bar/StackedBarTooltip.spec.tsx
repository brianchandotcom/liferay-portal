/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatStackedBarTooltipDate} from '../../../js/components/stacked-bar/StackedBarTooltip';
import {RangeSelectors} from '../../../js/types/global';

describe('Stacked Bar', () => {
	it('format stacked bar tooltip date', () => {
		const formattedDate = formatStackedBarTooltipDate(
			RangeSelectors.Last30Days,
			new Date(0)
		);

		expect(formattedDate).toEqual('1969 Dec 31 - 1');
	});
});
