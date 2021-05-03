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

import AddElementSidebar from '../../../src/main/resources/META-INF/resources/js/edit_blueprint/AddElementSidebar';
import {SELECTED_ELEMENTS} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

const DEFAULT_EXPANDED_LIST = ['match'];

function renderAddElementSidebar(props) {
	return render(
		<AddElementSidebar
			elements={SELECTED_ELEMENTS}
			onAddElement={jest.fn()}
			{...props}
		/>
	);
}

describe('AddElementSidebar', () => {
	it('renders the sidebar', () => {
		const {container} = renderAddElementSidebar();

		expect(container).not.toBeNull();
	});

	it('renders the titles for the possible query elements', () => {
		const {getByText} = renderAddElementSidebar();

		SELECTED_ELEMENTS.map((element) => {
			if (
				DEFAULT_EXPANDED_LIST.includes(
					element.elementTemplateJSON.category
				)
			) {
				getByText(element.elementTemplateJSON.title['en_US']);
			}
		});
	});

	it('renders the descriptions for the possible query elements', () => {
		const {getByText} = renderAddElementSidebar();

		SELECTED_ELEMENTS.map((element) => {
			if (
				DEFAULT_EXPANDED_LIST.includes(
					element.elementTemplateJSON.category
				)
			) {
				getByText(element.elementTemplateJSON.description['en_US']);
			}
		});
	});

	it('displays the add button when hovering over an element item', () => {
		const {container, queryAllByText} = renderAddElementSidebar();

		fireEvent.mouseOver(container.querySelectorAll('.list-group-title')[1]);

		expect(queryAllByText('add')[1]).toBeVisible();
	});
});
