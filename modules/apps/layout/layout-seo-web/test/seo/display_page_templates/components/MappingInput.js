/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, getByRole, render} from '@testing-library/react';
import React from 'react';

import MappingInput from '../../../../src/main/resources/META-INF/resources/js/seo/display_page_templates/components/MappingInput';

const baseProps = {
	fieldType: 'text',
	fields: [
		{key: 'field-1', label: 'Field 1', type: 'text'},
		{key: 'field-2', label: 'Field 2', type: 'text'},
		{key: 'field-3', label: 'Field 3', type: 'image'},
		{key: 'field-4', label: 'Field 4', type: 'text'},
		{key: 'field-5', label: 'Field 5', type: 'text'},
	],
	helpMessage: 'Map a text field, it will be used as Title.',
	label: 'Label test mapping field',
	name: 'testMappingInput',
	selectedSource: {
		classTypeLabel: 'Label source type',
	},
	value: 'field-2',
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
				beforeEach(() => {
					fireEvent.change(fieldSelect, {
						target: {value: baseProps.fields[0].key},
					});
					fireEvent.click(mappingPanelButton);
				});

				it('adds the new field key to the input', () => {
					expect(inputValue.value).toBe(
						` $\{${baseProps.fields[0].key}} ${baseProps.value}`
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
