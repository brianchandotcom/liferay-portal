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

import {computeEditableValue} from '../../../src/main/resources/META-INF/resources/js/utils/computeValues.es';
import {prefixSegmentsExperienceId} from '../../../src/main/resources/META-INF/resources/js/utils/prefixSegmentsExperienceId.es';

const EXPERIENCE_ID = '100';
const EXPERIENCE_KEY = prefixSegmentsExperienceId(EXPERIENCE_ID);

const FALSY_EDITABLE_VALUE = {
	[EXPERIENCE_KEY]: {
		de: 'null',
		en: 'false',
		es: 'undefined',
		'null-lang': null,
		pt: '',
		'undefined-lang': undefined
	},
	defaultValue: 'Test default values'
};

describe('computeEditableValue', () => {
	it('returns defaultValue for segment with no values', () => {
		const editableValues = {
			[EXPERIENCE_KEY]: {},
			defaultValue: 'Test default values'
		};

		const segmentedValue = computeEditableValue(editableValues, {
			defaultLanguageId: 'en',
			selectedExperienceId: EXPERIENCE_ID,
			selectedLanguageId: 'en'
		});

		expect(segmentedValue).toBe(editableValues.defaultValue);
	});

	it('returns defaultValue for segment with no defaultLanguage value for selectedSegmented', () => {
		const editableValues = {
			[EXPERIENCE_KEY]: {
				es: 'Un valor de prueba'
			},
			defaultValue: 'Test default values'
		};

		const segmentedValue = computeEditableValue(editableValues, {
			defaultLanguageId: 'en',
			selectedExperienceId: EXPERIENCE_ID,
			selectedLanguageId: 'en'
		});

		expect(segmentedValue).toBe(editableValues.defaultValue);
	});

	it('returns translated value for the selected segment', () => {
		const editableValues = {
			[EXPERIENCE_KEY]: {
				en: 'A test value',
				es: 'Un valor de prueba'
			},
			defaultValue: 'Test default values'
		};

		const segmentedValue = computeEditableValue(editableValues, {
			defaultLanguageId: 'en',
			selectedExperienceId: EXPERIENCE_ID,
			selectedLanguageId: 'es'
		});

		expect(segmentedValue).toBe(editableValues[EXPERIENCE_KEY]['es']);
	});

	it('returns  value for default language and for the selected segment', () => {
		const editableValues = {
			[EXPERIENCE_KEY]: {
				en: 'A test value',
				pt: 'Um valor de teste'
			},
			defaultValue: 'Test default values'
		};

		const segmentedValue = computeEditableValue(editableValues, {
			defaultLanguageId: 'en',
			selectedExperienceId: EXPERIENCE_ID,
			selectedLanguageId: 'es'
		});

		expect(segmentedValue).toBe(editableValues[EXPERIENCE_KEY]['en']);
	});

	describe('respects falsy string values', () => {
		it("returns 'false' string segmented value", () => {
			const falseSegmentedValue = computeEditableValue(
				FALSY_EDITABLE_VALUE,
				{
					defaultLanguageId: 'en',
					selectedExperienceId: EXPERIENCE_ID,
					selectedLanguageId: 'en'
				}
			);

			expect(falseSegmentedValue).toBe(
				FALSY_EDITABLE_VALUE[EXPERIENCE_KEY].en
			);
		});

		it("returns 'null' string segmented value", () => {
			const nullSegmentedValue = computeEditableValue(
				FALSY_EDITABLE_VALUE,
				{
					defaultLanguageId: 'en',
					selectedExperienceId: EXPERIENCE_ID,
					selectedLanguageId: 'de'
				}
			);

			expect(nullSegmentedValue).toBe(
				FALSY_EDITABLE_VALUE[EXPERIENCE_KEY].de
			);
		});

		it("returns 'undefined' string segmented value", () => {
			const undefinedSegmentedValue = computeEditableValue(
				FALSY_EDITABLE_VALUE,
				{
					defaultLanguageId: 'en',
					selectedExperienceId: EXPERIENCE_ID,
					selectedLanguageId: 'es'
				}
			);

			expect(undefinedSegmentedValue).toBe(
				FALSY_EDITABLE_VALUE[EXPERIENCE_KEY].es
			);
		});

		it('returns empty string segmented value', () => {
			const emptyStringSegmentedValue = computeEditableValue(
				FALSY_EDITABLE_VALUE,
				{
					defaultLanguageId: 'en',
					selectedExperienceId: EXPERIENCE_ID,
					selectedLanguageId: 'pt'
				}
			);

			expect(emptyStringSegmentedValue).toBe(
				FALSY_EDITABLE_VALUE[EXPERIENCE_KEY].pt
			);
		});
	});

	it('returns alternative to undefined segmented value', () => {
		const fallToDefaultLanguageSegmentValue = computeEditableValue(
			FALSY_EDITABLE_VALUE,
			{
				defaultLanguageId: 'en',
				selectedExperienceId: EXPERIENCE_ID,
				selectedLanguageId: 'undefined-lang'
			}
		);

		expect(fallToDefaultLanguageSegmentValue).toBe(
			FALSY_EDITABLE_VALUE[EXPERIENCE_KEY].en
		);
	});

	it('returns alternative to null segmented value', () => {
		const fallToDefaultLanguageSegmentValue = computeEditableValue(
			FALSY_EDITABLE_VALUE,
			{
				defaultLanguageId: 'en',
				selectedExperienceId: EXPERIENCE_ID,
				selectedLanguageId: 'null-lang'
			}
		);

		expect(fallToDefaultLanguageSegmentValue).toBe(
			FALSY_EDITABLE_VALUE[EXPERIENCE_KEY].en
		);
	});
});
