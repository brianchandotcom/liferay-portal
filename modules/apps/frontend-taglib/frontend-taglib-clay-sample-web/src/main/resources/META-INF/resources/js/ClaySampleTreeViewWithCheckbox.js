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

import {TreeView} from '@clayui/core';
import {ClayCheckbox as Checkbox} from '@clayui/form';
import React from 'react';

export default function ClaySampleTreeViewWithCheckbox() {
	return (
		<TreeView
			defaultItems={[
				{
					children: [
						{
							children: [
								{id: 5, name: 'Style Books'},
								{id: 6, name: 'Fragments'},
							],
							id: 4,
							name: 'Design',
						},
						{
							children: [
								{id: 8, name: 'Kaleo Forms Admin'},
								{id: 9, name: 'Web Contend'},
								{id: 10, name: 'Blogs'},
								{id: 11, name: 'Bookmarks'},
							],
							id: 7,
							name: 'Content & Data',
						},
						{
							children: [],
							id: 12,
							name: 'Categorization',
						},
					],
					id: 1,
					name: 'Liferay DXP',
				},
			]}
			defaultSelectedKeys={new Set([4, 5, 6, 8])}
			selectionMode="multiple-recursive"
			showExpanderOnHover={false}
		>
			{(item) => (
				<TreeView.Item key={item.name}>
					<TreeView.ItemStack>
						<Checkbox />

						{item.name}
					</TreeView.ItemStack>

					<TreeView.Group items={item.children}>
						{(item) => (
							<TreeView.Item>
								<Checkbox />

								{item.name}
							</TreeView.Item>
						)}
					</TreeView.Group>
				</TreeView.Item>
			)}
		</TreeView>
	);
}
