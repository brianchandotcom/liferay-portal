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

import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import HTMLEditorModal from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/HTMLEditorModal';

const renderModal = async ({initialContent = '', onClose, onSave} = {}) => {
	document.body.createTextRange = () => {
		const textRange = {
			getBoundingClientRect: () => 1,
			getClientRects: () => 1,
		};

		return textRange;
	};

	await act(async () => {
		render(
			<HTMLEditorModal
				initialContent={initialContent}
				onClose={onClose}
				onSave={onSave}
			/>
		);

		jest.advanceTimersByTime(1000);
	});
};

describe('HTMLEditorModal', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('modal is rendered', () => {
		renderModal();

		expect(screen.getByText('save')).toBeInTheDocument();
	});

	it('sets initialContent to the editor', () => {
		renderModal({initialContent: 'Hello Jordi Kappler'});

		expect(
			screen.queryAllByText('Hello Jordi Kappler')[0]
		).toBeInTheDocument();
	});

	it('defaults to column view type', () => {
		renderModal();

		const editor = document.querySelector(
			'.page-editor__html-editor-modal__editor-container > div'
		);

		expect(editor).toHaveClass('w-50');
	});

	it('changes to row view type when clicking the display horizontally button', () => {
		renderModal();

		fireEvent.click(screen.getByTitle('display-horizontally'));

		const editor = document.querySelector(
			'.page-editor__html-editor-modal__editor-container > div'
		);

		expect(editor).toHaveClass('w-100');
	});

	it('changes to full-screen view type when clicking the full-screen button', () => {
		renderModal();

		fireEvent.click(screen.getByTitle('full-screen'));

		expect(
			document.querySelector(
				'.page-editor__html-editor-modal__preview-rows'
			)
		).not.toBeInTheDocument();
	});

	it('calls close callback when cliking close button', () => {
		const onClose = jest.fn();

		renderModal({onClose});

		fireEvent.click(screen.getByText('cancel'));

		jest.advanceTimersByTime(1000);

		expect(onClose).toHaveBeenCalled();
	});
});
