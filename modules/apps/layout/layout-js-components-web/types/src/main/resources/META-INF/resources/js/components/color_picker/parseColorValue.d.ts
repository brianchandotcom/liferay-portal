/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field, Token} from './ColorPicker';
interface ColorValue {
	color?: string;
	label?: string;
	pickerColor: string;
	value?: string;
}
interface Error {
	error: string;
}
export declare function parseColorValue({
	editedTokenValues,
	field,
	token,
	value,
}: {
	editedTokenValues: Record<string, Token>;
	field: Field;
	token: Token | undefined;
	value: string;
}): ColorValue | Error | Record<string, never>;
export {};
