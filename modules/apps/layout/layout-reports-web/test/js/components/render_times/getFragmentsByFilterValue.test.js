/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getFragmentsByFilterValue from '../../../../src/main/resources/META-INF/resources/js/components/render_times/getFragmentsByFilterValue';

const FRAGMENTS = [
	{
		cached: true,
		fragment: true,
		fromMaster: false,
		name: 'Heading',
	},
	{
		cached: false,
		fragment: true,
		fromMaster: false,
		name: 'Container',
	},
	{
		cached: true,
		fragment: true,
		fromMaster: true,
		name: 'Header',
	},
	{
		cached: false,
		fragment: false,
		fromMaster: false,
		name: 'Asset',
	},
	{
		cached: false,
		fragment: true,
		fromMaster: true,
		name: 'Grid',
	},
	{
		cached: true,
		fragment: false,
		fromMaster: true,
		name: 'Footer',
	},
];
describe('getFragmentsByFilterValue', () => {
	it('filters by origin: all', () => {
		expect(getFragmentsByFilterValue({origin: 'all'}, FRAGMENTS)).toEqual(
			FRAGMENTS
		);
	});

	it('filters by origin: from master', () => {
		expect(
			getFragmentsByFilterValue({origin: 'fromMaster'}, FRAGMENTS)
		).toEqual([FRAGMENTS[2], FRAGMENTS[4], FRAGMENTS[5]]);
	});

	it('filters by type: fragment', () => {
		expect(
			getFragmentsByFilterValue({type: 'fragment'}, FRAGMENTS)
		).toEqual([FRAGMENTS[0], FRAGMENTS[1], FRAGMENTS[2], FRAGMENTS[4]]);
	});

	it('filters by type: widget', () => {
		expect(getFragmentsByFilterValue({type: 'widget'}, FRAGMENTS)).toEqual([
			FRAGMENTS[3],
			FRAGMENTS[5],
		]);
	});

	it('filters by status: cached', () => {
		expect(
			getFragmentsByFilterValue({status: 'cached'}, FRAGMENTS)
		).toEqual([FRAGMENTS[0], FRAGMENTS[2], FRAGMENTS[5]]);
	});

	it('filters by status: not cached', () => {
		expect(
			getFragmentsByFilterValue({status: 'notCached'}, FRAGMENTS)
		).toEqual([FRAGMENTS[1], FRAGMENTS[3], FRAGMENTS[4]]);
	});

	it('applies several filters', () => {
		expect(
			getFragmentsByFilterValue(
				{status: 'notCached', type: 'widget'},
				FRAGMENTS
			)
		).toEqual([FRAGMENTS[3]]);
	});
});
