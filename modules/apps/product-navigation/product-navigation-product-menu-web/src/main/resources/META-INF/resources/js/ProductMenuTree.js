/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		isLayoutSetPrototype = false,
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
							isLayoutSetPrototype,
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
