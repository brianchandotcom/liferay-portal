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

import ClayMultiSelect from '@clayui/multi-select';
import React from 'react';
declare type TItem = {
	key?: string;
	label?: string;
	value?: string;
	[propName: string]: any;
};
declare type TItems = Array<TItem>;
interface IProps extends React.ComponentProps<typeof ClayMultiSelect> {
	items: TItems;
	onItemsChange: Exclude<
		React.ComponentProps<typeof ClayMultiSelect>['onItemsChange'],
		undefined
	>;
	sourceItems: TItems;
	value: Exclude<
		React.ComponentProps<typeof ClayMultiSelect>['value'],
		undefined
	>;
}
declare function CheckboxMultiSelect({
	items,
	onItemsChange,
	sourceItems,
	value,
	...otherProps
}: IProps): JSX.Element;
export default CheckboxMultiSelect;
