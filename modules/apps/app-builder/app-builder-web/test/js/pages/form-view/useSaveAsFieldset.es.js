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
import {DataLayoutBuilder, DataLayoutBuilderActions} from 'data-engine-taglib';
import React from 'react';

import FormViewContext from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/FormViewContext.es';
import useSaveAsFieldset from '../../../../src/main/resources/META-INF/resources/js/pages/form-view/useSaveAsFieldset.es';
import * as toast from '../../../../src/main/resources/META-INF/resources/js/utils/toast.es';
import {dataLayoutBuilderConfig, formViewContext} from '../../constants.es';

const dataLayoutBuilder = new DataLayoutBuilder.default(
	dataLayoutBuilderConfig
);

const FormViewContextWrapper = ({children, defaultQuery = {}, dispatch}) => {
	return (
		<FormViewContext.Provider value={[defaultQuery, dispatch]}>
			{children}
		</FormViewContext.Provider>
	);
};

describe('useSaveAsFieldSet', () => {
	let successToastSpy;
	let errorToastSpy;
	let SaveAsFieldSetWrapper;
	let contextDispatch;
	let dispatchFn;
	let fieldName;
	let onClick;

	beforeEach(() => {
		cleanup();

		contextDispatch = jest.fn();
		dispatchFn = jest.fn();
		onClick = jest.fn();
		fieldName = 'Text';

		successToastSpy = jest
			.spyOn(toast, 'successToast')
			.mockImplementation(() => {});

		errorToastSpy = jest
			.spyOn(toast, 'errorToast')
			.mockImplementation(() => {});

		SaveAsFieldSetWrapper = () => (
			<FormViewContextWrapper
				defaultQuery={formViewContext}
				dispatch={contextDispatch}
			>
				<RenderSaveAsFieldSet
					dispatch={dispatchFn}
					fieldName={fieldName}
					onClick={onClick}
				/>
			</FormViewContextWrapper>
		);
	});

	const RenderSaveAsFieldSet = ({dispatch, fieldName, onClick}) => {
		const saveAsFieldSet = useSaveAsFieldset({
			dataLayoutBuilder: {
				...dataLayoutBuilder,
				dispatch,
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

	it('call saveAsFieldSet with success', async () => {
		const fieldSetResponse = {
			defaultDataLayout: {
				defaultDataLayout: {
					id: 1,
				},
			},
			id: 1,
		};

		fetch.mockResponseOnce(JSON.stringify(fieldSetResponse));

		const {queryByText} = render(<SaveAsFieldSetWrapper />);

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
			fieldName,
			propertyName: 'ddmStructureId',
			propertyValue: fieldSetResponse.id,
		});
		expect(contextDispatchCalls[0][0].payload.fieldSets.length).toBe(
			formViewContext.fieldSets.length + 1
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

		const {queryByText} = render(<SaveAsFieldSetWrapper />);

		const button = queryByText('Save');

		await act(async () => {
			fireEvent.click(button);
		});

		expect(errorToastSpy.mock.calls.length).toBe(1);
		expect(errorToastSpy.mock.calls[0][0]).toBe('fetch-error');
	});
});
