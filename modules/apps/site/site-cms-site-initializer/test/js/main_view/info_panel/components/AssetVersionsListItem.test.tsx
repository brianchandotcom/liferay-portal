/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import AssetVersionsListItem from '../../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/components/AssetVersionsListItem';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/tab_content',
	() => ({
		VERSION_ACTIONS: {},
	})
);

const TEST_VERSIONS = [
	{
		actions: {},
		creator: {name: 'Test Test'},
		dateModified: '2026-02-18T17:15:51Z',
		id: 1,
		status: {label: 'approved'},
		systemProperties: {version: {number: 1}},
	},
	{
		actions: {},
		creator: {name: 'Test Test'},
		dateModified: '2026-02-18T17:20:41Z',
		id: 2,
		status: {label: 'approved'},
		systemProperties: {version: {number: 2}},
	},
];

describe('CMS Asset Versions List Item', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders a row per version with title, modified-by, date, and status', () => {
		render(
			<AssetVersionsListItem
				getAssetVersions={async () => {}}
				items={
					TEST_VERSIONS as React.ComponentProps<
						typeof AssetVersionsListItem
					>['items']
				}
			/>
		);

		expect(screen.getAllByRole('listitem')).toHaveLength(2);

		expect(screen.getAllByText('version-x')).toHaveLength(2);

		expect(screen.getAllByText('modified-by-x')).toHaveLength(2);

		expect(screen.getAllByText('approved')).toHaveLength(2);

		expect(screen.getAllByText(/2026/)).toHaveLength(2);
	});
});
