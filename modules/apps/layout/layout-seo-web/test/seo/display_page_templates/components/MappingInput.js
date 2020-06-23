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
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import MappingInput from '../../../../src/main/resources/META-INF/resources/js/seo/display_page_templates/components/MappingInput';

const baseProps = {
	fieldType: 'text',
	helpMessage: 'Map a text field, it will be used as Title.',
	initialFields: [
		{key: 'field-1', label: 'Field 1', type: 'text'},
		{key: 'field-2', label: 'Field 2', type: 'text'},
		{key: 'field-3', label: 'Field 3', type: 'image'},
		{key: 'field-4', label: 'Field 4', type: 'text'},
		{key: 'field-5', label: 'Field 5', type: 'text'},
	],
	label: 'Label test mapping field',
	name: 'testMappingInput',
	selectedFieldKey: 'field-2',
	selectedSource: {
		classTypeLabel: 'Label source type',
	},
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
		let inputFeedback;

		beforeEach(() => {
			result = renderComponent();
			inputFeedback = result.getAllByRole('textbox')[0];
			inputValue = result.getByDisplayValue(baseProps.selectedFieldKey);
			mappingButton = result.getByTitle('map');
			mappingPanel = result.baseElement.querySelector(
				'.dpt-mapping-panel'
			);
		});

		it('has a hidden input with the selected field key', () => {
			expect(inputValue.type).toBe('hidden');
			expect(inputValue.name).toBe('testMappingInput');
		});

		it('has a read only input for user feedback with the selected field name', () => {
			expect(inputFeedback).toBeInTheDocument();
			expect(inputFeedback.readOnly).toBeTruthy();
			expect(inputFeedback.value).toBe('Label source type: Field 2');
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
			});

			it('opens the mapping panel', () => {
				expect(mappingPanel).toBeInTheDocument();
			});

			it('shows the selected field', () => {
				expect(fieldSelect.value).toBe(baseProps.selectedFieldKey);
			});

			describe('and the user selects another field', () => {
				beforeEach(() => {
					fireEvent.change(fieldSelect, {
						target: {value: baseProps.initialFields[0].key},
					});
				});

				it('sets the new field key in the hidden input', () => {
					expect(inputValue.value).toBe(
						baseProps.initialFields[0].key
					);
				});

				it('sets the new field name in the user feedback input', () => {
					expect(inputFeedback.value).toBe(
						'Label source type: Field 1'
					);
				});
			});

			describe('and the user clicks again in the mapping button', () => {
				beforeEach(() => {
					fireEvent.click(mappingButton);
				});

				it('closes the mapping panel', () => {
					expect(mappingPanel).not.toBeInTheDocument();
				});
			});

			describe('and the user clicks outside the panel', () => {
				beforeEach(() => {
					fireEvent.mouseDown(document);
				});

				it('closes the mapping panel', () => {
					expect(mappingPanel).not.toBeInTheDocument();
				});
			});

			describe('and the user unmap the field', () => {
				beforeEach(() => {
					fireEvent.change(fieldSelect, {
						target: {value: ''},
					});
				});

				it('sets the new field key in the hidden input', () => {
					expect(inputValue.value).toBe('');
				});

				it('sets the new field name in the user feedback input', () => {
					expect(inputFeedback.value).toBe('-- unmapped --');
				});
			});
		});
	});

	describe('when rendered without selected key', () => {
		let inputValue;
		let result;
		let inputFeedback;

		beforeEach(() => {
			result = renderComponent({
				...baseProps,
				selectedFieldKey: undefined,
			});
			inputFeedback = result.getAllByRole('textbox')[0];
			inputValue = result.getAllByRole('textbox')[1];
		});

		it('has a hidden input with unmapped key', () => {
			expect(inputValue.type).toBe('hidden');
			expect(inputValue.name).toBe('testMappingInput');
			expect(inputValue.value).toBe('');
		});

		it('has a read only input for user feedback with the selected field name', () => {
			expect(inputFeedback).toBeInTheDocument();
			expect(inputFeedback.readOnly).toBeTruthy();
			expect(inputFeedback.value).toBe('-- unmapped --');
		});
	});

	describe('when rendered filtred with fieldType image', () => {
		let result;
		let fieldSelect;
		let options;

		beforeEach(() => {
			result = renderComponent({
				...baseProps,
				fieldType: 'image',
				selectedFieldKey: undefined,
			});

			fireEvent.click(result.getByTitle('map'));

			fieldSelect = result.getByLabelText('field');
			options = fieldSelect.querySelectorAll('option');
		});

		it('only has two filtered options', () => {
			expect(options.length).toBe(2);
		});

		it('has the first option unmapped', () => {
			expect(options[0].value).toBe('');
			expect(fieldSelect.value).toBe('');
		});

		it('has the selected field unmapped', () => {
			expect(options[0].value).toBe('');
			expect(fieldSelect.value).toBe('');
		});

		it('has the image field in the second position', () => {
			expect(options[1].value).toBe(baseProps.initialFields[2].key);
		});
	});
});
