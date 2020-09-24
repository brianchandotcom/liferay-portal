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
import {DataLayoutBuilder, DataLayoutVisitor} from 'data-engine-taglib';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditFormView from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/EditFormView.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import {FORM_VIEW_CONTEXT, dataLayoutBuilderConfig} from '../../constants.es';

const EDIT_FORM_VIEW_PROPS = {
	basePortletURL: 'localhost',
	customObjectSidebarElementId: 'customObject',
	dataDefinitionId: 1,
	dataLayoutBuilderElementId: '',
	dataLayoutBuilderId: 1,
	dataLayoutId: 1,
	newCustomObject: true,
};

const dataLayoutBuilder = new DataLayoutBuilder.default(
	dataLayoutBuilderConfig
);

describe('EditFormView', () => {
	let dataLayoutBuilderProps;
	let dataLayoutVisitorSpy;
	let successToastSpy;

	beforeEach(() => {
		dataLayoutBuilderProps = {
			...dataLayoutBuilder,
			dispatch: jest.fn(),
			dispatchAction: jest.fn(),
			getDDMFormFieldSettingsContext: jest.fn(),
			getFieldTypes: () => {
				return [
					{
						name: 'Text',
					},
				];
			},
			getLayoutProvider: () => ({
				getEvents: () => ({
					fieldHovered: jest.fn(),
				}),
			}),
			getState: () => {
				return FORM_VIEW_CONTEXT;
			},
			getStore: () => {
				return {
					activePage: 0,
					pages: [
						{
							rows: [],
						},
					],
				};
			},
			on: jest.fn(),
			onEditingLanguageIdChange: jest.fn(),
			removeEventListener: jest.fn(),
		};

		dataLayoutVisitorSpy = jest
			.spyOn(DataLayoutVisitor, 'isDataLayoutEmpty')
			.mockImplementation(() => false);

		successToastSpy = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});

		window.Liferay = {
			...window.Liferay,
			componentReady: () => {
				return new Promise((resolve) => {
					resolve(dataLayoutBuilderProps);
				});
			},
		};

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

	it('renders', async () => {
		const {asFragment} = render(
			<AppContextProvider>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading" />
				</div>

				<DndProvider backend={HTML5Backend}>
					<div
						id={EDIT_FORM_VIEW_PROPS.customObjectSidebarElementId}
					/>

					<EditFormView {...EDIT_FORM_VIEW_PROPS} />
				</DndProvider>
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders as edit-form-view and make actions', async () => {
		const {queryByPlaceholderText, queryByText} = render(
			<AppContextProvider>
				<ClayModalProvider>
					<div className="tools-control-group">
						<div className="control-menu-level-1-heading" />
					</div>

					<DndProvider backend={HTML5Backend}>
						<div
							id={
								EDIT_FORM_VIEW_PROPS.customObjectSidebarElementId
							}
						/>

						<EditFormView {...EDIT_FORM_VIEW_PROPS} />
					</DndProvider>
				</ClayModalProvider>
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('edit-form-view')).toBeTruthy();

		const fieldDate = queryByText('Date');

		fireEvent.click(fieldDate);

		expect(
			dataLayoutBuilderProps.dispatchAction.mock.calls[1][0]
		).toStrictEqual({
			payload: {
				fieldTypeName: 'date',
			},
			type: 'ADD_CUSTOM_OBJECT_FIELD',
		});

		expect(dataLayoutBuilderProps.dispatchAction.mock.calls.length).toBe(2);

		const formName = queryByPlaceholderText('untitled-form-view');

		expect(formName.value).toBe('FormView');

		fireEvent.change(formName, {target: {value: 'My Form View'}});

		expect(
			dataLayoutBuilderProps.dispatchAction.mock.calls[2][0]
		).toStrictEqual({
			payload: {
				name: {
					en_US: 'My Form View',
				},
			},
			type: 'UPDATE_DATA_LAYOUT_NAME',
		});

		expect(dataLayoutBuilderProps.dispatchAction.mock.calls.length).toBe(3);

		expect(dataLayoutVisitorSpy.mock.calls.length).toBe(1);
	});

	it('renders as edit-form-view, simulate a field added to the layout and save it', async () => {
		const response = JSON.stringify({});

		fetch
			.mockResponseOnce(response)
			.mockResponseOnce(response)
			.mockResponseOnce(JSON.stringify({items: []}));

		const {container, queryByText} = render(
			<AppContextProvider>
				<ClayModalProvider>
					<div className="tools-control-group">
						<div className="control-menu-level-1-heading" />
					</div>

					<DndProvider backend={HTML5Backend}>
						<div
							id={
								EDIT_FORM_VIEW_PROPS.customObjectSidebarElementId
							}
						/>

						<EditFormView {...EDIT_FORM_VIEW_PROPS} />
					</DndProvider>
				</ClayModalProvider>
			</AppContextProvider>
		);

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
			<AppContextProvider>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading" />
				</div>

				<DndProvider backend={HTML5Backend}>
					<div
						id={EDIT_FORM_VIEW_PROPS.customObjectSidebarElementId}
					/>

					<EditFormView
						{...EDIT_FORM_VIEW_PROPS}
						dataLayoutId={0}
						newCustomObject={false}
					/>
				</DndProvider>
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('new-form-view')).toBeTruthy();

		const searchButton = container.querySelector('svg.lexicon-icon-search');

		fireEvent.click(searchButton);

		const [search] = queryAllByPlaceholderText('search...');

		expect(search.value).toBe('');

		fireEvent.change(search, {target: {value: 'Number'}});

		expect(search.value).toBe('Number');

		expect(queryAllByText('Name').length).toBe(0);
	});
});
