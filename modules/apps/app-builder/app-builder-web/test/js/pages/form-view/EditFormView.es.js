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
import {DataLayoutVisitor} from 'data-engine-taglib';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditFormView from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/EditFormView.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import {ENTRY, FORM_VIEW} from '../../constants.es';

const {
	EDIT_FORM_VIEW_PROPS,
	FORM_VIEW_CONTEXT,
	getDataLayoutBuilderProps,
} = FORM_VIEW;

const setDataLayoutBuilderProps = (props) => {
	window.Liferay = {
		...window.Liferay,
		componentReady: () => {
			return new Promise((resolve) => {
				resolve(props);
			});
		},
	};
};

const EditFormViewWrapper = ({trackingIndicator = false, ...otherProps}) => (
	<AppContextProvider>
		<ClayModalProvider>
			<div className="tools-control-group">
				<div className="control-menu-level-1-heading" />
			</div>

			{trackingIndicator && <div className="change-tracking-indicator" />}

			<DndProvider backend={HTML5Backend}>
				<div id={EDIT_FORM_VIEW_PROPS.customObjectSidebarElementId} />

				<EditFormView {...EDIT_FORM_VIEW_PROPS} {...otherProps} />
			</DndProvider>
		</ClayModalProvider>
	</AppContextProvider>
);

