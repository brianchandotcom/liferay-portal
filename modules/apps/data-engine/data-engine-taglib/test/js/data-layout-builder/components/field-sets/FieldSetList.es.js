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

import {ClayModalProvider} from '@clayui/modal';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import * as DDMForm from 'dynamic-data-mapping-form-builder';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import FieldSetList from '../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/components/field-sets/FieldSetList';
import * as DataConverter from '../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/utils/dataConverter.es';
import * as toast from '../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/utils/toast.es';
import {
	DATA_DEFINITION_FIELDSET,
	DATA_DEFINITION_RESPONSES,
	ENTRY,
	FORM_VIEW,
} from '../../../../utils/constants.es';

const {getDataLayoutBuilderProps} = FORM_VIEW;

const defaultState = {
	appProps: {
		config: {},
		contentTypeConfig: {},
		dataDefinitionId: 1,
		dataLayoutId: 1,
		fieldTypesModules: '',
		groupId: 1,
		sidebarPanels: {},
	},
	dataDefinition: DATA_DEFINITION_RESPONSES.ONE_ITEM,
	dataLayout: ENTRY.DATA_LAYOUT,
	fieldSets: [],
};

let dataLayoutBuilder;
let spySuccessToast;
let spyErrorToast;
let ddmFormSpy;

export const FieldSetWrapper = ({children}) => (
	<DndProvider backend={HTML5Backend}>
		<ClayModalProvider>{children}</ClayModalProvider>
	</DndProvider>
);

