/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
