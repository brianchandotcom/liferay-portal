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

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

const AssetCategoriesNavigationTreeView = ({
	selectedCategoryId,
	vocabularies,
}) => {
	const handleSelectionChange = (event, item) => {
		event.preventDefault();

		if (selectedCategoryId === item.id) {
			return;
		}

		Liferay.Util.navigate(item.url);
	};

	return (
		<ClayTreeView
			defaultItems={vocabularies}
			defaultSelectedKeys={
				new Set(selectedCategoryId ? [selectedCategoryId] : [])
			}
			showExpanderOnHover={false}
		>
			{(item) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack
						onClick={(event) => {
							event.preventDefault();

							handleSelectionChange(event, item);
						}}
					>
						<ClayIcon symbol={item.icon} />

						{item.name}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								onClick={(event) => {
									event.preventDefault();

									handleSelectionChange(event, item);
								}}
							>
								<ClayIcon symbol={item.icon} />

								{item.name}
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
};

export default AssetCategoriesNavigationTreeView;
