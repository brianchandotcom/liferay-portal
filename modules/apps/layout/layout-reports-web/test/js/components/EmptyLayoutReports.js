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

import EmptyLayoutReports from '../../../src/main/resources/META-INF/resources/js/components/EmptyLayoutReports';

import '@testing-library/jest-dom/extend-expect';

import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/StoreContext';

describe('EmptyLayoutReports', () => {
	afterEach(cleanup);

	it('renders empty view with information', () => {
		const {getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						configureGooglePageSpeedURL: null,
						imagesPath: 'imagesPath',
					},
				}}
			>
				<EmptyLayoutReports />
			</StoreContextProvider>
		);

		expect(
			getByText(
				"check-issues-that-impact-on-your-page's-accessibility-and-seo"
			)
		).toBeInTheDocument();
		expect(
			getByText(
				'connect-with-google-pagespeed-from-site-settings-pages-google-pagespeed'
			)
		).toBeInTheDocument();
	});

	it('renders empty view with information and button', () => {
		const {getByText} = render(
			<StoreContextProvider
				value={{
					data: {
						configureGooglePageSpeedURL: 'validURL',
						imagesPath: 'imagesPath',
					},
				}}
			>
				<EmptyLayoutReports />
			</StoreContextProvider>
		);

		expect(
			getByText(
				"check-issues-that-impact-on-your-page's-accessibility-and-seo"
			)
		).toBeInTheDocument();
		expect(
			getByText('connect-to-google-pagespeed-to-run-a-page-audit')
		).toBeInTheDocument();
		expect(getByText('connect-to-google-pagespeed')).toBeInTheDocument();
	});
});
