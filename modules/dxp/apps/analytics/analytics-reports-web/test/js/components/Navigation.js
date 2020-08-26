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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Navigation from '../../../src/main/resources/META-INF/resources/js/components/Navigation';
import ConnectionContext from '../../../src/main/resources/META-INF/resources/js/context/ConnectionContext';
import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/store';

import '@testing-library/jest-dom/extend-expect';

const mockEndpoints = {
	getHistoricalReads: jest.fn(() =>
		Promise.resolve({
			analyticsReportsHistoricalReads: {
				histogram: [
					{
						key: '2020-01-27T00:00',
						previousKey: '2020-01-20T00:00',
						previousValue: 22.0,
						value: 33.0,
					},
					{
						key: '2020-01-28T00:00',
						previousKey: '2020-01-21T00:00',
						previousValue: 23.0,
						value: 33.0,
					},
					{
						key: '2020-01-29T00:00',
						previousKey: '2020-01-22T00:00',
						previousValue: 24.0,
						value: 34.0,
					},
					{
						key: '2020-01-30T00:00',
						previousKey: '2020-01-23T00:00',
						previousValue: 25.0,
						value: 33.0,
					},
					{
						key: '2020-01-31T00:00',
						previousKey: '2020-01-24T00:00',
						previousValue: 26.0,
						value: 32.0,
					},
					{
						key: '2020-02-01T00:00',
						previousKey: '2020-01-25T00:00',
						previousValue: 27.0,
						value: 31.0,
					},
					{
						key: '2020-02-02T00:00',
						previousKey: '2020-01-26T00:00',
						previousValue: 28.0,
						value: 30.0,
					},
				],
				previousValue: 175.0,
				value: 226.0,
			},
		})
	),
	getHistoricalViews: jest.fn(() =>
		Promise.resolve({
			analyticsReportsHistoricalViews: {
				histogram: [
					{
						key: '2020-01-27T00:00',
						previousKey: '2020-01-20T00:00',
						previousValue: 22.0,
						value: 32.0,
					},
					{
						key: '2020-01-28T00:00',
						previousKey: '2020-01-21T00:00',
						previousValue: 23.0,
						value: 33.0,
					},
					{
						key: '2020-01-29T00:00',
						previousKey: '2020-01-22T00:00',
						previousValue: 24.0,
						value: 34.0,
					},
					{
						key: '2020-01-30T00:00',
						previousKey: '2020-01-23T00:00',
						previousValue: 25.0,
						value: 33.0,
					},
					{
						key: '2020-01-31T00:00',
						previousKey: '2020-01-24T00:00',
						previousValue: 26.0,
						value: 32.0,
					},
					{
						key: '2020-02-01T00:00',
						previousKey: '2020-01-25T00:00',
						previousValue: 27.0,
						value: 31.0,
					},
					{
						key: '2020-02-02T00:00',
						previousKey: '2020-01-26T00:00',
						previousValue: 28.0,
						value: 30.0,
					},
				],
				previousValue: 175.0,
				value: 225.0,
			},
		})
	),
	getTotalReads: jest.fn(() => {
		return Promise.resolve(999);
	}),
	getTotalViews: jest.fn(() => {
		return Promise.resolve(12345);
	}),
};

const mockTimeSpanOptions = [
	{
		key: 'last-30-days',
		label: 'Last 30 Days',
	},
	{
		key: 'last-7-days',
		label: 'Last 7 Days',
	},
];

const mockTrafficSources = [
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
];

const mockViewURLs = [
	{
		default: true,
		languageId: 'en-US',
		selected: true,
		viewURL: 'http://localhost:8080/en/web/guest/-/basic-web-content',
	},
	{
		default: false,
		languageId: 'es-ES',
		selected: false,
		viewURL: 'http://localhost:8080/es/web/guest/-/contenido-web-basico',
	},
];

const mockData = {
	author: {
		authorId: '20125',
		name: 'Test Test',
	},
	canonicalURL: 'http://localhost:8080/-/basic-web-content',
	endpoints: mockEndpoints,
	languageTag: 'en-US',
	namespace:
		'_com_liferay_analytics_reports_web_internal_portlet_AnalyticsReportsPortlet_',
	page: {
		plid: 19,
	},
	publishDate: '2020-08-23',
	timeRange: {
		endDate: '2020-08-23',
		startDate: '2020-08-17',
	},
	timeSpanKey: 'last-7-days',
	timeSpans: mockTimeSpanOptions,
	title: 'Basic Web Content',
	trafficSources: mockTrafficSources,
	validAnalyticsConnection: true,
	viewURLs: mockViewURLs,
};

describe('Navigation', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('displays an alert error message if there is no valid connection', () => {
		const testProps = {
			author: {
				authorId: '',
				name: 'John Tester',
				url: '',
			},
			canonicalURL:
				'http://localhost:8080/en/web/guest/-/basic-web-content',
			languageTag: 'en-US',
			page: {plid: 20},
			pagePublishDate: 'Thu Aug 10 08:17:57 GMT 2020',
			pageTitle: 'A testing page',
			timeRange: {endDate: '2020-01-27', startDate: '2020-02-02'},
			timeSpanKey: 'last-7-days',
		};

		const {getByText} = render(
			<ConnectionContext.Provider
				value={{
					validAnalyticsConnection: false,
				}}
			>
				<Navigation
					author={testProps.author}
					canonicalURL={testProps.canonicalURL}
					endpoints={{}}
					languageTag={testProps.languageTag}
					onSelectedLanguageClick={() => {}}
					page={testProps.page}
					pagePublishDate={testProps.pagePublishDate}
					pageTitle={testProps.pageTitle}
					timeRange={testProps.timeRange}
					timeSpanKey={testProps.timeSpanKey}
					timeSpanOptions={mockTimeSpanOptions}
					trafficSources={mockTrafficSources}
					viewURLs={mockViewURLs}
				/>
			</ConnectionContext.Provider>
		);

		expect(getByText('an-unexpected-error-occurred')).toBeInTheDocument();
	});

	it('displays an alert warning message if some data is temporarily unavailable', async () => {
		global.fetch = jest.fn(() =>
			Promise.resolve({
				json: () => Promise.resolve(mockData),
			})
		);

		const testProps = {
			author: {
				authorId: '',
				name: 'John Tester',
				url: '',
			},
			canonicalURL:
				'http://localhost:8080/en/web/guest/-/basic-web-content',
			languageTag: 'en-US',
			page: {plid: 20},
			pagePublishDate: 'Thu Aug 10 08:17:57 GMT 2020',
			pageTitle: 'A testing page',
			timeRange: {endDate: '2020-01-27', startDate: '2020-02-02'},
			timeSpanKey: 'last-7-days',
		};

		const {getByText} = render(
			<StoreContextProvider>
				<Navigation
					author={testProps.author}
					canonicalURL={testProps.canonicalURL}
					endpoints={mockEndpoints}
					languageTag={testProps.languageTag}
					onSelectedLanguageClick={() => {}}
					page={testProps.page}
					pagePublishDate={testProps.pagePublishDate}
					pageTitle={testProps.pageTitle}
					timeRange={testProps.timeRange}
					timeSpanKey={testProps.timeSpanKey}
					timeSpanOptions={mockTimeSpanOptions}
					trafficSources={mockTrafficSources}
					viewURLs={mockViewURLs}
				/>
			</StoreContextProvider>
		);

		expect(
			getByText('some-data-is-temporarily-unavailable')
		).toBeInTheDocument();
	});
});
