/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ERROR_MESSAGES} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/errorMessages';
import {INPUT_TYPES} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/inputTypes';
import {
	validateBoost,
	validateJSON,
	validateNumberRange,
	validateRequired,
} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/validation';

describe('validation', () => {
	describe('validateBoost', () => {
		it('returns error message for negative value in field mapping', () => {
			expect(
				validateBoost(
					{
						boost: -2,
						field: 'localized_title',
						locale: '${context.language_id}',
					},
					INPUT_TYPES.FIELD_MAPPING
				)
			).toEqual(ERROR_MESSAGES.NEGATIVE_BOOST);
		});

		it('returns error message for negative value in field mapping list', () => {
			expect(
				validateBoost(
					[
						{
							boost: -1,
							field: 'localized_title',
							locale: '${context.language_id}',
						},
						{
							boost: 1,
							field: 'content',
							locale: '${context.language_id}',
						},
					],
					INPUT_TYPES.FIELD_MAPPING_LIST
				)
			).toEqual(ERROR_MESSAGES.NEGATIVE_BOOST);
		});

		it('returns undefined for non-negative boost in field mapping', () => {
			expect(
				validateBoost(
					{
						boost: 0,
						field: 'localized_title',
						locale: '${context.language_id}',
					},
					INPUT_TYPES.FIELD_MAPPING
				)
			).toBeUndefined();
		});

		it('returns undefined for non-negative boost in field mapping list', () => {
			expect(
				validateBoost(
					[
						{
							boost: 0,
							field: 'localized_title',
							locale: '${context.language_id}',
						},
						{
							boost: 1,
							field: 'content',
							locale: '${context.language_id}',
						},
					],
					INPUT_TYPES.FIELD_MAPPING
				)
			).toBeUndefined();
		});

		it('returns undefined for no boost in field mapping', () => {
			expect(
				validateBoost(
					{
						field: 'localized_title',
						locale: '${context.language_id}',
					},
					INPUT_TYPES.FIELD_MAPPING
				)
			).toBeUndefined();
		});

		it('returns undefined for no boost in field mapping list', () => {
			expect(
				validateBoost(
					[
						{
							field: 'localized_title',
							locale: '${context.language_id}',
						},
						{
							field: 'content',
							locale: '${context.language_id}',
						},
					],
					INPUT_TYPES.FIELD_MAPPING
				)
			).toBeUndefined();
		});
	});

	describe('validateJSON', () => {
		it('returns error message for invalid json', () => {
			expect(validateJSON('{test}', INPUT_TYPES.JSON)).toEqual(
				ERROR_MESSAGES.INVALID_JSON
			);
		});

		it('returns undefined for non-json', () => {
			expect(validateJSON('{}', INPUT_TYPES.TEXT)).toBeUndefined();
		});

		it('returns undefined for valid json', () => {
			expect(validateJSON('{}', INPUT_TYPES.JSON)).toBeUndefined();
		});
	});

	describe('validateNumberRange', () => {
		const min = 0;
		const max = 1;

		it('returns error message for number below min', () => {
			expect(
				validateNumberRange(-10, INPUT_TYPES.NUMBER, {max, min})
			).toEqual(ERROR_MESSAGES.GREATER_THAN_X);
		});

		it('returns error message for number above max', () => {
			expect(
				validateNumberRange(10, INPUT_TYPES.NUMBER, {max, min})
			).toEqual(ERROR_MESSAGES.LESS_THAN_X);
		});

		it('returns undefined for non-number', () => {
			expect(
				validateNumberRange('test', INPUT_TYPES.TEXT)
			).toBeUndefined();
		});

		it('returns undefined for number in range', () => {
			expect(
				validateNumberRange(0.5, INPUT_TYPES.NUMBER, {max, min})
			).toBeUndefined();
		});

		it('returns undefined for number with undefined range', () => {
			expect(
				validateNumberRange(10, INPUT_TYPES.NUMBER, {})
			).toBeUndefined();
		});
	});

	describe('validateRequired', () => {
		it('returns error message for empty string', () => {
			expect(validateRequired('', INPUT_TYPES.NUMBER)).toEqual(
				ERROR_MESSAGES.REQUIRED
			);
		});

		it('returns error message for empty array', () => {
			expect(validateRequired([], INPUT_TYPES.MULTISELECT)).toEqual(
				ERROR_MESSAGES.REQUIRED
			);
		});

		it('returns error message for empty field', () => {
			expect(
				validateRequired(
					{
						field: '',
						locale: '',
					},
					INPUT_TYPES.FIELD_MAPPING
				)
			).toEqual(ERROR_MESSAGES.REQUIRED);
		});

		it('returns error message for empty field list', () => {
			expect(
				validateRequired(
					[
						{
							boost: 2,
							field: '',
							locale: '${context.language_id}',
						},
						{
							boost: 1,
							field: '',
							locale: '${context.language_id}',
						},
					],
					INPUT_TYPES.FIELD_MAPPING_LIST
				)
			).toEqual(ERROR_MESSAGES.REQUIRED);
		});

		it('returns undefined for non-empty string', () => {
			expect(
				validateRequired('test', INPUT_TYPES.NUMBER)
			).toBeUndefined();
		});

		it('returns undefined for non-empty array', () => {
			expect(
				validateRequired(
					[{label: 'test', value: 'test'}],
					INPUT_TYPES.MULTISELECT
				)
			).toBeUndefined();
		});

		it('returns undefined for non-empty field', () => {
			expect(
				validateRequired(
					{
						field: 'localized_title',
						locale: '${context.language_id}',
					},
					INPUT_TYPES.FIELD_MAPPING
				)
			).toBeUndefined();
		});

		it('returns undefined for non-empty field list', () => {
			expect(
				validateRequired(
					[
						{
							boost: 2,
							field: 'localized_title',
							locale: '${context.language_id}',
						},
						{
							boost: 1,
							field: 'content',
							locale: '${context.language_id}',
						},
					],
					INPUT_TYPES.FIELD_MAPPING_LIST
				)
			).toBeUndefined();
		});
	});
});
