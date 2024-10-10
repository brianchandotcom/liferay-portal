/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<TreeView.Item>
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
