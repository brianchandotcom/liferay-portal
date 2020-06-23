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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import MappingInputs from '../../../../src/main/resources/META-INF/resources/js/seo/display_page_templates/components/MappingInputs';

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
			fieldType: 'text',
			helpMessage: 'help message for input title',
			label: 'Title',
			name: 'title',
			selectedFieldKey: 'field-5',
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
	render(<MappingInputs {...baseProps} {...props} />);

describe('MappingInputs', () => {
	afterEach(cleanup);

	describe('when rendered', () => {
		let result;
		let feedbackTitleInput;
		let feedbackImageInput;
		let hiddenTitleInput;
		let hiddenImageInput;

		beforeEach(() => {
			result = renderComponent();
			feedbackTitleInput = result.getByLabelText(
				baseProps.inputs[0].label
			);
			feedbackImageInput = result.getByLabelText(
				baseProps.inputs[1].label
			);

			hiddenTitleInput = result.getAllByRole('textbox')[1];
			hiddenImageInput = result.getAllByRole('textbox')[3];
		});

		it('has two read only inputs for user feedback with the selected field names', () => {
			expect(feedbackTitleInput).toBeInTheDocument();
			expect(feedbackImageInput).toBeInTheDocument();
		});

		it('renders the title inputs for user feedback with the correct value', () => {
			expect(feedbackTitleInput.value).toBe('Label source type: Field 5');
		});

		it('renders the image input for user feedback with unmapped value', () => {
			expect(feedbackImageInput.value).toBe('-- unmapped --');
		});

		it('has a title hidden input with the correct selected field key', () => {
			expect(hiddenTitleInput.type).toBe('hidden');
			expect(hiddenTitleInput.name).toBe(baseProps.inputs[0].name);
			expect(hiddenTitleInput.value).toBe(
				baseProps.inputs[0].selectedFieldKey
			);
		});

		it('has an image hidden input without value', () => {
			expect(hiddenImageInput.type).toBe('hidden');
			expect(hiddenImageInput.name).toBe(baseProps.inputs[1].name);
			expect(hiddenImageInput.value).toBe('');
		});
	});
});
