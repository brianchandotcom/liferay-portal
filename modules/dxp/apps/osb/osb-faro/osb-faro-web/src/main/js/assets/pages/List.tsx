import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import FaroConstants from 'shared/util/constants';
import React, {useMemo, useState} from 'react';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {getMimeType} from 'assets/components/mime-type';
import {InfoPanel} from 'assets/components/InfoPanel';
import {pagination, useSnapshots} from 'shared/util/frontend-data-set';
import {pickBy} from 'lodash';

import {RangeSelectors} from 'shared/types';
import {
	removeUriQueryParam,
	Routes,
	setUriQueryValues,
	toRoute
} from 'shared/util/router';
import {toThousands} from 'shared/util/numbers';
import {useChannelContext} from 'shared/context/channel';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {useHistory, useParams} from 'react-router-dom';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

const {cur: DEFAULT_CUR} = FaroConstants.pagination;

const mapRoutes = {
	blog: Routes.ASSETS_BLOGS_OVERVIEW,
	document: Routes.ASSETS_DOCUMENTS_AND_MEDIA_OVERVIEW,
	form: Routes.ASSETS_FORMS_OVERVIEW,
	webContent: Routes.ASSETS_WEB_CONTENT_OVERVIEW
};

const getAssetURL = ({
	channelId,
	groupId,
	itemData,
	rangeSelectorParams,
	value = ''
}: {
	channelId: string;
	groupId: string;
	itemData: any;
	rangeSelectorParams: string;
	value?: string;
}) => {
	const assetTitle = value || itemData.assetTitle || itemData.id;

	const oldAssetRoute = mapRoutes?.[itemData.assetType];

	const route = oldAssetRoute ?? Routes.ASSETS_OBJECT_ENTRY_OVERVIEW;

	return `${toRoute(route, {
		assetId: itemData.id,
		channelId,
		groupId,
		touchpoint: 'Any',
		...(itemData.assetType && {
			type: encodeURIComponent(itemData.assetType)
		}),
		...(assetTitle && {
			title: encodeURIComponent(assetTitle)
		})
	})}?${rangeSelectorParams}`;
};

const columns = {
	assetMetricRenderer: ({value}) => <span>{toThousands(value.value)}</span>,
	assetTitleRenderer: ({channelId, groupId, rangeSelectorParams}) => ({
		itemData,
		value
	}) => {
		const URL = getAssetURL({
			channelId,
			groupId,
			itemData,
			rangeSelectorParams,
			value
		});

		const mimeType = getMimeType({
			assetType: itemData?.assetType,
			mimeType: itemData?.assetMimeType
		});

		return (
			<div className='align-items-center d-flex'>
				<div className='mr-3'>
					<ClaySticker
						className={mimeType.className}
						displayType='dark'
					>
						<ClayIcon symbol={mimeType.icon} />
					</ClaySticker>
				</div>

				<div>
					<ClayLink displayType='tertiary' href={URL}>
						{value || itemData.id}
					</ClayLink>
				</div>
			</div>
		);
	}
};

