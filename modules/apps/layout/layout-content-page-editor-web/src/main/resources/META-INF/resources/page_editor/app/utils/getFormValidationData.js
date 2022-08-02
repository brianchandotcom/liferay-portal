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

export const FORM_ERROR_TYPES = {
	deletedField: 'deletedField',
	deletedFragment: 'deletedFragment',
	hiddenFields: 'hiddenFields',
	hiddenFragment: 'hiddenFragment',
	missingFields: 'missingFields',
	missingFragments: 'missingFragments',
	missingSubmit: 'missingSubmit',
};

export function getFormValidationData({name = null, type}) {
	switch (type) {
		case FORM_ERROR_TYPES.deletedField:
			return {
				message: Liferay.Util.sub(
					Liferay.Language.get('the-required-field-x-was-deleted'),
					name
				),
			};

		case FORM_ERROR_TYPES.deletedFragment:
			return {
				message: Liferay.Language.get(
					'the-deleted-fragment-contained-required-fields'
				),
			};

		case FORM_ERROR_TYPES.hiddenFields:
			return {
				message: Liferay.Util.sub(
					Liferay.Language.get(
						'x-form-contains-one-or-more-hidden-fragments-mapped-to-required-fields'
					),
					name
				),
				summary: Liferay.Language.get(
					'one-or-more-fragments-mapped-to-required-fields-are-hidden'
				),
				title: Liferay.Language.get('required-fields-hidden'),
			};

		case FORM_ERROR_TYPES.hiddenFragment:
			return {
				message: Liferay.Language.get(
					'the-hidden-fragment-contained-required-fields'
				),
			};

		case FORM_ERROR_TYPES.missingFields:
			return {
				message: Liferay.Util.sub(
					Liferay.Language.get(
						'x-form-has-one-or-more-required-fields-not-mapped-from-the-form'
					),
					name
				),
				summary: Liferay.Language.get(
					'one-or-more-required-fields-are-not-mapped-from-the-form'
				),
				title: Liferay.Language.get('required-fields-missing'),
			};

		case FORM_ERROR_TYPES.missingFragments:
			return {
				message: Liferay.Util.sub(
					Liferay.Language.get(
						'x-form-has-some-fragments-not-mapped-to-object-fields'
					),
					name
				),
				summary: Liferay.Language.get(
					'some-fragments-are-not-mapped-to-object-fields'
				),
				title: Liferay.Language.get('fragment-mapping-missing'),
			};

		case FORM_ERROR_TYPES.missingSubmit:
			return {
				message: Liferay.Util.sub(
					Liferay.Language.get(
						'x-form-has-a-hidden-or-missing-submit-button'
					),
					name
				),
				summary: Liferay.Language.get(
					'submit-button-is-hidden-or-missing'
				),
				title: Liferay.Language.get('submit-button-missing'),
			};

		default:
			return;
	}
}
