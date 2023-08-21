/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/StoreContext';

import '@testing-library/jest-dom/extend-expect';

import PageAudit from '../../../src/main/resources/META-INF/resources/js/components/PageAudit';

const mockTabs = [
	{
		id: 'tab-1',
		name: 'First Tab',
		url: 'url',
	},
	{
		id: 'tab-2',
		name: 'Second Tab',
		url: 'url',
	},
];

const mockSegments = {
	segmentsExperiences: [
		{
			active: true,
			segmentsEntryId: '0',
			segmentsEntryName: 'Anyone',
			segmentsExperienceId: '33590',
			segmentsExperienceName: 'Experience Default',
			statusLabel: 'Active',
			url: 'url',
		},
	],
	selectedSegmentsExperience: {
		active: true,
		segmentsEntryId: '0',
		segmentsEntryName: 'Anyone',
		segmentsExperienceId: '33590',
		segmentsExperienceName: 'Experience Default',
		statusLabel: 'Active',
		url: 'url',
	},
};

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	fetch: () =>
		Promise.resolve({
			json: () => ({
				segmentExperienceSelectorData: mockSegments,
				tabsData: mockTabs,
			}),
		}),
}));

const renderPageAudit = ({panelIsOpen = true, selectedItem} = {}) =>
	render(
		<StoreContextProvider
			value={{
				selectedItem,
			}}
		>
			<PageAudit panelIsOpen={panelIsOpen} />
		</StoreContextProvider>
	);

describe('PageAudit', () => {
	it('does not render tabs or experience selector if there is an issue selected', async () => {
		const selectedItem = {
			description: 'This is a description',
			failingElements: [],
			key: 'key',
			tips: 'Tips',
			title: 'This is a title',
			total: '1',
		};

		await act(async () => renderPageAudit({selectedItem}));

		expect(
			screen.queryByText('Experience Default')
		).not.toBeInTheDocument();

		expect(screen.queryByText('First Tab')).not.toBeInTheDocument();
	});
});
