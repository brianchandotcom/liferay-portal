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

import {ERROR_MESSAGES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/errorMessages';
import {INPUT_TYPES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/types/inputTypes';
import validateRequired from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/validation/validate_required';

describe('validateRequired', () => {
	it('returns error message for empty string', () => {
		expect(
			validateRequired('', 'boost', false, true, INPUT_TYPES.NUMBER)
		).toEqual(ERROR_MESSAGES.REQUIRED);
	});

	it('returns error message for empty array', () => {
		expect(
			validateRequired(
				[],
				'selection',
				false,
				true,
				INPUT_TYPES.MULTISELECT
			)
		).toEqual(ERROR_MESSAGES.REQUIRED);
	});

	it('returns error message for empty category selector list', () => {
		expect(
			validateRequired(
				[],
				'asset_category_id',
				false,
				true,
				INPUT_TYPES.NUMBER
			)
		).toEqual(ERROR_MESSAGES.REQUIRED_CATEGORY_SELECTOR);
	});

	it('returns error message for empty field', () => {
		expect(
			validateRequired(
				{
					field: '',
					locale: '',
				},
				'field',
				false,
				true,
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
				'fields',
				false,
				true,
				INPUT_TYPES.FIELD_MAPPING_LIST
			)
		).toEqual(ERROR_MESSAGES.REQUIRED);
	});

	it('returns undefined for non-empty string', () => {
		expect(
			validateRequired('test', 'boost', false, true, INPUT_TYPES.NUMBER)
		).toBeUndefined();
	});

	it('returns undefined when nullable is true', () => {
		expect(
			validateRequired('', 'boost', true, undefined, INPUT_TYPES.NUMBER)
		).toBeUndefined();
	});

	it('returns undefined for required is false', () => {
		expect(
			validateRequired('', 'boost', undefined, false, INPUT_TYPES.NUMBER)
		).toBeUndefined();
	});

	it('returns undefined for non-empty array', () => {
		expect(
			validateRequired(
				[{label: 'test', value: 'test'}],
				'selection',
				false,
				true,
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
				'field',
				false,
				true,
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
				'fields',
				false,
				true,
				INPUT_TYPES.FIELD_MAPPING_LIST
			)
		).toBeUndefined();
	});
});
