import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import ClayLabel from '@clayui/label';
import ClayPanel from '@clayui/panel';
import ClayTabs from '@clayui/tabs';
import FaroConstants from 'shared/util/constants';
import getCN from 'classnames';
import React, {useMemo, useRef, useState} from 'react';
import {AssetIcon} from './components/AssetsIcon';
import {ClayButtonWithIcon} from '@clayui/button';
import {columns, pagination, useSnapshots} from 'shared/util/frontend-data-set';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {Heading, Text} from '@clayui/core';
import {pickBy} from 'lodash';
import {RangeSelectors} from 'shared/types';
import {removeUriQueryParam, setUriQueryValues} from 'shared/util/router';
import {sub} from 'shared/util/lang';
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

	const [infoPanel, setInfoPanel] = useState(null);

	const snapshots = useSnapshots('assetTable');

	const FrontendDataSet = useFrontendDataSet();

	const sidePanelRef = useRef(null);

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
		<BasePage
			className={getCN({'info-panel-opened': !!infoPanel})}
			documentTitle={Liferay.Language.get('assets')}
		>
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
									label: Liferay.Language.get(
										'show-info-panel'
									),
									onClick: setInfoPanel
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
			</BasePage.Body>

			<div
				className={getCN(
					'frontend-data-set-side-panel  c-slideout c-slideout-absolute c-slideout-push c-slideout-end',
					{
						'c-slideout-shown': !!infoPanel
					}
				)}
				id='infoPanel'
				ref={sidePanelRef}
			>
				<div
					className={getCN('sidebar sidebar-light', {
						'c-slideout-show': !!infoPanel
					})}
					style={{width: 472}}
				>
					<div className='sidebar-header'>
						<div className='autofit-row'>
							<div className='autofit-col autofit-col-expand'>
								<span className='component-title'>
									<div className='align-items-center d-flex'>
										{infoPanel?.itemData?.mimeType && (
											<AssetIcon
												className='mb-1 mr-2'
												mimeType={
													infoPanel?.itemData
														?.mimeType
												}
											/>
										)}

										<Heading level={4} weight='semi-bold'>
											{infoPanel?.itemData?.assetTitle ??
												infoPanel?.itemData?.id}
										</Heading>
									</div>
								</span>
							</div>
							<div className='autofit-col'>
								<ClayButtonWithIcon
									className='close'
									displayType='unstyled'
									onClick={() => setInfoPanel(null)}
									symbol='times'
								/>
							</div>
						</div>
					</div>

					<ClayTabs>
						<ClayTabs.Item
							innerProps={{
								'aria-controls': 'tabpanel-1'
							}}
						>
							{Liferay.Language.get('categorization')}
						</ClayTabs.Item>
					</ClayTabs>

					<ClayTabs.Content fade>
						<ClayTabs.TabPane aria-labelledby='tab-1'>
							<InfoPanelItemContent
								items={infoPanel?.itemData?.assetCategories}
								title={Liferay.Language.get('categories')}
							/>

							<InfoPanelItemContent
								items={infoPanel?.itemData?.assetTags}
								title={Liferay.Language.get('tags')}
							/>
						</ClayTabs.TabPane>
					</ClayTabs.Content>
				</div>
			</div>
		</BasePage>
	);
};

const InfoPanelItemContent = ({items, title}) => (
	<ClayPanel
		collapsable={false}
		displayTitle={
			<ClayPanel.Header className='border-bottom'>
				<ClayPanel.Title className='panel-title text-secondary'>
					{title}
				</ClayPanel.Title>
			</ClayPanel.Header>
		}
		displayType='unstyled'
	>
		<ClayPanel.Body>
			{!items?.length && (
				<>
					<div className='mb-2'>
						<Text size={4} weight='semi-bold'>
							{sub(
								Liferay.Language.get(
									'no-x-were-found-for-this-asset'
								),
								[title]
							)}
						</Text>
					</div>

					<Text color='secondary' size={3}>
						{sub(
							Liferay.Language.get(
								'go-to-your-content-management-system-to-manage-x'
							),
							[title]
						)}
					</Text>
				</>
			)}

			{!!items?.length &&
				items.map(({id, name}) => (
					<ClayLabel className='label-lg' key={id}>
						{name}
					</ClayLabel>
				))}
		</ClayPanel.Body>
	</ClayPanel>
);

export default List;
