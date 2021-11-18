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

import JSONSXPElement from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/JSONSXPElement';
import {QUERY_SXP_ELEMENTS} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

const onDeleteSXPElement = jest.fn();
const setFieldTouched = jest.fn();
const setFieldValue = jest.fn();

function renderJSONSXPElement(props) {
	return render(
		<JSONSXPElement
			collapseAll={false}
			id={0}
			onDeleteSXPElement={onDeleteSXPElement}
			setFieldTouched={setFieldTouched}
			setFieldValue={setFieldValue}
			sxpElement={QUERY_SXP_ELEMENTS[0]}
			{...props}
		/>
	);
}

describe('JSONSXPElement', () => {
	it('renders the element', () => {
		const {container} = renderJSONSXPElement();

		expect(container).not.toBeNull();
	});

	it('displays the title', () => {
		const {getByText} = renderJSONSXPElement();

		getByText(QUERY_SXP_ELEMENTS[0].title_i18n['en_US']);
	});

	it('displays the description', () => {
		const {getByText} = renderJSONSXPElement();

		getByText(QUERY_SXP_ELEMENTS[0].description_i18n['en_US']);
	});

	it('can collapse the query elements', () => {
		const {container, getByLabelText} = renderJSONSXPElement();

		fireEvent.click(getByLabelText('collapse'));

		expect(container.querySelector('.configuration-editor')).toBeNull();
	});

	it('calls onDeleteSXPElement when clicking on delete from dropdown', () => {
		const {getByLabelText, getByText} = renderJSONSXPElement();

		fireEvent.click(getByLabelText('dropdown'));

		fireEvent.click(getByText('remove'));

		expect(onDeleteSXPElement).toHaveBeenCalled();
	});
});
