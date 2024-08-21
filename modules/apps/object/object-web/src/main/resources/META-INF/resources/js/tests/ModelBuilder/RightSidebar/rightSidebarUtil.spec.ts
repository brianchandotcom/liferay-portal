/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

import {getRightSidebarWidth} from '../../../components/ModelBuilder/RightSidebar/rightSidebarUtil';

function buildObjectFieldNodeMock(businessType: ObjectFieldBusinessTypeName) {
	return {
		businessType,
		primaryKey: false,
		required: false,
		selected: true,
	} as ObjectFieldNodeRow;
}

describe('getRightSidebarWidth function', () => {
	it('will return 950 when the selected object field has the Aggregation businessType', () => {
		expect(
			getRightSidebarWidth(buildObjectFieldNodeMock('Aggregation'))
		).toBe(950);
	});

	it('will return 500 when the selected object field has the Picklist businessType', () => {
		expect(getRightSidebarWidth(buildObjectFieldNodeMock('Picklist'))).toBe(
			500
		);
	});

	it('will return 320 when there is no object field selected', () => {
		expect(getRightSidebarWidth()).toBe(320);
	});
});
