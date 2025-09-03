/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {Context} from '../../Context';
import useFetch from '../../hooks/useFetch';
import {MetricType} from '../../types/global';
import {buildQueryString} from '../../utils/buildQueryString';
import {getPercentage, toThousands} from '../../utils/math';
import Title from '../Title';

export type Metric = {
	metricType: MetricType;
	value: number;
};

export type TopPage = {
	canonicalUrl: string;
	defaultMetric: Metric;
	pageTitle: string;
	siteName: string;
};

export type AssetData = {
	topPages: TopPage[];
	totalCount: number;
};

export type FormattedPage = {
	count: string;
	link: string;
	page: string;
	percentage: string;
};

type TopPagesMetricsTableProps = {
	data: AssetData;
};

const TopPagesMetricsTable: React.FC<TopPagesMetricsTableProps> = ({data}) => {
	const formattedData: FormattedPage[] = data.topPages.map((topPage) => ({
		count: toThousands(topPage.defaultMetric.value),
		link: topPage.canonicalUrl,
		page: topPage.pageTitle,
		percentage: `${getPercentage((topPage.defaultMetric.value / data.totalCount) * 100)}%`,
	}));

	return (
		<div className="align-items-center justify-content-around mt-3">
			<Title
				section
				value={Liferay.Language.get('top-pages-asset-appears-on')}
			/>

			<div className="mb-3">
				<span className="text-3 text-secondary">
					{Liferay.Language.get(
						'this-metric-calculates-the-top-three-pages-that-generated-the-highest-number-of-views-for-the-asset'
					)}
				</span>
			</div>

			<ClayTable hover={false} responsive>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell headingCell noWrap>
							<span>{Liferay.Language.get('page-title')}</span>
						</ClayTable.Cell>

						<ClayTable.Cell headingCell>
							{Liferay.Language.get('views')}
						</ClayTable.Cell>

						<ClayTable.Cell headingCell>
							{sub(Liferay.Language.get('x-of-x'), [
								'%',
								Liferay.Language.get('views'),
							])}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{formattedData.map((row) => (
						<ClayTable.Row key={row.page}>
							<ClayTable.Cell>
								<ClayLink
									displayType="tertiary"
									href={row.link}
									weight="semi-bold"
								>
									{row.page}
								</ClayLink>
							</ClayTable.Cell>

							<ClayTable.Cell align="right">
								{row.count}
							</ClayTable.Cell>

							<ClayTable.Cell align="right">
								{row.percentage}
							</ClayTable.Cell>
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
};

const TopPagesMetrics: React.FC = () => {
	const {externalReferenceCode, filters} = useContext(Context);

	const queryString = buildQueryString({
		externalReferenceCode,
		groupId: filters.channel,
		rangeKey: filters.rangeSelector.rangeKey,
	});

	const {data, loading} = useFetch<AssetData>(
		`/o/analytics-cms-rest/v1.0/object-entry-top-pages${queryString}`
	);

	if (loading) {
		return <ClayLoadingIndicator className="my-5" />;
	}

	if (!data) {
		return null;
	}

	return <TopPagesMetricsTable data={data} />;
};

export {TopPagesMetrics, TopPagesMetricsTable};
