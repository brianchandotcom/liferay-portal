/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ERROR_MESSAGES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/errorMessages';
import {INPUT_TYPES} from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/types/inputTypes';
import validateBoost from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/validation/validate_boost';

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
