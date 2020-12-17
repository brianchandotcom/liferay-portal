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

import '@testing-library/jest-dom/extend-expect';

import DLVideoExternalShortcutDLFilePicker from '../src/main/resources/META-INF/resources/js/DLVideoExternalShortcutDLFilePicker';

window.onFilePickCallback = jest.fn();

const defaultProps = {
	getDLVideoExternalShortcutFieldsURL: 'getDLVideoExternalShortcutFieldsURL',
	namespace: 'namespace',
	onFilePickCallback: 'onFilePickCallback',
};

const renderComponent = (props) =>
	render(<DLVideoExternalShortcutDLFilePicker {...props} />);

describe('DLVideoExternalShortcutDLFilePicker', () => {
	afterEach(cleanup);

	describe('when rendered with the default props', () => {
		let result;

		beforeEach(() => {
			result = renderComponent(defaultProps);
		});

		it('has a url input', () => {
			expect(result.getByLabelText('video-url')).toBeInTheDocument();
		});

		it('has a video preview placeholder with video icon', () => {
			expect(
				document.querySelector(
					'.video-preview .video-preview-placeholder'
				)
			).toBeInTheDocument();
			expect(
				document.querySelector('svg.lexicon-icon-video')
			).toBeInTheDocument();
		});
	});

	describe('when rendered with initial video', () => {
		let result;
		const props = {
			dlVideoExternalShortcutHTML: '<iframe data-video-liferay></iframe>',
			dlVideoExternalShortcutURL: 'VIDEO-URL',
		};

		beforeEach(() => {
			result = renderComponent({
				...defaultProps,
				...props,
			});
		});

		it('has a url input filled with the video url', () => {
			expect(result.getByLabelText('video-url').value).toBe(
				props.dlVideoExternalShortcutURL
			);
		});

		it('has a video preview with embebed iframe', () => {
			expect(
				document.querySelector('iframe[data-video-liferay]')
			).toBeInTheDocument();
		});
	});
});
