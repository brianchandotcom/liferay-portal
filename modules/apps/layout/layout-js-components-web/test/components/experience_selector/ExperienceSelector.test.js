/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {navigate} from 'frontend-js-web';
import React from 'react';

import {ExperienceSelector} from '../../../src/main/resources/META-INF/resources/js/index';

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
		{
			active: false,
			segmentsEntryId: '0',
			segmentsEntryName: 'Segment 1',
			segmentsExperienceId: '33591',
			segmentsExperienceName: 'Experience 1',
			statusLabel: 'Inactive',
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
	navigate: jest.fn(),
}));

const renderComponent = ({
	displayType = 'light',
	disabled,
	segmentsExperiences = mockSegments.segmentsExperiences,
	selectedSegmentsExperience = mockSegments.selectedSegmentsExperience,
} = {}) =>
	render(
		<ExperienceSelector
			disabled={disabled}
			displayType={displayType}
			segmentsExperiences={segmentsExperiences}
			selectedSegmentsExperience={selectedSegmentsExperience}
		/>
	);

describe('ExperienceSelector', () => {
	it('renders', () => {
		renderComponent();

		expect(screen.getByText('Experience Default')).toBeInTheDocument();
		expect(screen.getByText('Active')).toBeInTheDocument();
	});

	it('disabled the selector', () => {
		renderComponent({disabled: true});

		expect(screen.getByRole('combobox')).toBeDisabled();
	});

	it('displays selector as secondary button when the display type is "light"', () => {
		renderComponent();

		expect(screen.getByRole('combobox')).toHaveClass('btn-secondary');
	});

	it('displays selector as button without styles when the display type is "dark"', () => {
		renderComponent({displayType: 'dark'});

		expect(screen.getByRole('combobox')).not.toHaveClass('btn-secondary');
	});

	it('renders name, status and segment in each option', () => {
		renderComponent();

		userEvent.click(screen.getByRole('combobox'));

		expect(screen.getByText('Experience 1')).toBeInTheDocument();
		expect(screen.getByText('Inactive')).toBeInTheDocument();
		expect(screen.getByText('segment: Segment 1')).toBeInTheDocument();
	});

	it('calls navigate when an option is selected', () => {
		renderComponent();

		userEvent.click(screen.getByRole('combobox'));

		const button = screen.getByRole('option', {
			name: 'Experience 1 segment: Segment 1 Inactive',
		});

		fireEvent.click(button);

		expect(navigate).toHaveBeenCalled();
	});
});
