/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import TooltipChart from '../../../../src/main/resources/META-INF/resources/js/shared/components/chart/TooltipChart.es';

test('Should render component', () => {
	const header = [{label: 'Thu, Aug 1', weight: 'semibold', width: 160}];
	const rows = [{columns: [{label: '10 Inst / Day', weight: 'normal'}]}];

	const body = TooltipChart({header, rows});

	expect(body.includes('Thu, Aug 1')).toBe(true);
	expect(body.includes('10 Inst / Day')).toBe(true);
});
