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

export {FieldBase as ReactFieldBase} from './FieldBase/ReactFieldBase.es';
export {default as FieldBase} from './FieldBase/FieldBase.es';
declare global {
	type Direction = 'ltr' | 'rtl';
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
		| 'choose-an-option'
		| 'decimal-separator'
		| 'input-mask-append-placeholder'
		| 'prefix'
		| 'prefix-or-suffix'
		| 'suffix'
		| 'the-maximum-length-is-10-characters'
		| 'thousands-separator';
	type LocalizedValue<T> = {
		[key in Locale]?: T;
	};
	const Liferay: {
		Language: {
			direction: LocalizedValue<Direction>;
			get: (key: LocalizedTextKey) => string;
		};
	};
}
