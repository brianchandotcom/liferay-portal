import * as API from 'shared/api';
import ClayDropDown from '@clayui/drop-down';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {createTagProperty, createVocabularyProperty} from '../utils/utils';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {List} from 'immutable';
import {Option, Picker} from '@clayui/core';
import {PaginationBar} from '@clayui/pagination-bar';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';
import {ReferencedObjectsContext} from '../context/referencedObjects';
import {SegmentTypes} from 'shared/util/constants';
import {translateQueryToCriteria} from '../utils/odata';

const REMOTE_OPERATORS = new Set([
	CustomFunctionOperators.VocabulariesFilter,
	NotOperators.NotVocabulariesFilter,
	CustomFunctionOperators.TagsFilter,
	NotOperators.NotTagsFilter
]);

function extractRemoteCriteria(
	criteria: any
): Array<{id: string; isTag: boolean; name: string}> {
	if (!criteria) return [];

	if (criteria.items) {
		return criteria.items.flatMap(extractRemoteCriteria);
	}

	if (criteria.propertyName && REMOTE_OPERATORS.has(criteria.operatorName)) {
		const id = criteria.propertyName;
		const isTag =
			criteria.operatorName === CustomFunctionOperators.TagsFilter ||
			criteria.operatorName === NotOperators.NotTagsFilter;
		const items = criteria.value?.getIn?.(['criterionGroup', 'items']);
		const nameItem = items?.find?.(
			(item: any) =>
				item.get?.('propertyName') === 'vocabularies/name' ||
				item.get?.('propertyName') === 'tags/name'
		);
		const name = (nameItem?.get?.('value') as string) ?? id;

		return [{id, isTag, name}];
	}

	return [];
}

const REMOTE_PAGE_SIZE = 12;

const PROPERTY_KEY_TO_GROUP: Record<string, string> = {
	account: 'attributes',
	individual: 'attributes',
	interest: 'page-topics',
	organization: 'attributes',
	session: 'attributes',
	tag: 'asset-categorization',
	vocabulary: 'asset-categorization',
	web: 'behavioral'
};

const GROUP_ORDER = [
	'behavioral',
	'attributes',
	'asset-categorization',
	'page-topics'
];

const GROUP_LABELS: Record<string, string> = {
	'asset-categorization': Liferay.Language.get('asset-categorization'),
	attributes: Liferay.Language.get('attributes'),
	behavioral: Liferay.Language.get('behavioral'),
	'page-topics': Liferay.Language.get('interests')
};

interface IPickerGroup {
	items: Array<{label: string; value: string}>;
	label: string;
}

interface ICriteriaSidebarProps {
	channelId: string;
	criteriaString?: string;
	groupId: string;
	propertyGroupsIList: List<PropertyGroup>;
	type: string;
}

