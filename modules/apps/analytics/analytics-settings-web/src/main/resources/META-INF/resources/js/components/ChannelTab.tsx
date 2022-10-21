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

import React, {useEffect, useState} from 'react';

import {fetchChannels} from '../utils/api';
import {TProperty} from './PropertiesTable';
import TabsTemplate, {TData} from './TabsTemplate';

interface IChannelTabProps {
	description?: string;
	displayChannels?: boolean;
	property: TProperty;
}

const ChannelTab: React.FC<IChannelTabProps> = ({
	displayChannels,
	property,
}) => {
	const [items, setItems] = useState<TData>([]);
	const [selectedAll, setSelectedAll] = useState(false);

	useEffect(() => {
		const request = async () => {
			const response = await fetchChannels();
			setItems(response.items);
		};
		request();
	}, []);

	useEffect(() => {
		setSelectedAll(items.every((item) => item.channelName));
	}, [items]);

	const handleCheckboxChange = (index: number) => {
		const newItems = items;
		newItems[index].channelName
			? delete newItems[index].channelName
			: (newItems[index].channelName = property.name);
		setItems([...newItems]);
	};

	return (
		<TabsTemplate
			channelTab
			checked={selectedAll}
			displayChannels={displayChannels}
			handleCheckboxChange={handleCheckboxChange}
			handleSelectAll={(checked: boolean) => {
				const newItems = items.map((item) => {
					const disabled =
						(item.channelName &&
							item.channelName !== property.name) ||
						!displayChannels;

					if (disabled) {
						return item;
					} else {
						if (
							(checked && !item.channelName) ||
							(checked && item.channelName)
						) {
							return {...item, channelName: property.name};
						} else {
							delete item.channelName;

							return item;
						}
					}
				});
				setItems(newItems);
			}}
			items={items}
			property={property}
			selectedAllDisabled={!displayChannels}
		/>
	);
};

export default ChannelTab;
