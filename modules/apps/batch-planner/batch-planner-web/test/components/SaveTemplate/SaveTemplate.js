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
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import SaveTemplate from '../../../src/main/resources/META-INF/resources/js/SaveTemplate';
import {SCHEMA_SELECTED_EVENT} from '../../../src/main/resources/META-INF/resources/js/constants';

const INPUT_VALUE_TEST = 'test';
const BASE_PROPS = {
	formSaveAsTemplateDataQuerySelector: 'form',
	formSaveAsTemplateURL: 'https://formUrl.test',
	portletNamespace: 'test',
};

function fireSchemaChangeEvent() {
	Liferay.fire(SCHEMA_SELECTED_EVENT, {schema: 'something'});
}

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
	});

	it('must render save template button', () => {
		const {getByText} = render(<SaveTemplate {...BASE_PROPS} />);

		expect(
			getByText(Liferay.Language.get('save-as-template'))
		).toBeInTheDocument();
	});

	it('must initially has button disabled', () => {
		const {getByText} = render(<SaveTemplate {...BASE_PROPS} />);

		expect(
			getByText(Liferay.Language.get('save-as-template'))
		).toBeDisabled();
	});

	it('must enable button on Schema Change Event', () => {
		const {getByText} = render(<SaveTemplate {...BASE_PROPS} />);

		act(() => {
			fireSchemaChangeEvent();
		});

		expect(
			getByText(Liferay.Language.get('save-as-template'))
		).not.toBeDisabled();
	});

	it('must has button disabled if forceDisable property is true', () => {
		const {getByText} = render(
			<SaveTemplate {...BASE_PROPS} forceDisable={true} />
		);

		act(() => {
			fireSchemaChangeEvent();
		});

		expect(
			getByText(Liferay.Language.get('save-as-template'))
		).toBeDisabled();
	});

	it('must show modal when the button is clicked', async () => {
		const {findByText, getByText} = render(
			<SaveTemplate {...BASE_PROPS} />
		);

		act(() => {
			fireSchemaChangeEvent();
		});

		act(() => {
			fireEvent.click(
				getByText(Liferay.Language.get('save-as-template'))
			);
		});

		const saveButton = await findByText(Liferay.Language.get('save'));

		expect(saveButton).toBeInTheDocument();
	});

	describe('modal', () => {
		it('must has button disabled if no text input provided', async () => {
			const {findByText, getByText} = render(
				<SaveTemplate {...BASE_PROPS} />
			);

			act(() => {
				fireSchemaChangeEvent();
			});

			act(() => {
				fireEvent.click(
					getByText(Liferay.Language.get('save-as-template'))
				);
			});

			const saveButton = await findByText(Liferay.Language.get('save'));

			expect(saveButton).toBeDisabled();
		});

		it('must has button enabled if text input provided', async () => {
			const testName = 'test';
			const {findByText, getByPlaceholderText, getByText} = render(
				<SaveTemplate {...BASE_PROPS} />
			);

			act(() => {
				fireSchemaChangeEvent();
			});

			act(() => {
				fireEvent.click(
					getByText(Liferay.Language.get('save-as-template'))
				);
			});

			await findByText(Liferay.Language.get('save'));

			act(() => {
				fireEvent.change(
					getByPlaceholderText(Liferay.Language.get('template-name')),
					{target: {value: testName}}
				);
			});

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
			const {
				findByText,
				getByPlaceholderText,
				getByText,
				queryByLabelText,
			} = render(<SaveTemplate {...BASE_PROPS} />);

			act(() => {
				fireSchemaChangeEvent();
			});

			act(() => {
				fireEvent.click(
					getByText(Liferay.Language.get('save-as-template'))
				);
			});

			await findByText(Liferay.Language.get('save'));

			act(() => {
				fireEvent.change(
					getByPlaceholderText(Liferay.Language.get('template-name')),
					{target: {value: testName}}
				);
			});

			act(() => {
				fireEvent.click(getByText(Liferay.Language.get('save')));
			});

			expect(mockedApi.called()).toBe(true);

			await waitFor(() => {
				expect(
					queryByLabelText(Liferay.Language.get('name'))
				).toBeNull();
			});
		});
	});
});
