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
import * as toast from 'data-engine-js-components-web/js/utils/toast.es';
import {DataLayoutBuilder, DataLayoutBuilderActions} from 'data-engine-taglib';
import React from 'react';

import FormViewContext from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/FormViewContext.es';
import useSaveAsFieldset from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/useSaveAsFieldset.es';
import {FORM_VIEW, dataLayoutBuilderConfig} from '../../constants.es';

const {FORM_VIEW_CONTEXT} = FORM_VIEW;

const FormViewContextWrapper = ({children, dispatch}) => {
	return (
		<FormViewContext.Provider value={[FORM_VIEW_CONTEXT, dispatch]}>
			{children}
		</FormViewContext.Provider>
	);
};

const SaveAsFieldSetWrapper = ({dispatch, fieldName, onClick}) => {
	const dataLayoutBuilder = new DataLayoutBuilder.default(
		dataLayoutBuilderConfig
	);

	const saveAsFieldSet = useSaveAsFieldset({
		dataLayoutBuilder: {
			...dataLayoutBuilder,
			formBuilderWithLayoutProvider: {
				refs: {
					layoutProvider: {
						dispatch,
					},
				},
			},
		},
	});

	return (
		<button
			onClick={() => {
				onClick();

				saveAsFieldSet(fieldName);
			}}
		>
			Save
		</button>
	);
};

describe('useSaveAsFieldSet', () => {
	let contextDispatch;
	let dispatchFn;
	let errorToastSpy;
	let onClick;
	let successToastSpy;

	beforeEach(() => {
		cleanup();

		contextDispatch = jest.fn();
		dispatchFn = jest.fn();
		errorToastSpy = jest
			.spyOn(toast, 'errorToast')
			.mockImplementation(() => {});
		onClick = jest.fn();
		successToastSpy = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});
	});

	it('call saveAsFieldSet successfully', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({
				defaultDataLayout: {
					defaultDataLayout: {
						id: 1,
					},
				},
				id: 1,
			})
		);

		const {queryByText} = render(
			<FormViewContextWrapper dispatch={contextDispatch}>
				<SaveAsFieldSetWrapper
					dispatch={dispatchFn}
					fieldName="SelectFromList"
					onClick={onClick}
				/>
			</FormViewContextWrapper>
		);

		const button = queryByText('Save');

		await act(async () => {
			fireEvent.click(button);
		});

		const contextDispatchCalls = contextDispatch.mock.calls;

		expect(onClick.mock.calls.length).toBe(1);
		expect(fetch.mock.calls.length).toBe(1);
		expect(dispatchFn.mock.calls.length).toBe(1);
		expect(contextDispatchCalls.length).toBe(2);
		expect(successToastSpy.mock.calls.length).toBe(1);
		expect(dispatchFn.mock.calls[0][1]).toStrictEqual({
			fieldName: 'SelectFromList',
			propertyName: 'ddmStructureId',
			propertyValue: 1,
		});
		expect(contextDispatchCalls[0][0].payload.fieldSets.length).toBe(
			FORM_VIEW_CONTEXT.fieldSets.length + 1
		);
		expect(
			contextDispatchCalls[0][0].type ===
				DataLayoutBuilderActions.UPDATE_FIELDSETS
		);
		expect(
			contextDispatchCalls[1][0].type ===
				DataLayoutBuilderActions.UPDATE_DATA_DEFINITION
		);
		expect(dispatchFn.mock.calls[0][0]).toBe('fieldEdited');
	});

	it('call saveAsFieldSet with error', async () => {
		fetch.mockReject(new Error('fetch-error'));

		const {queryByText} = render(
			<FormViewContextWrapper dispatch={contextDispatch}>
				<SaveAsFieldSetWrapper
					dispatch={dispatchFn}
					fieldName="Text"
					onClick={onClick}
				/>
			</FormViewContextWrapper>
		);

		await act(async () => {
			fireEvent.click(queryByText('Save'));
		});

		expect(errorToastSpy.mock.calls.length).toBe(1);
		expect(errorToastSpy.mock.calls[0][0]).toBe('fetch-error');
	});
});
