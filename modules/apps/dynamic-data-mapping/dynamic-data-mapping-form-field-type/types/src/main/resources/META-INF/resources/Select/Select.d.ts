/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {MainProps} from './select.d';
declare const Main: ({
	fixedOptions,
	label,
	localizedValue,
	localizedValueEdited,
	multiple,
	name,
	onChange,
	options,
	predefinedValue,
	readOnly,
	showEmptyOption,
	value,
	selectedKey,
	...otherProps
}: MainProps) => JSX.Element;
export default Main;
