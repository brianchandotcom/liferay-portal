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

import React from 'react';

import LayoutFinder from './LayoutFinder';
import NavigationMenuItemsTree from './NavigationMenuItemsTree';
import PageTypeSelector from './PageTypeSelector';
import PagesAdministrationLink from './PagesAdministrationLink';
import PagesTree from './PagesTree';

export default function ProductMenuTree({portletNamespace, ...props}) {
	const {
		config,
		hasAdministrationPortletPermission,
		isPrivateLayoutsTree,
		isSiteNavigationMenu,
		items,
		pageTypeOptions,
		pageTypeSelectedOption,
		pageTypeSelectedOptionLabel,
		selectedLayoutId,
		selectedLayoutPath,
		selectedSiteNavigationMenuItemId,
		showAddIcon,
		siteNavigationMenuItems,
	} = props.productMenuTreeData;

	const {
		addCollectionLayoutURL,
		addLayoutURL,
		administrationPortletNamespace,
		administrationPortletURL,
		configureLayoutSetURL,
		findLayoutsURL,
		loadMoreItemsURL,
		maxPageSize,
		moveItemURL,
		pagesTreeURL,
		productMenuPortletURL,
		viewInPageAdministrationURL,
	} = config;

	return (
		<>
			<LayoutFinder
				administrationPortletNamespace={administrationPortletNamespace}
				administrationPortletURL={administrationPortletURL}
				findLayoutsURL={findLayoutsURL}
				namespace={portletNamespace}
				productMenuPortletURL={productMenuPortletURL}
				viewInPageAdministrationURL={viewInPageAdministrationURL}
			/>

			<PageTypeSelector
				addCollectionLayoutURL={addCollectionLayoutURL}
				addLayoutURL={addLayoutURL}
				configureLayoutSetURL={configureLayoutSetURL}
				namespace={portletNamespace}
				pageTypeOptions={pageTypeOptions}
				pageTypeSelectedOption={pageTypeSelectedOption}
				pageTypeSelectedOptionLabel={pageTypeSelectedOptionLabel}
				pagesTreeURL={pagesTreeURL}
				showAddIcon={showAddIcon}
			/>

			{isSiteNavigationMenu ? (
				<NavigationMenuItemsTree
					portletNamespace={portletNamespace}
					selectedSiteNavigationMenuItemId={
						selectedSiteNavigationMenuItemId
					}
					siteNavigationMenuItems={siteNavigationMenuItems}
				/>
			) : (
				<PagesTree
					config={{
						loadMoreItemsURL,
						maxPageSize,
						moveItemURL,
						namespace: portletNamespace,
					}}
					isPrivateLayoutsTree={isPrivateLayoutsTree}
					items={items}
					selectedLayoutId={selectedLayoutId}
					selectedLayoutPath={selectedLayoutPath}
				/>
			)}

			<PagesAdministrationLink
				administrationPortletURL={administrationPortletURL}
				hasAdministrationPortletPermission={
					hasAdministrationPortletPermission
				}
			/>
		</>
	);
}
