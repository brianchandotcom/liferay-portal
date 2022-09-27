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
import ThemeContext from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/ThemeContext';

import '@testing-library/jest-dom/extend-expect';

jest.useFakeTimers();

const context = {
	availableLanguages: {
		de_DE: 'German (Germany)',
		en_US: 'English (United States)',
		es_ES: 'Spanish (Spain)',
		fr_FR: 'French (France)',
	},
	defaultLocale: 'en_US',
	locale: 'en_US',
};

const description = {
	'en-US': 'Description in English',
	'es-ES': 'Descripcion en Espanol',
};
const title = {
	'en-US': 'Title in English',
	'es-ES': 'Titulo en Espanol',
};

const onSubmit = jest.fn();
const onTitleAndDescriptionChange = jest.fn();

function PageToolbarComponent(props) {
	return (
		<PageToolbar
			description={description}
			onCancel="/link"
			onChangeTab={jest.fn()}
			onSubmit={onSubmit}
			onTitleAndDescriptionChange={onTitleAndDescriptionChange}
			tab="query-builder"
			tabs={{
				'query-builder': 'query-builder',
			}}
			title={title}
			{...props}
		/>
	);
}

function renderPageToolbar(props) {
	return render(PageToolbarComponent(props));
}

function renderPageToolbarWithContext(context, props) {
	return render(
		<ThemeContext.Provider value={context}>
			{PageToolbarComponent(props)}
		</ThemeContext.Provider>
	);
}

describe('PageToolbar', () => {
	it('renders the page toolbar form', () => {
		const {container} = renderPageToolbar();

		expect(container).not.toBeNull();
	});

	it('renders the title', () => {
		const {getByText} = renderPageToolbar();

		getByText(title['en-US']);
	});

	it('calls onTitleAndDescriptionChange when updating title', () => {
		const {getByLabelText, getByText} = renderPageToolbar();

		getByText(title['en-US']);

		fireEvent.click(getByLabelText('edit-title'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('title'), {
			target: {value: 'Updated Title'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(onTitleAndDescriptionChange).toHaveBeenCalled();
	});

	it('calls onTitleAndDescriptionChange when updating description', () => {
		const {getByLabelText, getByText} = renderPageToolbar();

		getByText(description['en-US']);

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		fireEvent.change(getByLabelText('description'), {
			target: {value: 'Updated Description'},
		});

		fireEvent.click(getByText('done'));

		act(() => jest.runAllTimers());

		expect(onTitleAndDescriptionChange).toHaveBeenCalled();
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
		const {getByLabelText} = renderPageToolbar();

		fireEvent.click(getByLabelText('edit-title'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('title')).toHaveFocus();
	});

	it('focuses on the description input when clicked on', () => {
		const {getByLabelText} = renderPageToolbar();

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		expect(getByLabelText('description')).toHaveFocus();
	});

	it('displays the title and description based on locale', () => {
		const {getByText} = renderPageToolbarWithContext({
			...context,
			locale: 'es_ES',
		});

		getByText(title['es-ES']);
		getByText(description['es-ES']);
	});

	it('switches locales in modal with language selector', () => {
		const {
			getAllByText,
			getAllByTitle,
			getByDisplayValue,
			getByLabelText,
			getByText,
		} = renderPageToolbarWithContext(context);

		fireEvent.click(getByLabelText('edit-description'));

		act(() => jest.runAllTimers());

		fireEvent.click(getAllByTitle('Open Localizations')[0]);

		fireEvent.click(getAllByText('es-ES')[0]);

		getByDisplayValue(title['es-ES']);
		getByText(description['es-ES']);
	});
});
