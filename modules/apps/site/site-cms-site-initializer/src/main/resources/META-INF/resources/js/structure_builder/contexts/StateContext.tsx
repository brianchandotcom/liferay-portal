/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {objectDefinitionUtils} from '@liferay/object-js-components-web';
import React, {
	Dispatch,
	ReactNode,
	createContext,
	useContext,
	useReducer,
} from 'react';

import {ObjectField} from '../types/ObjectDefinition';
import {Field} from '../utils/field';
import findAvailableFieldName from '../utils/findAvailableFieldName';
import getRandomId from '../utils/getRandomId';
import updateFields from '../utils/updateFields';

const DEFAULT_STRUCTURE_LABEL = Liferay.Language.get('untitled-structure');

type Status = 'new' | 'draft' | 'published';

export type State = {
	erc: string;
	error: string | null;
	fields: Map<string, Field>;
	id: number | null;
	label: string;
	name: string;
	selectedItem: {type: 'structure'} | {name: string; type: 'field'};
	status: Status;
};

const INITIAL_STATE: State = {
	erc: getRandomId(),
	error: null,
	fields: new Map(),
	id: null,
	label: DEFAULT_STRUCTURE_LABEL,
	name: objectDefinitionUtils.normalizeName(DEFAULT_STRUCTURE_LABEL),
	selectedItem: {type: 'structure'},
	status: 'new',
};

type AddFieldAction = {field: Field; type: 'add-field'};

type CreateStructureAction = {
	id: number;
	name: string;
	objectFields: ObjectField[];
	type: 'create-structure';
};

type DeleteFieldAction = {fieldName: Field['name']; type: 'delete-field'};

type PublishStructureAction = {type: 'publish-structure'};

type SelectItemAction = {
	item: {type: 'structure'} | {name: string; type: 'field'};
	type: 'select-item';
};

type SetErrorAction = {error: string | null; type: 'set-error'};

type SetLabelAction = {label: string; type: 'set-label'};

type UpdateStructureAction = {
	erc?: string;
	name?: string;
	objectFields?: ObjectField[];
	type: 'update-structure';
};

export type Action =
	| AddFieldAction
	| CreateStructureAction
	| DeleteFieldAction
	| PublishStructureAction
	| SelectItemAction
	| SetErrorAction
	| SetLabelAction
	| UpdateStructureAction;

function reducer(state: State, action: Action) {
	switch (action.type) {
		case 'add-field': {
			const {field} = action;

			const name = findAvailableFieldName(state.fields, field.name);

			const nextFields = new Map(state.fields);

			nextFields.set(name, {...field, name});

			return {...state, fields: nextFields};
		}
		case 'create-structure': {
			const fields = updateFields(state.fields, action.objectFields);

			return {
				...state,
				error: null,
				fields,
				id: action.id,
				name: action.name,
				status: 'draft' as Status,
			};
		}
		case 'delete-field': {
			const {fieldName} = action;

			const nextFields = new Map(state.fields);

			nextFields.delete(fieldName);

			let nextState = {...state, fields: nextFields};

			if (
				'name' in state.selectedItem &&
				state.selectedItem.name === fieldName
			) {
				nextState = {
					...nextState,
					selectedItem: INITIAL_STATE.selectedItem,
				};
			}

			return nextState;
		}
		case 'publish-structure':
			return {...state, error: null, status: 'published' as Status};
		case 'update-structure': {
			let nextErc = state.erc;
			let nextFields = state.fields;
			let nextName = state.name;

			if (action.erc) {
				nextErc = action.erc;
			}

			if (action.objectFields) {
				nextFields = updateFields(state.fields, action.objectFields);
			}

			if (action.name) {
				nextName = action.name;
			}

			return {
				...state,
				erc: nextErc,
				error: null,
				fields: nextFields,
				name: nextName,
			};
		}
		case 'select-item': {
			const {item} = action;

			return {...state, selectedItem: item};
		}
		case 'set-error':
			return {...state, error: action.error};
		case 'set-label':
			return {...state, label: action.label};
		default:
			return state;
	}
}

const StateContext = createContext<{dispatch: Dispatch<Action>; state: State}>({
	dispatch: () => {},
	state: INITIAL_STATE,
});

export default function StateContextProvider({
	children,
}: {
	children: ReactNode;
}) {
	const [state, dispatch] = useReducer<React.Reducer<State, Action>>(
		reducer,
		INITIAL_STATE
	);

	return (
		<StateContext.Provider value={{dispatch, state}}>
			{children}
		</StateContext.Provider>
	);
}

function useSelector<T>(selector: (state: State) => T) {
	const {state} = useContext(StateContext);

	return selector(state);
}

function useStateDispatch() {
	return useContext(StateContext).dispatch;
}

export {StateContext, StateContextProvider, useSelector, useStateDispatch};
