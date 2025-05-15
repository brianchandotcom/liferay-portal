/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {Locale} from '../types';
import {normalizeOptions} from '../util/options';

import type {Option} from './select.d';

export function useNormalizedOptionsMemo({
	editingLanguageId,
	fixedOptions,
	multiple,
	options,
	showEmptyOption,
	valueArray,
}: {
	editingLanguageId: Locale;
	fixedOptions: Option<string>[];
	multiple: boolean;
	options: Option<string>[];
	showEmptyOption: boolean;
	valueArray: string[];
}) {
	return useMemo(
		() =>
			normalizeOptions({
				editingLanguageId,
				fixedOptions,
				multiple,
				options,
				showEmptyOption,
				valueArray,
			}),

		[
			editingLanguageId,
			fixedOptions,
			multiple,
			options,
			showEmptyOption,
			valueArray,
		]
	);
}
