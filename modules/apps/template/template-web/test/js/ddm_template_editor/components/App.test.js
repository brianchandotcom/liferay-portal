/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import App from '../../../../src/main/resources/META-INF/resources/js/ddm_template_editor/components/App';

const renderApp = ({initialScript = ''} = {}) => {
	return render(
		<App
			editorAutocompleteData={{}}
			portletNamespace="portletNamespace"
			script={initialScript}
			showCacheableWarning
			templateVariableGroups={[
				{
					items: [
						{
							content: 'this is a variable 1',
							label: 'variableTemplate1',
							repetable: true,
							tooltip: 'this is a tooltip 1',
						},
						{
							content: 'this is a variable 2',
							label: 'variableTemplate2',
							repetable: true,
							tooltip: 'this is a tooltip 2',
						},
					],
					label: 'group',
				},
			]}
		/>
	);
};

describe('', () => {
	beforeEach(() => {
		cleanup();

		if (global.document) {
			global.document.body.createTextRange = () => ({
				commonAncestorContainer: {
					nodeName: 'BODY',
					ownerDocument: document,
				},
				getBoundingClientRect: () => {},
				getClientRects: () => ({length: 0}),
				setEnd: () => {},
				setStart: () => {},
			});

			const saveButton = global.document.createElement('button');
			saveButton.classList.add('save-button');

			const saveAndContinueButton = global.document.createElement(
				'button'
			);
			saveAndContinueButton.classList.add('save-and-continue-button');

			global.document.body.appendChild(saveButton);
			global.document.body.appendChild(saveAndContinueButton);
		}
		global.Liferay.SideNavigation = {instance: () => {}};
		global.Liferay.on = () => ({
			detach: () => {},
		});
	});

	it('renders', () => {
		const {getByText} = renderApp({
			initialScript: 'this is the initial script',
		});

		expect(getByText('this is the initial script')).toBeInTheDocument();
	});

	it('includes the variable in the script when clicked', () => {
		const {getByText} = renderApp();

		const variableButton = getByText('variableTemplate1');

		userEvent.click(variableButton);

		expect(getByText('this is a variable 1')).toBeInTheDocument();
	});

	it('shows a popover with the tooltip when the preview icon is hovered', () => {
		const {getByText} = renderApp();

		const variableButton = getByText('variableTemplate1');

		fireEvent.mouseEnter(variableButton.querySelector('.preview-icon'));

		expect(getByText('this is a tooltip 1')).toBeInTheDocument();
	});

	it('filters variable groups when search', () => {
		const {getByLabelText, queryByText} = renderApp();

		const searchInput = getByLabelText('search');

		userEvent.type(searchInput, 'variableTemplate2');

		expect(queryByText('variableTemplate2')).toBeInTheDocument();
		expect(queryByText('variableTemplate1')).not.toBeInTheDocument();
	});

	it('no result when searching', () => {
		const {getByLabelText, queryByText} = renderApp();

		const searchInput = getByLabelText('search');

		userEvent.type(searchInput, 'anotherVariable');

		expect(queryByText('no-results-found')).toBeInTheDocument();
	});
});
