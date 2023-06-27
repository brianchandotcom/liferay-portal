import AcquisitionsQuery, {
	AcquisitionsQueryData,
	AcquisitionsQueryVariables
} from 'shared/queries/AcquisitionsQuery';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import CardTabs from 'shared/components/CardTabs';
import CardWithRangeKey from 'shared/hoc/CardWithRangeKey';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import React, {useContext, useState} from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import Table from 'shared/components/table';
import URLConstants from 'shared/util/url-constants';
import {ACQUISITION_LABEL_MAP} from 'shared/util/lang';
import {AcquisitionTypes, CompositionTypes} from 'shared/util/constants';
import {ApolloError} from 'apollo-client';
import {compositionListColumns} from 'shared/util/table-columns';
import {getSafeRangeSelectors} from 'shared/util/util';
import {RangeSelectors} from 'shared/types';
import {useQuery} from '@apollo/react-hooks';

const ROW_IDENTIFIER = 'name';

const {Channel, Referrer, SourceMedium} = AcquisitionTypes;

const getColumnsFn = acquisitionType => {
	const label = ACQUISITION_LABEL_MAP[acquisitionType];

	return ({maxCount, totalCount}) => [
		compositionListColumns.getName({
			label,
			maxWidth: 200,
			sortable: true,
			tooltip: true
		}),
		compositionListColumns.getRelativeMetricBar({
			label: Liferay.Language.get('sessions'),
			maxCount,
			totalCount
		}),
		compositionListColumns.getPercentOf({
			metricName: Liferay.Language.get('sessions'),
			totalCount
		})
	];
};

const tabs = [
	{
		getColumns: getColumnsFn(Channel),
		rowIdentifier: ROW_IDENTIFIER,
		tabId: Channel,
		title: Liferay.Language.get('channels')
	},
	{
		getColumns: getColumnsFn(SourceMedium),
		rowIdentifier: ROW_IDENTIFIER,
		tabId: SourceMedium,
		title: Liferay.Language.get('source-medium')
	},
	{
		getColumns: getColumnsFn(Referrer),
		rowIdentifier: ROW_IDENTIFIER,
		tabId: Referrer,
		title: Liferay.Language.get('referrers')
	}
];

interface IAcquisitionsCardProps extends React.HTMLAttributes<HTMLElement> {
	compositionBagName: CompositionTypes;
	label: string;
	legacyDropdownRangeKey?: boolean;
}

const AcquisitionsCard: React.FC<IAcquisitionsCardProps> = ({
	className,
	compositionBagName,
	label,
	legacyDropdownRangeKey
}) => (
	<CardWithRangeKey
		className={className}
		label={label}
		legacyDropdownRangeKey={legacyDropdownRangeKey}
	>
		{({rangeSelectors}) => (
			<AcquisitionsCardWithData
				compositionBagName={compositionBagName}
				rangeSelectors={rangeSelectors}
			/>
		)}
	</CardWithRangeKey>
);

interface IAcquisitionsCard extends Partial<IAcquisitionsCardProps> {
	rangeSelectors: RangeSelectors;
}

const AcquisitionsCardWithData: React.FC<IAcquisitionsCard> = ({
	compositionBagName,
	rangeSelectors
}) => {
	const [activeTabId, setActiveTabId] = useState<string>(tabs[0].tabId);
	const {
		router: {
			params: {channelId}
		}
	} = useContext(BasePage.Context);
	const {data, error, loading} = useQuery<
		AcquisitionsQueryData,
		AcquisitionsQueryVariables
	>(AcquisitionsQuery, {
		variables: {
			...getSafeRangeSelectors(rangeSelectors),
			activeTabId,
			channelId,
			size: 5,
			start: 0
		}
	});

	const {getColumns, rowIdentifier} = tabs.find(
		({tabId}) => tabId === activeTabId
	);

	const {compositions = [], maxCount = 0, total = 0, totalCount = 0} =
		data?.[compositionBagName] ?? {};

	return (
		<Card.Body className='w-100 d-flex flex-column flex-grow-1' noPadding>
			<CardTabs
				activeTabId={activeTabId}
				onChange={tabId => setActiveTabId(tabId)}
				tabs={tabs.map(({tabId, title}) => ({tabId, title}))}
			/>

			<AcquisitionsCardWithStatesRenderer
				empty={!total}
				error={error}
				loading={loading}
			>
				<Table
					className='flex-grow-1 table-hover'
					columns={getColumns({
						items: compositions,
						maxCount,
						total,
						totalCount
					} as any)}
					items={compositions}
					rowIdentifier={rowIdentifier}
				/>
			</AcquisitionsCardWithStatesRenderer>
		</Card.Body>
	);
};

interface IAcquisitionsCardWithStatesRendererProps
	extends React.HTMLAttributes<HTMLElement> {
	empty?: boolean;
	error: ApolloError;
	loading?: boolean;
}

const AcquisitionsCardWithStatesRenderer: React.FC<IAcquisitionsCardWithStatesRendererProps> = ({
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
						href={URLConstants.SitesDashboardAcquisitions}
						key='DOCUMENTATION'
						target='_blank'
					>
						{Liferay.Language.get('learn-more-about-acquisitions')}
					</a>
				</>
			}
			showIcon={false}
			title={Liferay.Language.get(
				'there-are-no-sessions-on-the-selected-period'
			)}
		/>
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export default AcquisitionsCard;
