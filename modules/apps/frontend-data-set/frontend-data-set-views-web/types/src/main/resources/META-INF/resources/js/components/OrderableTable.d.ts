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

import React from 'react';
import '../../css/OrderableTable.scss';
interface Action {
	icon: string;
	label: string;
	onClick: Function;
}
interface ContentRendererProps {
	item: any;
}
interface Field {
	contentRenderer?: React.FC<ContentRendererProps>;
	headingTitle?: boolean;
	label: string;
	name: string;
}
interface OrderableTableProps {
	actions?: Array<Action>;
	disableSave?: boolean;
	fields: Array<Field>;
	items: Array<any>;
	noItemsButtonLabel: string;
	noItemsDescription: string;
	noItemsTitle: string;
	onCancelButtonClick: Function;
	onCreationButtonClick: Function;
	onOrderChange: (args: {orderedItems: any[]}) => void;
	onSaveButtonClick: Function;
	title: string;
}
declare const OrderableTable: ({
	actions,
	disableSave,
	fields,
	items: initialItems,
	noItemsButtonLabel,
	noItemsDescription,
	noItemsTitle,
	onCancelButtonClick,
	onCreationButtonClick,
	onOrderChange,
	onSaveButtonClick,
	title,
}: OrderableTableProps) => JSX.Element;
export default OrderableTable;
