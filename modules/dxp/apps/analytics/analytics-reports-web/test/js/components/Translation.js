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

import Translation from '../../../src/main/resources/META-INF/resources/js/components/Translation';
import {ChartStateContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/ChartStateContext';

import '@testing-library/jest-dom/extend-expect';

const mockViewURLs = [
	{
		default: true,
		languageId: 'en-US',
		languageLabel: 'English (United States)',
		selected: true,
		viewURL: 'http://localhost:8080/en/web/guest/-/basic-web-content',
	},
	{
		default: false,
		languageId: 'es-ES',
		languageLabel: 'Spanish (Spain)',
		selected: false,
		viewURL: 'http://localhost:8080/es/web/guest/-/contenido-web-basico',
	},
];

const noop = () => {};

describe('Translation', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('renders', () => {
		const testProps = {
			defaultLanguage: 'en-US',
			pagePublishDate: 'Thu Aug 10 08:17:57 GMT 2020',
			timeRange: {endDate: '2020-01-27', startDate: '2020-02-02'},
			timeSpanKey: 'last-7-days',
		};

		const {asFragment} = render(
			<ChartStateContextProvider
				publishDate={testProps.pagePublishDate}
				timeRange={testProps.timeRange}
				timeSpanKey={testProps.timeSpanKey}
			>
				<Translation
					defaultLanguage={testProps.defaultLanguage}
					onSelectedLanguageClick={noop}
					viewURLs={mockViewURLs}
				/>
			</ChartStateContextProvider>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders languages translated into', () => {
		const testProps = {
			defaultLanguage: 'en-US',
			pagePublishDate: 'Thu Aug 10 08:17:57 GMT 2020',
			timeRange: {endDate: '2020-01-27', startDate: '2020-02-02'},
			timeSpanKey: 'last-7-days',
		};

		const {getByText} = render(
			<ChartStateContextProvider
				publishDate={testProps.pagePublishDate}
				timeRange={testProps.timeRange}
				timeSpanKey={testProps.timeSpanKey}
			>
				<Translation
					defaultLanguage={testProps.defaultLanguage}
					onSelectedLanguageClick={noop}
					viewURLs={mockViewURLs}
				/>
			</ChartStateContextProvider>
		);

		expect(getByText('en-US')).toBeInTheDocument();
		expect(getByText('English (United States)')).toBeInTheDocument();
		expect(getByText('default')).toBeInTheDocument();
		expect(getByText('Spanish (Spain)')).toBeInTheDocument();
	});
});
