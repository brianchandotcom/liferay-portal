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

import LanguagesDropdown from '../../../src/main/resources/META-INF/resources/js/components/LanguagesDropdown';

import '@testing-library/jest-dom/extend-expect';

const noop = () => {};

describe('LanguageDropdown', () => {
	afterEach(cleanup);

	it('renders all available languages', () => {
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
			selectedLanguageId: 'en-US',
		};

		const {getAllByText, getByText} = render(
			<LanguagesDropdown
				canonicalURLs={testProps.canonicalURLs}
				defaultLanguageId={testProps.defaultLanguageId}
				onSelectedLanguageId={noop}
				selectedLanguageId={testProps.selectedLanguageId}
			/>
		);

		const defaultLanguageId = getAllByText('en-US');
		expect(defaultLanguageId.length === 2);
		expect(defaultLanguageId[0]).toBeInTheDocument();

		expect(getByText('es-ES')).toBeInTheDocument();
	});
});
