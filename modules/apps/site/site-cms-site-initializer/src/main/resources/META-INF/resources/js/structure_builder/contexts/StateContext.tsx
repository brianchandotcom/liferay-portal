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
import findAvailableFieldName from '../utils/findAvailableFieldName';
import updateFields from '../utils/updateFields';

const DEFAULT_STRUCTURE_LABEL = Liferay.Language.get('untitled-structure');

export type FieldType = 'text';

type Status = 'new' | 'draft' | 'published';

export type Field = {
	erc?: number;
	label: string;
	name: string;
	type: FieldType;
};

export type State = {
	error: string | null;
	fields: Map<string, Field>;
	id: number | null;
	label: string;
	name: string;
	status: Status;
};

const INITIAL_STATE: State = {
	error: null,
	fields: new Map(),
	id: null,
	label: DEFAULT_STRUCTURE_LABEL,
	name: objectDefinitionUtils.normalizeName(DEFAULT_STRUCTURE_LABEL),
	status: 'new',
};

type AddFieldAction = {field: Field; type: 'add-field'};

type CreateStructureAction = {
	id: number;
	name: string;
	objectFields: ObjectField[];
	type: 'create-structure';
};

type PublishStructureAction = {type: 'publish-structure'};

type SetErrorAction = {error: string | null; type: 'set-error'};

type setLabelAction = {label: string; type: 'set-label'};

type UpdateStructureAction = {
	objectFields: ObjectField[];
	type: 'update-structure';
};

type Action =
	| AddFieldAction
	| CreateStructureAction
	| PublishStructureAction
	| UpdateStructureAction
	| SetErrorAction
	| setLabelAction;

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
		case 'publish-structure':
			return {...state, error: null, status: 'published' as Status};
		case 'update-structure': {
			const fields = updateFields(state.fields, action.objectFields);

			return {
				...state,
				error: null,
				fields,
			};
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

function useStateDispatch() {
	return useContext(StateContext).dispatch;
}

function useStructureError() {
	const {state} = useContext(StateContext);

	return state.error;
}

function useStructureFields() {
	const {state} = useContext(StateContext);

	return Array.from(state.fields.values());
}

function useStructureId() {
	const {state} = useContext(StateContext);

	return state.id;
}

function useStructureLabel() {
	const {state} = useContext(StateContext);

	return state.label;
}

function useStructureName() {
	const {state} = useContext(StateContext);

	return state.name;
}

function useStructureStatus() {
	const {state} = useContext(StateContext);

	return state.status;
}

export {
	StateContextProvider,
	useStateDispatch,
	useStructureError,
	useStructureFields,
	useStructureId,
	useStructureLabel,
	useStructureName,
	useStructureStatus,
};
