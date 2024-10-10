import AssetAppearsOnQuery from 'shared/queries/AssetAppearsOnQuery';
import BaseCard from 'shared/components/base-card';
import ClayLink from '@clayui/link';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import FaroConstants from 'shared/util/constants';
import React, {useMemo, useState} from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import Table from 'shared/components/table';
import TextTruncate from 'shared/components/TextTruncate';
import URLConstants from 'shared/util/url-constants';
import {AssetTypes} from 'shared/util/constants';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {getSafeRangeSelectors} from 'shared/util/util';
import {getUrl} from 'shared/util/urls';
import {metricsListColumns} from 'shared/util/table-columns';
import {pickBy} from 'lodash';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {Routes} from 'shared/util/router';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

const {cur, delta, deltaValues} = FaroConstants.pagination;

export enum Accessor {
	ViewsMetric = 'viewsMetric',
	DownloadsMetric = 'downloadsMetric',
	SubmissionsMetric = 'submissionsMetric',
	PreviewsMetric = 'previewsMetric'
}

export enum EmptyStateLink {
	Asset = URLConstants.AssetsCustomAssetsListDocumentation,
	Blog = URLConstants.AssetsAppearsBlogsOnDocumentation,
	Document = URLConstants.AssetsAppearsDocumentsAndMediaOnDocumentation,
	Form = URLConstants.AssetsAppearsFormsOnDocumentation,
	Journal = URLConstants.AssetsAppearsWebContentOnDocumentation
}

export enum EmptyStateText {
	Blog = Liferay.Language.get('learn-more-about-blogs'),
	Document = Liferay.Language.get('learn-more-about-documents-and-media'),
	Form = Liferay.Language.get('learn-more-about-forms'),
	Journal = Liferay.Language.get('learn-more-about-web-content')
}

interface IAssetAppearsOnCardProps {
	accessors: Accessor[];
	assetType: AssetTypes;
	emptyStateLink: EmptyStateLink;
	emptyStateText: EmptyStateText;
}

export const AssetAppearsOnCard: React.FC<IAssetAppearsOnCardProps> = ({
	accessors,
	assetType,
	emptyStateLink,
	emptyStateText
}) => (
	<BaseCard
		label={Liferay.Language.get('asset-appears-on')}
		legacyDropdownRangeKey={false}
		minHeight={536}
		reportContainer={ReportContainer.AssetAppearsOnCard}
	>
		{({rangeSelectors}) => (
			<AssetAppearsOnStateRenderer
				accessors={accessors}
				assetType={assetType}
				emptyStateLink={emptyStateLink}
				emptyStateText={emptyStateText}
				rangeSelectors={rangeSelectors}
			/>
		)}
	</BaseCard>
);

const AssetAppearsOnStateRenderer = ({
	accessors,
	assetType,
	emptyStateLink,
	emptyStateText,
	rangeSelectors
}) => {
	const {assetId, channelId, title} = useParams();
	const [pagination, setPagination] = useState({
		page: cur,
		size: delta,
		start: (cur - 1) * delta
	});

	const {data, error, loading} = useQuery(AssetAppearsOnQuery, {
		fetchPolicy: 'network-only',
		variables: {
			assetId,
			assetType: assetType.toUpperCase(),
			channelId,
			selectedMetrics: accessors,
			title: decodeURIComponent(title),
			...pagination,
			...getSafeRangeSelectors(rangeSelectors)
		}
	});

	return (
		<StatesRenderer
			empty={!data?.assetPages.total}
			error={!!error}
			loading={loading}
		>
			<StatesRenderer.Loading />
			<StatesRenderer.Empty
				description={
					<>
						<span className='mr-1'>
							{Liferay.Language.get(
								'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
							)}
						</span>

						<ClayLink
							href={emptyStateLink}
							key='DOCUMENTATION'
							target='_blank'
						>
							{emptyStateText}
						</ClayLink>
					</>
				}
				showIcon={false}
				title={Liferay.Language.get(
					'there-are-no-assets-on-the-selected-period'
				)}
			/>
			<StatesRenderer.Error apolloError={error}>
				<ErrorDisplay />
			</StatesRenderer.Error>
			<StatesRenderer.Success>
				<AssetApperarsOnContentCard
					accessors={accessors}
					data={data}
					onPaginationChange={setPagination}
					pagination={pagination}
				/>
			</StatesRenderer.Success>
		</StatesRenderer>
	);
};

const formatItems = data =>
	data.assetPages.assetMetrics.map(
		({assetId, assetTitle, selectedMetrics}) => ({
			title: assetTitle ? assetTitle : assetId,
			touchpoint: assetId,
			...selectedMetrics.reduce((acc, {name, value}) => {
				acc[name] = value;

				return acc;
			}, {})
		})
	);

const AssetApperarsOnContentCard = ({
	accessors,
	data,
	onPaginationChange,
	pagination
}) => {
	const {channelId, groupId} = useParams();
	const rangeSelectors = useQueryRangeSelectors();

	const items = useMemo(() => formatItems(data), [data]);

	return (
		<>
			<Table
				className='mb-3 table-hover'
				columns={getTableColumns({
					accessors,
					channelId,
					groupId,
					rangeSelectors
				})}
				items={items}
				rowIdentifier={['touchpoint', 'title']}
			/>

			<ClayPaginationBarWithBasicItems
				active={pagination.page}
				activeDelta={pagination.size}
				className='px-3 pb-2'
				deltas={deltaValues.map(delta => ({label: delta}))}
				onActiveChange={page =>
					onPaginationChange({
						...pagination,
						page,
						start: (page - 1) * pagination.size
					})
				}
				onDeltaChange={size =>
					onPaginationChange({...pagination, size})
				}
				totalItems={data?.assetPages.total}
			/>
		</>
	);
};

const getTableColumns = ({accessors, channelId, groupId, rangeSelectors}) => {
	const generateURL = ({title, touchpoint}) => {
		const router = {
			params: {
				channelId,
				groupId,
				title,
				touchpoint: encodeURIComponent(touchpoint)
			},
			query: {
				...pickBy(rangeSelectors)
			}
		};

		return getUrl(Routes.SITES_TOUCHPOINTS_OVERVIEW, router);
	};

	const tableColumns = [
		{
			accessor: 'title',
			cellRenderer: ({data}) => {
				const url = generateURL(data);

				return (
					<td className='table-cell-expand'>
						<ClayLink
							className='font-weight-semibold text-truncate-inline text-dark'
							href={url}
						>
							<TextTruncate title={data.title} />
						</ClayLink>
					</td>
				);
			},
			className: 'table-cell-expand',
			label: Liferay.Language.get('page-name'),
			sortable: false,
			title: true
		},
		{
			accessor: 'url',
			cellRenderer: ({data}) => (
				<td className='table-cell-expand'>
					<ClayLink
						className='text-secondary text-truncate-inline'
						// @ts-ignore
						externalLink
						href={data.touchpoint}
						target='_blank'
					>
						<TextTruncate title={data.touchpoint} />
					</ClayLink>
				</td>
			),
			className: 'table-cell-expand',
			label: Liferay.Language.get('canonical-url'),
			sortable: false
		},
		...accessors.map(accessor => ({
			...metricsListColumns[accessor],
			sortable: false
		}))
	];

	return tableColumns;
};
