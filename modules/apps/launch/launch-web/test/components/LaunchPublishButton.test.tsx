/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import LaunchPublishButton from '../../src/main/resources/META-INF/resources/js/components/LaunchPublishButton';

describe('LaunchPublishButton', () => {
	it('enables publish and shows the failed label for a failed launch', () => {
		render(
			<LaunchPublishButton
				launchStatus="failed"
				publishLaunchURL="/publish"
			/>
		);

		expect(screen.getByRole('button', {name: 'publish'})).toBeEnabled();
		expect(screen.getByText('failed')).toBeInTheDocument();
	});

	it('enables publish for an open launch', () => {
		render(
			<LaunchPublishButton
				launchStatus="open"
				publishLaunchURL="/publish"
			/>
		);

		expect(screen.getByRole('button', {name: 'publish'})).toBeEnabled();
		expect(screen.queryByText('failed')).not.toBeInTheDocument();
	});

	it('disables publish for a published launch', () => {
		render(
			<LaunchPublishButton
				launchStatus="published"
				publishLaunchURL="/publish"
			/>
		);

		expect(screen.getByRole('button', {name: 'published'})).toBeDisabled();
	});

	it('disables publish for a scheduled launch', () => {
		render(
			<LaunchPublishButton
				launchStatus="scheduled"
				publishLaunchURL="/publish"
			/>
		);

		expect(screen.getByRole('button', {name: 'publish'})).toBeDisabled();
	});
});
