/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {
	render,
	screen,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ApiHelper from '../../../js/apis/ApiHelper';
import {TrafficChannels} from '../../../js/components/cms/TrafficChannels';

const mockData = {
	data: {
		items: [
			{
				count: 10,
				name: 'Direct',
			},
			{
				count: 20,
				name: 'Social',
			},
			{
				count: 15,
				name: 'Referrals',
			},
			{
				count: 10,
				name: 'Paid Search',
			},
			{
				count: 30,
				name: 'Email',
			},
			{
				count: 35,
				name: 'Others',
			},
		],
		totalCount: 120,
	},
	error: null,
};

describe('TrafficChannels', () => {
	it('renders', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		const {getByText} = render(<TrafficChannels />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		expect(
			getByText(
				'this-metric-calculates-the-top-five-traffic-channels-that-generated-the-highest-number-of-views-for-the-asset'
			)
		).toBeTruthy();
	});

	it('renders 5 traffic channels and others', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);
		render(<TrafficChannels />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const trafficChannelItem = screen.queryAllByRole('row');

		expect(trafficChannelItem).toHaveLength(7);
	});

	it('calculates the percentage correctly', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue({
			data: {
				items: [
					{
						count: 10,
						name: 'Direct',
					},
					{
						count: 10,
						name: 'Social',
					},
					{
						count: 10,
						name: 'Referrals',
					},
					{
						count: 10,
						name: 'Paid Search',
					},
					{
						count: 10,
						name: 'Email',
					},
					{
						count: 10,
						name: 'Others',
					},
				],
				totalCount: 60,
			},
			error: null,
		});

		const expectedPercentage = ((10 / 60) * 100).toFixed(2);

		const container = render(<TrafficChannels />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const trafficChannelItem = container.queryByRole('row', {
			name: /Direct/,
		});

		expect(trafficChannelItem?.textContent).toContain(expectedPercentage);
	});

	it('is accessible via keyboard navigation', async () => {
		jest.spyOn(ApiHelper, 'get').mockResolvedValue(mockData);

		render(<TrafficChannels />);

		await waitForElementToBeRemoved(() => screen.getByTestId('loading'));

		const firstChannel = screen.getByRole('row', {name: /Direct/});
		firstChannel.focus();

		expect(firstChannel).toHaveFocus();

		await userEvent.tab();

		const secondChannel = screen.getByRole('row', {name: /Social/});
		expect(secondChannel).toHaveFocus();
	});
});
