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

import {MouseEventHandler} from 'react';
export declare function ElementItem({
	label,
	onClick,
	tooltip,
}: IElementItem): JSX.Element;
export default function Element({items, onItemClick}: IProps): JSX.Element;
interface IProps {
	items: ObjectValidationRuleElementItem[];
	onItemClick: (item: ObjectValidationRuleElementItem) => void;
}
interface IElementItem {
	label: string;
	onClick?: MouseEventHandler;
	tooltip: string;
}
export {};
