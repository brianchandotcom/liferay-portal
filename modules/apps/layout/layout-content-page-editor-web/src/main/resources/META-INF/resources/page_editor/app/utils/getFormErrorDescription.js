/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

export const FORM_ERROR_TYPES = {
	deletedFragment: 'deletedFragment',
	draftNotAvailable: 'draftNotAvailable',
	hiddenFields: 'hiddenFields',
	hiddenFragment: 'hiddenFragment',
	missingFields: 'missingFields',
	missingFragments: 'missingFragments',
	missingSubmit: 'missingSubmit',
};

export function getFormErrorDescription({name = null, type}) {
	switch (type) {
		case FORM_ERROR_TYPES.deletedFragment:
			return {
				message: sub(
					Liferay.Language.get(
						'the-deleted-fragment-was-marked-as-required'
					),
					name
				),
			};

		case FORM_ERROR_TYPES.draftNotAvailable:
			return {
				message: sub(
					Liferay.Language.get(
						'x-form-does-not-allow-creating-entries-as-draft'
					),
					name
				),
				title: Liferay.Language.get('save-as-draft-not-available'),
			};

		case FORM_ERROR_TYPES.hiddenFields:
			return {
				message: sub(
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
				message: sub(
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
				message: sub(
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
				message: sub(
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
