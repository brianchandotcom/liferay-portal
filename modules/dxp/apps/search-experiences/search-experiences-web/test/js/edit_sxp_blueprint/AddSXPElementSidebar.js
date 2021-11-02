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

import AddSXPElementSidebar from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/edit_sxp_blueprint/AddSXPElementSidebar';
import {SELECTED_SXP_ELEMENTS} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

const DEFAULT_EXPANDED_LIST = ['match'];

function renderAddSXPElementSidebar(props) {
	return render(
		<AddSXPElementSidebar
			onAddSXPElement={jest.fn()}
			sxpElements={SELECTED_SXP_ELEMENTS}
			{...props}
		/>
	);
}

describe('AddSXPElementSidebar', () => {
	it('renders the sidebar', () => {
		const {container} = renderAddSXPElementSidebar();

		expect(container).not.toBeNull();
	});

	it('renders the titles for the possible query elements', () => {
		const {getByText} = renderAddSXPElementSidebar();

		SELECTED_SXP_ELEMENTS.map((sxpElement) => {
			if (
				DEFAULT_EXPANDED_LIST.includes(
					sxpElement.sxpElementTemplateJSON.category
				)
			) {
				getByText(sxpElement.sxpElementTemplateJSON.title['en_US']);
			}
		});
	});

	it('renders the descriptions for the possible query elements', () => {
		const {getByText} = renderAddSXPElementSidebar();

		SELECTED_SXP_ELEMENTS.map((sxpElement) => {
			if (
				DEFAULT_EXPANDED_LIST.includes(
					sxpElement.sxpElementTemplateJSON.category
				)
			) {
				getByText(
					sxpElement.sxpElementTemplateJSON.description['en_US']
				);
			}
		});
	});

	it('displays the add button when hovering over an element item', () => {
		const {container, queryAllByText} = renderAddSXPElementSidebar();

		fireEvent.mouseOver(container.querySelectorAll('.list-group-title')[0]);

		expect(queryAllByText('add')[0]).toBeVisible();
	});
});
