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

import QueryBuilder from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/edit_sxp_blueprint/query_builder_tab/index';
import {QUERY_SXP_ELEMENTS} from '../../mocks/data';

import '@testing-library/jest-dom/extend-expect';

import {getUIConfigurationValues} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/utils';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

function renderBuilder(props) {
	return render(
		<QueryBuilder
			elementInstances={QUERY_SXP_ELEMENTS.map((sxpElement, index) => ({
				id: index,
				sxpElement,
				uiConfigurationValues: getUIConfigurationValues(sxpElement),
			}))}
			onDeleteSXPElement={jest.fn()}
			onFrameworkConfigChange={jest.fn()}
			setFieldValue={jest.fn()}
			{...props}
		/>
	);
}

describe('QueryBuilder', () => {
	global.URL.createObjectURL = jest.fn();

	it('renders the builder', () => {
		const {container} = renderBuilder();

		expect(container).not.toBeNull();
	});

	it('renders the title for the selected query element', () => {
		const {getByText} = renderBuilder();

		QUERY_SXP_ELEMENTS.map((sxpElement) =>
			getByText(sxpElement.title_i18n['en_US'])
		);
	});

	it('renders the description for the selected query element', () => {
		const {getByText} = renderBuilder();

		QUERY_SXP_ELEMENTS.map((sxpElement) =>
			getByText(sxpElement.description_i18n['en_US'])
		);
	});

	it('can collapse all the query elements', () => {
		const {container, getByText} = renderBuilder();

		fireEvent.click(getByText('collapse-all'));

		expect(
			container.querySelectorAll('.configuration-form-list').length
		).toBe(0);
	});
});
