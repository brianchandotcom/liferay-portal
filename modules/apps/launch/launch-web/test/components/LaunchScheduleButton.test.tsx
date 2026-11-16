/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import LaunchScheduleButton from '../../src/main/resources/META-INF/resources/js/components/LaunchScheduleButton';

const DEFAULT_PROPS = {
	launchName: 'Halloween Campaign',
	portletNamespace: '_portletNamespace_',
	scheduleLaunchURL: '/schedule',
	scheduledDate: null,
	timeZoneId: 'UTC',
	timeZones: [{label: '(UTC) Coordinated Universal Time', value: 'UTC'}],
};

describe('LaunchScheduleButton', () => {
	it('shows schedule for a failed launch', () => {
		render(
			<LaunchScheduleButton {...DEFAULT_PROPS} launchStatus="failed" />
		);

		expect(
			screen.getByRole('button', {name: 'schedule'})
		).toBeInTheDocument();
	});

	it('shows schedule for an open launch', () => {
		render(<LaunchScheduleButton {...DEFAULT_PROPS} launchStatus="open" />);

		expect(
			screen.getByRole('button', {name: 'schedule'})
		).toBeInTheDocument();
	});

	it('hides schedule for a published launch', () => {
		render(
			<LaunchScheduleButton {...DEFAULT_PROPS} launchStatus="published" />
		);

		expect(
			screen.queryByRole('button', {name: 'schedule'})
		).not.toBeInTheDocument();
	});

	it('shows the scheduled date instead of schedule for a scheduled launch', () => {
		render(
			<LaunchScheduleButton
				{...DEFAULT_PROPS}
				launchStatus="scheduled"
				scheduledDate="2026-12-01T10:00:00Z"
			/>
		);

		expect(screen.getByText(/^scheduled: /)).toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: 'schedule'})
		).not.toBeInTheDocument();
	});
});