const List = () => {
	const history = useHistory();
	const {selectedChannel} = useChannelContext();
	const {channelId, groupId} = useParams();
	const initialRangeSelectors = useQueryRangeSelectors();

	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		initialRangeSelectors
	);

	const [infoPanelData, setInfoPanelData] = useState(null);

	const snapshots = useSnapshots('assetTable');

	const FrontendDataSet = useFrontendDataSet();

	let rangeSelectorParams = `rangeKey=${rangeSelectors.rangeKey}`;

	if (rangeSelectors.rangeEnd) {
		rangeSelectorParams += `&rangeEnd=${rangeSelectors.rangeEnd}`;
	}

	if (rangeSelectors.rangeStart) {
		rangeSelectorParams += `&rangeStart=${rangeSelectors.rangeStart}`;
	}

	const filters = useMemo(
		() => [
			{
				apiURL: `/o/faro/contacts/${groupId}/asset-summary-types?channelId=${channelId}&${rangeSelectorParams}`,
				entityFieldType: 'string',
				id: 'assetType',
				itemKey: 'name',
				itemLabel: 'name',
				label: Liferay.Language.get('type'),
				multiple: true,
				type: 'selection'
			},
			{
				apiURL: `/o/faro/contacts/${groupId}/asset-summary-tags?channelId=${channelId}&${rangeSelectorParams}`,
				entityFieldType: 'string',
				id: 'tags/id',
				itemKey: 'id',
				itemLabel: 'name',
				label: Liferay.Language.get('tags'),
				multiple: true,
				type: 'selection'
			},
			{
				apiURL: `/o/faro/contacts/${groupId}/asset-summary-categories?channelId=${channelId}&${rangeSelectorParams}`,
				entityFieldType: 'string',
				id: 'categories/id',
				itemKey: 'id',
				itemLabel: 'name',
				label: Liferay.Language.get('categories'),
				multiple: true,
				type: 'selection'
			}
		],
		[groupId, rangeSelectorParams]
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
				fluid
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('assets')}
				/>
			</BasePage.Header>

			<BasePage.SubHeader fluid>
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

			<BasePage.Body fluid sidebarOpened={!!infoPanelData}>
				<Card>
					{FrontendDataSet && (
						<FrontendDataSet
							apiURL={`/o/faro/contacts/${groupId}/asset-summary?channelId=${channelId}&${rangeSelectorParams}`}
							// Trick to turn off dirty the URL with paramas.

							configInURLBehavior='off'
							customDataRenderers={{
								assetMetricRenderer:
									columns.assetMetricRenderer,
								assetTitleRenderer: columns.assetTitleRenderer({
									channelId,
									groupId,
									rangeSelectorParams
								})
							}}
							filters={filters}
							id='assetTable'
							itemsActions={[
								{
									data: {
										id: 'infoPanel'
									},
									icon: 'info-circle-open',
									label: Liferay.Language.get('show-details'),
									onClick: setInfoPanelData
								},
								{
									data: {
										id: 'viewAsset'
									},
									icon: 'view',
									label: Liferay.Language.get('view'),
									onClick: ({itemData}) => {
										history.push(
											getAssetURL({
												channelId,
												groupId,
												itemData,
												rangeSelectorParams
											})
										);
									}
								}
							]}
							// Trick to restart FDS every time the rangeSelectors changes.

							key={Object.values(rangeSelectors).join()}
							pagination={pagination}
							showPagination
							snapshots={snapshots}
							snapshotsEnabled
							views={[
								{
									contentRenderer: 'table',
									default: true,
									label: Liferay.Language.get('default-view'),
									name: 'table',
									schema: {
										fields: [
											{
												_key: 'assetTitle',
												contentRenderer:
													'assetTitleRenderer',
												fieldName: 'assetTitle',
												label: `${Liferay.Language.get(
													'title'
												)} | ${Liferay.Language.get(
													'erc'
												)}`,
												sortable: true,
												truncate: true
											},
											{
												_key: 'assetTypeMetric',
												fieldName: 'assetType',
												label: Liferay.Language.get(
													'type'
												),
												sortable: true
											},
											{
												_key: 'viewsMetric',
												contentRenderer:
													'assetMetricRenderer',
												fieldName: 'viewsMetric',
												label: Liferay.Language.get(
													'views'
												),
												sortable: true
											},
											{
												_key: 'impressionsMetric',
												contentRenderer:
													'assetMetricRenderer',
												fieldName: 'impressionsMetric',
												label: Liferay.Language.get(
													'impressions'
												),
												sortable: true
											},
											{
												_key: 'downloadsMetric',
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

				<InfoPanel
					data={infoPanelData}
					onClose={() => setInfoPanelData(null)}
				/>
			</BasePage.Body>
		</BasePage>
	);
};

export default List;
