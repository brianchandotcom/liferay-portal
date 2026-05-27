import * as API from 'shared/api';
import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import {Text} from '@clayui/core';
import {toThousands} from 'shared/util/numbers';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

export type TaxonomyMetric =
	| 'downloadsMetric'
	| 'impressionsMetric'
	| 'viewsMetric';

export interface ITopCategory {
	downloadsMetric: {value: number};
	id: string;
	impressionsMetric: {value: number};
	name: string;
	viewsMetric: {value: number};
	vocabularyId: string;
	vocabularyName: string;
}

export interface ITopTag {
	downloadsMetric: {value: number};
	id: string;
	impressionsMetric: {value: number};
	name: string;
	viewsMetric: {value: number};
}

interface ITopCategoriesAndTagsProps {
	className?: string;
}

enum GroupByMetric {
	DOWNLOADS = 'downloads',
	IMPRESSIONS = 'impressions',
	VIEWS = 'views'
}

const GROUP_BY_TO_METRIC: Record<GroupByMetric, TaxonomyMetric> = {
	[GroupByMetric.DOWNLOADS]: 'downloadsMetric',
	[GroupByMetric.IMPRESSIONS]: 'impressionsMetric',
	[GroupByMetric.VIEWS]: 'viewsMetric'
};

const TABS = ['category', 'tag'] as const;

type TaxonomyItem = ITopCategory | ITopTag;

interface ITabContentProps {
	groupBy: GroupByMetric;
	isCategory: boolean;
	items: TaxonomyItem[];
	loading: boolean;
	selectedMetric: TaxonomyMetric;
	setGroupBy: (metric: GroupByMetric) => void;
}

const TabContent: React.FC<ITabContentProps> = ({
	groupBy,
	isCategory,
	items,
	loading,
	selectedMetric,
	setGroupBy
}) => {
	const groupByLabels: Record<GroupByMetric, string> = {
		[GroupByMetric.DOWNLOADS]: Liferay.Language.get('downloads'),
		[GroupByMetric.IMPRESSIONS]: Liferay.Language.get('impressions'),
		[GroupByMetric.VIEWS]: Liferay.Language.get('views')
	};

	const groupByLabel = groupByLabels[groupBy];

	const isEmpty = !loading && items.length === 0;

	return (
		<StatesRenderer empty={isEmpty} loading={loading}>
			<StatesRenderer.Loading />
			<StatesRenderer.Empty>
				<ClayEmptyState
					className='py-3 text-center'
					description={
						isCategory
							? Liferay.Language.get(
									'categories-will-appear-here-when-available'
							  )
							: Liferay.Language.get(
									'tags-will-appear-here-when-available'
							  )
					}
					small
					title={
						isCategory
							? Liferay.Language.get('no-categories-available')
							: Liferay.Language.get('no-tags-available')
					}
				/>
			</StatesRenderer.Empty>
			<StatesRenderer.Success>
				<ClayDropDown
					alignmentPosition={Align.BottomRight}
					closeOnClick
					trigger={
						<ClayButton
							borderless
							className='align-items-baseline d-inline-flex'
							displayType='unstyled'
							size='sm'
						>
							<div className='font-weight-semi-bold mr-3'>
								<Text size={3}>
									{Liferay.Language.get('group-by')}
								</Text>
							</div>

							<div className='font-weight-semi-bold text-secondary'>
								<Text size={3}>
									{groupByLabel}
									<ClayIcon
										className='ml-1'
										symbol='caret-bottom'
									/>
								</Text>
							</div>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{(Object.keys(groupByLabels) as GroupByMetric[]).map(
							key => (
								<ClayDropDown.Item
									key={key}
									onClick={() => setGroupBy(key)}
									symbolRight={
										groupBy === key ? 'check' : undefined
									}
								>
									{groupByLabels[key]}
								</ClayDropDown.Item>
							)
						)}
					</ClayDropDown.ItemList>
				</ClayDropDown>

				<ClayTable className='mt-3'>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell expanded headingCell>
								{isCategory
									? Liferay.Language.get('category-name')
									: Liferay.Language.get('tag-name')}
							</ClayTable.Cell>
							{isCategory && (
								<ClayTable.Cell headingCell>
									{Liferay.Language.get('vocabulary')}
								</ClayTable.Cell>
							)}
							<ClayTable.Cell headingCell>
								{groupByLabel}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>
						{items.map(item => (
							<ClayTable.Row key={item.id}>
								<ClayTable.Cell expanded>
									<Text size={3} weight='semi-bold'>
										{item.name}
									</Text>
								</ClayTable.Cell>
								{isCategory && (
									<ClayTable.Cell>
										<Text size={3}>
											{
												(item as ITopCategory)
													.vocabularyName
											}
										</Text>
									</ClayTable.Cell>
								)}
								<ClayTable.Cell>
									{toThousands(item[selectedMetric].value)}
								</ClayTable.Cell>
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				</ClayTable>
			</StatesRenderer.Success>
		</StatesRenderer>
	);
};

const TopCategoriesAndTags: React.FC<ITopCategoriesAndTagsProps> = ({
	className
}) => {
	const {
		channelId,
		groupId,
		id: accountId
	} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();

	const [activeTab, setActiveTab] = useState(0);
	const [groupBy, setGroupBy] = useState<GroupByMetric>(
		GroupByMetric.IMPRESSIONS
	);

	const selectedMetric = GROUP_BY_TO_METRIC[groupBy];

	const isCategory = TABS[activeTab] === 'category';

	const {data, loading} = useRequest<
		{
			accountId: string;
			channelId: string;
			groupId: string;
			selectedMetric: TaxonomyMetric;
		},
		{items: TaxonomyItem[]}
	>({
		dataSourceFn: isCategory
			? API.categories.fetchAccountTopCategories
			: API.tags.fetchAccountTopTags,
		variables: {accountId, channelId, groupId, selectedMetric}
	});

	const items = data?.items ?? [];

	const tabContent = (
		<TabContent
			groupBy={groupBy}
			isCategory={isCategory}
			items={items}
			loading={loading}
			selectedMetric={selectedMetric}
			setGroupBy={setGroupBy}
		/>
	);

	return (
		<Card className={classNames(className)} minHeight={260}>
			<Card.Title className='p-3'>
				<Text weight='semi-bold'>
					{Liferay.Language.get(
						'top-asset-vocabularies-and-categories'
					).toUpperCase()}
				</Text>
			</Card.Title>
			<Card.Body className='p-0'>
				<ClayTabs active={activeTab} onActiveChange={setActiveTab}>
					<ClayTabs.Item>
						{Liferay.Language.get('category')}
					</ClayTabs.Item>
					<ClayTabs.Item>{Liferay.Language.get('tag')}</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTab} fade>
					<ClayTabs.TabPane className='pb-0'>
						{tabContent}
					</ClayTabs.TabPane>
					<ClayTabs.TabPane className='pb-0'>
						{tabContent}
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</Card.Body>
		</Card>
	);
};

export default TopCategoriesAndTags;
