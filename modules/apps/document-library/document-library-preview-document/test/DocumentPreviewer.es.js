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

import DocumentPreviewer from '../src/main/resources/META-INF/resources/preview/js/DocumentPreviewer.es';

describe('document-library-preview-document', () => {
	afterEach(cleanup);

	it('renders a document previewer with ten pages and the first page rendered', () => {
		const {asFragment} = render(
			<DocumentPreviewer
				baseImageURL="/document-images/"
				initialPage={1}
				spritemap="icons.svg"
				totalPages={10}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders a document previewer with nineteen pages and the fifth page rendered', () => {
		const {asFragment} = render(
			<DocumentPreviewer
				baseImageURL="/document-images/"
				initialPage={5}
				spritemap="icons.svg"
				totalPages={19}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
