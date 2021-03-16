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

import {fireEvent, render, waitForElement} from '@testing-library/react';
import React from 'react';

import Element from '../../../../src/main/resources/META-INF/resources/js/shared/element/index';
import {INDEX_FIELDS, SELECTED_ELEMENTS} from '../../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

const onDeleteElement = jest.fn();
const onUpdateElement = jest.fn();

function renderElement(props) {
	return render(
		<Element
			collapseAll={false}
			elementTemplateJSON={SELECTED_ELEMENTS[0].elementTemplateJSON}
			indexFields={INDEX_FIELDS}
			initialUIConfigurationValues={
				SELECTED_ELEMENTS[0].uiConfigurationValues
			}
			onDeleteElement={onDeleteElement}
			onUpdateElement={onUpdateElement}
			uiConfigurationJSON={SELECTED_ELEMENTS[0].uiConfigurationJSON}
			uiConfigurationValues={SELECTED_ELEMENTS[0].uiConfigurationValues}
			{...props}
		/>
	);
}

describe('Element', () => {
	it('renders the element', () => {
		const {container} = renderElement();

		expect(container).not.toBeNull();
	});

	it('displays the title', () => {
		const {getByText} = renderElement();

		getByText(SELECTED_ELEMENTS[0].elementTemplateJSON.title['en_US']);
	});

	it('displays the description', () => {
		const {getByText} = renderElement();

		getByText(
			SELECTED_ELEMENTS[0].elementTemplateJSON.description['en_US']
		);
	});

	it('can collapse the query elements', () => {
		const {container, getByLabelText} = renderElement();

		fireEvent.click(getByLabelText('collapse'));

		expect(container.querySelector('.configuration-form-list')).toBeNull();
	});

	it('calls updateJson when typing in one of the form inputs', async () => {
		const {getByLabelText} = renderElement();

		getByLabelText('Boost');

		fireEvent.change(getByLabelText('Boost'), {
			target: {value: '2'},
		});

		waitForElement(() => expect(onUpdateElement).toHaveBeenCalled());
	});

	it('calls onDeleteElement when clicking on remove from dropdown', () => {
		const {getByLabelText, getByText} = renderElement();

		fireEvent.click(getByLabelText('dropdown'));

		fireEvent.click(getByText('remove'));

		expect(onDeleteElement).toHaveBeenCalled();
	});
});
