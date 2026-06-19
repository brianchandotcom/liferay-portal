/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import viewsReducer, {
	EViewsActionTypes,
} from '../../src/main/resources/META-INF/resources/views/viewsReducer';

describe('viewsReducer startup view', () => {
	describe('SET_DATA_SET_SNAPSHOT_STARTUP_VIEW', () => {
		it('sets the startup snapshot ERC', () => {
			const state = {dataSetSnapshotStartupViewERC: null};

			const nextState = viewsReducer(state, {
				type: EViewsActionTypes.SET_DATA_SET_SNAPSHOT_STARTUP_VIEW,
				value: {dataSetSnapshotStartupViewERC: 'erc-1'},
			});

			expect(nextState.dataSetSnapshotStartupViewERC).toBe('erc-1');
		});

		it('replaces a previously set startup snapshot ERC', () => {
			const state = {dataSetSnapshotStartupViewERC: 'erc-1'};

			const nextState = viewsReducer(state, {
				type: EViewsActionTypes.SET_DATA_SET_SNAPSHOT_STARTUP_VIEW,
				value: {dataSetSnapshotStartupViewERC: 'erc-2'},
			});

			expect(nextState.dataSetSnapshotStartupViewERC).toBe('erc-2');
		});
	});

	describe('DELETE_SNAPSHOT', () => {
		const baseState = {
			dataSetSnapshotStartupViewERC: 'erc-1',
			defaultSnapshot: {},
			snapshots: [
				{
					headerVisible: false,
					items: [{erc: 'erc-1'}, {erc: 'erc-2'}],
				},
			],
		};

		it('clears the startup snapshot ERC when the startup view is deleted', () => {
			const nextState = viewsReducer(baseState, {
				type: EViewsActionTypes.DELETE_SNAPSHOT,
				value: {snapshotERC: 'erc-1'},
			});

			expect(nextState.dataSetSnapshotStartupViewERC).toBeNull();
		});

		it('keeps the startup snapshot ERC when a different view is deleted', () => {
			const nextState = viewsReducer(baseState, {
				type: EViewsActionTypes.DELETE_SNAPSHOT,
				value: {snapshotERC: 'erc-2'},
			});

			expect(nextState.dataSetSnapshotStartupViewERC).toBe('erc-1');
		});
	});
});
