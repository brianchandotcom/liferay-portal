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
interface IOption extends PickListItem {
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
