/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../../../../src/main/resources/META-INF/resources/js/core/actions/eventTypes.es';
import reducer from '../../../../src/main/resources/META-INF/resources/js/core/reducers/languageReducer.es';

describe('core/reducers/languageReducer', () => {
	describe('EVENT_TYPES.LANGUAGE.UPDATE', () => {
		it('returns the same state when no language is added or deleted', () => {
			const state = {availableLanguageIds: ['en_US']};

			const action = {
				payload: {activeLanguageIds: ['en_US']},
				type: EVENT_TYPES.LANGUAGE.UPDATE,
			};

			const newState = reducer(state, action);

			expect(newState).toBe(state);
		});

		it('adds a language to availableLanguageIds when a language is added', () => {
			const state = {availableLanguageIds: ['en_US']};

			const activeLanguageIds = ['en_US', 'pt_BR'];

			const action = {
				payload: {activeLanguageIds},
				type: EVENT_TYPES.LANGUAGE.UPDATE,
			};

			const {availableLanguageIds} = reducer(state, action);

			expect(availableLanguageIds).toEqual(['en_US', 'pt_BR']);

			expect(availableLanguageIds).not.toBe(activeLanguageIds);
		});

		it('deletes a language to availableLanguageIds when a language is deleted', () => {
			const focusedField = {
				instanceId: '12345678',
				localizedValue: {en_US: 'value', pt_BR: 'valor'},
			};
			const pages = [
				{
					localizedDescription: {
						en_US: 'description',
						pt_BR: 'descrição',
					},
					localizedTitle: {en_US: 'title', pt_BR: 'título'},
					rows: [{columns: [{fields: [focusedField]}]}],
				},
			];
			const state = {
				availableLanguageIds: ['en_US', 'pt_BR'],
				focusedField,
				pages,
			};

			const activeLanguageIds = ['en_US'];

			const action = {
				payload: {activeLanguageIds},
				type: EVENT_TYPES.LANGUAGE.UPDATE,
			};

			const {
				availableLanguageIds,
				focusedField: updatedFocusedField,
				pages: updatedPages,
			} = reducer(state, action);

			const expectedField = {
				instanceId: '12345678',
				localizedValue: {en_US: 'value'},
			};

			expect(availableLanguageIds).toEqual(['en_US']);

			expect(updatedFocusedField).toEqual(expectedField);

			expect(updatedPages).toEqual([
				{
					localizedDescription: {en_US: 'description'},
					localizedTitle: {en_US: 'title'},
					rows: [{columns: [{fields: [expectedField]}]}],
				},
			]);
		});
	});
});
