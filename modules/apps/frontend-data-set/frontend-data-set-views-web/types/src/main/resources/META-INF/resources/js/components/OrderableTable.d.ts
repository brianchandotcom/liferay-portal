/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';
import '../../css/OrderableTable.scss';
interface IAction {
	icon: string;
	label: string;
	onClick: Function;
}
interface IContentRendererProps {
	item: any;
	query: string;
}
interface IContentRenderer {
	component: React.FC<IContentRendererProps>;
	textMatch?: Function;
}
interface IField {
	contentRenderer?: IContentRenderer;
	headingTitle?: boolean;
	label: string;
	name: string;
}
interface IOrderableTableProps {
	actions?: Array<IAction>;
	className?: string;
	creationMenuItems?: React.ComponentProps<
		typeof ClayDropDownWithItems
	>['items'];
	creationMenuLabel?: string;
	fields: Array<IField>;
	items: Array<any>;
	noItemsButtonLabel: string;
	noItemsDescription: string;
	noItemsTitle: string;
	onOrderChange: (args: {order: string}) => void;
	title?: string;
}
declare const OrderableTable: ({
	actions,
	className,
	creationMenuItems,
	creationMenuLabel,
	fields,
	items: initialItems,
	noItemsButtonLabel,
	noItemsDescription,
	noItemsTitle,
	onOrderChange,
	title,
}: IOrderableTableProps) => JSX.Element;
export default OrderableTable;
