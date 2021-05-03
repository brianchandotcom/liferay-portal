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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import JSONElement from '../../../src/main/resources/META-INF/resources/js/shared/JSONElement';
import {SELECTED_ELEMENTS} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

const onDeleteElement = jest.fn();
const setFieldTouched = jest.fn();
const setFieldValue = jest.fn();

function renderElement(props) {
	return render(
		<JSONElement
			collapseAll={false}
			description={SELECTED_ELEMENTS[0].elementTemplateJSON.description}
			elementTemplateJSON={SELECTED_ELEMENTS[0].elementTemplateJSON}
			id={SELECTED_ELEMENTS[0].elementTemplateJSON.id}
			onDeleteElement={onDeleteElement}
			setFieldTouched={setFieldTouched}
			setFieldValue={setFieldValue}
			title={SELECTED_ELEMENTS[0].elementTemplateJSON.title}
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

		expect(container.querySelector('.configuration-editor')).toBeNull();
	});

	it('calls onDeleteElement when clicking on delete from dropdown', () => {
		const {getByLabelText, getByText} = renderElement();

		fireEvent.click(getByLabelText('dropdown'));

		fireEvent.click(getByText('remove'));

		expect(onDeleteElement).toHaveBeenCalled();
	});
});
