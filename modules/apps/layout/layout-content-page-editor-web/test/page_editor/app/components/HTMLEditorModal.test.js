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
import HTMLProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/HTMLProcessor';

const renderModal = async ({initialContent = '', onClose, onSave}) => {
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

	it('injects the given string as innerHTML', () => {
		const element = document.createElement('div');

		HTMLProcessor.render(element, 'Jordi Kappler');
		expect(element.innerHTML).toBe('Jordi Kappler');
	});

	it('modal is rendered', () => {
		renderModal('');

		expect(screen.getByText('save')).toBeInTheDocument();
	});

	it('initialContent is correct', () => {
		renderModal({initialContent: 'Hello Jordi Kappler'});

		expect(
			screen.getAllByText('Hello Jordi Kappler')[0].innerHTML
		).toContain('Hello Jordi Kappler');
	});

	it('view type column is displayed correctly', () => {
		renderModal('');

		const editor = document.querySelector(
			'.page-editor__html-editor-modal__editor-container > div'
		);

		expect(editor.classList.contains('w-50')).toBe(true);
	});

	it('view type rows is displayed correctly', () => {
		renderModal('');

		fireEvent.click(screen.getByTitle('display-horizontally'));

		const editor = document.querySelector(
			'.page-editor__html-editor-modal__editor-container > div'
		);

		expect(editor).toHaveClass('w-100');
	});

	it('view type full screen is displayed correctly', () => {
		renderModal('');

		fireEvent.click(screen.getByTitle('full-screen'));

		const preview = document.querySelector(
			'.page-editor__html-editor-modal__preview-rows'
		);

		expect(preview).toBeNull();
	});

	it('Close button', async () => {
		const onClose = jest.fn();

		renderModal({onClose});

		fireEvent.click(screen.getByText('cancel'));

		jest.advanceTimersByTime(1000);

		expect(onClose).toHaveBeenCalled();
	});
});
