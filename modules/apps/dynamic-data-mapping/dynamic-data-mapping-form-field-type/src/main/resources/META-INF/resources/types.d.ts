/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Direction = Liferay.Language.Direction;
export type FieldChangeEventHandler<T> = (event: {target: {value: T}}) => void;
export type Locale = Liferay.Language.Locale;
export type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;
