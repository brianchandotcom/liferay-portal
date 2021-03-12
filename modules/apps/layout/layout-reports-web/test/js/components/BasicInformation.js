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
import userEvent from '@testing-library/user-event';
import React from 'react';

import BasicInformation from '../../../src/main/resources/META-INF/resources/js/components/BasicInformation';

import '@testing-library/jest-dom/extend-expect';

const testProps = {
	canonicalURLs: [
		{
			canonicalURL: 'http://foo.com:8080/en/web/guest/home',
			languageId: 'en-US',
			title: 'Home',
		},
		{
			canonicalURL: 'http://foo.com:8080/es/en/web/guest/inicio',
			languageId: 'es-ES',
			title: 'Inicio',
		},
	],
	defaultLanguageId: 'en-US',
};

describe('BasicInformation', () => {
	afterEach(cleanup);

	it('renders page title, canonicalURL and languages', () => {
		const {getByText} = render(
			<BasicInformation
				canonicalURLs={testProps.canonicalURLs}
				defaultLanguageId={testProps.defaultLanguageId}
			/>
		);

		expect(getByText('Home')).toBeInTheDocument();
		expect(
			getByText('http://foo.com:8080/en/web/guest/home')
		).toBeInTheDocument();
	});

	it('renders page title and canonicalURL changed when selecting another language in the dropdown', () => {
		const {getByText} = render(
			<BasicInformation
				canonicalURLs={testProps.canonicalURLs}
				defaultLanguageId={testProps.defaultLanguageId}
			/>
		);

		const dropdownItem = getByText('es-ES');

		userEvent.click(dropdownItem);

		expect(getByText('Inicio')).toBeInTheDocument();
		expect(
			getByText('http://foo.com:8080/es/en/web/guest/inicio')
		).toBeInTheDocument();
	});
});
