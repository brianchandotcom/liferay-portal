import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import CardTabs from 'shared/components/CardTabs';
import CardWithRangeKey from 'shared/hoc/CardWithRangeKey';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import React, {useContext, useState} from 'react';
import SitesTopPagesQuery, {
	SitesTopPagesQueryData,
	SitesTopPagesQueryVariables
} from 'shared/queries/SitesTopPagesQuery';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import Table from 'shared/components/table';
import URLConstants from 'shared/util/url-constants';
import {ApolloError} from 'apollo-client';
import {ENTRANCES_METRIC, EXIT_RATE_METRIC} from 'shared/util/pagination';
import {getSafeRangeSelectors} from 'shared/util/util';
import {metricsListColumns} from 'shared/util/table-columns';
import {NameCell} from 'shared/components/table/cell-components';
import {OrderByDirections} from 'shared/util/constants';
import {pickBy} from 'lodash';
import {RangeSelectors} from 'shared/types';
import {setUriQueryValues} from 'shared/util/router';
import {useQuery} from '@apollo/react-hooks';

const ROW_IDENTIFIER = ['assetId', 'assetTitle'];

const ASSET_TITLE_COLUMN = {
	cellRenderer: NameCell,
	cellRendererProps: {
		nameKey: 'assetTitle',
		renderSecondaryInfo: ({assetId}) => assetId
	},
	className: 'table-cell-expand',
	label: `${Liferay.Language.get('page-title')}
			|
			${Liferay.Language.get('canonical-url')}`,
	sortable: false
};

const DEFAULT_METRIC_COLUMN = {
	sortable: false,
	title: true
};

const tabs = [
	{
		getColumns: () => [
			ASSET_TITLE_COLUMN,
			{
				...DEFAULT_METRIC_COLUMN,
				...metricsListColumns.visitorsMetric,
				accessor: 'visitorsMetric.value'
			}
		],
		rowIdentifier: ROW_IDENTIFIER,
		tabId: 'visitorsMetric',
		title: Liferay.Language.get('visited-pages')
	},
	{
		getColumns: () => [
			ASSET_TITLE_COLUMN,
			{
				...DEFAULT_METRIC_COLUMN,
				...metricsListColumns.entrancesMetric,
				accessor: 'entrancesMetric.value'
			}
		],
		rowIdentifier: ROW_IDENTIFIER,
		tabId: ENTRANCES_METRIC,
		title: Liferay.Language.get('entrance-pages')
	},
	{
		getColumns: () => [
			ASSET_TITLE_COLUMN,
			{
				...DEFAULT_METRIC_COLUMN,
				...metricsListColumns.exitRateMetric,
				accessor: 'exitRateMetric.value'
			}
		],
		rowIdentifier: ROW_IDENTIFIER,
		tabId: EXIT_RATE_METRIC,
		title: Liferay.Language.get('exit-pages')
	}
];

interface ITopPagesCardProps extends React.HTMLAttributes<HTMLElement> {
	footer: {
		label: string;
		href: string;
	};
	label: string;
	legacyDropdownRangeKey?: boolean;
}

const TopPagesCard: React.FC<ITopPagesCardProps> = ({
	className,
	footer,
	label,
	legacyDropdownRangeKey
}) => (
	<CardWithRangeKey
		className={className}
		label={label}
		legacyDropdownRangeKey={legacyDropdownRangeKey}
	>
		{({rangeSelectors}) => (
			<TopPagesCardWithData
				footer={footer}
				rangeSelectors={rangeSelectors}
			/>
		)}
	</CardWithRangeKey>
);

interface ITopPageCardWithData extends Partial<ITopPagesCardProps> {
	rangeSelectors: RangeSelectors;
}

const TopPagesCardWithData: React.FC<ITopPageCardWithData> = ({
	footer,
	rangeSelectors
}) => {
	const [activeTabId, setActiveTabId] = useState(tabs[0].tabId);
	const {
		router: {
			params: {channelId}
		}
	} = useContext(BasePage.Context);
	const {data, error, loading = false} = useQuery<
		SitesTopPagesQueryData,
		SitesTopPagesQueryVariables
	>(SitesTopPagesQuery, {
		variables: {
			...getSafeRangeSelectors(rangeSelectors),
			channelId,
			size: 5,
			sort: {
				column: activeTabId,
				type: OrderByDirections.Descending
			},
			start: 0
		}
	});

	const {getColumns, rowIdentifier} = tabs.find(
		({tabId}) => tabId === activeTabId
	);

	return (
		<>
			<Card.Body
				className='w-100 d-flex flex-column flex-grow-1'
				noPadding
			>
				<CardTabs
					activeTabId={activeTabId}
					onChange={tabId => setActiveTabId(tabId)}
					tabs={tabs.map(({tabId, title}) => ({tabId, title}))}
				/>

				<TopPagesCardWithStatesRenderer
					empty={!data?.pages.total}
					error={error}
					loading={loading}
				>
					<Table
						className='flex-grow-1 table-hover'
						columns={getColumns() as any}
						items={data?.pages.assetMetrics ?? []}
						rowIdentifier={rowIdentifier}
					/>
				</TopPagesCardWithStatesRenderer>
			</Card.Body>

			{!!Object.keys(footer).length && (
				<Card.Footer>
					<ClayLink
						borderless
						button
						className='button-root'
						displayType='secondary'
						href={setUriQueryValues(
							pickBy({...rangeSelectors}),
							footer.href
						)}
						small
					>
						{footer.label}

						<ClayIcon
							className='icon-root ml-2'
							symbol='angle-right-small'
						/>
					</ClayLink>
				</Card.Footer>
			)}
		</>
	);
};

interface ITopPagesCardWithStatesRendererProps
	extends React.HTMLAttributes<HTMLElement> {
	empty?: boolean;
	error: ApolloError;
	loading?: boolean;
}

const TopPagesCardWithStatesRenderer: React.FC<ITopPagesCardWithStatesRendererProps> = ({
	children,
	empty,
	error,
	loading
}) => (
	<StatesRenderer empty={empty} error={!!error} loading={loading}>
		<StatesRenderer.Loading />
		<StatesRenderer.Empty
			description={
				<>
					<span className='mr-1'>
						{Liferay.Language.get(
							'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
						)}
					</span>

					<a
						href={URLConstants.SitesDashboardTopPages}
						key='DOCUMENTATION'
						target='_blank'
					>
						{Liferay.Language.get('learn-more-about-pages')}
					</a>
				</>
			}
			showIcon={false}
			title={Liferay.Language.get(
				'there-are-no-visitors-on-the-selected-period'
			)}
		/>
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export default TopPagesCard;
