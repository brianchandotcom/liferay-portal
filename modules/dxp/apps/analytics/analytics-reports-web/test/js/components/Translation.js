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

import '@testing-library/jest-dom/extend-expect';

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

describe('Translation', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('renders', () => {
		const testProps = {
			defaultLanguage: 'en-US',
			publishDate: '2020-08-23',
			timeSpanKey: 'last-7-days',
		};

		const {asFragment} = render(
			<Translation
				defaultLanguage={'en-US'}
				onSelectedLanguageClick={() => {}}
				publishDate={testProps.publishDate}
				timeSpanKey={testProps.timeSpanKey}
				viewURLs={mockViewURLs}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders languages translated into', () => {
		const testProps = {
			defaultLanguage: 'en-US',
			publishDate: '2020-08-23',
			timeSpanKey: 'last-7-days',
		};

		const {getAllByText, getByText} = render(
			<Translation
				defaultLanguage={'en-US'}
				onSelectedLanguageClick={() => {}}
				publishDate={testProps.publishDate}
				timeSpanKey={testProps.timeSpanKey}
				viewURLs={mockViewURLs}
			/>
		);

		expect(getByText('languages-translated-into')).toBeInTheDocument();
		expect(
			getByText('select-language-to-view-its-metrics')
		).toBeInTheDocument();

		const englishLanguage = getAllByText('en-US');
		expect(englishLanguage.length).toBe(2);
		expect(getByText('default')).toBeInTheDocument();

		expect(getByText('es-ES')).toBeInTheDocument();
		expect(getByText('translated')).toBeInTheDocument();
	});
});
