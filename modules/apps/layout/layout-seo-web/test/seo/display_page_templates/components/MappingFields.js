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

import MappingFields from '../../../../src/main/resources/META-INF/resources/js/seo/display_page_templates/components/MappingFields';

const baseProps = {
	fields: [
		{key: 'field-1', label: 'Field 1', type: 'text'},
		{key: 'field-2', label: 'Field 2', type: 'text'},
		{key: 'field-3', label: 'Field 3', type: 'image'},
		{key: 'field-4', label: 'Field 4', type: 'text'},
		{key: 'field-5', label: 'Field 5', type: 'text'},
	],
	inputs: [
		{
			fieldType: 'image',
			helpMessage: 'help message for input image with default value',
			label: 'Image with default value',
			name: 'image_small',
			selectedFieldKey: 'field-3',
		},
		{
			fieldType: 'image',
			helpMessage: 'help message for input image',
			label: 'Image for social share',
			name: 'image',
		},
	],
	selectedSource: {
		classTypeLabel: 'Label source type',
	},
};

const renderComponent = (props) =>
	render(<MappingFields {...baseProps} {...props} />);

describe('MappingFields', () => {
	afterEach(cleanup);

	describe('when rendered', () => {
		let feedbackImageInput;
		let feedbackImageWithDefaultValueInput;
		let hiddenImageInput;
		let hiddenImageWithDefaulInput;
		let result;

		beforeEach(() => {
			result = renderComponent();
			feedbackImageWithDefaultValueInput = result.getByLabelText(
				baseProps.inputs[0].label
			);
			feedbackImageInput = result.getByLabelText(
				baseProps.inputs[1].label
			);

			hiddenImageWithDefaulInput = result.getAllByRole('textbox')[1];
			hiddenImageInput = result.getAllByRole('textbox')[3];
		});

		it('has two read only inputs for user feedback with the selected field names', () => {
			expect(feedbackImageWithDefaultValueInput).toBeInTheDocument();
			expect(feedbackImageInput).toBeInTheDocument();
		});

		it('renders the image with default value input for user feedback with the correct value', () => {
			expect(feedbackImageWithDefaultValueInput.value).toBe(
				'Label source type: Field 3'
			);
		});

		it('renders the image input for user feedback with unmapped value', () => {
			expect(feedbackImageInput.value).toBe('-- unmapped --');
		});

		it('has an image with default value hidden input with the correct selected field key', () => {
			expect(hiddenImageWithDefaulInput.type).toBe('hidden');
			expect(hiddenImageWithDefaulInput.name).toBe(
				baseProps.inputs[0].name
			);
			expect(hiddenImageWithDefaulInput.value).toBe(
				baseProps.inputs[0].selectedFieldKey
			);
		});

		it('has an image hidden input without value', () => {
			expect(hiddenImageInput.type).toBe('hidden');
			expect(hiddenImageInput.name).toBe(baseProps.inputs[1].name);
			expect(hiddenImageInput.value).toBe('unmapped');
		});
	});

	describe('when rendered with filtered fieldType image', () => {
		let fieldSelect;
		let options;
		let result;

		beforeEach(() => {
			result = renderComponent();

			fireEvent.click(result.getAllByTitle('map')[1]);

			fieldSelect = result.getByLabelText('field');
			options = fieldSelect.querySelectorAll('option');
		});

		it('only has two filtered options', () => {
			expect(options.length).toBe(2);
		});

		it('has the image field in the second position', () => {
			expect(options[1].value).toBe(baseProps.fields[2].key);
		});
	});
});
