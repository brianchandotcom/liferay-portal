/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Body, Cell, Row, Table, Text} from '@clayui/core';
import Icon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {buildQueryString} from '@liferay/analytics-reports-js-components-web';
import {openModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import ApiHelper from '../../../common/services/ApiHelper';
import formatActionURL from '../../../common/utils/formatActionURL';
import {ViewDashboardContext} from '../ViewDashboardContext';
import {AssetType, AssetTypeIcons} from '../utils/assetTypes';
import {BaseCard} from './BaseCard';
import {Item} from './FilterDropdown';

type ExpiredAsset = {

	// TODO: When available, implement asset type to show the correct icon

	assetType?: AssetType;
	href: string;
	title: string;
	usages: number;
};

type ExpiredAssetsApiResponse = {
	items: ExpiredAsset[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

async function fetchExpiredAssetsData({
	language,
	space,
}: {
	language: Item;
	space: Item;
}) {
	const queryParams = buildQueryString(
		{
			depotEntryId: space?.value,
			languageId: language?.value,
		},
		{
			shouldIgnoreParam: (value) => value === 'all',
		}
	);

	const endpoint = `/o/analytics-cms-rest/v1.0/expired-assets${queryParams}`;

	const {data, error} =
		await ApiHelper.get<ExpiredAssetsApiResponse>(endpoint);

	if (error) {
		console.error(error);
	}

	if (data) {
		return data;
	}
}

function ExpiredAssetItem({

	// TODO: When available, implement asset type to show the correct icon

	assetType,
	href,
	title,
	usage,
}: {
	assetType?: AssetType;
	href: string;
	title: string;
	usage: number;
}) {
	return (
		<div className="align-items-center cms-dashboard__expired-assets__item d-flex">
			<div className="bg-white mb-1 mr-2 p-2 rounded-lg">
				<Icon
					className="mx-1"
					color={
						assetType ? AssetTypeIcons[assetType]?.color : '#5c9531'
					}
					symbol={
						assetType ? AssetTypeIcons[assetType]?.symbol : 'blogs'
					}
				/>
			</div>

			<div className="d-flex flex-column">
				<span className="mb-0 text-dark text-weight-semi-bold">
					{title}
				</span>

				<Text color="secondary" size={3}>
					{sub(
						usage === 1
							? Liferay.Language.get('x-usage')
							: Liferay.Language.get('x-usages'),
						[usage]
					).toLowerCase()}
				</Text>
			</div>

			<ClayTooltipProvider>
				<div className="ml-auto">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('view-asset')}
						className="border-0"
						data-testid="view-asset-button"
						data-tooltip-align="top"
						displayType="secondary"
						onClick={() => {
							openModal({
								size: 'full-screen',
								title,
								url: formatActionURL(title, href),
							});
						}}
						symbol="view"
						title={Liferay.Language.get('view-asset')}
					/>
				</div>
			</ClayTooltipProvider>
		</div>
	);
}

function ExpiredAssetsCard() {
	const {
		filters: {language, space},
	} = useContext(ViewDashboardContext);
	const [expiredAssetsList, setExpiredAssetsList] =
		useState<ExpiredAssetsApiResponse>();
	const [tableSpecs, setTableSpecs] = useState<{delta: number; page: number}>(
		{
			delta: 10,
			page: 1,
		}
	);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		async function fetchData() {
			setLoading(true);
			const data = await fetchExpiredAssetsData({language, space});

			if (data) {
				setExpiredAssetsList(data);
			}

			setLoading(false);
		}

		fetchData();
	}, [language, space]);

	const handlePageChange = (newPage: number) => {
		setTableSpecs({...tableSpecs, page: newPage});
	};

	const handleDeltaChange = (newDelta: number) => {
		setTableSpecs({delta: newDelta, page: 1});
	};

	const displayedItems = useMemo(() => {
		const startIndex = (tableSpecs.page - 1) * tableSpecs.delta;
		const endIndex = startIndex + tableSpecs.delta;

		return expiredAssetsList?.items.slice(startIndex, endIndex) || [];
	}, [tableSpecs, expiredAssetsList]);

	return (
		<div className="cms-dashboard__expired-assets">
			{loading ? (
				<ClayLoadingIndicator
					data-testid="loading"
					displayType="primary"
					shape="squares"
					size="md"
				/>
			) : (
				<BaseCard
					description={Liferay.Language.get(
						'this-report-provides-a-list-of-assets-that-have-reached-their-expiration-date'
					)}
					title={Liferay.Language.get('expired-assets')}
				>
					<Table borderless striped={true}>
						<Body items={displayedItems}>
							{(row) => (
								<Row>
									<Cell className="borderless">
										<ExpiredAssetItem
											assetType={row['assetType']}
											href={row['href']}
											title={row['title']}
											usage={row['usages']}
										/>
									</Cell>
								</Row>
							)}
						</Body>
					</Table>

					<ClayPaginationBarWithBasicItems
						activeDelta={tableSpecs.delta}
						className="mt-3"
						ellipsisBuffer={3}
						ellipsisProps={{
							'aria-label': Liferay.Language.get('more'),
							'title': Liferay.Language.get('more'),
						}}
						onActiveChange={(newPage: number) =>
							handlePageChange(newPage)
						}
						onDeltaChange={(newDelta: number) =>
							handleDeltaChange(newDelta)
						}
						totalItems={expiredAssetsList?.totalCount || 0}
					/>
				</BaseCard>
			)}
		</div>
	);
}

export {ExpiredAssetsCard};
