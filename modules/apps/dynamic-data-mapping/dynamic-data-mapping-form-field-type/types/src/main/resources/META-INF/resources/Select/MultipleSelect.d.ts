/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {MultiSelectProps} from './select';
declare const MultipleSelection: ({
	name,
	onChange,
	options,
	readOnly,
	required,
	value: values,
}: MultiSelectProps) => JSX.Element;
export default MultipleSelection;
