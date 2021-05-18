/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import BasicInformation from '../../../src/main/resources/META-INF/resources/js/components/BasicInformation';

import '@testing-library/jest-dom/extend-expect';

const testProps = {
	defaultLanguageId: 'en-US',
	onLanguageChange: () => {},
	pageURLs: [
		{
			languageId: 'en-US',
			title: 'Home',
			url: 'http://foo.com:8080/en/web/guest/home',
		},
		{
			languageId: 'es-ES',
			title: 'Inicio',
			url: 'http://foo.com:8080/es/en/web/guest/inicio',
		},
	],
	selectedLanguageId: 'es-ES',
};

describe('BasicInformation', () => {
	afterEach(cleanup);

	it('renders page title, pageURL and languages', () => {
		const {getByText} = render(
			<BasicInformation
				defaultLanguageId={testProps.defaultLanguageId}
				onLanguageChange={testProps.onLanguageChange}
				pageURLs={testProps.pageURLs}
			/>
		);

		expect(getByText('Home')).toBeInTheDocument();
		expect(
			getByText('http://foo.com:8080/en/web/guest/home')
		).toBeInTheDocument();
	});

	it('renders corresponding page title and pageURL changed when getting a selected language id', () => {
		const {getByText} = render(
			<BasicInformation
				defaultLanguageId={testProps.defaultLanguageId}
				onLanguageChange={testProps.onLanguageChange}
				pageURLs={testProps.pageURLs}
				selectedLanguageId={testProps.selectedLanguageId}
			/>
		);

		expect(getByText('Inicio')).toBeInTheDocument();
		expect(
			getByText('http://foo.com:8080/es/en/web/guest/inicio')
		).toBeInTheDocument();
	});
});
