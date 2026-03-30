import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import FaroConstants from 'shared/util/constants';
import React, {useMemo, useState} from 'react';
import {columns, pagination} from 'shared/util/frontend-data-set';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {pickBy} from 'lodash';
import {RangeSelectors} from 'shared/types';
import {removeUriQueryParam, setUriQueryValues} from 'shared/util/router';
import {useChannelContext} from 'shared/context/channel';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {useHistory, useParams} from 'react-router-dom';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

const {cur: DEFAULT_CUR} = FaroConstants.pagination;

const List = () => {
	const history = useHistory();

	const {selectedChannel} = useChannelContext();
	const {channelId, groupId} = useParams();
	const initialRangeSelectors = useQueryRangeSelectors();

	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		initialRangeSelectors
	);

	const FrontendDataSet = useFrontendDataSet();

	let queryParams = `channelId=${channelId}&rangeKey=${rangeSelectors.rangeKey}`;

	if (rangeSelectors.rangeEnd) {
		queryParams += `&rangeEnd=${rangeSelectors.rangeEnd}`;
	}

	if (rangeSelectors.rangeStart) {
		queryParams += `&rangeStart=${rangeSelectors.rangeStart}`;
	}

	const filters = useMemo(
		() => [
			{
				apiURL: `/o/faro/contacts/${groupId}/asset-summary-types?${queryParams}`,
				entityFieldType: 'string',
				id: 'assetType',
				itemKey: 'name',
				itemLabel: 'name',
				label: Liferay.Language.get('type'),
				multiple: true,
				type: 'selection'
			},
			{
				apiURL: `/o/faro/contacts/${groupId}/asset-summary-tags?${queryParams}`,
				entityFieldType: 'string',
				id: 'assetTags',
				itemKey: 'name',
				itemLabel: 'name',
				label: Liferay.Language.get('tags'),
				multiple: true,
				type: 'selection'
			},
			{
				apiURL: `/o/faro/contacts/${groupId}/asset-summary-categories?${queryParams}`,
				entityFieldType: 'string',
				id: 'assetCategories',
				itemKey: 'name',
				itemLabel: 'name',
				label: Liferay.Language.get('categories'),
				multiple: true,
				type: 'selection'
			}
		],
		[channelId, groupId, rangeSelectors.rangeKey]
	);

	return (
		<BasePage documentTitle={Liferay.Language.get('assets')}>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel?.name
					})
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('assets')}
				/>
			</BasePage.Header>

			<BasePage.SubHeader>
				<div className='d-flex justify-content-end w-100'>
					<DropdownRangeKey
						legacy={false}
						onRangeSelectorChange={rangeSelectors => {
							history.push(
								setUriQueryValues(
									pickBy({
										page: DEFAULT_CUR,
										...rangeSelectors
									}),
									removeUriQueryParam(
										window.location.href,
										'rangeEnd',
										'rangeStart'
									)
								)
							);

							setRangeSelectors(rangeSelectors);
						}}
						rangeSelectors={rangeSelectors}
					/>
				</div>
			</BasePage.SubHeader>

			<BasePage.Body>
				<Card>
					{FrontendDataSet && (
						<FrontendDataSet
							apiURL={`/o/faro/contacts/${groupId}/asset-summary?${queryParams}`}
							// Trick to turn off dirty the URL with paramas.

							configInURLBehavior='off'
							customDataRenderers={{
								assetMetricRenderer:
									columns.assetMetricRenderer,
								assetTitleRenderer: columns.assetTitleRenderer({
									channelId,
									groupId
								})
							}}
							filters={filters}
							id='assetTable'
							// Trick to restart FDS every time the rangeSelectors changes.

							key={Object.values(rangeSelectors).join()}
							pagination={pagination}
							showPagination
							views={[
								{
									contentRenderer: 'table',
									default: false,
									label: 'table',
									name: 'table',
									schema: {
										fields: [
											{
												contentRenderer:
													'assetTitleRenderer',
												fieldName: 'assetTitle',
												label: Liferay.Language.get(
													'title'
												),
												sortable: true,
												truncate: true
											},
											{
												fieldName: 'assetType',
												label: Liferay.Language.get(
													'type'
												),
												sortable: true
											},
											{
												contentRenderer:
													'assetMetricRenderer',
												fieldName: 'viewsMetric',
												label: Liferay.Language.get(
													'views'
												),
												sortable: true
											},
											{
												contentRenderer:
													'assetMetricRenderer',
												fieldName: 'impressionsMetric',
												label: Liferay.Language.get(
													'impressions'
												),
												sortable: true
											},
											{
												contentRenderer:
													'assetMetricRenderer',
												fieldName: 'downloadsMetric',
												label: Liferay.Language.get(
													'downloads'
												),
												sortable: true,
												visible: false
											}
										]
									},
									thumbnail: 'table'
								}
							]}
						/>
					)}
				</Card>
			</BasePage.Body>
		</BasePage>
	);
};

export default List;
