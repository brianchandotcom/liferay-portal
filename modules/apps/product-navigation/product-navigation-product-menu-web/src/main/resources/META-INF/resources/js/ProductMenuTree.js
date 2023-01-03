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

import classNames from 'classnames';
import React, {useState} from 'react';

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
	} = props;

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
		stagingEnabled,
		viewInPageAdministrationURL,
	} = config;

	const [searchKeywords, setSearchKeywords] = useState('');

	return (
		<>
			<LayoutFinder
				administrationPortletNamespace={administrationPortletNamespace}
				administrationPortletURL={administrationPortletURL}
				findLayoutsURL={findLayoutsURL}
				keywords={searchKeywords}
				namespace={portletNamespace}
				productMenuPortletURL={productMenuPortletURL}
				setKeywords={setSearchKeywords}
				viewInPageAdministrationURL={viewInPageAdministrationURL}
			/>

			<div className={classNames({hide: searchKeywords.length})}>
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
							stagingEnabled,
						}}
						isPrivateLayoutsTree={isPrivateLayoutsTree}
						items={items}
						selectedLayoutId={selectedLayoutId}
						selectedLayoutPath={selectedLayoutPath}
					/>
				)}
			</div>

			<PagesAdministrationLink
				administrationPortletURL={administrationPortletURL}
				hasAdministrationPortletPermission={
					hasAdministrationPortletPermission
				}
			/>
		</>
	);
}
