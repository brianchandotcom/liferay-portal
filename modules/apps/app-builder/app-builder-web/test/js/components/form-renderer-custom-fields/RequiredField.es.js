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
import React, {createContext} from 'react';

import RequiredField from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/RequiredField.es';
import * as utils from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/shared/utils.es';
import {FORM_VIEW} from '../../constants.es';

const DEFAULT_STATE = {
	editingLanguageId: 'en_US',
	focusedCustomObjectField: {},
	focusedField: {
		fieldName: 'RequiredName',
	},
};

const DATA_DEFINITION_FIELD = (label) => ({
	label: {
		en_US: label,
	},
	name: `Required${label}`,
});

const STRUCTURE_LEVEL_STATE = {
	...DEFAULT_STATE,
	dataDefinitionField: {
		...DATA_DEFINITION_FIELD('Name'),
		required: true,
	},
	dataDefinitionFields: [
		{
			...DATA_DEFINITION_FIELD('Name'),
			required: true,
		},
		{
			...DATA_DEFINITION_FIELD('Email'),
			required: true,
		},
	],
	dataLayoutFields: {
		RequiredName: {required: false},
	},
};

const VIEW_LEVEL_STATE = {
	...STRUCTURE_LEVEL_STATE,
	dataDefinitionField: {
		...DATA_DEFINITION_FIELD('Name'),
		required: false,
	},
	dataLayoutField: {
		...DATA_DEFINITION_FIELD('Name'),
		required: false,
	},
};

const AppContext = createContext();

const RequiredFieldWrapper = ({
	state = STRUCTURE_LEVEL_STATE,
	dataLayoutBuilder = FORM_VIEW.getDataLayoutBuilderProps(),
	dispatch = jest.fn(),
}) => (
	<AppContext.Provider value={[state, jest.fn()]}>
		<RequiredField
			dataLayoutBuilder={dataLayoutBuilder}
			dispatch={dispatch}
			state={state}
		/>
	</AppContext.Provider>
);

describe('RequiredField', () => {
	afterEach(() => {
		cleanup();
		jest.spyOn(utils, 'containsFieldInsideFormBuilder').mockImplementation(
			() => true
		);
	});

	it('renders with structure-level', () => {
		const {asFragment} = render(<RequiredFieldWrapper />);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with view-level', () => {
		const {asFragment} = render(
			<RequiredFieldWrapper state={VIEW_LEVEL_STATE} />
		);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders DropDownButton enabled when toggleSwitch was true', () => {
		const {container} = render(<RequiredFieldWrapper />);

		const toggleSwitch = container.querySelector('.toggle-switch-check');

		const dropdownButton = container.querySelector(
			'[title="required-options"]'
		);

		expect(toggleSwitch.checked).toBeTruthy();

		expect(dropdownButton.disabled).toBeFalsy();
	});

	it('renders DropDownButton disabled when toggleSwitch was false', () => {
		const {container} = render(
			<RequiredFieldWrapper state={VIEW_LEVEL_STATE} />
		);

		const toggleSwitch = container.querySelector('.toggle-switch-check');

		const dropdownButton = container.querySelector(
			'[title="required-options"]'
		);

		expect(toggleSwitch.checked).toBeFalsy();

		expect(dropdownButton.disabled).toBeTruthy();
	});

	it('renders the popover with structure level option selected', async () => {
		const {container} = render(<RequiredFieldWrapper />);

		const dropdownButton = container.querySelector(
			'[title="required-options"]'
		);
		const popover = container.querySelector(
			'.form-renderer-required-field__popover'
		);
		const options = document.querySelectorAll(
			'.form-renderer-required-field input.custom-control-input'
		);

		expect(popover.classList.contains('show')).toBeFalsy();

		fireEvent.click(dropdownButton);

		expect(popover.classList.contains('show')).toBeTruthy();

		// View level option

		expect(options[0].checked).toBeTruthy();
	});

	it('renders the popover with view level option selected', async () => {
		const VIEW_LEVEL_STATE_CUSTUM = {
			...VIEW_LEVEL_STATE,
			dataLayoutField: {
				...DATA_DEFINITION_FIELD('Name'),
				required: true,
			},
		};

		const {container} = render(
			<RequiredFieldWrapper state={VIEW_LEVEL_STATE_CUSTUM} />
		);

		const dropdownButton = container.querySelector(
			'[title="required-options"]'
		);
		const popover = container.querySelector(
			'.form-renderer-required-field__popover'
		);
		const options = document.querySelectorAll(
			'.form-renderer-required-field input.custom-control-input'
		);

		expect(popover.classList.contains('show')).toBeFalsy();

		fireEvent.click(dropdownButton);

		expect(popover.classList.contains('show')).toBeTruthy();

		// View level option

		expect(options[1].checked).toBeTruthy();
	});

	it('change level field', async () => {
		const dispatch = jest.fn();

		const {container} = render(
			<RequiredFieldWrapper dispatch={dispatch} state={VIEW_LEVEL_STATE} />
		);
		
		const options = container.querySelectorAll(
			'.form-renderer-required-field input.custom-control-input'
		);

		fireEvent.click(options[0]);

		expect(dispatch).toBeCalled();
	});

	it('change ToggleSwitch to false', async () => {
		const dataLayoutBuilder = FORM_VIEW.getDataLayoutBuilderProps();

		const {container} = render(
			<RequiredFieldWrapper
				dataLayoutBuilder={dataLayoutBuilder}
			/>
		);

		const toggleSwitch = container.querySelector('.toggle-switch-check');

		fireEvent.click(toggleSwitch);

		expect(dataLayoutBuilder.dispatch).toHaveBeenCalledWith('fieldEdited', {
			propertyName: 'required',
			propertyValue: false,
		});
	});

	it('change ToggleSwitch to true', async () => {
		const dataLayoutBuilder = FORM_VIEW.getDataLayoutBuilderProps();

		const {container} = render(
			<RequiredFieldWrapper
				dataLayoutBuilder={dataLayoutBuilder}
				state={VIEW_LEVEL_STATE}
			/>
		);

		const toggleSwitch = container.querySelector('.toggle-switch-check');

		fireEvent.click(toggleSwitch);

		expect(dataLayoutBuilder.dispatch).toHaveBeenCalledWith('fieldEdited', {
			propertyName: 'required',
			propertyValue: true,
		});
	});
});