describe('EditFormView', () => {
	let dataLayoutBuilderProps;
	let dataLayoutVisitorSpy;
	let successToastSpy;

	beforeEach(() => {
		jest.useFakeTimers();

		dataLayoutBuilderProps = getDataLayoutBuilderProps();

		dataLayoutVisitorSpy = jest
			.spyOn(DataLayoutVisitor, 'isDataLayoutEmpty')
			.mockImplementation(() => false);

		successToastSpy = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});

		setDataLayoutBuilderProps(dataLayoutBuilderProps);
	});

	afterEach(() => {
		jest.clearAllTimers();
		jest.restoreAllMocks();
		cleanup();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders', async () => {
		const {asFragment} = render(<EditFormViewWrapper />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with required fields at object level', async () => {
		const {DATA_DEFINITION} = ENTRY;

		const context = {
			...FORM_VIEW_CONTEXT,
			dataDefinition: {
				...DATA_DEFINITION,
				dataDefinitionFields: [
					{
						...DATA_DEFINITION.dataDefinitionFields[0],
						name: 'Text',
						required: true,
					},
				],
			},
			dataLayout: {
				...FORM_VIEW_CONTEXT.dataLayout,
				dataLayoutFields: {
					Text: {

						// Set form view level to required to ensure that when
						// the object view level is NOT required, the mask should
						// not be displayed

						required: true,
					},
				},
			},
		};

		dataLayoutBuilderProps = {
			...dataLayoutBuilderProps,
			getState: () => context,
		};

		setDataLayoutBuilderProps(dataLayoutBuilderProps);

		const {container, queryByText, rerender} = render(
			<EditFormViewWrapper />
		);

		await act(async () => {
			jest.runAllTimers();
		});

		// assert with required fields at Form View Level

		expect(queryByText('Name')).toBeTruthy();
		expect(container.querySelector('.reference-mark')).toBeTruthy();

		// assert with required fields at Object Level

		context.dataDefinition.dataDefinitionFields[0].required = false;

		setDataLayoutBuilderProps({
			...dataLayoutBuilderProps,
			getState: () => context,
		});

		rerender(<EditFormViewWrapper />);

		expect(queryByText('Name')).toBeTruthy();
		expect(container.querySelector('.reference-mark')).toBeFalsy();
	});

	it('renders as edit-form-view and make actions', async () => {
		const {queryByPlaceholderText, queryByText} = render(
			<EditFormViewWrapper />
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('edit-form-view')).toBeTruthy();

		const fieldDate = queryByText('Date');

		fireEvent.click(fieldDate);

		expect(
			dataLayoutBuilderProps.dispatchAction.mock.calls[2][0]
		).toStrictEqual({
			payload: {
				fieldTypeName: 'date',
			},
			type: 'ADD_CUSTOM_OBJECT_FIELD',
		});

		expect(dataLayoutBuilderProps.dispatchAction.mock.calls.length).toBe(3);

		const formName = queryByPlaceholderText('untitled-form-view');

		expect(formName.value).toBe('FormView');

		fireEvent.change(formName, {target: {value: 'My Form View'}});

		expect(
			dataLayoutBuilderProps.dispatchAction.mock.calls[3][0]
		).toStrictEqual({
			payload: {
				name: {
					en_US: 'My Form View',
				},
			},
			type: 'UPDATE_DATA_LAYOUT_NAME',
		});

		expect(dataLayoutBuilderProps.dispatchAction.mock.calls.length).toBe(4);

		expect(dataLayoutVisitorSpy.mock.calls.length).toBe(1);
	});

	it('renders as edit-form-view, simulate a field added to the layout and save it', async () => {
		const response = JSON.stringify({});

		fetch
			.mockResponseOnce(response)
			.mockResponseOnce(response)
			.mockResponseOnce(JSON.stringify({items: []}));

		const {container, queryByText} = render(<EditFormViewWrapper />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('edit-form-view')).toBeTruthy();

		const fieldTypeName = container.querySelector(
			'[data-field-type-name="Text"]'
		);

		fireEvent.dblClick(fieldTypeName);

		expect(dataLayoutBuilderProps.dispatch.mock.calls.length).toBe(1);
		expect(dataLayoutBuilderProps.dispatch.mock.calls[0][0]).toBe(
			'fieldAdded'
		);

		const saveButton = queryByText('save');

		expect(fetch.mock.calls.length).toBe(0);

		await act(async () => {
			fireEvent.click(saveButton);
		});

		expect(fetch.mock.calls.length).toBe(2);

		const [, dataLayoutParams] = fetch.mock.calls[1];

		expect(dataLayoutParams.method).toBe('POST');
		expect(dataLayoutParams.body).toEqual(
			JSON.stringify(FORM_VIEW_CONTEXT.dataLayout)
		);

		const deleteFromObject = container.querySelector(
			'[title="delete-from-object"]'
		);

		await act(async () => {
			fireEvent.click(deleteFromObject);
		});

		expect(document.querySelector('.modal')).toBeTruthy();
		expect(successToastSpy.mock.calls.length).toBe(1);
	});

	it('renders as new-form-view and make actions', async () => {
		const {
			container,
			queryAllByPlaceholderText,
			queryAllByText,
			queryByText,
		} = render(
			<EditFormViewWrapper
				dataLayoutId={0}
				newCustomObject={false}
				trackingIndicator
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(container.querySelector('.menu-indicator-enabled')).toBeTruthy();

		expect(queryByText('new-form-view')).toBeTruthy();

		const searchButton = container.querySelector('svg.lexicon-icon-search');

		fireEvent.click(searchButton);

		const [search] = queryAllByPlaceholderText('search...');

		expect(search.value).toBe('');

		fireEvent.change(search, {target: {value: 'Number'}});

		expect(search.value).toBe('Number');

		expect(queryAllByText('Name').length).toBe(0);
	});

	it('renders as new-form-view and delete field', async () => {
		fetch.mockResponse(
			JSON.stringify({
				items: [
					{
						dataDefinition: {},
						dataLayouts: [],
						dataListViews: [],
					},
				],
			})
		);
		const {container, queryByText} = render(<EditFormViewWrapper />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(dataLayoutBuilderProps.dispatchAction.mock.calls.length).toBe(2);
		expect(dataLayoutVisitorSpy.mock.calls.length).toBe(1);

		expect(container.querySelector('.menu-indicator-enabled')).toBeFalsy();

		const [, deleteButton] = container.querySelectorAll(
			'.field-type-remove-icon button'
		);

		await act(async () => {
			fireEvent.click(deleteButton);
		});

		act(() => {
			jest.runAllTimers();
		});

		let modal = document.querySelector('.remove-object-field-panel');

		expect(modal).toBeTruthy();

		const deleteField = queryByText('delete');

		await act(async () => {
			fireEvent.click(deleteField);
		});

		expect(
			dataLayoutBuilderProps.dispatchAction.mock.calls[2][0]
		).toStrictEqual({
			payload: {
				fieldName: 'SelectFromList',
			},
			type: 'DELETE_DATA_DEFINITION_FIELD',
		});

		act(() => {
			jest.runAllTimers();
		});

		modal = document.querySelector('.remove-object-field-panel');

		expect(modal).toBeFalsy();
	});

	it('delete field from Layout', async () => {
		fetch.mockResponse(
			JSON.stringify({
				items: [
					{
						dataDefinition: {
							defaultLanguageId: 'en_US',
						},
						dataLayouts: [
							{
								name: {
									en_US: 'Test FormView',
								},
							},
						],
						dataListViews: [
							{
								name: {
									en_US: 'Test TableView',
								},
							},
						],
					},
				],
			})
		);
		const {container, queryByText} = render(<EditFormViewWrapper />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(dataLayoutBuilderProps.dispatchAction.mock.calls.length).toBe(2);
		expect(dataLayoutVisitorSpy.mock.calls.length).toBe(1);

		const deleteButton = container.querySelector(
			'.field-type-remove-icon button'
		);

		await act(async () => {
			fireEvent.click(deleteButton);
		});

		act(() => {
			jest.runAllTimers();
		});

		let modal = document.querySelector('.remove-object-field-panel');

		expect(modal).toBeTruthy();

		const deleteField = queryByText('delete');

		expect(queryByText('1. Test FormView')).toBeTruthy();
		expect(queryByText('1. Test TableView')).toBeTruthy();

		await act(async () => {
			fireEvent.click(deleteField);
		});

		const [action, payload] = dataLayoutBuilderProps.dispatch.mock.calls[0];

		expect(action).toEqual('fieldDeleted');
		expect(payload).toStrictEqual({activePage: 0, fieldName: 'Text'});

		act(() => {
			jest.runAllTimers();
		});

		modal = document.querySelector('.remove-object-field-panel');

		expect(modal).toBeFalsy();
	});
});
