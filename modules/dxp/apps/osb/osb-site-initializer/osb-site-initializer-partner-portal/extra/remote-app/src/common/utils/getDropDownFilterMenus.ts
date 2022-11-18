/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import DropDownWithDrillDown from '../components/TableHeader/Filter/components/DropDownWithDrillDown';

interface FilterItem {
	component: JSX.Element;
	disabled?: boolean;
	name: string;
}
export default function getDropDownFilterMenus(filters: FilterItem[]) {
	return filters.reduce<
		React.ComponentProps<typeof DropDownWithDrillDown>['menus']
	>(
		(previousValue, currentValue, index) => ({
			...previousValue,
			x0a0: [
				...(previousValue.x0a0 || []),
				{
					child: `x0a${index + 1}`,
					disabled: currentValue.disabled,
					title: currentValue.name,
				},
			],
			[`x0a${index + 1}`]: [
				{
					child: currentValue.component,
					type: 'component',
				},
			],
		}),
		{}
	);
}
