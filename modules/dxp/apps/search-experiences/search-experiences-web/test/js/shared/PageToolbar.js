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

import PageToolbar from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/PageToolbar';

import '@testing-library/jest-dom/extend-expect';

jest.useFakeTimers();

const onChangeTitleAndDescription = jest.fn();
const onSubmit = jest.fn();

Liferay.ThemeDisplay.getDefaultLanguageId = () => 'en_US';

function renderPageToolbar(props) {
	return render(
		<PageToolbar
			description=""
			onCancel="/link"
			onChangeTab={jest.fn()}
			onChangeTitleAndDescription={onChangeTitleAndDescription}
			onSubmit={onSubmit}
			tab="query-builder"
			tabs={{
				'query-builder': 'query-builder',
			}}
			title=""
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
		const title = 'Apple';

		const {getByText} = renderPageToolbar({title});

		getByText(title);
	});

	it('calls onChangeTitle when updating title', () => {
		const title = 'Apple';

		const {getByLabelText, getByText} = renderPageToolbar({
			title,
		});

		getByText(title);

		fireEvent.click(getByLabelText('edit-title'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('title'), {
			target: {value: 'Banana'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(onChangeTitleAndDescription).toHaveBeenCalled();
	});

	it('calls onChangeTitle when updating description', () => {
		const title = 'Apple';

		const description = 'A fruit';

		const {getByLabelText, getByText} = renderPageToolbar({
			description,
			title,
		});

		getByText('A fruit');

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('description'), {
			target: {value: 'A red fruit'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(onChangeTitleAndDescription).toHaveBeenCalled();
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

	it('focuses on the title input when clicked on', () => {
		const title = 'Apple';

		const {getByLabelText} = renderPageToolbar({
			title,
		});

		fireEvent.click(getByLabelText('edit-title'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('title')).toHaveFocus();
	});

	it('focuses on the description input when clicked on', () => {
		const title = 'Apple';

		const {getByLabelText} = renderPageToolbar({
			title,
		});

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('description')).toHaveFocus();
	});
});
