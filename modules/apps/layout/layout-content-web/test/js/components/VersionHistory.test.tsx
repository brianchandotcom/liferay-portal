/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMediaQuery} from '@liferay/layout-js-components-web';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom';

import VersionHistory from '../../../src/main/resources/META-INF/resources/js/components/VersionHistory';

jest.mock('@liferay/layout-js-components-web', () => ({
	useMediaQuery: jest.fn(),
}));

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(() =>
		Promise.resolve({
			json: () => Promise.resolve({items: []}),
			ok: true,
		})
	),
}));

const mockUseMediaQuery = useMediaQuery as jest.Mock;

function mockLargeScreen() {
	mockUseMediaQuery.mockReturnValue(true);
}

function mockSmallScreen() {
	mockUseMediaQuery.mockReturnValue(false);
}

function renderComponent() {
	return render(<VersionHistory pageSpecificationVersionsURL="url" />);
}

describe('VersionHistory', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('does not show the open button on large screens', async () => {
		mockLargeScreen();

		renderComponent();

		expect(screen.getByText('version-history')).toBeInTheDocument();

		expect(
			screen.queryByRole('button', {name: 'open-x'})
		).not.toBeInTheDocument();
	});

	it('reveals the open button after the panel is closed on small screens', async () => {
		mockSmallScreen();

		renderComponent();

		expect(
			screen.queryByRole('button', {name: 'open-x'})
		).not.toBeInTheDocument();

		await userEvent.click(screen.getByRole('button', {name: 'close'}));

		expect(
			screen.getByRole('button', {name: 'open-x'})
		).toBeInTheDocument();
	});
});
