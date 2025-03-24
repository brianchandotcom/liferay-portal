/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';
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
import getUuid from '../utils/getUuid';
import isFieldInvalid from '../utils/isFieldInvalid';
import isStructureInvalid from '../utils/isStructureInvalid';
import openDeletionModal from '../utils/openDeletionModal';

const DEFAULT_STRUCTURE_LABEL = Liferay.Language.get('untitled-structure');

type Status = 'new' | 'draft' | 'published';

type Spaces = 'all' | string[];

export type Uuid = string & {__brand: 'Uuid'};

export type State = {
	erc: string;
	error: string | null;
	fields: Map<Uuid, Field>;
	id: number | null;
	invalids: Set<Uuid>;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	publishedFields: Set<Uuid>;
	selection: Uuid[];
	spaces: Spaces;
	status: Status;
	uuid: Uuid;
};

const INITIAL_STATE: State = {
	erc: getRandomId(),
	error: null,
	fields: new Map(),
	id: null,
	invalids: new Set(),
	label: {
		[Liferay.ThemeDisplay.getDefaultLanguageId()]: DEFAULT_STRUCTURE_LABEL,
	},
	name: objectDefinitionUtils.normalizeName(DEFAULT_STRUCTURE_LABEL),
	publishedFields: new Set(),
	selection: [],
	spaces: [],
	status: 'new',
	uuid: getUuid(),
};

type AddFieldAction = {field: Field; type: 'add-field'};

type CreateStructureAction = {
	id: number;
	type: 'create-structure';
};

type DeleteFieldAction = {type: 'delete-field'; uuid: Uuid};

type DeleteSelectionAction = {type: 'delete-selection'};

type PublishStructureAction = {id?: number; type: 'publish-structure'};

type SetErrorAction = {error: string | null; type: 'set-error'};

type SetSelection = {
	selection: State['selection'];
	type: 'set-selection';
};

type UpdateFieldAction = {
	erc?: string;
	indexableConfig?: Field['indexableConfig'];
	label?: Liferay.Language.LocalizedValue<string>;
	localized?: boolean;
	name?: string;
	newName?: string;
	picklistId?: string;
	required?: boolean;
	settings?: Field['settings'];
	type: 'update-field';
	uuid: Uuid;
};

type UpdateStructureAction = {
	erc?: string;
	label?: Liferay.Language.LocalizedValue<string>;
	name?: string;
	spaces?: Spaces;
	type: 'update-structure';
};

export type Action =
	| AddFieldAction
	| CreateStructureAction
	| DeleteFieldAction
	| DeleteSelectionAction
	| PublishStructureAction
	| SetErrorAction
	| SetSelection
	| UpdateFieldAction
	| UpdateStructureAction;

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'add-field': {
			const {field} = action;

			const name = findAvailableFieldName(state.fields, field.name);

			const nextFields = new Map(state.fields);

			nextFields.set(field.uuid, {...field, name});

			return {...state, fields: nextFields, selection: [field.uuid]};
		}
		case 'create-structure': {
			return {
				...state,
				error: INITIAL_STATE.error,
				id: action.id,
				status: 'draft' as Status,
			};
		}
		case 'delete-field': {
			if (state.fields.size === 1) {
				openDeletionModal();

				return state;
			}

			const {uuid} = action;

			const nextFields = new Map(state.fields);

			nextFields.delete(uuid);

			let nextState = {...state, fields: nextFields};

			if (state.selection.includes(uuid)) {
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

			if (nextFields.size === 0) {
				openDeletionModal();

				return state;
			}

			return {
				...state,
				fields: nextFields,
				selection: INITIAL_STATE.selection,
			};
		}
		case 'publish-structure': {
			const nextState = {
				...state,
				error: INITIAL_STATE.error,
				publishedFields: new Set(
					Array.from(state.fields.values()).map((field) => field.uuid)
				),
				status: 'published' as Status,
			};

			if (action.id) {
				return {...nextState, id: action.id};
			}

			return nextState;
		}
		case 'set-error':
			return {
				...state,
				error: action.error,
				selection: [state.uuid],
			};
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
				picklistId,
				required,
				settings,
				uuid,
			} = action;

			const nextFields: State['fields'] = new Map(state.fields);

			const field = nextFields.get(uuid);

			if (!field) {
				return state;
			}

			const nextField = {
				...field,
				erc: erc ?? field.erc,
				indexableConfig: indexableConfig ?? field.indexableConfig,
				label: label ?? field.label,
				localized: localized ?? field.localized,
				name: name ?? field.name,
				required: required ?? field.required,
				settings: settings ?? field.settings,
			};

			if (picklistId && 'picklistId' in nextField) {
				nextField.picklistId = picklistId;
			}

			nextFields.set(nextField.uuid, nextField);

			const invalids = new Set(state.invalids);

			if (isFieldInvalid(nextField)) {
				invalids.add(nextField.uuid);
			}
			else {
				invalids.delete(nextField.uuid);
			}

			return {
				...state,
				fields: nextFields,
				invalids,
				selection: [nextField.uuid],
			};
		}
		case 'update-structure': {
			let nextErc = state.erc;
			let nextLabel = state.label;
			let nextName = state.name;

			if (!isNullOrUndefined(action.erc)) {
				nextErc = action.erc;
			}

			if (!isNullOrUndefined(action.label)) {
				nextLabel = action.label;
			}

			if (!isNullOrUndefined(action.name)) {
				nextName = action.name;
			}

			const nextState = {
				...state,
				erc: nextErc,
				label: nextLabel,
				name: nextName,
				spaces: action.spaces ?? state.spaces,
			};

			const invalids = new Set(state.invalids);

			if (isStructureInvalid(nextState)) {
				invalids.add(state.uuid);
			}
			else {
				invalids.delete(state.uuid);
			}

			return {
				...nextState,
				invalids,
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
