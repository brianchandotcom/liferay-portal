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

import QueryBuilder from '../../../../src/main/resources/META-INF/resources/js/edit_blueprint/tabs/QueryBuilder';
import {SEARCHABLE_ASSET_TYPES, SELECTED_ELEMENTS} from '../../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

const onUpdateElement = jest.fn();

function renderBuilder(props) {
	return render(
		<QueryBuilder
			frameworkConfig={{
				apply_indexer_clauses: false,
				searchable_asset_types: SEARCHABLE_ASSET_TYPES,
			}}
			initialSelectedElements={SELECTED_ELEMENTS}
			onDeleteElement={jest.fn()}
			onFrameworkConfigChange={jest.fn()}
			onUpdateElement={onUpdateElement}
			searchableAssetTypes={SEARCHABLE_ASSET_TYPES}
			selectedElements={SELECTED_ELEMENTS}
			{...props}
		/>
	);
}

describe('QueryBuilder', () => {
	it('renders the builder', () => {
		const {container} = renderBuilder();

		expect(container).not.toBeNull();
	});

	it('renders the titles for the selected query elements', () => {
		const {getByText} = renderBuilder();

		SELECTED_ELEMENTS.map((element) =>
			getByText(element.elementTemplateJSON.title['en_US'])
		);
	});

	it('renders the description for the selected query elements', () => {
		const {getByText} = renderBuilder();

		SELECTED_ELEMENTS.map((element) =>
			getByText(element.elementTemplateJSON.description['en_US'])
		);
	});

	it('can collapse all the query elements', () => {
		const {container, getByText} = renderBuilder();

		fireEvent.click(getByText('collapse-all'));

		expect(
			container.querySelectorAll('.configuration-form-list').length
		).toBe(0);
	});

	it('can expand all the query elements', () => {
		const {container, getByText} = renderBuilder();

		fireEvent.click(getByText('collapse-all'));

		fireEvent.click(getByText('expand-all'));

		expect(
			container.querySelectorAll('.configuration-form-list').length
		).toBe(29);
	});
});
