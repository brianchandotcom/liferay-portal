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

import {ReferencedStructure, Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import actionGeneratesChanges from '../utils/actionGeneratesChanges';
import {
	Field,
	MultiselectField,
	SingleSelectField,
	getDefaultField,
} from '../utils/field';
import findAvailableFieldName from '../utils/findAvailableFieldName';
import getRandomId from '../utils/getRandomId';
import getRandomName from '../utils/getRandomName';
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

export type State = {
	error: string | null;
	history: History;
	invalids: Map<Uuid, Set<ValidationError>>;
	publishedFields: Set<Uuid>;
	selection: Uuid[];
	structure: Structure;
	unsavedChanges: boolean;
};

const INITIAL_STATE: State = {
	error: null,
	history: {
		deletedFields: false,
	},
	invalids: new Map(),
	publishedFields: new Set(),
	selection: [],
	structure: {
		erc: '',
		fields: new Map(),
		id: null,
		label: {
			[Liferay.ThemeDisplay.getDefaultLanguageId()]:
				DEFAULT_STRUCTURE_LABEL,
		},
		name: normalizeName(DEFAULT_STRUCTURE_LABEL),
		spaces: [],
		status: 'new',
		uuid: getUuid(),
	},
	unsavedChanges: false,
};

type AddFieldAction = {field: Field; type: 'add-field'};

type AddReferencedStructuresAction = {
	ercs: string[];
	type: 'add-referenced-structures';
};

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
	spaces?: Structure['spaces'];
	type: 'update-structure';
};

type ValidateAction = {
	invalids: State['invalids'];
	type: 'validate';
};

export type Action =
	| AddFieldAction
	| AddReferencedStructuresAction
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
	if (actionGeneratesChanges(action.type)) {
		state = {...state, unsavedChanges: true};
	}

	switch (action.type) {
		case 'add-field': {
			const {field} = action;

			const {structure} = state;

			const name = findAvailableFieldName(structure.fields, field.name);

			const nextFields = new Map(structure.fields);

			nextFields.set(field.uuid, {...field, name});

			return {
				...state,
				selection: [field.uuid],
				structure: {...structure, fields: nextFields},
			};
		}
		case 'add-referenced-structures': {
			const {ercs} = action;

			const {structure} = state;

			const nextFields = new Map(structure.fields);

			let selection: State['selection'] = [];

			for (const [i, erc] of ercs.entries()) {
				const uuid = getUuid();
				const name = getRandomName();

				const referencedStructure: ReferencedStructure = {
					erc,
					name,
					parent: structure.uuid,
					type: 'referenced-structure',
					uuid,
				};

				nextFields.set(referencedStructure.uuid, referencedStructure);

				if (i === 0) {
					selection = [uuid];
				}
			}

			return {
				...state,
				selection,
				structure: {...structure, fields: nextFields},
			};
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
			const {structure} = state;

			return {
				...state,
				error: INITIAL_STATE.error,
				structure: {
					...structure,
					id: action.id,
					status: 'draft' as Structure['status'],
				},
			};
		}
		case 'delete-field': {
			const {structure} = state;

			if (structure.fields.size === 1) {
				openDeletionModal();

				return state;
			}

			const {uuid} = action;

			const nextFields = new Map(structure.fields);

			nextFields.delete(uuid);

			const invalids = new Map(state.invalids);

			invalids.delete(uuid);

			let nextState: State = {
				...state,
				invalids,
				structure: {...state.structure, fields: nextFields},
			};

			if (state.selection.includes(uuid)) {
				nextState = {
					...nextState,
					selection: INITIAL_STATE.selection,
				};
			}

			if (state.publishedFields.has(uuid)) {
				nextState = {
					...nextState,
					history: {...nextState.history, deletedFields: true},
				};
			}

			return nextState;
		}
		case 'delete-selection': {
			const {structure} = state;

			const nextFields = new Map(structure.fields);

			for (const fieldName of state.selection) {
				nextFields.delete(fieldName);
			}

			if (nextFields.size === 0) {
				openDeletionModal();

				return state;
			}

			return {
				...state,
				selection: INITIAL_STATE.selection,
				structure: {
					...structure,
					fields: nextFields,
				},
			};
		}
		case 'publish-structure': {
			const {structure} = state;

			let nextStructure = {
				...structure,
				status: 'published' as Structure['status'],
			};

			if (action.id) {
				nextStructure = {...nextStructure, id: action.id};
			}

			return {
				...state,
				error: INITIAL_STATE.error,
				history: INITIAL_STATE.history,
				publishedFields: new Set(
					Array.from(structure.fields.values()).map(
						(field) => field.uuid
					)
				),
				structure: nextStructure,
				unsavedChanges: false,
			};
		}
		case 'set-error':
			return {
				...state,
				error: action.error,
				selection: [state.structure.uuid],
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

			const {structure} = state;

			const nextFields: Structure['fields'] = new Map(structure.fields);

			const field = nextFields.get(uuid) as Field;

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
				invalids,
				selection: [nextField.uuid],
				structure: {
					...structure,
					fields: nextFields,
				},
			};
		}
		case 'update-structure': {

			// Prepare updated state

			const {erc, label, name, spaces} = action;

			const {structure} = state;

			const nextState: State = {
				...state,
				structure: {
					...state.structure,
					erc: erc ?? structure.erc,
					label: label ?? structure.label,
					name: name ?? structure.name,
					spaces: spaces ?? structure.spaces,
				},
			};

			// Validate the data sent in the action

			const invalids = new Map(state.invalids);

			const errors = validateStructure({
				currentErrors: invalids.get(structure.uuid),
				data: {erc, label, name, spaces},
			});

			if (errors.size) {
				invalids.set(structure.uuid, errors);
			}
			else {
				invalids.delete(structure.uuid);
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

function initState(state: State): State {
	const {structure} = state;

	if (structure.erc) {
		return state;
	}

	return {
		...state,
		structure: {
			...structure,
			erc: getRandomId(),
			fields: getDefaultFields(structure.uuid),
		},
	};
}

const StateContext = createContext<{
	dispatch: Dispatch<Action>;
	state: State;
}>({
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

function getDefaultFields(structureUuid: Uuid) {
	const url = new URL(window.location.href);

	const type = url.searchParams.get('objectFolderExternalReferenceCode');

	const fields = new Map();

	const title = getDefaultField({
		label: Liferay.Language.get('title'),
		name: 'title',
		parent: structureUuid,
		type: 'text',
	});

	fields.set(title.uuid, title);

	if (type === 'L_CMS_FILE_TYPES') {
		const file = getDefaultField({
			label: Liferay.Language.get('file'),
			name: 'file',
			parent: structureUuid,
			type: 'upload',
		});

		fields.set(file.uuid, file);
	}

	return fields;
}

export {StateContext, StateContextProvider, useSelector, useStateDispatch};
