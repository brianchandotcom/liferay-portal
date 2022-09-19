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

import {
	act,
	fireEvent,
	render,
	screen,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import Sidebar, {
	MAX_SIDEBAR_WIDTH,
	MIN_SIZEBAR_WIDTH,
	SIDEBAR_WIDTH_RESIZE_STEP,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/Sidebar';
import {DragAndDropContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/drag-and-drop/useDragAndDrop';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test-utils/StoreMother';

const renderSidebar = async () => {
	render(
		<StoreMother.Component>
			<DndProvider backend={HTML5Backend}>
				<DragAndDropContextProvider>
					<Sidebar />
				</DragAndDropContextProvider>
			</DndProvider>
		</StoreMother.Component>
	);

	await waitForElementToBeRemoved(() =>
		document.querySelector('.loading-animation')
	);
};

describe('Sidebar', () => {
	describe('resize', () => {
		afterEach(() => {
			window.sessionStorage.clear();
		});

		it('can be resized with mouse', async () => {
			await renderSidebar();

			const handler = screen.getByRole('separator', {
				label: 'resize-sidebar',
			});

			act(() => {
				fireEvent(
					handler,
					new MouseEvent('mousedown', {
						bubbles: true,
						cancelable: true,
						clientX: 0,
					})
				);

				fireEvent(
					document.body,
					new MouseEvent('mousemove', {
						bubbles: true,
						cancelable: true,
						clientX: 100,
					})
				);

				fireEvent(
					document.body,
					new MouseEvent('mouseup', {
						bubbles: true,
						cancelable: true,
						clientX: 100,
					})
				);
			});

			expect(handler.getAttribute('aria-valuenow')).toBe(
				(MIN_SIZEBAR_WIDTH + 100).toString()
			);
		});

		it('can be resized with arrow keys', async () => {
			await renderSidebar();

			const handler = screen.getByRole('separator', {
				label: 'resize-sidebar',
			});

			act(() => {
				fireEvent(
					handler,
					new KeyboardEvent('keydown', {
						bubbles: true,
						cancelable: true,
						key: 'ArrowRight',
					})
				);
			});

			expect(handler.getAttribute('aria-valuenow')).toBe(
				(MIN_SIZEBAR_WIDTH + SIDEBAR_WIDTH_RESIZE_STEP).toString()
			);

			act(() => {
				fireEvent(
					handler,
					new KeyboardEvent('keydown', {
						bubbles: true,
						cancelable: true,
						key: 'End',
					})
				);
			});

			expect(handler.getAttribute('aria-valuenow')).toBe(
				MAX_SIDEBAR_WIDTH.toString()
			);

			act(() => {
				fireEvent(
					handler,
					new KeyboardEvent('keydown', {
						bubbles: true,
						cancelable: true,
						key: 'Home',
					})
				);
			});

			expect(handler.getAttribute('aria-valuenow')).toBe(
				MIN_SIZEBAR_WIDTH.toString()
			);
		});
	});
});
