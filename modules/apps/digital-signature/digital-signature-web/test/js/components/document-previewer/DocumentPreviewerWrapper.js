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

import DocumentPreviewerWrapper from '../../../../src/main/resources/META-INF/resources/js/components/document-previewer/DocumentPreviewerWrapper';

describe('DocumentPreviewerWrapper', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders with empty state', () => {
		const {asFragment, container} = render(<DocumentPreviewerWrapper />);

		const emptyResultMessage = container.querySelector(
			'div.taglib-empty-result-message'
		);

		expect(emptyResultMessage).toBeTruthy();

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with an image', () => {
		const fileEntries = [
			{
				imageURL: 'http://localhost:8080/image/example.png',
				title: 'Image',
			},
		];

		const {asFragment, container} = render(
			<DocumentPreviewerWrapper fileEntries={fileEntries} />
		);

		const ImagePreviewer = container.querySelector('div.image-container');

		expect(ImagePreviewer).toBeTruthy();

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with a document', () => {
		const fileEntries = [
			{
				initialPage: 1,
				previewFileCount: 1,
				previewFileURL: 'http://localhost:8080/image/example.png',
				title: 'Document',
			},
		];

		const {asFragment, container} = render(
			<DocumentPreviewerWrapper fileEntries={fileEntries} />
		);

		const DocumentPreviewer = container.querySelector(
			'div.preview-file-container'
		);

		expect(DocumentPreviewer).toBeTruthy();

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with a document without preview', () => {
		const fileEntries = [
			{
				initialPage: 1,
				previewFileCount: 1,
				title: 'Document',
			},
		];

		const {asFragment, container} = render(
			<DocumentPreviewerWrapper fileEntries={fileEntries} />
		);

		const messageElement = container.querySelector(
			'.empty-message-color.taglib-empty-result-message-description'
		);

		expect(messageElement).toBeTruthy();

		expect(asFragment()).toMatchSnapshot();
	});
});
