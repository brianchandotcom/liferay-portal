/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ObjectFieldFormBase.scss';
interface UniqueValuesProps {
	disabled?: boolean;
	objectField: Partial<ObjectField>;
	setValues: (values: Partial<ObjectField>) => void;
}
export declare function UniqueValues({
	disabled,
	objectField: values,
	setValues,
}: UniqueValuesProps): JSX.Element;
export {};
