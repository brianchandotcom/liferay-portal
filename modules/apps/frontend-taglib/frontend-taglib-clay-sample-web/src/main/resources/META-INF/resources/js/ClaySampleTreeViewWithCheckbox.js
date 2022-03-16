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
								{id: 5, name: 'Blogs'},
								{id: 6, name: 'Documents and Media'},
							],
							id: 4,
							name: 'Repositories',
						},
						{
							children: [
								{id: 8, name: 'PDF'},
								{id: 9, name: 'Word'},
								{id: 10, name: 'Google Drive'},
								{id: 11, name: 'Figma'},
							],
							id: 7,
							name: 'Documents and Media',
						},
						{
							children: [],
							id: 12,
							name: 'Empty directory',
						},
					],
					id: 1,
					name: 'Liferay Drive',
				},
			]}
			defaultSelectedKeys={new Set([5, 6, 8])}
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
