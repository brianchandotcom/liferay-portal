/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	createContext,
	useContext,
	useReducer,
} from 'react';

import {Field, MultiselectField, SingleSelectField} from '../utils/field';
import findAvailableFieldName from '../utils/findAvailableFieldName';
import getRandomId from '../utils/getRandomId';
import getUuid from '../utils/getUuid';
import normalizeName from '../utils/normalizeName';
import openDeletionModal from '../utils/openDeletionModal';
import {
	ValidationError,
	validateField,
	validateStructure,
} from '../utils/validation';

const DEFAULT_STRUCTURE_LABEL = Liferay.Language.get('untitled-structure');

type History = {
	deletedFields: boolean;
};

type Status = 'new' | 'draft' | 'published';

type Spaces = 'all' | string[];

export type Uuid = string & {__brand: 'Uuid'};

export type State = {
	erc: string;
	error: string | null;
	fields: Map<Uuid, Field>;
	history: History;
	id: number | null;
	invalids: Map<Uuid, Set<ValidationError>>;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	publishedFields: Set<Uuid>;
	selection: Uuid[];
	spaces: Spaces;
	status: Status;
	uuid: Uuid;
};

const INITIAL_STATE: State = {
	erc: '',
	error: null,
	fields: new Map(),
	history: {
		deletedFields: false,
	},
	id: null,
	invalids: new Map(),
	label: {
		[Liferay.ThemeDisplay.getDefaultLanguageId()]: DEFAULT_STRUCTURE_LABEL,
	},
	name: normalizeName(DEFAULT_STRUCTURE_LABEL),
	publishedFields: new Set(),
	selection: [],
	spaces: [],
	status: 'new',
	uuid: getUuid(),
};

type AddFieldAction = {field: Field; type: 'add-field'};

type AddValidationError = {
	error: ValidationError;
	type: 'add-validation-error';
	uuid: Uuid;
};

type ClearErrorAction = {
	type: 'clear-error';
};

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
	picklistId?: number;
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

type ValidateAction = {
	invalids: State['invalids'];
	type: 'validate';
};

export type Action =
	| AddFieldAction
	| AddValidationError
	| ClearErrorAction
	| CreateStructureAction
	| DeleteFieldAction
	| DeleteSelectionAction
	| PublishStructureAction
	| SetErrorAction
	| SetSelection
	| UpdateFieldAction
	| UpdateStructureAction
	| ValidateAction;

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'add-field': {
			const {field} = action;

			const name = findAvailableFieldName(state.fields, field.name);

			const nextFields = new Map(state.fields);

			nextFields.set(field.uuid, {...field, name});

			return {...state, fields: nextFields, selection: [field.uuid]};
		}
		case 'add-validation-error': {
			const {error, uuid} = action;

			const invalids = new Map(state.invalids);

			const currentErrors = new Set(invalids.get(uuid));

			currentErrors.add(error);

			invalids.set(uuid, currentErrors);

			return {
				...state,
				invalids,
			};
		}
		case 'clear-error': {
			return {
				...state,
				error: INITIAL_STATE.error,
			};
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

			const invalids = new Map(state.invalids);

			invalids.delete(uuid);

			let nextState = {...state, fields: nextFields, invalids};

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

			// Prepare updated field

			const nextField: Field = {
				...field,
				erc: erc ?? field.erc,
				indexableConfig: indexableConfig ?? field.indexableConfig,
				label: label ?? field.label,
				localized: localized ?? field.localized,
				name: name ?? field.name,
				required: required ?? field.required,
				settings: settings ?? field.settings,
			};

			if (picklistId) {
				(nextField as SingleSelectField | MultiselectField).picklistId =
					picklistId;
			}

			nextFields.set(nextField.uuid, nextField);

			// Validate the data sent in the action

			const invalids = new Map(state.invalids);

			const {type: _, ...data} = action;

			const errors = validateField({
				currentErrors: invalids.get(nextField.uuid),
				data,
			});

			if (errors.size) {
				invalids.set(nextField.uuid, errors);
			}
			else {
				invalids.delete(nextField.uuid);
			}

			// Return new state

			return {
				...state,
				fields: nextFields,
				invalids,
				selection: [nextField.uuid],
			};
		}
		case 'update-structure': {

			// Prepare updated state

			const nextState = {
				...state,
				erc: action.erc ?? state.erc,
				label: action.label ?? state.label,
				name: action.name ?? state.name,
				spaces: action.spaces ?? state.spaces,
			};

			// Validate the data sent in the action

			const invalids = new Map(state.invalids);

			const errors = validateStructure({
				currentErrors: invalids.get(state.uuid),
				data: action,
			});

			if (errors.size) {
				invalids.set(state.uuid, errors);
			}
			else {
				invalids.delete(state.uuid);
			}

			// Return new state

			return {
				...nextState,
				invalids,
			};
		}
		case 'validate': {
			const {invalids} = action;

			const [firstUuid] = [...invalids.keys()];

			return {
				...state,
				error: INITIAL_STATE.error,
				invalids,
				selection: [firstUuid],
			};
		}
		default:
			return state;
	}
}

function initState(state: State) {
	if (state.erc) {
		return state;
	}

	return {...state, erc: getRandomId()};
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
	const [state, dispatch] = useReducer<React.Reducer<State, Action>, State>(
		reducer,
		initialState ?? INITIAL_STATE,
		initState
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
