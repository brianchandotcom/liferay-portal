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
import {useSessionState} from '@liferay/layout-content-page-editor-web';
import PropTypes from 'prop-types';
import React, {useRef} from 'react';

const ENTER_KEYCODE = 13;

export default function NavigationMenuItemsTree({
	portletNamespace,
	selectedSiteNavigationMenuItemId,
	siteNavigationMenuItems,
}) {
	const [
		expandedKeys,
		setExpandedKeys,
	] = useSessionState(`${portletNamespace}_navigationMenusExpandedKeys`, [
		selectedSiteNavigationMenuItemId,
	]);

	return (
		<ClayTreeView
			defaultItems={siteNavigationMenuItems}
			displayType="dark"
			expandedKeys={new Set(expandedKeys)}
			nestedKey="children"
			onExpandedChange={(keys) => {
				setExpandedKeys(Array.from(keys));
			}}
			showExpanderOnHover={false}
		>
			{(item) => (
				<TreeItem
					item={item}
					selectedItemId={selectedSiteNavigationMenuItemId}
				/>
			)}
		</ClayTreeView>
	);
}

NavigationMenuItemsTree.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
	selectedSiteNavigationMenuItemId: PropTypes.string.isRequired,
	siteNavigationMenuItems: PropTypes.array.isRequired,
};

function TreeItem({item, selectedItemId}) {
	const hasUrl = item.url && item.url !== '#';

	const stackAnchorRef = useRef(null);
	const itemAnchorRef = useRef(null);

	return (
		<ClayTreeView.Item>
			<ClayTreeView.ItemStack
				active={selectedItemId === item.id ? 'true' : null}
				onKeyDown={(event) => {
					if (event.keyCode === ENTER_KEYCODE && hasUrl) {
						stackAnchorRef.current.click();
					}
				}}
			>
				<ClayIcon symbol={item.url ? 'page' : 'folder'} />

				<div className="align-items-center d-flex pl-2">
					{hasUrl ? (
						<a
							className="flex-grow-1 text-decoration-none text-truncate w-100"
							data-tooltip-floating="true"
							href={item.url}
							ref={stackAnchorRef}
							tabIndex="-1"
							target={item.target}
							title={item.name}
						>
							{item.name}
						</a>
					) : (
						<span>{item.name}</span>
					)}
				</div>
			</ClayTreeView.ItemStack>

			<ClayTreeView.Group items={item.children}>
				{(item) => (
					<ClayTreeView.Item
						active={selectedItemId === item.id ? 'true' : null}
						onKeyDown={(event) => {
							if (event.keyCode === ENTER_KEYCODE && hasUrl) {
								stackAnchorRef.current.click();
							}
						}}
					>
						<ClayIcon symbol={item.url ? 'page' : 'folder'} />

						<div className="align-items-center d-flex pl-2">
							{hasUrl ? (
								<a
									className="flex-grow-1 text-decoration-none text-truncate w-100"
									data-tooltip-floating="true"
									href={item.url}
									ref={itemAnchorRef}
									tabIndex="-1"
									target={item.target}
									title={item.name}
								>
									{item.name}
								</a>
							) : (
								<span>{item.name}</span>
							)}
						</div>
					</ClayTreeView.Item>
				)}
			</ClayTreeView.Group>
		</ClayTreeView.Item>
	);
}

TreeItem.propTypes = {
	items: PropTypes.array.isRequired,
	selectedItemId: PropTypes.string.isRequired,
};
