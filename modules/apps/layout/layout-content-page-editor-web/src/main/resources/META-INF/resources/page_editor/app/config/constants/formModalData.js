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

const REQUIRED_FORM_FRAGMENT = Liferay.Language.get('required-form-fragment');

export const REQUIRED_FIELD_DATA = {
	actionLabel: Liferay.Language.get('hide'),
	message: Liferay.Language.get(
		'this-fragment-contains-a-required-form-field-or-a-submit-element.-are-you-sure-you-want-to-hide-it'
	),
	title: REQUIRED_FORM_FRAGMENT,
};

export const MISSING_FIELD_DATA = {
	actionLabel: Liferay.Language.get('publish'),
	message: Liferay.Language.get(
		'this-page-contains-one-or-several-forms-with-a-missing-or-hidden-required-elements'
	),
	title: REQUIRED_FORM_FRAGMENT,
};
