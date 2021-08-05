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

// @ts-ignore

export {default as elementSetReducer} from './elementSetReducer.es';

// @ts-ignore

export {default as formInfoReducer} from './formInfoReducer.es';

// @ts-ignore

export {default as rulesReducer} from './rulesReducer.es';

// @ts-ignore

export {default as sidebarReducer} from './sidebarReducer.es';

declare global {

	// Global Types

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

	type LocalizedTextKey = 'error' | 'please-add-at-least-one-field';

	type LocalizedValue<T> = {[key in Locale]?: T};

	// Global Variables

	const Liferay: {
		Language: {
			direction: LocalizedValue<Direction>;
			get: (key: LocalizedTextKey) => string;
		};
	};
}
