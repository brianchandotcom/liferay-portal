/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './StateDefinition.scss';
export default function StateDefinition({
	currentKey,
	disabled,
	index,
	initialValues,
	setValues,
	stateName,
	values,
}: IProps): JSX.Element;
interface IOption extends ListTypeEntry {
	checked: boolean;
}
interface IProps {
	currentKey: string;
	disabled: boolean;
	index: number;
	initialValues: IOption[];
	setValues: (values: Partial<ObjectField>) => void;
	stateName: string;
	values: Partial<ObjectField>;
}
export {};
