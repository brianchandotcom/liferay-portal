/* eslint-disable @typescript-eslint/no-unused-vars */

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Row, Table, Text} from '@clayui/core';
import Icon from '@clayui/icon';
import Link from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {buildQueryString} from '@liferay/analytics-reports-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import ApiHelper from '../../../common/services/ApiHelper';
import {ViewDashboardContext} from '../ViewDashboardContext';
import {AssetType, AssetTypeIcon, AssetTypeIcons} from '../utils/assetTypes';
import {BaseCard} from './BaseCard';
import {Item} from './FilterDropdown';

type ExpiredAssetsApiResponse = {
	expiredAssets: {
		assetType: AssetType;
		title: string;
		usages: number;
	}[];
};

async function fetchStructureData({
	language,
	paginationSpecs,
	space,
}: {
	language: Item;
	paginationSpecs?: {delta: number; page: number};
	space: Item;
}) {
	const queryParams = buildQueryString(
		{
			depotEntryId: space?.value,
			languageId: language?.value,
			page: paginationSpecs?.page?.toString() || '',
			pageSize: paginationSpecs?.delta?.toString() || '',
		},
		{
			shouldIgnoreParam: (value) => value === '',
		}
	);

	const endpoint = `/o/analytics-cms-rest/v1.0/endpoint${queryParams}`;

	const {data, error} =
		await ApiHelper.get<ExpiredAssetsApiResponse>(endpoint);

	if (error) {
		console.error(error);
	}

	if (data) {
		return data;
	}

	// return {
	// 	expiredAssets: [
	// 		{
	// 			assetType: AssetType.JournalArticle,
	// 			title: 'Understanding Quantum Computing for Beginners',
	// 			usages: 3,
	// 		},
	// 		{
	// 			assetType: AssetType.KnowledgeTransfer,
	// 			title: 'A Guide to Sustainable Energy Solutions',
	// 			usages: 8,
	// 		},
	// 		{
	// 			assetType: AssetType.WebContent,
	// 			title: 'Top 10 Tips for Remote Work Productivity',
	// 			usages: 6,
	// 		},
	// 		{
	// 			assetType: AssetType.JournalArticle,
	// 			title: 'Exploring the History of Artificial Intelligence',
	// 			usages: 4,
	// 		},
	// 		{
	// 			assetType: AssetType.JournalArticle,
	// 			title: 'The Future of Space Exploration',
	// 			usages: 1,
	// 		},
	// 		{
	// 			assetType: AssetType.KnowledgeTransfer,
	// 			title: 'How to Build a Community Garden',
	// 			usages: 7,
	// 		},
	// 		{
	// 			assetType: AssetType.WebContent,
	// 			title: 'The Impact of Social Media on Mental Health',
	// 			usages: 5,
	// 		},
	// 		{
	// 			assetType: AssetType.KnowledgeTransfer,
	// 			title: 'Mastering the Art of Public Speaking',
	// 			usages: 9,
	// 		},
	// 		{
	// 			assetType: AssetType.WebContent,
	// 			title: 'The Basics of Cryptocurrency and Blockchain',
	// 			usages: 11,
	// 		},
	// 		{
	// 			assetType: AssetType.JournalArticle,
	// 			title: 'Advancements in Renewable Energy Technologies',
	// 			usages: 2,
	// 		},
	// 		{
	// 			assetType: AssetType.WebContent,
	// 			title: 'The Role of AI in Modern Healthcare',
	// 			usages: 10,
	// 		},
	// 	],
	// };

}

function ExpiredAssetItem({
	assetType,
	title,
	usage,
}: {
	assetType: AssetTypeIcon;
	title: string;
	usage: number;
}) {
	return (
		<div className="align-items-center cms-dashboard__expired-assets__item d-flex">
			<div className="bg-white mb-1 mr-2 p-2 rounded-lg">
				<Icon
					className="mx-1"
					color={assetType.color || '#5c9531'}
					symbol={assetType.symbol || 'blogs'}
				/>
			</div>

			<div className="d-flex flex-column">
				<Link
					className="text-dark"
					displayType="primary"
					href=""
					weight="semi-bold"
				>
					{title}
				</Link>

				<Text color="secondary" size={3}>
					{sub(
						usage === 1
							? Liferay.Language.get('x-usage')
							: Liferay.Language.get('x-usages'),
						[usage]
					).toLowerCase()}
				</Text>
			</div>
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
			const data = await fetchStructureData({language, space});

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

		return (
			expiredAssetsList?.expiredAssets.slice(startIndex, endIndex) || []
		);
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
											assetType={
												AssetTypeIcons[row['assetType']]
											}
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
						totalItems={
							expiredAssetsList?.expiredAssets.length || 0
						}
					/>
				</BaseCard>
			)}
		</div>
	);
}

export {ExpiredAssetsCard};
