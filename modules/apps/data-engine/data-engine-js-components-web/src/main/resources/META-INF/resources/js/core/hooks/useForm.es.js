/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useThunk} from '@liferay/frontend-js-react-web';
import React, {useCallback, useContext, useReducer} from 'react';

import {createReducer} from '../reducers/createReducer.es';
import {useConfig} from './useConfig.es';
import {useDataView} from './useDataView.es';

const FormDispatchContext = React.createContext(() => {});

FormDispatchContext.displayName = 'FormDispatchContext';

const FormStateContext = React.createContext({editingLanguageId: 'en_US'});

FormStateContext.displayName = 'FormStateContext';

/**
 * This is a no-op implementation for the store, a dispatch call will
 * not call any action on the FormProvider and the state will always
 * be the value passed through the value property.
 *
 * This is a temporary implementation that works as an intermediate
 * provider and compatibility layer for the React and Metal.js frontier
 * in FormBuilder. The one who actually causes an action is the
 * LayoutProvider when that component is used.
 *
 * <LayoutProvider> -> Metal.js
 *  <FormBuilder> -> Metal.js
 *   <FormNoop> -> React.js with Adapter
 *    <FormNoopProvider> -> React.js
 *     ...
 *    </FormNoopProvider>
 *   </FormNoop>
 *  </FormBuilder>
 * </LayoutProvider>
 */
export function FormNoopProvider({children, initialState, onAction, value}) {
	const [, dispatch] = useThunk([{}, onAction]);

	return (
		<FormDispatchContext.Provider value={dispatch}>
			<FormStateContext.Provider value={{...initialState, ...value}}>
				{children}
			</FormStateContext.Provider>
		</FormDispatchContext.Provider>
	);
}

/**
 * Propagate Action is used in conjunction with useReducer that "listens"
 * for any action in a dispatch call.
 *
 * const [state, dispatch] = usePropagateAction(useReducer(...));
 */
const usePropagateAction = ([state, dispatch], onAction) => {
	const callBack = useCallback(
		(action) => {
			dispatch(action);
			onAction?.(action);
		},
		[dispatch, onAction]
	);

	return [state, callBack];
};

/**
 * Form Provider is a "store" that is compatible with the concept of
 * thunk but has no coupling to any specific reducer, the reducers
 * can be configured through the property, as well as configuring
 * the data init function and the initial state.
 *
 * <FormProvider
 *  reducers={[dragAndDropReducer, fieldEditableReducer]}
 *  value={state}
 * >
 * 	...
 * </FormProvider>
 *
 * Reducers receive the extra config property in addition to state
 * and action, it is mandatory that ConfigProvider be declared
 * before FormProvider.
 *
 * <ConfigProvider>
 *  <FormProvider>
 *   ...
 *  </FormProvider>
 * </ConfigProvider>
 *
 * FormProvider is built to be composed and can be declared more
 * than once in the application to deal with the same structure in
 * some part of the application tree. The real example is Sidebar,
 * which has the same `pages` structure that needs to be manipulated
 * but is isolated from the application and the main store.
 *
 * <ConfigProvider>
 *  <FormProvider> <- Main Store
 *   <FormBuilder>
 *    <Pages/>
 *    <Sidebar>
 *     <FormProvider> <- Isolated state
 *      <Pages />
 *     </FormProvider>
 *    </Sidebar>
 *   </FormBuilder>
 *  </FormProvider>
 * </ConfigProvider>
 */
export function FormProvider({
	children,
	init = (props) => props,
	initialState = {},
	onAction,
	reducers,
	value,
}) {
	const config = useConfig();

	const [state, dispatch] = useThunk(
		usePropagateAction(
			useReducer(
				createReducer(reducers, config),
				{...initialState, ...value},
				(props) => init(props, config)
			),
			onAction
		)
	);

	return (
		<FormDispatchContext.Provider value={dispatch}>
			<FormStateContext.Provider value={state}>
				{typeof children === 'function' ? children(state) : children}
			</FormStateContext.Provider>
		</FormDispatchContext.Provider>
	);
}

FormProvider.displayName = 'FormProvider';

export function useForm() {
	return useContext(FormDispatchContext);
}

export function useFormState({schema} = {}) {
	return useDataView(useContext(FormStateContext), schema);
}
