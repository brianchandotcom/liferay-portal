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

import React, {useContext, useEffect, useReducer} from 'react';

import {EVENT_TYPES} from '../actions/types.es';
import {createReducer} from '../reducers/index.es';
import {useThunk, useThunkDispatch} from './useThunk.es';

const FormContext = React.createContext(() => {});

FormContext.displayName = 'FormContext';

export const FormNoopProvider = ({children, onEvent}) => (
	<FormContext.Provider
		value={useThunkDispatch(({payload, type}) => onEvent(type, payload))}
	>
		{children}
	</FormContext.Provider>
);

export const FormProvider = ({children, onEvent, value}) => {
	const [state, dispatch] = useThunk(
		useReducer(createReducer(onEvent), value)
	);

	useEffect(() => dispatch({payload: value, type: EVENT_TYPES.ALL}), [
		dispatch,
		value,
	]);

	return (
		<FormContext.Provider value={dispatch}>
			{children(state)}
		</FormContext.Provider>
	);
};

export const useForm = () => {
	return useContext(FormContext);
};
