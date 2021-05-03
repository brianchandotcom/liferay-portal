/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';

import PageToolbar from '../../../src/main/resources/META-INF/resources/js/shared/PageToolbar';

import '@testing-library/jest-dom/extend-expect';

jest.useFakeTimers();

const onSubmit = jest.fn();

function renderPageToolbar(props) {
	return render(
		<PageToolbar
			initialDescription={{}}
			initialTitle={{}}
			onCancel="/link"
			onChangeTab={jest.fn()}
			onSubmit={onSubmit}
			tab="query-builder"
			tabs={{
				'query-builder': 'query-builder',
			}}
			{...props}
		/>
	);
}

describe('PageToolbar', () => {
	it('renders the page toolbar form', () => {
		const {container} = renderPageToolbar();

		expect(container).not.toBeNull();
	});

	it('renders the title', () => {
		const initialTitle = {
			'en-US': 'Apple',
		};

		const {getByText} = renderPageToolbar({
			initialTitle,
		});

		getByText(initialTitle['en-US']);
	});

	it('updates the title', () => {
		const initialTitle = {
			'en-US': 'Apple',
		};

		const {getByLabelText, getByText, queryByText} = renderPageToolbar({
			initialTitle,
		});

		getByText('Apple');

		fireEvent.click(getByLabelText('edit-name'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('name'), {
			target: {value: 'Banana'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(queryByText('Apple')).toBeNull();
		getByText('Banana');
	});

	it('updates the description', () => {
		const initialTitle = {
			'en-US': 'Apple',
		};

		const initialDescription = {
			'en-US': 'A fruit',
		};

		const {getByLabelText, getByText, queryByText} = renderPageToolbar({
			initialDescription,
			initialTitle,
		});

		getByText('A fruit');

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('description'), {
			target: {value: 'A red fruit'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(queryByText('A fruit')).toBeNull();
		getByText('A red fruit');
	});

	it('offers link to cancel', () => {
		const {getByText} = renderPageToolbar();

		expect(getByText('cancel')).toHaveAttribute('href', '/link');
	});

	it('calls onSubmit when clicking on Save', () => {
		const {getByText} = renderPageToolbar();

		fireEvent.click(getByText('save'));

		expect(onSubmit).toHaveBeenCalled();
	});

	it('disables submit button when submitting', () => {
		const {getByText} = renderPageToolbar({isSubmitting: true});

		expect(getByText('save')).toBeDisabled();
	});

	it('focuses on the name input when clicked on', () => {
		const initialTitle = {
			'en-US': 'Apple',
		};

		const {getByLabelText} = renderPageToolbar({
			initialTitle,
		});

		fireEvent.click(getByLabelText('edit-name'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('name')).toHaveFocus();
	});

	it('focuses on the description input when clicked on', () => {
		const initialTitle = {
			'en-US': 'Apple',
		};

		const {getByLabelText} = renderPageToolbar({
			initialTitle,
		});

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('description')).toHaveFocus();
	});
});
