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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React, {createContext} from 'react';

import LabelField from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/LabelField.es';
import * as utils from '../../../../src/main/resources/META-INF/resources/js/components/form-renderer-custom-fields/shared/utils.es';
import {FORM_VIEW} from '../../constants.es';
const {getDataLayoutBuilderProps} = FORM_VIEW;

const INITIAL_STATE = {
	dataDefinition: {
		dataDefinitionFields: [
			{
				customProperties: {
					labelAtStructureLevel: true
				},
				label: {
					en_US: 'Text',
				},
				name: 'Text11111',
			},
			{
				customProperties: {
					labelAtStructureLevel: true
				},
				label: {
					en_US: 'Text 2',
				},
				name: 'Text22222',
			},
		],
	},
	dataLayout: {
		dataLayoutFields: {},
	},
	editingLanguageId: 'en_US',
	focusedCustomObjectField: {},
	focusedField: {
		fieldName: 'Text11111',
	},
};

const INITIAL_STATE_FORM_VIEW = {
	...INITIAL_STATE,
	dataDefinition: {
		dataDefinitionFields: [
			{
				...INITIAL_STATE.dataDefinition.dataDefinitionFields[0],
				customProperties: {
					labelAtStructureLevel: false
				},
			},
		],
	},
	dataLayout: {
		dataLayoutFields: {
			Text11111: {
				label: {
					en_US: 'Text Form View Level',
				},
			},
		},
	},
};

const INITIAL_FIELD = {
	value: 'asdf',
};

const Context = createContext();

const LabelFieldWrapper = ({
	initialState = INITIAL_STATE,
	field = INITIAL_FIELD,
	dataLayoutBuilder = getDataLayoutBuilderProps(),
}) => (
	<Context.Provider value={[initialState, jest.fn()]}>
		<LabelField
			AppContext={Context}
			dataLayoutBuilder={dataLayoutBuilder}
			field={field}
		/>
	</Context.Provider>
);

describe('LabelField', () => {
	let setPropertyAtStructureLevel;
	let setPropertyAtViewLevel;

	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();

		jest.clearAllTimers();
		jest.restoreAllMocks();
		setPropertyAtStructureLevel = jest
			.fn()
			.mockImplementation(() => jest.fn());
		jest.spyOn(utils, 'setPropertyAtStructureLevel').mockImplementation(
			setPropertyAtStructureLevel
		);
		setPropertyAtViewLevel = jest.fn().mockImplementation(() => jest.fn());
		jest.spyOn(utils, 'setPropertyAtViewLevel').mockImplementation(
			setPropertyAtViewLevel
		);
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders with object-level', () => {
		const {asFragment} = render(<LabelFieldWrapper />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('display object-level label and do not render the object-field-label message', () => {
		const {container, queryByText} = render(<LabelFieldWrapper />);

		const input = container.querySelector('.form-control.ddm-field-text');

		expect(input.value).toEqual('Text');
		expect(queryByText('object-field-label-x')).toBeFalsy();
	});

	it('renders with form-view-level', () => {
		const {asFragment} = render(
			<LabelFieldWrapper initialState={INITIAL_STATE_FORM_VIEW} />
		);
		expect(asFragment()).toMatchSnapshot();
	});

	it('display form-view-level label and render the message object-field-label', () => {
		const {container, queryByText} = render(
			<LabelFieldWrapper initialState={INITIAL_STATE_FORM_VIEW} />
		);

		const input = container.querySelector('.form-control.ddm-field-text');

		expect(input.value).toEqual('Text Form View Level');
		expect(queryByText('object-field-label-x')).toBeTruthy();
	});

	it('change object-level label text', async () => {
		const {container} = render(<LabelFieldWrapper />);

		const input = container.querySelector('.form-control.ddm-field-text');

		fireEvent.change(input, {target: {value: 'New Object Level Text'}});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(setPropertyAtStructureLevel).toHaveBeenCalledWith('label', {
			en_US: 'New Object Level Text',
		});
		expect(setPropertyAtViewLevel).not.toBeCalled();
	});

	it('change form-view-level label text', async () => {
		const {container} = render(
			<LabelFieldWrapper initialState={INITIAL_STATE_FORM_VIEW} />
		);

		const input = container.querySelector('.form-control.ddm-field-text');

		fireEvent.change(input, {target: {value: 'New Form View Level Text'}});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(setPropertyAtStructureLevel).not.toBeCalled();
		expect(setPropertyAtViewLevel).toHaveBeenCalledWith('label', {
			en_US: 'New Form View Level Text',
		});
	});

	it('change to object-level label', async () => {
		const {queryByLabelText} = render(
			<LabelFieldWrapper initialState={INITIAL_STATE_FORM_VIEW} />
		);

		const radioObjectLevel = queryByLabelText(
			'label-for-all-forms-using-this-field'
		);

		fireEvent.click(radioObjectLevel);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(setPropertyAtStructureLevel).toHaveBeenCalledWith('label', {
			en_US: 'Text',
		});
	});

	it('change to form-view-level label', async () => {
		const {queryByLabelText} = render(<LabelFieldWrapper />);

		const radioFormView = queryByLabelText('label-for-only-this-form');
		
		await fireEvent.click(radioFormView);
		
		await act(async () => {
			jest.runAllTimers();
		});

		expect(setPropertyAtViewLevel).toHaveBeenCalledWith('label', {
			en_US: 'Text',
		});
	});

	it('update FormBuilder field', async () => {
		const containsFieldInsideFormBuilder = jest
			.fn()
			.mockImplementation(() => true);
		jest.spyOn(utils, 'containsFieldInsideFormBuilder').mockImplementation(
			containsFieldInsideFormBuilder
		);

		const dataLayoutBuilder = getDataLayoutBuilderProps();

		const {container} = render(
			<LabelFieldWrapper dataLayoutBuilder={dataLayoutBuilder} />
		);

		const input = container.querySelector('.form-control.ddm-field-text');

		fireEvent.change(input, {target: {value: 'New Text'}});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(dataLayoutBuilder.dispatch).toHaveBeenCalledWith('fieldEdited', {
			propertyName: 'label',
			propertyValue: 'New Text',
		});
	});
});