export default function CriteriaSidebar({
	channelId,
	criteriaString,
	groupId,
	propertyGroupsIList,
	type
}: ICriteriaSidebarProps) {
	const [searchValue, setSearchValue] = useState('');
	const [selectedPropertyKey, setSelectedPropertyKey] = useState<
		string | null
	>(() => propertyGroupsIList.first()?.propertyKey ?? null);

	const [remoteQuery, setRemoteQuery] = useState<{
		keywords: string;
		page: number;
	}>({keywords: '', page: 1});
	const [remoteItems, setRemoteItems] = useState<List<Property>>(List());

	const [remoteTotalCount, setRemoteTotalCount] = useState(0);

	const {addProperty} = useContext(ReferencedObjectsContext);

	const isVocabularySection = selectedPropertyKey === 'vocabulary';
	const isTagsSection = selectedPropertyKey === 'tag';
	const isRemoteSection = isVocabularySection || isTagsSection;
	const remoteKeywords = isRemoteSection ? searchValue : '';

	useEffect(() => {
		if (type !== SegmentTypes.Batch || !criteriaString || !addProperty) {
			return;
		}

		const remoteCriteria = extractRemoteCriteria(
			translateQueryToCriteria(criteriaString)
		);

		if (!remoteCriteria.length) return;

		remoteCriteria.forEach(({id, isTag, name}) => {
			if (isTag) {
				addProperty(createTagProperty({id, name}));
			} else {
				addProperty(createVocabularyProperty({id, name}));
			}
		});
	}, []);

	useEffect(() => {
		setRemoteQuery(q =>
			q.keywords === remoteKeywords
				? q
				: {keywords: remoteKeywords, page: 1}
		);
	}, [remoteKeywords]);

	useEffect(() => {
		setRemoteItems(List());
		setRemoteTotalCount(0);
		setRemoteQuery(q => (q.page === 1 ? q : {...q, page: 1}));
	}, [selectedPropertyKey]);

	useEffect(() => {
		if (type !== SegmentTypes.Batch || !isRemoteSection) {
			return;
		}

		const apiMethod = isTagsSection
			? API.tags.search
			: API.vocabularies.search;

		const factoryMethod = isTagsSection
			? createTagProperty
			: createVocabularyProperty;

		apiMethod({
			channelId,
			groupId,
			keywords: remoteQuery.keywords,
			page: remoteQuery.page,
			pageSize: REMOTE_PAGE_SIZE
		}).then(
			(result: {
				items: Array<{id: string; name: string}>;
				totalCount: number;
			}) => {
				const properties: List<Property> = List(
					(result.items ?? []).map(factoryMethod)
				);

				setRemoteItems(properties);
				setRemoteTotalCount(result.totalCount ?? 0);

				if (addProperty) {
					properties.forEach(
						property => property && addProperty(property)
					);
				}
			}
		);
	}, [
		channelId,
		groupId,
		type,
		selectedPropertyKey,
		remoteQuery,
		isRemoteSection,
		isTagsSection
	]);

	const effectivePropertyGroupsIList = useMemo(
		() =>
			propertyGroupsIList
				.map(group => {
					if (
						!group ||
						(group.propertyKey !== 'vocabulary' &&
							group.propertyKey !== 'tag')
					) {
						return group as PropertyGroup;
					}

					return group.set(
						'propertySubgroups',
						List([new PropertySubgroup({properties: remoteItems})])
					) as PropertyGroup;
				})
				.toList(),
		[propertyGroupsIList, remoteItems]
	);

	const groupedBySection = propertyGroupsIList
		.toArray()
		.reduce<Record<string, PropertyGroup[]>>((acc, pg) => {
			const groupKey =
				PROPERTY_KEY_TO_GROUP[pg.propertyKey] ?? 'attributes';

			if (!acc[groupKey]) {
				acc[groupKey] = [];
			}

			acc[groupKey].push(pg);

			return acc;
		}, {});

	const pickerItems: IPickerGroup[] = GROUP_ORDER.filter(
		groupKey => groupedBySection[groupKey]?.length > 0
	).map(groupKey => ({
		items: groupedBySection[groupKey].map(({label, propertyKey}) => ({
			label,
			value: propertyKey
		})),
		label: GROUP_LABELS[groupKey] ?? groupKey
	}));

	return (
		<div className='criteria-sidebar-root'>
			<div className='sidebar-title'>
				{Liferay.Language.get('segment-criteria')}
			</div>

			<div className='sidebar-header'>
				<Picker
					items={pickerItems}
					onSelectionChange={key => {
						setSelectedPropertyKey(key as string);
					}}
					selectedKey={selectedPropertyKey ?? undefined}
				>
					{(group: IPickerGroup) => (
						<ClayDropDown.Group
							header={group.label}
							items={group.items}
						>
							{(item: {label: string; value: string}) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</ClayDropDown.Group>
					)}
				</Picker>
			</div>

			<div className='sidebar-search'>
				<CriteriaSidebarSearchBar
					onChange={setSearchValue}
					searchValue={searchValue}
				/>
			</div>

			<div className='sidebar-collapse'>
				<CriteriaSidebarCollapse
					propertyGroupsIList={effectivePropertyGroupsIList}
					propertyKey={selectedPropertyKey ?? ''}
					searchValue={isRemoteSection ? '' : searchValue}
				/>

				{isRemoteSection && remoteTotalCount > 0 && (
					<PaginationBar className='justify-content-center sidebar-pagination mt-2'>
						<ClayPaginationWithBasicItems
							active={remoteQuery.page}
							onActiveChange={page =>
								setRemoteQuery(q => ({...q, page}))
							}
							totalPages={Math.ceil(
								remoteTotalCount / REMOTE_PAGE_SIZE
							)}
						/>
					</PaginationBar>
				)}
			</div>
		</div>
	);
}
