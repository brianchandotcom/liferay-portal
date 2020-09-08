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
import {cleanup, render, wait} from '@testing-library/react';
import React from 'react';

import TrafficSources from '../../../src/main/resources/META-INF/resources/js/components/TrafficSources';

describe('TrafficSources', () => {
	afterEach(cleanup);

	it('displays the traffic sources with buttons to view keywords', async () => {
		const mockTrafficSourcesDataProvider = jest.fn(() =>
			Promise.resolve([
				{
					countryKeywords: [
						{
							countryCode: 'us',
							countryName: 'United States',
							keywords: [],
						},
						{
							countryCode: 'es',
							countryName: 'Spain',
							keywords: [],
						},
					],
					helpMessage: 'Testing Help Message',
					name: 'testing',
					share: 30,
					title: 'Testing',
					value: 32178,
				},
				{
					countryKeywords: [
						{
							countryCode: 'us',
							countryName: 'United States',
							keywords: [],
						},
						{
							countryCode: 'es',
							countryName: 'Spain',
							keywords: [],
						},
					],
					helpMessage: 'Second Testing Help Message',
					name: 'second-testing',
					share: 70,
					title: 'Second Testing',
					value: 278256,
				},
			])
		);

		const {getByText} = render(
			<TrafficSources
				dataProvider={mockTrafficSourcesDataProvider}
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
			/>
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalled()
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalledTimes(1)
		);

		const button1 = getByText('Testing');

		expect(button1).toBeInTheDocument();
		expect(button1).not.toBeDisabled();
		expect(button1).toHaveAttribute('type', 'button');
		expect(getByText('32,178')).toBeInTheDocument();

		const button2 = getByText('Second Testing');

		expect(button2).toBeInTheDocument();
		expect(button2).not.toBeDisabled();
		expect(button2).toHaveAttribute('type', 'button');
		expect(getByText('278,256')).toBeInTheDocument();
	});

	it('displays the traffic sources without buttons to view keywords when the value is 0', async () => {
		const mockTrafficSourcesDataProvider = jest.fn(() =>
			Promise.resolve([
				{
					countryKeywords: [
						{
							countryCode: 'us',
							countryName: 'United States',
							keywords: [],
						},
						{
							countryCode: 'es',
							countryName: 'Spain',
							keywords: [],
						},
					],
					helpMessage: 'Testing Help Message',
					name: 'testing',
					share: 0,
					title: 'Testing',
					value: 0,
				},
				{
					countryKeywords: [
						{
							countryCode: 'us',
							countryName: 'United States',
							keywords: [],
						},
						{
							countryCode: 'es',
							countryName: 'Spain',
							keywords: [],
						},
					],
					helpMessage: 'Second Testing Help Message',
					name: 'second-testing',
					share: 0,
					title: 'Second Testing',
					value: 0,
				},
			])
		);

		const {getAllByText, getByText} = render(
			<TrafficSources
				dataProvider={mockTrafficSourcesDataProvider}
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
			/>
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalled()
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalledTimes(1)
		);

		const text1 = getByText('Testing');

		expect(text1).toBeInTheDocument();
		expect(text1).not.toHaveAttribute('type', 'button');

		const text2 = getByText('Second Testing');

		expect(text2).toBeInTheDocument();
		expect(text2).not.toHaveAttribute('type', 'button');

		const zeroValues = getAllByText('0');

		expect(zeroValues.length).toBe(2);
	});

	it('displays the traffic sources without buttons to view keywords when the value is missing', async () => {
		const mockTrafficSourcesDataProvider = jest.fn(() =>
			Promise.resolve([
				{
					countryKeywords: [
						{
							countryCode: 'us',
							countryName: 'United States',
							keywords: [],
						},
						{
							countryCode: 'es',
							countryName: 'Spain',
							keywords: [],
						},
					],
					helpMessage: 'Testing Help Message',
					name: 'testing',
					share: 0,
					title: 'Testing',
				},
				{
					countryKeywords: [
						{
							countryCode: 'us',
							countryName: 'United States',
							keywords: [],
						},
						{
							countryCode: 'es',
							countryName: 'Spain',
							keywords: [],
						},
					],
					helpMessage: 'Second Testing Help Message',
					name: 'second-testing',
					share: 0,
					title: 'Second Testing',
				},
			])
		);

		const {getAllByText, getByText} = render(
			<TrafficSources
				dataProvider={mockTrafficSourcesDataProvider}
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
			/>
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalled()
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalledTimes(1)
		);

		const text1 = getByText('Testing');

		expect(text1).toBeInTheDocument();
		expect(text1).not.toHaveAttribute('type', 'button');

		const text2 = getByText('Second Testing');

		expect(text2).toBeInTheDocument();
		expect(text2).not.toHaveAttribute('type', 'button');

		const dashValues = getAllByText('-');

		expect(dashValues.length).toBe(2);
	});

	it('displays a dash instead of value when the value is missing', async () => {
		const mockTrafficSourcesDataProvider = jest.fn(() =>
			Promise.resolve([
				{
					countryKeywords: [],
					helpMessage: 'Testing Help Message',
					name: 'testing',
					share: 100,
					title: 'Testing',
					value: 32178,
				},
				{
					countryKeywords: [],
					helpMessage: 'Second Testing Help Message',
					name: 'second-testing',
					share: 0,
					title: 'Second Testing',
				},
			])
		);

		const {getByText} = render(
			<TrafficSources
				dataProvider={mockTrafficSourcesDataProvider}
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
			/>
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalled()
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalledTimes(1)
		);

		expect(getByText('Testing')).toBeInTheDocument();
		expect(getByText('32,178')).toBeInTheDocument();

		expect(getByText('Second Testing')).toBeInTheDocument();
		expect(getByText('-')).toBeInTheDocument();
	});

	it('displays a message informing the user that there is no incoming traffic from search engines yet', async () => {
		const mockTrafficSourcesDataProvider = jest.fn(() =>
			Promise.resolve([
				{
					countryKeywords: [],
					helpMessage: 'Testing Help Message',
					name: 'testing',
					share: 0,
					title: 'Testing',
					value: 0,
				},
				{
					countryKeywords: [],
					helpMessage: 'Second Testing Help Message',
					name: 'second-testing',
					share: 0,
					title: 'Second Testing',
					value: 0,
				},
			])
		);

		const {getAllByText, getByText} = render(
			<TrafficSources
				dataProvider={mockTrafficSourcesDataProvider}
				languageTag="en-US"
				onTrafficSourceClick={() => {}}
			/>
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalled()
		);

		await wait(() =>
			expect(mockTrafficSourcesDataProvider).toHaveBeenCalledTimes(1)
		);

		expect(getByText('Testing')).toBeInTheDocument();
		expect(getByText('Second Testing')).toBeInTheDocument();

		const zeroValues = getAllByText('0');
		expect(zeroValues.length).toBe(2);

		expect(
			getByText(
				'your-page-has-no-incoming-traffic-from-search-engines-yet'
			)
		).toBeInTheDocument();
	});
});
