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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {Treeview} from 'frontend-js-components-web';
import React, {useCallback, useState} from 'react';

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

const SelectSiteNavigationMenuItem = ({itemSelectorSaveEvent, nodes}) => {
	const [filterQuery, setFilterQuery] = useState('');

	const handleQueryChange = useCallback((event) => {
		const value = event.target.value;

		setFilterQuery(value);
	}, []);

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

	return (
		<ClayLayout.ContainerFluid>
			<nav className="collapse-basic-search navbar navbar-default navbar-no-collapse">
				<ClayInput.Group className="basic-search">
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							onChange={handleQueryChange}
							placeholder={`${Liferay.Language.get('search')}`}
							type="text"
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="search"
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</nav>

			<Treeview
				NodeComponent={Treeview.Card}
				filterQuery={filterQuery}
				nodes={nodes}
				onSelectedNodesChange={handleSelectionChange}
			/>
		</ClayLayout.ContainerFluid>
	);
};

export default SelectSiteNavigationMenuItem;
