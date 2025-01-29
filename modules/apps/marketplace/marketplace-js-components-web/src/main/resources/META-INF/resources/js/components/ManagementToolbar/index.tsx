/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import {sub} from 'frontend-js-web';
import React, {ComponentProps, useMemo, useState} from 'react';

import {useMarketplaceContext} from '../../MarketplaceContext';

type DropdownItems = ComponentProps<typeof ClayDropDownWithItems>['items'];

const SORT_ICON = {
	asc: 'order-list-up',
	desc: 'order-list-down',
};

export function ManagementToolbar() {
	const {
		productListView: {
			loading,
			productsResponse,
			searchParams,
			setProductSearchParams,
		},
	} = useMarketplaceContext();

	const {totalCount = 0} = productsResponse || {};
	const {search, sortDirection, sortKey} = searchParams;

	const [searchInput, setSearchInput] = useState(search);
	const [searchMobile, setSearchMobile] = useState(false);

	const dropdownItems = useMemo(() => {
		return [
			{
				...(sortKey === 'createDate' && {
					active: true,
					symbolLeft: 'check',
				}),
				label: Liferay.Language.get('create-date'),
				name: 'createDate',
				onClick: () =>
					setProductSearchParams((prevSearchParams) => ({
						...prevSearchParams,
						sortKey: 'createDate',
					})),
			},
			{
				...(sortKey === 'name' && {
					active: true,
					symbolLeft: 'check',
				}),
				label: Liferay.Language.get('product-name'),
				name: 'name',
				onClick: () =>
					setProductSearchParams((prevSearchParams) => ({
						...prevSearchParams,
						sortKey: 'name',
					})),
			},
			{
				type: 'divider',
			},
			{
				...(sortDirection === 'asc' && {
					active: true,
					symbolLeft: 'check',
				}),
				label: Liferay.Language.get('ascending'),
				name: 'asc',
				onClick: () =>
					setProductSearchParams((prevSearchParams) => ({
						...prevSearchParams,
						sortDirection: 'asc',
					})),
			},
			{
				...(sortDirection === 'desc' && {
					active: true,
					symbolLeft: 'check',
				}),
				label: Liferay.Language.get('descending'),
				name: 'desc',
				onClick: () =>
					setProductSearchParams((prevSearchParams) => ({
						...prevSearchParams,
						sortDirection: 'desc',
					})),
			},
		];
	}, [setProductSearchParams, sortDirection, sortKey]);

	return (
		<>
			<ClayManagementToolbar className="w-100">
				<ClayManagementToolbar.ItemList>
					<ClayDropDownWithItems
						items={dropdownItems as DropdownItems}
						trigger={
							<ClayButton
								className="nav-link"
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									<ClayIcon
										className="inline-item inline-item-before"
										symbol={SORT_ICON[sortDirection]}
									/>

									<span className="navbar-text-truncate">
										{Liferay.Language.get('order')}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										symbol="caret-bottom"
									/>
								</span>
							</ClayButton>
						}
					/>
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.Search
					onSubmit={(event) => {
						event.preventDefault();

						setProductSearchParams({...searchParams, search});
					}}
					showMobile={searchMobile}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								onChange={(event) =>
									setSearchInput(event.target.value)
								}
								placeholder={Liferay.Language.get('search-for')}
								type="text"
								value={searchInput}
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									aria-label="Close search"
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									onClick={() => setSearchMobile(false)}
									symbol="times"
								/>

								<ClayButtonWithIcon
									aria-label="Search"
									displayType="unstyled"
									onClick={() =>
										setProductSearchParams({
											...searchParams,
											search: searchInput,
										})
									}
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>

				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							aria-label={Liferay.Language.get('search')}
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => setSearchMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			{!loading && search && totalCount > 0 && (
				<ClayResultsBar>
					<ClayResultsBar.Item>
						<span className="component-text text-truncate-inline">
							<span
								className="text-truncate"
								dangerouslySetInnerHTML={{
									__html: sub(
										totalCount === 1
											? Liferay.Language.get(
													'x-result-for-x'
												)
											: Liferay.Language.get(
													'x-results-for-x'
												),
										[
											`${totalCount}`,
											`<strong>"${search}"</strong>`,
										]
									),
								}}
							/>
						</span>
					</ClayResultsBar.Item>
				</ClayResultsBar>
			)}
		</>
	);
}
