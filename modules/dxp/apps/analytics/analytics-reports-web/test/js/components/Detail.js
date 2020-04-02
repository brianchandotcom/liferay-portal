/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, wait, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import Detail from '../../../src/main/resources/META-INF/resources/js/components/Detail';

const mockCurrentPage = {
	data: {
		keywords: [
			{
				position: 1,
				title: 'commerce',
				value: 90000,
				volume: 12300,
			},
			{
				position: 2,
				title: 'e-commerce',
				value: 14800,
				volume: 9800,
			},
			{
				position: 3,
				title: 'what is commerce',
				value: 14000,
				volume: 9500,
			},
			{
				position: 4,
				title: 'what is e-commerce',
				value: 12100,
				volume: 8700,
			},
			{
				position: 5,
				title: 'commerce definition for new business strategy',
				value: 10100,
				volume: 7100,
			},
		],
		title: 'Organic Traffic',
	},
	view: 'traffic-source-detail',
};

const mockOnCurrentPageChange = jest.fn(() => Promise.resolve({view: 'main'}));

const mockOnTrafficSourceNameChange = jest.fn(() => Promise.resolve(''));

const mockTrafficShareDataProvider = jest.fn(() => Promise.resolve(90));

const mockTrafficVolumeDataProvider = jest.fn(() => Promise.resolve('278,256'));

