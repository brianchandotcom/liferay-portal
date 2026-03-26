/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import React from 'react';

import RoomTrend from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/RoomTrend';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/common/services/RoomService',
	() => ({
		__esModule: true,
		default: {
			updateRoom: jest.fn((_roomId, {trend}) => Promise.resolve({trend})),
		},
	})
);

describe('RoomTrend', () => {
	beforeAll(() => {
		global.Liferay = {
			Language: {
				get: (key: string) => key,
			},
			ThemeDisplay: {
				getPathContext: () => '',
				getPortalURL: () => 'http://localhost',
			},
		} as any;
	});

	afterEach(cleanup);

	it('renders with the default trend status', () => {
		const {container} = render(<RoomTrend roomId={1212} />);

		expect(screen.getByText('room-trend')).toBeInTheDocument();

		const buttonText = container.querySelector('.room-trend-button-text');

		expect(buttonText?.textContent).toBe('cold');
	});

	it('changes the trend status when an option is selected', async () => {
		const {container} = render(<RoomTrend roomId={1212} />);

		const dropdownButton = container.querySelector(
			'.room-trend-button'
		) as Element;
		await waitFor(() => fireEvent.click(dropdownButton));

		const hotOptions = screen.getAllByText('hot');
		await waitFor(() => fireEvent.click(hotOptions[0]));

		await waitFor(() => {
			expect(
				container.querySelector('.room-trend-button-text')?.textContent
			).toBe('hot');
		});
	});

	it('updates the pointer rotation when the trend status changes', async () => {
		const {container} = render(<RoomTrend roomId={1212} />);

		const pointer = container.querySelector(
			'.room-trend-pointer'
		) as HTMLElement;

		await waitFor(() =>
			expect(pointer.style.transform).toBe('rotate(-81deg) translateZ(0)')
		);

		const dropdownButton = container.querySelector(
			'.room-trend-button'
		) as Element;

		await waitFor(() => fireEvent.click(dropdownButton));

		const hotOptions = screen.getAllByText('hot');

		await waitFor(() => fireEvent.click(hotOptions[0]));

		await waitFor(() =>
			expect(pointer.style.transform).toBe('rotate(81deg) translateZ(0)')
		);
	});
});
