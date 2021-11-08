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

import '@testing-library/jest-dom/extend-expect';
import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import SaveTemplate from '../../../src/main/resources/META-INF/resources/js/SaveTemplate';

const INPUT_VALUE_TEST = 'test';
const BASE_PROPS = {
	formSaveAsTemplateDataQuerySelector: 'form',
	formSaveAsTemplateURL: 'https://formUrl.test',
	portletNamespace: 'test',
};

describe('SaveTemplateModal', () => {
	beforeEach(() => {
		const form = document.createElement('form');

		form.innerHTML = `
			<input type="text" value="${INPUT_VALUE_TEST}" />
		`;

		document.body.appendChild(form);
	});

	afterEach(() => {
		fetchMock.restore();
		cleanup();
	});

	it('must render save template button', () => {
		const {getByText} = render(<SaveTemplate {...BASE_PROPS} />);

		expect(
			getByText(Liferay.Language.get('save-as-template'))
		).toBeInTheDocument();
	});

	it('must show modal when the button is clicked', async () => {
		const {getByText} = render(<SaveTemplate {...BASE_PROPS} />);

		fireEvent.click(getByText(Liferay.Language.get('save-as-template')));

		const saveButton = await waitForElement(() =>
			getByText(Liferay.Language.get('save'))
		);

		expect(saveButton).toBeInTheDocument();
	});

	describe('modal', () => {
		it('must has button disabled if no text input provided', async () => {
			const {getByText} = render(<SaveTemplate {...BASE_PROPS} />);

			fireEvent.click(
				getByText(Liferay.Language.get('save-as-template'))
			);

			const saveButton = await waitForElement(() =>
				getByText(Liferay.Language.get('save'))
			);

			expect(saveButton).toBeDisabled();
		});

		it('must has button enabled if text input provided', async () => {
			const testName = 'test';
			const {getByPlaceholderText, getByText} = render(
				<SaveTemplate {...BASE_PROPS} />
			);

			fireEvent.click(
				getByText(Liferay.Language.get('save-as-template'))
			);

			await waitForElement(() => getByText(Liferay.Language.get('save')));

			fireEvent.change(
				getByPlaceholderText(Liferay.Language.get('template-name')),
				{target: {value: testName}}
			);

			expect(getByText(Liferay.Language.get('save'))).not.toBeDisabled();
		});

		it('must call api with form data and add the input field data', async () => {
			const mockedApi = fetchMock.mock(
				BASE_PROPS.formSaveAsTemplateURL,
				() => {
					return {test: 'test'};
				}
			);

			const testName = 'test';
			const {getByPlaceholderText, getByText} = render(
				<SaveTemplate {...BASE_PROPS} />
			);

			fireEvent.click(
				getByText(Liferay.Language.get('save-as-template'))
			);

			await waitForElement(() => getByText(Liferay.Language.get('save')));

			fireEvent.change(
				getByPlaceholderText(Liferay.Language.get('template-name')),
				{target: {value: testName}}
			);

			fireEvent.click(getByText(Liferay.Language.get('save')));

			expect(mockedApi.called()).toBe(true);
		});
	});
});
