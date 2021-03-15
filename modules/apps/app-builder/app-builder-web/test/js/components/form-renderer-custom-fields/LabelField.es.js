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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import LabelField from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/LabelField.es';
import {getFormattedState} from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/shared/utils.es';
import {FORM_VIEW} from '../../constants.es';

const DEFAULT_STATE = {
	editingLanguageId: 'en_US',
	focusedCustomObjectField: {},
	focusedField: {
		fieldName: 'TextName',
	},
};

const DATA_DEFINITION_FIELD = (label) => ({
	label: {
		en_US: label,
	},
	name: `Text${label}`,
});

const STRUCTURE_LEVEL_STATE = {
	...DEFAULT_STATE,
	dataDefinition: {
		dataDefinitionFields: [
			{
				...DATA_DEFINITION_FIELD('Name'),
				customProperties: {
					labelAtStructureLevel: true,
				},
			},
			{
				...DATA_DEFINITION_FIELD('Email'),
				customProperties: {
					labelAtStructureLevel: true,
				},
			},
		],
	},
	dataLayout: {
		dataLayoutFields: {},
	},
};

const VIEW_LEVEL_STATE = {
	...DEFAULT_STATE,
	dataDefinition: {
		dataDefinitionFields: [
			{
				...DATA_DEFINITION_FIELD('Name'),
				customProperties: {
					labelAtStructureLevel: false,
				},
			},
			{
				...DATA_DEFINITION_FIELD('Email'),
				customProperties: {
					labelAtStructureLevel: false,
				},
			},
		],
	},
	dataLayout: {
		dataLayoutFields: {
			TextEmail: {
				label: {
					en_US: 'Login',
				},
			},
			TextName: {
				label: {
					en_US: 'Username',
				},
			},
		},
	},
};

const LabelFieldWrapper = ({
	state = STRUCTURE_LEVEL_STATE,
	dataLayoutBuilder = FORM_VIEW.getDataLayoutBuilderProps(),
}) => (
	<LabelField
		dataLayoutBuilder={dataLayoutBuilder}
		dispatch={jest.fn()}
		field={{
			label: 'Label',
			name: 'LabelName',
			placeholder: 'Enter a field label.',
			tooltip:
				'Enter a descriptive field label that guides users to enter the information you want.',
		}}
		state={getFormattedState(state)}
	/>
);

describe('LabelField', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();

		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders with structure-level', () => {
		const {asFragment} = render(<LabelFieldWrapper />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with view-level', () => {
		const {asFragment} = render(
			<LabelFieldWrapper state={VIEW_LEVEL_STATE} />
		);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with structure-level passing the focusedCustomObjectField', () => {
		const {container} = render(
			<LabelFieldWrapper
				state={{
					...STRUCTURE_LEVEL_STATE,
					focusedCustomObjectField: {
						name: 'TextName',
					},
					focusedField: {},
				}}
			/>
		);

		const input = container.querySelector('.ddm-field-text');

		expect(input.value).toBe('Name');
	});

	it('display structure-level label and do not render the object-field-label message', () => {
		const {container, queryByText} = render(<LabelFieldWrapper />);

		const input = container.querySelector('.ddm-field-text');

		expect(input.value).toEqual('Name');
		expect(queryByText('object-field-label-x')).toBeFalsy();
	});

	it('display view-level label and render the message object-field-label', () => {
		const {container, queryByText} = render(
			<LabelFieldWrapper state={VIEW_LEVEL_STATE} />
		);

		const input = container.querySelector('.ddm-field-text');

		expect(input.value).toEqual('Username');
		expect(queryByText('object-field-label-x')).toBeTruthy();
	});

	it('renders the popover with structure level option selected', () => {
		const {container} = render(<LabelFieldWrapper />);

		const dropdownButton = container.querySelector(
			'.form-renderer-label-field__button'
		);
		const popover = container.querySelector(
			'.form-renderer-label-field__popover'
		);
		const options = document.querySelectorAll(
			'.form-renderer-label-field input.custom-control-input'
		);

		expect(popover.classList.contains('show')).toBeFalsy();

		fireEvent.click(dropdownButton);

		expect(popover.classList.contains('show')).toBeTruthy();

		// Structure level option

		expect(options[0].checked).toBeTruthy();
	});

	it('renders the popover with view level option selected', () => {
		const {container} = render(
			<LabelFieldWrapper state={VIEW_LEVEL_STATE} />
		);

		const dropdownButton = container.querySelector(
			'.form-renderer-label-field__button'
		);
		const popover = container.querySelector(
			'.form-renderer-label-field__popover'
		);
		const options = document.querySelectorAll(
			'.form-renderer-label-field input.custom-control-input'
		);

		expect(popover.classList.contains('show')).toBeFalsy();

		fireEvent.click(dropdownButton);

		expect(popover.classList.contains('show')).toBeTruthy();

		// View level option

		expect(options[1].checked).toBeTruthy();
	});

	it('change the label after choose the popover option', () => {
		const {container} = render(
			<LabelFieldWrapper state={VIEW_LEVEL_STATE} />
		);

		const options = document.querySelectorAll(
			'.form-renderer-label-field input.custom-control-input'
		);
		const input = container.querySelector('.ddm-field-text');

		// Structure level option

		fireEvent.click(options[0]);

		expect(input.value).toBe('Username');

		// View Level option

		fireEvent.click(options[1]);

		expect(input.value).toBe('Username');
	});
});
