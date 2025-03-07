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

import {Field} from '../utils/field';
import findAvailableFieldName from '../utils/findAvailableFieldName';
import getRandomId from '../utils/getRandomId';

const DEFAULT_STRUCTURE_LABEL = Liferay.Language.get('untitled-structure');

type Status = 'new' | 'draft' | 'published';

export type State = {
	erc: string;
	error: string | null;
	fields: Map<string, Field>;
	id: number | null;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	publishedFields: Set<Field['name']>;
	selection: Field['name'][];
	status: Status;
};

const INITIAL_STATE: State = {
	erc: getRandomId(),
	error: null,
	fields: new Map(),
	id: null,
	label: {
		[Liferay.ThemeDisplay.getDefaultLanguageId()]: DEFAULT_STRUCTURE_LABEL,
	},
	name: objectDefinitionUtils.normalizeName(DEFAULT_STRUCTURE_LABEL),
	publishedFields: new Set(),
	selection: [],
	status: 'new',
};

type AddFieldAction = {field: Field; type: 'add-field'};

type CreateStructureAction = {
	id: number;
	name: string;
	type: 'create-structure';
};

type DeleteFieldAction = {fieldName: Field['name']; type: 'delete-field'};

type DeleteSelectionAction = {type: 'delete-selection'};

type PublishStructureAction = {type: 'publish-structure'};

type SaveStructureAction = {
	type: 'save-structure';
};

type SetErrorAction = {error: string | null; type: 'set-error'};

type SetLabelAction = {
	label: Liferay.Language.LocalizedValue<string>;
	type: 'set-label';
};

type SetSelection = {
	selection: State['selection'];
	type: 'set-selection';
};

type UpdateFieldAction = {
	erc?: string;
	indexableConfig?: Field['indexableConfig'];
	label?: Liferay.Language.LocalizedValue<string>;
	localized?: boolean;
	name: string;
	newName?: string;
	required?: boolean;
	settings?: Field['settings'];
	type: 'update-field';
};

type UpdateStructureAction = {
	erc?: string;
	name?: string;
	type: 'update-structure';
};

export type Action =
	| AddFieldAction
	| CreateStructureAction
	| DeleteFieldAction
	| DeleteSelectionAction
	| PublishStructureAction
	| SaveStructureAction
	| SetErrorAction
	| SetLabelAction
	| SetSelection
	| UpdateFieldAction
	| UpdateStructureAction;

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'add-field': {
			const {field} = action;

			const name = findAvailableFieldName(state.fields, field.name);

			const nextFields = new Map(state.fields);

			nextFields.set(name, {...field, name});

			return {...state, fields: nextFields};
		}
		case 'create-structure': {
			return {
				...state,
				error: null,
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

			if (state.selection.includes(fieldName)) {
				nextState = {
					...nextState,
					selection: INITIAL_STATE.selection,
				};
			}

			return nextState;
		}
		case 'delete-selection': {
			const nextFields = new Map(state.fields);

			for (const fieldName of state.selection) {
				nextFields.delete(fieldName);
			}

			return {
				...state,
				fields: nextFields,
				selection: INITIAL_STATE.selection,
			};
		}
		case 'publish-structure': {
			return {
				...state,
				error: null,
				publishedFields: new Set(
					Array.from(state.fields.values()).map((field) => field.name)
				),
				status: 'published' as Status,
			};
		}
		case 'save-structure': {
			let nextPublishedFields = state.publishedFields;

			if (state.status === 'published') {
				nextPublishedFields = new Set(
					Array.from(state.fields.values()).map((field) => field.name)
				);
			}

			return {
				...state,
				error: null,
				publishedFields: nextPublishedFields,
			};
		}
		case 'set-error':
			return {...state, error: action.error};
		case 'set-label':
			return {...state, label: action.label};
		case 'set-selection': {
			const {selection} = action;

			return {...state, selection};
		}
		case 'update-field': {
			const {
				erc,
				indexableConfig,
				label,
				localized,
				name,
				newName,
				required,
				settings,
			} = action;

			const nextFields = new Map(state.fields);

			const field = nextFields.get(name);

			if (!field) {
				return state;
			}

			const nextField = {
				...field,
				erc: erc ?? field.erc,
				indexableConfig: indexableConfig ?? field.indexableConfig,
				label: label ?? field.label,
				localized: localized ?? field.localized,
				name: newName ?? field.name,
				required: required ?? field.required,
				settings: settings ?? field.settings,
			};

			if (newName) {
				nextFields.delete(name);
			}

			nextFields.set(nextField.name, nextField);

			return {
				...state,
				fields: nextFields,
				selection: [nextField.name],
			};
		}
		case 'update-structure': {
			let nextErc = state.erc;
			let nextName = state.name;

			if (action.erc) {
				nextErc = action.erc;
			}

			if (action.name) {
				nextName = action.name;
			}

			return {
				...state,
				erc: nextErc,
				error: null,
				name: nextName,
			};
		}
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
	initialState,
}: {
	children: ReactNode;
	initialState: State | null;
}) {
	const [state, dispatch] = useReducer<React.Reducer<State, Action>>(
		reducer,
		initialState ?? INITIAL_STATE
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
