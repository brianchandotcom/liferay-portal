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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
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
	const [filter, setFilter] = useState('');

	const handleQueryChange = useCallback((event) => {
		const value = event.target.value;

		setFilter(value);
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

			<Treeview
				NodeComponent={Treeview.Card}
				filter={filter}
				nodes={nodes}
				onSelectedNodesChange={handleSelectionChange}
			/>
		</ClayLayout.ContainerFluid>
	);
};

export default SelectSiteNavigationMenuItem;
