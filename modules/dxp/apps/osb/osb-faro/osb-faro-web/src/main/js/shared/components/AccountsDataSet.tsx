import Card from 'shared/components/Card';
import React from 'react';
import {columns, pagination, useSnapshots} from 'shared/util/frontend-data-set';
import {
	EConfigInURLBehavior,
	FrontendDataSet
} from '@liferay/frontend-data-set-web';
import {
	LifecycleStages,
	lifecycleStagesLabelMap
} from 'contacts/pages/account/utils/constants';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {RangeSelectors} from 'shared/types';
import {Routes} from 'shared/util/router';
import {toThousands} from 'shared/util/numbers';

const lifecycleStageItems = Object.entries(lifecycleStagesLabelMap).map(
	([stage]) => ({
		label: lifecycleStagesLabelMap[stage as LifecycleStages].label,
		value: stage
	})
);

interface IAccountsDataSetProps {
	apiURL: string;
	channelId: string;
	countryFilter?: string;
	groupId: string;
	industryFilter?: string;
	lifecycleStageFilter?: LifecycleStages;
	rangeSelectors?: RangeSelectors;
}

const buildSelectionPreloadedData = (value?: string, label?: string) =>
	value
		? {
				exclude: false,
				selectedItems: [{label: label ?? value, value}]
		  }
		: undefined;

const AccountsDataSet: React.FC<IAccountsDataSetProps> = ({
	apiURL,
	channelId,
	countryFilter,
	groupId,
	industryFilter,
	lifecycleStageFilter,
	rangeSelectors = {
		rangeEnd: null,
		rangeKey: RangeKeyTimeRanges.Last30Days,
		rangeStart: null
	}
}) => {
	const snapshots = useSnapshots('accounts-list-dataset');

	let rangeSelectorParams = `rangeKey=${rangeSelectors.rangeKey}`;

	if (rangeSelectors.rangeEnd) {
		rangeSelectorParams += `&rangeEnd=${rangeSelectors.rangeEnd}`;
	}

	if (rangeSelectors.rangeStart) {
		rangeSelectorParams += `&rangeStart=${rangeSelectors.rangeStart}`;
	}

	const rangeApiURL = `${apiURL}&${rangeSelectorParams}`;

	return (
		<Card>
			<FrontendDataSet
				apiURL={rangeApiURL}
				configInURLBehavior={EConfigInURLBehavior.OFF}
				customDataRenderers={{
					accountLifecycleStageRenderer: ({
						value
					}: {
						value: LifecycleStages;
					}) =>
						value &&
						columns.cmsLabelRenderer({
							displayType:
								lifecycleStagesLabelMap[value].displayType,
							label: lifecycleStagesLabelMap[value].label
						}),
					accountNameRenderer: ({
						itemData,
						value
					}: {
						itemData: {id: string | number};
						value: string;
					}) =>
						columns.nameAndLinkRenderer({
							channelId,
							groupId,
							itemData,
							route: Routes.CONTACTS_ACCOUNT,
							value
						}),
					annualRevenueRenderer: ({value}: {value: number}) => (
						<div>{toThousands(value)}</div>
					),
					dateRenderer: ({value}: {value: string}) =>
						columns.dateRenderer({itemData: {}, value})
				}}
				emptyState={{
					description: Liferay.Language.get(
						'no-accounts-were-synced-from-the-connected-data-sources'
					),
					image: '/states/satellite.svg',
					title: Liferay.Language.get('no-accounts-found')
				}}
				filters={[
					{
						id: 'lifecycleStatus',
						items: lifecycleStageItems,
						label: Liferay.Language.get('status'),
						name: 'status',
						preloadedData: buildSelectionPreloadedData(
							lifecycleStageFilter,
							lifecycleStageFilter
								? lifecycleStagesLabelMap[lifecycleStageFilter]
										.label
								: undefined
						),
						type: 'selection'
					},
					{
						apiURL: `/o/faro/contacts/${groupId}/account/fds_field_values?channelId=${channelId}&fieldMappingFieldName=industry`,
						entityFieldType: 'string',
						id: 'industry',
						itemKey: 'name',
						itemLabel: 'name',
						label: Liferay.Language.get('industry'),
						multiple: true,
						preloadedData:
							buildSelectionPreloadedData(industryFilter),
						type: 'selection'
					},
					{
						apiURL: `/o/faro/contacts/${groupId}/account/fds_field_values?channelId=${channelId}&fieldMappingFieldName=country`,
						entityFieldType: 'string',
						id: 'country',
						itemKey: 'name',
						itemLabel: 'name',
						label: Liferay.Language.get('country'),
						multiple: true,
						preloadedData:
							buildSelectionPreloadedData(countryFilter),
						type: 'selection'
					}
				]}
				id='accounts-list-dataset'
				key={[
					countryFilter,
					industryFilter,
					lifecycleStageFilter,
					...Object.values(rangeSelectors)
				].join()}
				pagination={pagination}
				showPagination
				snapshots={snapshots}
				snapshotsEnabled
				sorts={[
					{
						active: true,
						direction: 'asc',
						key: 'accountName',
						label: Liferay.Language.get('account')
					}
				]}
				views={[
					{
						contentRenderer: 'table',
						default: true,
						label: Liferay.Language.get('default-view'),
						name: 'table',
						schema: {
							fields: [
								{
									contentRenderer: 'accountNameRenderer',
									fieldName: 'accountName',
									label: Liferay.Language.get('account'),
									sortable: true,
									truncate: true
								},
								{
									fieldName: 'industry',
									label: Liferay.Language.get('industry'),
									sortable: true
								},
								{
									contentRenderer:
										'accountLifecycleStageRenderer',
									fieldName: 'lifecycleStage',
									label: Liferay.Language.get(
										'lifecycle-stage'
									),
									sortable: true
								},
								{
									contentRenderer: 'annualRevenueRenderer',
									fieldName: 'annualRevenue',
									label: Liferay.Language.get(
										'annual-revenue'
									),
									sortable: true
								},
								{
									fieldName: 'country',
									label: Liferay.Language.get('country'),
									sortable: true
								},
								{
									contentRenderer: 'dateRenderer',
									fieldName: 'lastActive',
									label: Liferay.Language.get('last-active'),
									sortable: true
								},
								{
									contentRenderer: 'dateRenderer',
									fieldName: 'lastEnriched',
									label: Liferay.Language.get(
										'last-enriched'
									),
									sortable: true
								}
							]
						},
						thumbnail: 'table'
					}
				]}
			/>
		</Card>
	);
};

export default AccountsDataSet;
