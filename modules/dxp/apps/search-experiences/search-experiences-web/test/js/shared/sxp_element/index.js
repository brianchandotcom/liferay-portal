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

import SXPElement from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/sxp_element/index';
import {getUIConfigurationValues} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/utils';
import {INDEX_FIELDS, QUERY_SXP_ELEMENTS} from '../../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

const onDeleteSXPElement = jest.fn();

function renderSXPElement(props) {
	return render(
		<SXPElement
			collapseAll={false}
			indexFields={INDEX_FIELDS}
			onDeleteSXPElement={onDeleteSXPElement}
			sxpElement={QUERY_SXP_ELEMENTS[0]}
			uiConfigurationValues={getUIConfigurationValues(
				QUERY_SXP_ELEMENTS[0]
			)}
			{...props}
		/>
	);
}

describe('SXPElement', () => {
	global.URL.createObjectURL = jest.fn();

	it('renders the element', () => {
		const {container} = renderSXPElement();

		expect(container).not.toBeNull();
	});

	it('displays the title', () => {
		const {getByText} = renderSXPElement();

		getByText(QUERY_SXP_ELEMENTS[0].title_i18n['en_US']);
	});

	it('displays the description', () => {
		const {getByText} = renderSXPElement();

		getByText(QUERY_SXP_ELEMENTS[0].description_i18n['en_US']);
	});

	it('can collapse the query elements', () => {
		const {container, getByLabelText} = renderSXPElement();

		fireEvent.click(getByLabelText('collapse'));

		expect(container.querySelector('.configuration-form-list')).toBeNull();
	});

	it('calls onDeleteElement when clicking on remove from dropdown', () => {
		const {getByLabelText, getByText} = renderSXPElement();

		fireEvent.click(getByLabelText('dropdown'));

		fireEvent.click(getByText('remove'));

		expect(onDeleteSXPElement).toHaveBeenCalled();
	});
});
