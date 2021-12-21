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

type Direction = 'ltr' | 'rtl';

type FieldChangeEventHandler<T = any> = (event: {
	target: {
		value: T;
	};
}) => void;

type Locale =
	| 'ar_SA'
	| 'ca_ES'
	| 'de_DE'
	| 'en_US'
	| 'es_ES'
	| 'fi_FI'
	| 'fr_FR'
	| 'hu_HU'
	| 'nl_NL'
	| 'ja_JP'
	| 'pt_BR'
	| 'sv_SE'
	| 'zh_CN';

type LocalizedTextKey =
	| 'back'
	| 'error'
	| 'please-add-at-least-one-field'
	| 'see-partial-results'
	| 'submit-again'
	| 'summary'
	| 'there-are-no-entries'
	| 'understood'
	| 'x-entries'
	| 'x-entry'
	| 'your-responses-will-be-visible-to-all-form-respondents';

type LocalizedValue<T> = {
	[key in Locale]?: T;
};
