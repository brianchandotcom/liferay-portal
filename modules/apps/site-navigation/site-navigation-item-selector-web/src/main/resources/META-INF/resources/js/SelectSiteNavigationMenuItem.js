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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {Treeview} from 'frontend-js-components-web';
import React, {useRef, useState} from 'react';

function findSiteNavigationMenuItem(
	siteNavigationMenuItemId,
	siteNavigationMenuItems = []
) {
	// eslint-disable-next-line no-for-of-loops/no-for-of-loops
	for (const siteNavigationMenuItem of siteNavigationMenuItems) {
		if (siteNavigationMenuItem.id === siteNavigationMenuItemId) {
			return siteNavigationMenuItem;
		}

		const childrenSiteNavigationMenuItem = findSiteNavigationMenuItem(
			siteNavigationMenuItemId,
			siteNavigationMenuItem.children
		);

		if (childrenSiteNavigationMenuItem) {
			return childrenSiteNavigationMenuItem;
		}
	}

	return null;
}

const nodeByName = (items, name) => {
	return items.reduce(function reducer(acc, item) {
		if (item.name.match(new RegExp(name, 'i'))) {
			acc.push(item);
		}

		if (item.children) {
			acc.concat(item.children.reduce(reducer, acc));
		}

		return acc;
	}, []);
};

const SelectSiteNavigationMenuItem = ({itemSelectorSaveEvent, nodes}) => {
	const [filter, setFilter] = useState('');
	const [items, setItems] = useState(nodes);
	const initialItemsRef = useRef(items);

	const handleQueryChange = (event) => {
		const value = event.target.value;

		if (!window.Liferay.__FF__.enableClayTreeView) {
			setFilter(value);
		}
		else {
			if (!value) {
				setItems(initialItemsRef.current);

				return;
			}

			const newItems = nodeByName(initialItemsRef.current, value);

			if (newItems.length) {
				setItems(newItems);
			}
		}
	};

	const handleSelectionChange = (selectedNodeIds) => {
		const selectedNodeId = [...selectedNodeIds][0];

		if (selectedNodeId) {
			const {id, name} = findSiteNavigationMenuItem(
				selectedNodeId,
				nodes
			);

			const data = {
				selectSiteNavigationMenuItemId: id,
				selectSiteNavigationMenuItemName: name,
			};

			Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data,
			});
		}
	};

	const handleTreeViewSelectionChange = (event, item) => {
		event.preventDefault();

		Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
			data: {
				selectSiteNavigationMenuItemId: item.id,
				selectSiteNavigationMenuItemName: item.name,
			},
		});
	};

	return (
		<ClayLayout.ContainerFluid className="p-4">
			<ClayForm.Group>
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							className="input-group-inset input-group-inset-after"
							onChange={handleQueryChange}
							placeholder={`${Liferay.Language.get('search')}`}
							type="text"
						/>

						<ClayInput.GroupInsetItem after>
							<div className="link-monospaced">
								<ClayIcon symbol="search" />
							</div>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			{!window.Liferay.__FF__.enableClayTreeView ? (
				<Treeview
					NodeComponent={Treeview.Card}
					filter={filter}
					nodes={nodes}
					onSelectedNodesChange={handleSelectionChange}
				/>
			) : (
				<ClayTreeView
					items={items}
					onItemsChange={setItems}
					showExpanderOnHover={false}
				>
					{(item) => (
						<ClayTreeView.Item>
							<ClayTreeView.ItemStack
								onClick={(event) => {
									if (!item.disabled) {
										handleTreeViewSelectionChange(
											event,
											item
										);
									}
								}}
							>
								<ClayIcon symbol="folder" />

								{item.name}
							</ClayTreeView.ItemStack>

							<ClayTreeView.Group items={item.children}>
								{(item) => (
									<ClayTreeView.Item
										onClick={(event) =>
											handleTreeViewSelectionChange(
												event,
												item
											)
										}
									>
										<ClayIcon symbol="folder" />

										{item.name}
									</ClayTreeView.Item>
								)}
							</ClayTreeView.Group>
						</ClayTreeView.Item>
					)}
				</ClayTreeView>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default SelectSiteNavigationMenuItem;
