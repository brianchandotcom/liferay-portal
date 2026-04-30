/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, waitFor} from '@testing-library/dom';

import DataEngineLayoutBuilderHandler from '../../../../src/main/resources/META-INF/resources/js/document_library/data-engine/DataEngineLayoutBuilderHandler.es';

jest.mock('frontend-js-components-web', () => ({openToast: jest.fn()}));
jest.mock('frontend-js-web', () => ({postForm: jest.fn(), sub: jest.fn()}));
jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/document_library/saveDDMStructure.es',
	() => ({
		getDataEngineStructure: jest.fn(),
		getInputLocalizedValues: jest.fn(),
	})
);

const namespace = 'test_';

describe('DataEngineLayoutBuilderHandler > detectClickOutside', () => {
	let handler;
	let mockDispatch;

	beforeEach(() => {
		mockDispatch = jest.fn();

		document.body.innerHTML = `<form id="${namespace}fm"></form>`;

		global.Liferay.componentReady = jest.fn().mockResolvedValue({
			current: {dispatch: mockDispatch},
		});

		handler = DataEngineLayoutBuilderHandler({namespace});
	});

	afterEach(() => {
		handler.dispose();
		document.body.innerHTML = '';
	});

	it('dispatches sidebar_field_blur when clicking outside the form builder', async () => {
		const outsideElement = document.createElement('div');
		document.body.appendChild(outsideElement);

		fireEvent.mouseDown(outsideElement);

		await waitFor(() => {
			expect(mockDispatch).toHaveBeenCalledWith(
				expect.objectContaining({type: 'sidebar_field_blur'})
			);
		});
	});

	it('does not dispatch sidebar_field_blur when clicking inside .ddm-form-builder-wrapper', async () => {
		document.body.innerHTML += `
			<div class="ddm-form-builder-wrapper">
				<div id="inner-element"></div>
			</div>
		`;

		fireEvent.mouseDown(document.getElementById('inner-element'));

		await expect(
			waitFor(() => expect(mockDispatch).toHaveBeenCalled())
		).rejects.toThrow();
	});

	it('does not dispatch sidebar_field_blur when clicking inside .multi-panel-sidebar', async () => {
		document.body.innerHTML += `
			<div class="multi-panel-sidebar">
				<div id="sidebar-element"></div>
			</div>
		`;

		fireEvent.mouseDown(document.getElementById('sidebar-element'));

		await expect(
			waitFor(() => expect(mockDispatch).toHaveBeenCalled())
		).rejects.toThrow();
	});

	it('does not dispatch sidebar_field_blur when clicking a Clay Picker dropdown option', async () => {
		document.body.innerHTML += `
			<div class="dropdown-menu-select">
				<ul class="inline-scroller list-unstyled">
					<li class="dropdown-item" id="picker-option" role="option">True</li>
				</ul>
			</div>
		`;

		fireEvent.mouseDown(document.getElementById('picker-option'));

		await expect(
			waitFor(() => expect(mockDispatch).toHaveBeenCalled())
		).rejects.toThrow();
	});
});
