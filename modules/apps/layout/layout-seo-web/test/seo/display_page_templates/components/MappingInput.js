/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, getByRole, render} from '@testing-library/react';
import React from 'react';

import MappingInput from '../../../../src/main/resources/META-INF/resources/js/seo/display_page_templates/components/MappingInput';

const baseProps = {
	fieldTypes: ['text'],
	fields: [
		{key: 'field-1', label: 'Field 1', type: 'text'},
		{key: 'field-2', label: 'Field 2', type: 'text'},
		{
			key: 'field-3',
			label:
				'Field 3: with }right curly brackets}}}}, line breaks by \r\nwin\r\n\r\n\r\n, \nlinux\n and \rold mac\r',
			type: 'image',
		},
		{key: 'field-4', label: 'Field 4', type: 'text'},
		{key: 'field-5', label: 'Field 5', type: 'text'},
	],
	helpMessage: 'Map a text field, it will be used as Title.',
	label: 'Label test mapping field',
	name: 'testMappingInput',
	selectedSource: {
		classTypeLabel: 'Label source type',
	},
	value: '${field-2}',
};

const renderComponent = (props) =>
	render(<MappingInput {...baseProps} {...props} />);

describe('MappingInput', () => {
	afterEach(cleanup);

	describe('when rendered', () => {
		let inputValue;
		let mappingButton;
		let result;
		let mappingPanel;
		let mappingPanelButton;

		beforeEach(() => {
			result = renderComponent();

			inputValue = result.getByDisplayValue(baseProps.value);
			mappingButton = result.getByTitle('map');
			mappingPanel = result.baseElement.querySelector(
				'.dpt-mapping-panel'
			);
		});

		it('has an input with the initial value', () => {
			expect(inputValue.type).toBe('text');
			expect(inputValue.name).toBe('testMappingInput');
			expect(inputValue.value).toBe(baseProps.value);
		});

		it('has a mapping button', () => {
			expect(mappingButton).toBeInTheDocument();
		});

		it('does not have the mapping panel', () => {
			expect(mappingPanel).not.toBeInTheDocument();
		});

		it('has a help message', () => {
			expect(result.getByText(baseProps.helpMessage)).toBeInTheDocument();
		});

		describe('when the user clicks the mapping button', () => {
			let fieldSelect;

			beforeEach(() => {
				fireEvent.click(mappingButton);

				mappingPanel = result.baseElement.querySelector(
					'.dpt-mapping-panel'
				);
				fieldSelect = result.getByLabelText('field');
				mappingPanelButton = getByRole(mappingPanel, 'button');
			});

			it('opens the mapping panel', () => {
				expect(mappingPanel).toBeInTheDocument();
			});

			describe('and the user selects another field', () => {
				it('adds the new field ${key:label} to the input', () => {
					fireEvent.change(fieldSelect, {
						target: {value: baseProps.fields[0].key},
					});
					fireEvent.click(mappingPanelButton);

					expect(inputValue.value).toBe(
						`$\{${baseProps.fields[0].key}:${baseProps.fields[0].label}} ${baseProps.value}`
					);
				});

				it('adds the new field ${key:label} sanitized to the input', () => {
					fireEvent.change(fieldSelect, {
						target: {value: baseProps.fields[2].key},
					});
					fireEvent.click(mappingPanelButton);

					const sanitizedLabel =
						'Field 3: with right curly brackets, line breaks by win, linux and old mac';

					expect(inputValue.value).toBe(
						`$\{${baseProps.fields[2].key}:${sanitizedLabel}} ${baseProps.value}`
					);
				});
			});
		});
	});

	describe('when rendered without initial value', () => {
		it('has a input with empty value', () => {
			const {getByRole} = renderComponent({
				...baseProps,
				value: undefined,
			});

			const inputValue = getByRole('textbox');

			expect(inputValue.type).toBe('text');
			expect(inputValue.name).toBe('testMappingInput');
			expect(inputValue.value).toBe('');
		});
	});
});
