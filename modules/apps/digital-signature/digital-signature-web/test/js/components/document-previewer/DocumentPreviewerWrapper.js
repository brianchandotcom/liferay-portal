/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			'div.c-empty-state.c-empty-state-animation'
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

		const messageElement = container.querySelector('.c-empty-state-text');

		expect(messageElement).toBeTruthy();

		expect(asFragment()).toMatchSnapshot();
	});
});
