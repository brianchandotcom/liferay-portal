/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import {DocumentPreviewer} from '../src/main/resources/META-INF/resources/preview/js/index';

describe('document-library-preview-document', () => {
	afterEach(cleanup);

	it('renders a document previewer with ten pages and the first page rendered', () => {
		const {asFragment} = render(
			<DocumentPreviewer
				baseImageURL="http://localhost/document-images/"
				totalPages={10}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders a document previewer with nineteen pages and the fifth page rendered', () => {
		const {asFragment} = render(
			<DocumentPreviewer
				baseImageURL="http://localhost/document-images/"
				initialPage={5}
				totalPages={19}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders a document previewer with alt attribute', () => {
		const {asFragment} = render(
			<DocumentPreviewer
				alt="alt text"
				baseImageURL="http://localhost/document-images/"
				initialPage={1}
				totalPages={10}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('can change current page with keyboard', () => {
		const {getByLabelText, getByPlaceholderText, getByText} = render(
			<DocumentPreviewer
				alt="alt text"
				baseImageURL="http://localhost/document-images/"
				initialPage={1}
				totalPages={10}
			/>
		);

		const button = getByLabelText('click-to-jump-to-a-page');
		fireEvent.click(button);

		const input = getByPlaceholderText('page-...');

		fireEvent.change(input, {target: {value: '3'}});
		fireEvent.keyDown(input, {key: 'Enter'});

		expect(getByText('page 3 / 10')).toBeInTheDocument();
	});
});
