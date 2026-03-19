/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default interface ConfigurationCustomComponentProps<
	T extends Record<string, unknown> = Record<string, unknown>,
> {
	onValueSelect: <K extends keyof T & string>(name: K, value: T[K]) => void;
	values: T;
}
