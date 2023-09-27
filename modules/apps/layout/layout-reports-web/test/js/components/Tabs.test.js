/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import Tabs from '../../../src/main/resources/META-INF/resources/js/components/Tabs';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/components/render_times/RenderTimes',
	() => jest.fn(() => <div />)
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/components/layout_reports/LayoutReports',
	() => jest.fn(() => <div />)
);

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

const mockTabs = [
	{
		id: 'page-speed-insights',
		name: 'Page Speed',
		url: 'url',
	},
	{
		id: 'performance',
		name: 'Performance',
		url: 'url',
	},
];

const renderTabs = ({segments = mockSegments} = {}) =>
	render(<Tabs segments={segments} tabs={mockTabs} />);

describe('Tabs', () => {
	it('does not render the experience selector when there is only the default experience', () => {
		renderTabs();

		expect(
			screen.queryByText('Experience Default')
		).not.toBeInTheDocument();
	});

	it('renders experience selector if there is more than one experience', () => {
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

		renderTabs({segments: newSegments});

		expect(screen.getByText('Experience Default')).toBeInTheDocument();
	});

	it('renders tabs', async () => {
		renderTabs();

		expect(screen.getByText('Performance')).toBeInTheDocument();
		expect(screen.getByText('Page Speed')).toBeInTheDocument();
	});
});
