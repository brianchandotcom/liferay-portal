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
		label: 'Field label',
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

		beforeEach(() => {
			result = renderComponent();
			inputValue = result.getByDisplayValue('field-2');
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

		it('has not render a panel', () => {
			expect(mappingPanel).not.toBeInTheDocument();
		});

		describe('then the user expand the panel', () => {
			beforeEach(() => {
				fireEvent.click(mappingButton);
				mappingPanel = result.baseElement.querySelector(
					'.dpt-mapping-panel'
				);
			});

			it('has render a panel', () => {
				expect(mappingPanel).toBeInTheDocument();
			});
		});
	});
});
