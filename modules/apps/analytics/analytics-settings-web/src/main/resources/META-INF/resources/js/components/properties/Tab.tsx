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

import {Text} from '@clayui/core';
import React from 'react';

import {TQueries} from '../../utils/request';
import Table, {TColumn, TFormattedItems} from '../table/Table';
import {TProperty} from './Properties';

export type TRawItem = {
	channelName?: string;
	friendlyURL?: string;
	id: string;
	name: string;
	siteName: string;
};

interface ITabProps {
	columns: Array<keyof TRawItem>;
	description?: string;
	emptyStateTitle: string;
	enableCheckboxs?: boolean;
	header: TColumn[];
	noResultsTitle: string;
	onItemsChange: (items: TFormattedItems) => void;
	property: TProperty;
	requestFn: (params: TQueries) => Promise<any>;
}

const Tab: React.FC<ITabProps> = ({
	columns,
	description,
	emptyStateTitle,
	enableCheckboxs = true,
	header,
	noResultsTitle,
	onItemsChange,
	property,
	requestFn,
}) => (
	<>
		{description && (
			<div className="my-3 text-secondary">
				<Text size={3}>{description}</Text>
			</div>
		)}

		<Table<TRawItem>
			columns={header}
			disabled={!enableCheckboxs}
			emptyStateTitle={emptyStateTitle}
			mapperItems={(items: TRawItem[]) => {
				return items.map((item) => ({
					checked: !!(
						item.channelName && item.channelName === property.name
					),
					columns: columns.map((column) => ({
						label: item?.[column] ?? '',
					})),
					disabled: !!(
						item.channelName && item.channelName !== property.name
					),
					id: item.id,
				}));
			}}
			noResultsTitle={noResultsTitle}
			onItemsChange={onItemsChange}
			requestFn={requestFn}
		/>
	</>
);

export default Tab;
