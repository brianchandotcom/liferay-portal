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
import classNames from 'classnames';
import React from 'react';

export default function NavigationMenuItemsTree({
	selectedSiteNavigationMenuItemId,
	siteNavigationMenuItems,
}) {
	const selectedKeys = new Set([selectedSiteNavigationMenuItemId]);

	return (
		<div className="navigation-menu-items-tree">
			<ClayTreeView
				defaultExpandedKeys={selectedKeys}
				defaultItems={siteNavigationMenuItems}
				displayType="dark"
				expanderIcons={{
					close: <ClayIcon symbol="hr" />,
					open: <ClayIcon symbol="plus" />,
				}}
				nestedKey="children"
				showExpanderOnHover={false}
			>
				{(item) => {
					const hasUrl = item.url && item.url !== '#';

					return (
						<ClayTreeView.Item>
							<ClayTreeView.ItemStack
								className={classNames(
									'navigation-menu-items-tree-node',
									{selected: selectedKeys.has(item.id)}
								)}
							>
								<ClayIcon
									symbol={item.url ? 'page' : 'folder'}
								/>

								{hasUrl ? (
									<a
										className="d-block h-100 w-100"
										href={item.url}
									>
										{item.name}
									</a>
								) : (
									<p className="m-0">{item.name}</p>
								)}
							</ClayTreeView.ItemStack>

							<ClayTreeView.Group items={item.children}>
								{(item) => (
									<ClayTreeView.Item>
										<ClayIcon
											symbol={
												item.url ? 'page' : 'folder'
											}
										/>

										{hasUrl ? (
											<a
												className="d-block h-100 w-100"
												href={item.url}
											>
												{item.name}
											</a>
										) : (
											<p className="m-0">{item.name}</p>
										)}
									</ClayTreeView.Item>
								)}
							</ClayTreeView.Group>
						</ClayTreeView.Item>
					);
				}}
			</ClayTreeView>
		</div>
	);
}
