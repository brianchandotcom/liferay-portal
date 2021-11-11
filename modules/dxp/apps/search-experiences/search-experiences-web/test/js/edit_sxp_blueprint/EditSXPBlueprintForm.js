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

import {fireEvent, render, within} from '@testing-library/react';
import React from 'react';

import EditSXPBlueprintForm from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/edit_sxp_blueprint/EditSXPBlueprintForm';
const Toasts = require('../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/toasts');
import {getSXPElementOutput} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/utils';
import {
	ENTITY_JSON,
	INITIAL_CONFIGURATION,
	SELECTED_SXP_ELEMENTS,
} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

// Prevents "TypeError: Liferay.component is not a function" error on openToast

Toasts.openSuccessToast = jest.fn();

// Suppress act warning until @testing-library/react is updated past 9
// to use screen. See https://javascript.plainenglish.io/
// you-probably-dont-need-act-in-your-react-tests-2a0bcd2ad65c

const originalError = console.error;

beforeAll(() => {
	console.error = (...args) => {
		if (/Warning.*not wrapped in act/.test(args[0])) {
			return;
		}
		originalError.call(console, ...args);
	};
});

afterAll(() => {
	console.error = originalError;
});

Liferay.ThemeDisplay = {getDefaultLanguageId: () => 'en_US'};

function renderEditSXPBlueprintForm(props) {
	return render(
		<EditSXPBlueprintForm
			entityJSON={ENTITY_JSON}
			initialConfiguration={INITIAL_CONFIGURATION}
			initialDescription={{}}
			initialSXPElementInstances={{
				queryConfiguration: {queryEntries: []},
			}}
			initialTitle={{
				en_US: 'Test Title',
			}}
			sxpBlueprintId="0"
			{...props}
		/>
	);
}

describe('EditSXPBlueprintForm', () => {
	global.URL.createObjectURL = jest.fn();

	it('renders the configuration set form', () => {
		const {container} = renderEditSXPBlueprintForm();

		expect(container).not.toBeNull();
	});

	it('renders the query elements', async () => {
		const {container, findByText} = renderEditSXPBlueprintForm({
			initialConfiguration: {
				...INITIAL_CONFIGURATION,
				queryConfiguration: {
					applyIndexerClauses: true,
					queryEntries: SELECTED_SXP_ELEMENTS.map(
						getSXPElementOutput
					),
				},
			},
			initialSXPElementInstances: {
				queryConfiguration: {queryEntries: SELECTED_SXP_ELEMENTS},
			},
		});

		await findByText('query-settings');

		const {getByText} = within(container.querySelector('.builder'));

		SELECTED_SXP_ELEMENTS.map((sxpElement) =>
			getByText(sxpElement.sxpElementTemplateJSON.title_i18n['en_US'])
		);
	});

	it('adds additional query element from sidebar', async () => {
		const {
			container,
			findByText,
			queryAllByLabelText,
		} = renderEditSXPBlueprintForm();

		await findByText('query-settings');

		const sxpElementCountBefore = container.querySelectorAll('.sxp-element')
			.length;

		fireEvent.click(container.querySelectorAll('.panel-header')[0]);

		fireEvent.mouseOver(queryAllByLabelText('add')[0]);

		fireEvent.click(queryAllByLabelText('add')[0]);

		const sxpElementCountAfter = container.querySelectorAll('.sxp-element')
			.length;

		expect(sxpElementCountAfter).toBe(sxpElementCountBefore + 1);
	});

	it('enables removal of additional query elements', async () => {
		const {
			container,
			findByText,
			getAllByLabelText,
			getAllByText,
		} = renderEditSXPBlueprintForm({
			initialConfiguration: {
				...INITIAL_CONFIGURATION,
				queryConfiguration: {
					applyIndexerClauses: true,
					queryEntries: SELECTED_SXP_ELEMENTS.map(
						getSXPElementOutput
					),
				},
			},
			initialSXPElementInstances: {
				queryConfiguration: {queryEntries: SELECTED_SXP_ELEMENTS},
			},
		});

		await findByText('query-settings');

		const sxpElementCountBefore = container.querySelectorAll('.sxp-element')
			.length;

		fireEvent.click(getAllByLabelText('dropdown')[0]);

		fireEvent.click(getAllByText('remove')[0]);

		const sxpElementCountAfter = container.querySelectorAll('.sxp-element')
			.length;

		expect(sxpElementCountAfter).toBe(sxpElementCountBefore - 1);
	});
});
