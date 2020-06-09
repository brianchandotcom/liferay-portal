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

import MappingField from '../../../../src/main/resources/META-INF/resources/js/seo/display_page_templates/components/MappingField';

const baseProps = {
	fields: [
		{key: 'field-1', label: 'Field 1'},
		{key: 'field-2', label: 'Field 2'},
	],
	label: 'Label test mapping field',
	name: 'testMappingField',
	selectedField: {
		key: 'field-2',
		label: 'Field 2',
	},
	selectedSource: {
		classTypeLabel: 'Label source type',
	},
};

const renderComponent = (props) =>
	render(<MappingField {...baseProps} {...props} />);

describe('MappingField', () => {
	afterEach(cleanup);

	describe('when rendered with the default props', () => {
		let inputValue;
		let mappingButton;
		let result;
		let mappingPanel;
		let inputFeedback;

		beforeEach(() => {
			result = renderComponent();
			inputFeedback = result.getAllByRole('textbox')[0];
			inputValue = result.getByDisplayValue(baseProps.selectedField.key);
			mappingButton = result.getByTitle('map');
			mappingPanel = result.baseElement.querySelector(
				'.dpt-mapping-panel'
			);
		});

		it('has an input hidden with the selected field key', () => {
			expect(inputValue.type).toBe('hidden');
		});

		it('the input has the passed name', () => {
			expect(inputValue.name).toBe('testMappingField');
		});

		it('has a mapping button', () => {
			expect(mappingButton).toBeInTheDocument();
		});

		it('has the field for user feedback', () => {
			expect(inputFeedback).toBeInTheDocument();
		});

		it('has not render a panel', () => {
			expect(mappingPanel).not.toBeInTheDocument();
		});

		describe('then the user show the panel', () => {
			let fieldSelect;

			beforeEach(() => {
				fireEvent.click(mappingButton);
				mappingPanel = result.baseElement.querySelector(
					'.dpt-mapping-panel'
				);
				fieldSelect = result.getByLabelText('field');
			});

			it('has render a panel', () => {
				expect(mappingPanel).toBeInTheDocument();
			});

			it('the field select has selectedField value', () => {
				expect(fieldSelect.value).toBe(baseProps.selectedField.key);
			});

			it('the field for user feedback has selected value', () => {
				expect(inputFeedback.value).toBe('Label source type: Field 2');
			});

			describe('then the user change the fields select', () => {
				beforeEach(() => {
					fireEvent.change(fieldSelect, {
						target: {value: baseProps.fields[0].key},
					});
				});

				it('the fieldMapping update the value', () => {
					expect(inputValue.value).toBe(baseProps.fields[0].key);
				});

				it('the field for user feedback update the value', () => {
					expect(inputFeedback.value).toBe(
						'Label source type: Field 1'
					);
				});

				describe('click in the mapping button', () => {
					it('hide the panel', () => {
						fireEvent.click(mappingButton);

						expect(mappingPanel).not.toBeInTheDocument();
					});
				});

				describe('click outside', () => {
					it('hide the panel', () => {
						fireEvent.mouseDown(document);

						expect(mappingPanel).not.toBeInTheDocument();
					});
				});
			});
		});
	});
});
