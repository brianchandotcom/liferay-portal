/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {ConstantsContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/ConstantsContext';
import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/StoreContext';

import '@testing-library/jest-dom/extend-expect';

import PageAudit, {
	PageAuditBody,
} from '../../../src/main/resources/META-INF/resources/js/components/PageAudit';

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

const renderPageAudit = ({panelIsOpen = true} = {}) =>
	render(
		<ConstantsContextProvider
			constants={{
				layoutReportsTabsURL: 'url',
			}}
		>
			<PageAudit panelIsOpen={panelIsOpen} />
		</ConstantsContextProvider>
	);

const renderPageAuditBody = ({segments = mockSegments, selectedIssue} = {}) =>
	render(
		<StoreContextProvider
			value={{
				selectedIssue,
			}}
		>
			<PageAuditBody segments={segments} tabs={mockTabs}>
				<div>This is the body</div>
			</PageAuditBody>
		</StoreContextProvider>
	);

describe('PageAudit', () => {
	it('renders tabs', async () => {
		await act(async () => renderPageAudit());

		expect(screen.getByText('First Tab')).toBeInTheDocument();
		expect(screen.getByText('Second Tab')).toBeInTheDocument();
	});

	it('does not render the experience selector when there is only the default experience', async () => {
		await act(async () => renderPageAudit());

		expect(
			screen.queryByText('Experience Default')
		).not.toBeInTheDocument();
	});

	it('renders experience selector if there is more than one experience', async () => {
		const newSegments = {
			...mockSegments,
			segmentsExperiences: [
				...mockSegments.segmentsExperiences,
				{
					active: true,
					segmentsEntryId: '0',
					segmentsEntryName: 'Anyone',
					segmentsExperienceId: '33591',
					segmentsExperienceName: 'Experience1',
					statusLabel: 'Inactive',
					url: 'url',
				},
			],
		};

		await act(async () => renderPageAuditBody({segments: newSegments}));

		expect(screen.getByText('Experience Default')).toBeInTheDocument();
	});

	it('does not render tabs or experience selector if there is an issue selected', async () => {
		const selectedIssue = {
			description: 'This is a description',
			failingElements: [],
			key: 'key',
			tips: 'Tips',
			title: 'This is a title',
			total: '1',
		};

		await act(async () => renderPageAuditBody({selectedIssue}));

		expect(
			screen.queryByText('Experience Default')
		).not.toBeInTheDocument();
		expect(screen.queryByText('First Tab')).not.toBeInTheDocument();
	});
});
