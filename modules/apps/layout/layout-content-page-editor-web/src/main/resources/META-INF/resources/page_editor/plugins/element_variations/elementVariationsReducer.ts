/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

export interface ElementVariation {
	audienceEntryERC: string;
	externalReferenceCode: string;
	hide: boolean;
	html: string;
	js: string;
	key: string;
	name: string;
	segmentsExperienceERC: string;
	targetElement: string;
}

export interface State {
	draftElementVariation: ElementVariation | null;
	elementVariations: ElementVariation[];
}

type Action =
	| {
			draftElementVariation: ElementVariation;
			type: 'CREATE_ELEMENT_VARIATION_DRAFT';
	  }
	| {key: string; type: 'DELETE_ELEMENT_VARIATION'}
	| {key: string; type: 'EDIT_ELEMENT_VARIATION'}
	| {
			properties: Partial<ElementVariation>;
			type: 'UPDATE_ELEMENT_VARIATION_DRAFT';
	  }
	| {type: 'CANCEL_ELEMENT_VARIATION_DRAFT' | 'SAVE_ELEMENT_VARIATION_DRAFT'};

export function createElementVariation(
	segmentsExperienceERC: string
): ElementVariation {
	return {
		audienceEntryERC: '',
		externalReferenceCode: uuidv4(),
		hide: false,
		html: '',
		js: '',
		key: uuidv4(),
		name: '',
		segmentsExperienceERC,
		targetElement: '',
	};
}

export function createInitialState(
	elementVariations: Omit<ElementVariation, 'key'>[]
): State {
	return {
		draftElementVariation: null,
		elementVariations: elementVariations.map((elementVariation) => ({
			...elementVariation,
			key: uuidv4(),
		})),
	};
}

export function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'CANCEL_ELEMENT_VARIATION_DRAFT':
			return {...state, draftElementVariation: null};

		case 'DELETE_ELEMENT_VARIATION':
			return {
				...state,
				elementVariations: state.elementVariations.filter(
					(elementVariation) => elementVariation.key !== action.key
				),
			};

		case 'EDIT_ELEMENT_VARIATION': {
			const elementVariation = state.elementVariations.find(
				(elementVariation) => elementVariation.key === action.key
			);

			if (!elementVariation) {
				return state;
			}

			return {...state, draftElementVariation: {...elementVariation}};
		}

		case 'SAVE_ELEMENT_VARIATION_DRAFT': {
			const {draftElementVariation, elementVariations} = state;

			if (!draftElementVariation) {
				return state;
			}

			const existing = elementVariations.some(
				(elementVariation) =>
					elementVariation.key === draftElementVariation.key
			);

			return {
				draftElementVariation: null,
				elementVariations: existing
					? elementVariations.map((elementVariation) =>
							elementVariation.key === draftElementVariation.key
								? draftElementVariation
								: elementVariation
						)
					: [...elementVariations, draftElementVariation],
			};
		}

		case 'CREATE_ELEMENT_VARIATION_DRAFT':
			return {
				...state,
				draftElementVariation: action.draftElementVariation,
			};

		case 'UPDATE_ELEMENT_VARIATION_DRAFT':
			return {
				...state,
				draftElementVariation: state.draftElementVariation
					? {...state.draftElementVariation, ...action.properties}
					: null,
			};

		default:
			return state;
	}
}
