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

import DocumentPreviewer from '../src/main/resources/META-INF/resources/preview/js/DocumentPreviewer.es';

let component;

const defaultDocumentPreviewerConfig = {
	baseImageURL: '/document-images/',
	spritemap: 'icons.svg'
};

describe('document-library-preview-document', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render a document previewer with ten pages and the first page rendered', () => {
		component = new DocumentPreviewer({
			...defaultDocumentPreviewerConfig,
			currentPage: 1,
			totalPages: 10
		});

		expect(component).toMatchSnapshot();
	});

	it('should render a document previewer with nineteen pages and the fifth page rendered', () => {
		component = new DocumentPreviewer({
			...defaultDocumentPreviewerConfig,
			currentPage: 5,
			totalPages: 19
		});

		expect(component).toMatchSnapshot();
	});
});
