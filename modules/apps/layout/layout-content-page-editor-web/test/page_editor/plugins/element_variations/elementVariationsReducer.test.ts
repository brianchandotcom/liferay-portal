/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ElementVariation,
	createElementVariation,
	createInitialState,
	reducer,
} from '../../../../src/main/resources/META-INF/resources/page_editor/plugins/element_variations/elementVariationsReducer';

function buildElementVariation(
	properties: Partial<ElementVariation> = {}
): ElementVariation {
	return {
		audienceEntryERC: '',
		externalReferenceCode: '',
		hide: false,
		html: '',
		js: '',
		key: 'key-1',
		name: '',
		segmentsExperienceERC: 'experience-1',
		targetElement: '',
		...properties,
	};
}

describe('elementVariationsReducer', () => {
	describe('createElementVariation', () => {
		it('creates an empty variation for the given experience with a unique key', () => {
			const elementVariation = createElementVariation('experience-1');

			expect(elementVariation.segmentsExperienceERC).toBe('experience-1');
			expect(elementVariation.name).toBe('');
			expect(elementVariation.hide).toBe(false);
			expect(elementVariation.key).toBeTruthy();

			expect(createElementVariation('experience-1').key).not.toBe(
				elementVariation.key
			);
		});
	});

	describe('createInitialState', () => {
		it('starts with no draft and no variations when none are loaded', () => {
			expect(createInitialState([])).toEqual({
				draftElementVariation: null,
				elementVariations: [],
			});
		});

		it('assigns a key to each loaded variation', () => {
			const {draftElementVariation, elementVariations} =
				createInitialState([
					{
						audienceEntryERC: '',
						externalReferenceCode: 'erc-1',
						hide: false,
						html: '',
						js: '',
						name: 'Variation 1',
						segmentsExperienceERC: 'experience-1',
						targetElement: '#main',
					},
				]);

			expect(draftElementVariation).toBeNull();
			expect(elementVariations).toHaveLength(1);
			expect(elementVariations[0].name).toBe('Variation 1');
			expect(elementVariations[0].key).toBeTruthy();
		});
	});

	describe('reducer', () => {
		it('sets the draft on CREATE_ELEMENT_VARIATION_DRAFT', () => {
			const draftElementVariation = buildElementVariation();

			const state = reducer(
				{draftElementVariation: null, elementVariations: []},
				{draftElementVariation, type: 'CREATE_ELEMENT_VARIATION_DRAFT'}
			);

			expect(state.draftElementVariation).toBe(draftElementVariation);
		});

		it('merges properties into the draft on UPDATE_ELEMENT_VARIATION_DRAFT', () => {
			const state = reducer(
				{
					draftElementVariation: buildElementVariation(),
					elementVariations: [],
				},
				{
					properties: {hide: true, name: 'Renamed'},
					type: 'UPDATE_ELEMENT_VARIATION_DRAFT',
				}
			);

			expect(state.draftElementVariation?.name).toBe('Renamed');
			expect(state.draftElementVariation?.hide).toBe(true);
		});

		it('appends a new draft and clears it on SAVE_ELEMENT_VARIATION_DRAFT', () => {
			const draftElementVariation = buildElementVariation({key: 'new'});

			const state = reducer(
				{draftElementVariation, elementVariations: []},
				{type: 'SAVE_ELEMENT_VARIATION_DRAFT'}
			);

			expect(state.draftElementVariation).toBeNull();
			expect(state.elementVariations).toEqual([draftElementVariation]);
		});

		it('updates an existing variation in place on SAVE_ELEMENT_VARIATION_DRAFT', () => {
			const existingElementVariation = buildElementVariation({
				key: 'key-1',
				name: 'Old',
			});

			const state = reducer(
				{
					draftElementVariation: {
						...existingElementVariation,
						name: 'New',
					},
					elementVariations: [existingElementVariation],
				},
				{type: 'SAVE_ELEMENT_VARIATION_DRAFT'}
			);

			expect(state.elementVariations).toHaveLength(1);
			expect(state.elementVariations[0].name).toBe('New');
		});

		it('loads a variation into the draft on EDIT_ELEMENT_VARIATION', () => {
			const elementVariation = buildElementVariation({key: 'key-1'});

			const state = reducer(
				{
					draftElementVariation: null,
					elementVariations: [elementVariation],
				},
				{key: 'key-1', type: 'EDIT_ELEMENT_VARIATION'}
			);

			expect(state.draftElementVariation).toEqual(elementVariation);
		});

		it('clears the draft on CANCEL_ELEMENT_VARIATION_DRAFT', () => {
			const state = reducer(
				{
					draftElementVariation: buildElementVariation(),
					elementVariations: [],
				},
				{type: 'CANCEL_ELEMENT_VARIATION_DRAFT'}
			);

			expect(state.draftElementVariation).toBeNull();
		});
	});
});