describe('FieldSets', () => {
	beforeEach(() => {
		dataLayoutBuilder = getDataLayoutBuilderProps();

		jest.spyOn(DataConverter, 'getFieldSetDDMForm').mockReturnValue({
			name: 'Field53354166',
			pages: FORM_VIEW.pages,
		});

		ddmFormSpy = jest
			.spyOn(DDMForm, 'default')
			.mockImplementation((props) => {
				const state = {
					...dataLayoutBuilder,
					dispose: jest.fn(),
					emit: jest.fn(),
					formBuilderWithLayoutProvider: {
						refs: {
							layoutProvider: {
								getRules: jest
									.fn()
									.mockImplementation(() => []),
								on: jest.fn().mockImplementation(() => ({
									removeListener: jest.fn(),
								})),
							},
						},
					},
				};

				props.layoutProviderProps.onLoad(state);

				return state;
			});

		jest.useFakeTimers();

		spySuccessToast = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});
		spyErrorToast = jest
			.spyOn(toast, 'errorToast')
			.mockImplementation(() => {});

		window.Liferay = {
			...window.Liferay,
			Language: {
				...window.Liferay.Language,
				direction: {
					pt_BR: 'ltr',
				},
			},
			Loader: {
				require: () => jest.fn(),
			},
			SideNavigation: {
				instance: () => {},
			},
		};
	});

	afterEach(() => {
		jest.clearAllTimers();
		jest.restoreAllMocks();

		cleanup();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	xit('renders', () => {
		const {asFragment} = render(
			<FieldSetWrapper>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	xit('renders fieldset list with empty state', () => {
		const {queryByText} = render(
			<FieldSetWrapper>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(queryByText('create-new-fieldset')).toBeTruthy();
		expect(queryByText('there-are-no-fieldsets')).toBeTruthy();
		expect(queryByText('there-are-no-fieldsets-description')).toBeTruthy();
	});

	xit('renders fieldset list with 1 fieldset', () => {
		const {label, nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const state = {
			...defaultState,
			fieldSets: [
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: nestedDataDefinitionFields,
					name: label,
				},
			],
		};

		const {container, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<FieldSetWrapper state={state}>
					<FieldSetList />
				</FieldSetWrapper>
			</DndProvider>
		);

		const fields = container.querySelectorAll('.field-type');

		expect(queryByText('create-new-fieldset')).toBeTruthy();
		expect(fields.length).toBe(1);

		expect(fields[0].querySelector('.list-group-title').textContent).toBe(
			'Address'
		);
		expect(
			fields[0].querySelector('.list-group-subtitle').textContent
		).toBe('x-field');
	});

	xit('renders fieldset list with more than 1 fieldset', () => {
		const {nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const state = {
			...defaultState,
			fieldSets: [
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: nestedDataDefinitionFields,
					dataDefinitionKey: '110',
					defaultLanguageId: 'en_US',
					name: {
						en_US: 'Address',
						pt_BR: 'Endereço',
					},
				},
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: [
						...nestedDataDefinitionFields,
						nestedDataDefinitionFields,
					],
					dataDefinitionKey: '220',
					defaultLanguageId: 'pt_BR',
					name: {
						en_US: 'House',
						pt_BR: 'Casa',
					},
				},
			],
		};

		const {container, queryByText} = render(
			<FieldSetWrapper state={state}>
				<FieldSetList />
			</FieldSetWrapper>
		);

		const fields = container.querySelectorAll('.field-type');

		expect(queryByText('create-new-fieldset')).toBeTruthy();
		expect(fields.length).toBe(2);

		expect(fields[0].querySelector('.list-group-title').textContent).toBe(
			'Address'
		);
		expect(
			fields[0].querySelector('.list-group-subtitle').textContent
		).toBe('x-field');

		expect(fields[1].querySelector('.list-group-title').textContent).toBe(
			'Casa'
		);
		expect(
			fields[1].querySelector('.list-group-subtitle').textContent
		).toBe('x-fields');
	});

	xit('renders modal when click to create a new fieldset by using empty state', async () => {
		const {queryByText} = render(
			<FieldSetWrapper>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(document.querySelector('.fieldset-modal')).toBeFalsy();

		await act(async () => {
			await fireEvent.click(queryByText('create-new-fieldset'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(document.querySelector('.fieldset-modal')).toBeTruthy();
		expect(document.querySelector('.modal-title').textContent).toBe(
			'create-new-fieldset'
		);

		expect(queryByText('cancel')).toBeTruthy();
		expect(queryByText('save')).toBeTruthy();
	});

	xit('renders modal when click to add a new fieldset with fieldsets in the fieldset list', async () => {
		const {label, nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const state = {
			...defaultState,
			fieldSets: [
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: nestedDataDefinitionFields,
					name: label,
				},
			],
		};

		const {queryByText} = render(
			<FieldSetWrapper state={state}>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(document.querySelector('.fieldset-modal')).toBeFalsy();

		await act(async () => {
			await fireEvent.click(queryByText('create-new-fieldset'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(document.querySelector('.fieldset-modal')).toBeTruthy();
		expect(document.querySelector('.modal-title').textContent).toBe(
			'create-new-fieldset'
		);

		// Make sure the localization button is shown in the Fielset
		// builder when the user is editing a Fieldset

		expect(
			document.querySelector('.dropdown.localizable-dropdown')
		).toBeTruthy();

		expect(queryByText('cancel')).toBeTruthy();
		expect(queryByText('save')).toBeTruthy();
	});

	xit('renders modal when click to create a new fieldset and close it after click to cancel', async () => {
		const {queryByText} = render(
			<FieldSetWrapper>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(document.querySelector('.fieldset-modal')).toBeFalsy();

		await act(async () => {
			await fireEvent.click(queryByText('create-new-fieldset'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('cancel')).toBeTruthy();
		expect(queryByText('save')).toBeTruthy();

		await act(async () => {
			await fireEvent.click(queryByText('cancel'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(document.querySelector('.fieldset-modal')).toBeFalsy();
		expect(queryByText('cancel')).toBeFalsy();
		expect(queryByText('save')).toBeFalsy();
	});

	xit('renders modal when click to edit a fieldset in the fieldset list', async () => {
		fetch.mockResponseOnce(JSON.stringify({}));

		const {label, nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const state = {
			...defaultState,
			fieldSets: [
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: nestedDataDefinitionFields,
					defaultDataLayout: {id: 1},
					name: label,
				},
			],
		};

		const {queryByText} = render(
			<FieldSetWrapper state={state}>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(document.querySelector('.fieldset-modal')).toBeFalsy();

		await act(async () => {
			await fireEvent.click(queryByText('edit'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(ddmFormSpy.mock.calls.length).toBe(1);
		expect(document.querySelector('.fieldset-modal')).toBeTruthy();
		expect(document.querySelector('.modal-title').textContent).toBe(
			'edit-fieldset'
		);

		// Make sure the localization button is shown in the Fielset
		// builder when the user is editing a Fieldset

		expect(
			document.querySelector('.dropdown.localizable-dropdown')
		).toBeTruthy();
		expect(queryByText('cancel')).toBeTruthy();
		expect(queryByText('save')).toBeTruthy();
	});

	xit('renders fieldset list with one fieldset and create it on form builder', () => {
		const {nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const fieldSet = {
			...DATA_DEFINITION_FIELDSET,
			dataDefinitionFields: nestedDataDefinitionFields,
		};
		const state = {
			...defaultState,
			fieldSets: [fieldSet],
		};

		jest.spyOn(DataConverter, 'getDataDefinitionFieldSet').mockReturnValue({
			fieldSet,
		});

		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<FieldSetWrapper state={state}>
					<FieldSetList />
				</FieldSetWrapper>
			</DndProvider>
		);

		fireEvent.doubleClick(container.querySelector('.field-type'));

		const [
			action,
			{
				fieldSet: {name},
				indexes,
			},
		] = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.dispatch.mock.calls[0];

		expect(action).toBe('fieldSetAdded');
		expect(name).toStrictEqual('Field53354166');
		expect(indexes).toStrictEqual({
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 1,
		});
	});

	xit('renders fieldset list with more than one fieldset and filter it', async () => {
		const {nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const state = {
			...defaultState,
			fieldSets: [
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: nestedDataDefinitionFields,
					dataDefinitionKey: '110',
					defaultLanguageId: 'en_US',
					name: {
						en_US: 'Address',
						pt_BR: 'Endereço',
					},
				},
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: [
						...nestedDataDefinitionFields,
						nestedDataDefinitionFields,
					],
					dataDefinitionKey: '220',
					defaultLanguageId: 'en_US',
					name: {
						en_US: 'House',
						pt_BR: 'Casa',
					},
				},
			],
		};

		const {container, queryByText, rerender} = render(
			<FieldSetWrapper state={state}>
				<FieldSetList />
			</FieldSetWrapper>
		);

		expect(queryByText('Address')).toBeTruthy();
		expect(queryByText('House')).toBeTruthy();
		expect(container.querySelectorAll('.field-type').length).toBe(2);

		rerender(
			<FieldSetWrapper state={state}>
				<FieldSetList keywords="Address" />
			</FieldSetWrapper>
		);

		expect(queryByText('Address')).toBeTruthy();
		expect(queryByText('House')).toBeFalsy();
		expect(container.querySelectorAll('.field-type').length).toBe(1);
	});

	xit('renders fieldset list with one fieldset and delete it', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({
				actions: {},
				facets: [],
				items: [],
				lastPage: 1,
				page: 1,
				pageSize: 0,
				totalCount: 0,
			})
		);
		fetch.mockResponseOnce(JSON.stringify({}));

		const {label, nestedDataDefinitionFields} = DATA_DEFINITION_FIELDSET;
		const state = {
			...defaultState,
			fieldSets: [
				{
					...DATA_DEFINITION_FIELDSET,
					dataDefinitionFields: nestedDataDefinitionFields,
					name: label,
				},
			],
		};

		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<FieldSetWrapper state={state}>
					<FieldSetList />
				</FieldSetWrapper>
			</DndProvider>
		);

		expect(document.querySelector('.modal-dialog')).toBeFalsy();

		await act(async () => {
			await fireEvent.click(queryByText('delete'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		const modal = document.querySelector('.modal-dialog.modal-danger');

		expect(modal).toBeTruthy();

		const [, deleteButton] = modal.querySelectorAll('.modal-footer button');

		await act(async () => {
			await fireEvent.click(deleteButton);
		});

		const {
			dispatch: {
				mock: {calls: dispatchCalls},
			},
		} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;
		const [action, payload] = dispatchCalls[0];

		expect(action).toEqual('fieldDeleted');

		expect(spyErrorToast.mock.calls.length).toBe(0);
		expect(spySuccessToast.mock.calls.length).toBe(1);
		expect(dispatchCalls.length).toBe(1);
		expect(payload).toStrictEqual({activePage: 0, fieldName: 'Text'});
	});

	xit('renders the modal fieldset and shows the default language of the object being created', async () => {
		const state = {
			...defaultState,
			dataDefinition: {
				...defaultState.dataDefinition,
				defaultLanguageId: 'pt_BR',
			},
		};

		const {queryByText} = render(
			<FieldSetWrapper state={state}>
				<FieldSetList />
			</FieldSetWrapper>
		);

		await act(async () => {
			await fireEvent.click(queryByText('create-new-fieldset'));
		});

		await act(async () => {
			jest.runAllTimers();
		});

		expect(
			document.querySelector('.localizable-item-default .autofit-section')
				.textContent
		).toBe('pt-BR');
	});
});
