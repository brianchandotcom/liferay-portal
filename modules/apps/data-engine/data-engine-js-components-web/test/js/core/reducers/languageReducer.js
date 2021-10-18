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