describe('Detail', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('displays the detail according to API', async () => {
		const {getByText} = render(
			<Detail
				currentPage={mockCurrentPage}
				languageTag={'en-US'}
				onCurrentPageChange={mockOnCurrentPageChange}
				onTrafficSourceNameChange={mockOnTrafficSourceNameChange}
				trafficShareDataProvider={mockTrafficShareDataProvider}
				trafficVolumeDataProvider={mockTrafficVolumeDataProvider}
			/>
		);

		await wait(() =>
			expect(mockTrafficShareDataProvider).toHaveBeenCalled()
		);
		await wait(() =>
			expect(mockTrafficVolumeDataProvider).toHaveBeenCalled()
		);

		expect(getByText('Organic Traffic')).toBeInTheDocument();
		expect(getByText('90%')).toBeInTheDocument();
		expect(getByText('278,256')).toBeInTheDocument();

		expect(mockTrafficShareDataProvider).toHaveBeenCalledTimes(1);
		expect(mockTrafficVolumeDataProvider).toHaveBeenCalledTimes(1);
	});

	it('displays the top five relevant keywords sorted by traffic', async () => {
		const {getByText} = render(
			<Detail
				currentPage={mockCurrentPage}
				languageTag={'en-US'}
				onCurrentPageChange={mockOnCurrentPageChange}
				onTrafficSourceNameChange={mockOnTrafficSourceNameChange}
				trafficShareDataProvider={mockTrafficShareDataProvider}
				trafficVolumeDataProvider={mockTrafficVolumeDataProvider}
			/>
		);

		await wait(() =>
			expect(mockTrafficShareDataProvider).toHaveBeenCalled()
		);
		await wait(() =>
			expect(mockTrafficVolumeDataProvider).toHaveBeenCalled()
		);

		expect(getByText('commerce')).toBeInTheDocument();
		expect(getByText('90,000')).toBeInTheDocument();

		expect(getByText('e-commerce')).toBeInTheDocument();
		expect(getByText('14,800')).toBeInTheDocument();

		expect(getByText('what is commerce')).toBeInTheDocument();
		expect(getByText('14,000')).toBeInTheDocument();

		expect(getByText('what is e-commerce')).toBeInTheDocument();
		expect(getByText('12,100')).toBeInTheDocument();

		expect(
			getByText('commerce definition for new business strategy')
		).toBeInTheDocument();
		expect(getByText('10,100')).toBeInTheDocument();
	});

	it('displays a tooltip with info on hover tooltip signs', async () => {
		const {getByText} = render(
			<Detail
				currentPage={mockCurrentPage}
				languageTag={'en-US'}
				onCurrentPageChange={mockOnCurrentPageChange}
				onTrafficSourceNameChange={mockOnTrafficSourceNameChange}
				trafficShareDataProvider={mockTrafficShareDataProvider}
				trafficVolumeDataProvider={mockTrafficVolumeDataProvider}
			/>
		);

		await wait(() =>
			expect(mockTrafficShareDataProvider).toHaveBeenCalled()
		);
		await wait(() =>
			expect(mockTrafficVolumeDataProvider).toHaveBeenCalled()
		);

		const bestKeywordLabel = getByText('best-keyword');
		const questionCircleIcon = within(bestKeywordLabel).getByRole(
			'presentation'
		);
		userEvent.click(questionCircleIcon);
		expect(getByText('best-keyword-help')).toBeInTheDocument();
	});

	it('displays a dropdown with options to display in the keywords table', async () => {
		const {getAllByText, getByText} = render(
			<Detail
				currentPage={mockCurrentPage}
				languageTag={'en-US'}
				onCurrentPageChange={mockOnCurrentPageChange}
				onTrafficSourceNameChange={mockOnTrafficSourceNameChange}
				trafficShareDataProvider={mockTrafficShareDataProvider}
				trafficVolumeDataProvider={mockTrafficVolumeDataProvider}
			/>
		);

		await wait(() =>
			expect(mockTrafficShareDataProvider).toHaveBeenCalled()
		);
		await wait(() =>
			expect(mockTrafficVolumeDataProvider).toHaveBeenCalled()
		);

		const trafficDropdown = getAllByText('traffic')[0];
		userEvent.click(trafficDropdown);
		expect(getByText('search-volume')).toBeInTheDocument();
		expect(getByText('position')).toBeInTheDocument();
	});

	it('displays the top five relevant keywords sorted by search volume when user clicks on the dropdown option search volume', async () => {
		const {getAllByText, getByText} = render(
			<Detail
				currentPage={mockCurrentPage}
				languageTag={'en-US'}
				onCurrentPageChange={mockOnCurrentPageChange}
				onTrafficSourceNameChange={mockOnTrafficSourceNameChange}
				trafficShareDataProvider={mockTrafficShareDataProvider}
				trafficVolumeDataProvider={mockTrafficVolumeDataProvider}
			/>
		);

		await wait(() =>
			expect(mockTrafficShareDataProvider).toHaveBeenCalled()
		);
		await wait(() =>
			expect(mockTrafficVolumeDataProvider).toHaveBeenCalled()
		);

		const trafficDropdown = getAllByText('traffic')[0];
		userEvent.click(trafficDropdown);
		const searchVolumeLabel = getByText('search-volume');
		userEvent.click(searchVolumeLabel);

		expect(getByText('commerce')).toBeInTheDocument();
		expect(getByText('12,300')).toBeInTheDocument();

		expect(getByText('e-commerce')).toBeInTheDocument();
		expect(getByText('9,800')).toBeInTheDocument();

		expect(getByText('what is commerce')).toBeInTheDocument();
		expect(getByText('9,500')).toBeInTheDocument();

		expect(getByText('what is e-commerce')).toBeInTheDocument();
		expect(getByText('8,700')).toBeInTheDocument();

		expect(
			getByText('commerce definition for new business strategy')
		).toBeInTheDocument();
		expect(getByText('7,100')).toBeInTheDocument();
	});

	it('displays the top five relevant keywords sorted by position when user clicks on the dropdown option position', async () => {
		const {getAllByText, getByText} = render(
			<Detail
				currentPage={mockCurrentPage}
				languageTag={'en-US'}
				onCurrentPageChange={mockOnCurrentPageChange}
				onTrafficSourceNameChange={mockOnTrafficSourceNameChange}
				trafficShareDataProvider={mockTrafficShareDataProvider}
				trafficVolumeDataProvider={mockTrafficVolumeDataProvider}
			/>
		);

		await wait(() =>
			expect(mockTrafficShareDataProvider).toHaveBeenCalled()
		);
		await wait(() =>
			expect(mockTrafficVolumeDataProvider).toHaveBeenCalled()
		);

		const trafficDropdown = getAllByText('traffic')[0];
		userEvent.click(trafficDropdown);
		const searchVolumeLabel = getByText('position');
		userEvent.click(searchVolumeLabel);

		expect(getByText('commerce')).toBeInTheDocument();
		expect(getByText('1')).toBeInTheDocument();

		expect(getByText('e-commerce')).toBeInTheDocument();
		expect(getByText('2')).toBeInTheDocument();

		expect(getByText('what is commerce')).toBeInTheDocument();
		expect(getByText('3')).toBeInTheDocument();

		expect(getByText('what is e-commerce')).toBeInTheDocument();
		expect(getByText('4')).toBeInTheDocument();

		expect(
			getByText('commerce definition for new business strategy')
		).toBeInTheDocument();
		expect(getByText('5')).toBeInTheDocument();
	});
});
