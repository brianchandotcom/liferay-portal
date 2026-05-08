import * as API from 'shared/api';
import ClayDropDown from '@clayui/drop-down';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {createVocabularyProperty} from '../utils/utils';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {List} from 'immutable';
import {Option, Picker} from '@clayui/core';
import {PaginationBar} from '@clayui/pagination-bar';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';
import {ReferencedObjectsContext} from '../context/referencedObjects';
import {SegmentTypes} from 'shared/util/constants';
import {translateQueryToCriteria} from '../utils/odata';

const VOCABULARY_OPERATORS = new Set([
	CustomFunctionOperators.VocabulariesFilter,
	NotOperators.NotVocabulariesFilter
]);

function extractVocabularies(criteria: any): Array<{id: string; name: string}> {
	if (!criteria) return [];

	if (criteria.items) {
		return criteria.items.flatMap(extractVocabularies);
	}

	if (
		criteria.propertyName &&
		VOCABULARY_OPERATORS.has(criteria.operatorName)
	) {
		const id = criteria.propertyName;
		const items = criteria.value?.getIn?.(['criterionGroup', 'items']);
		const nameItem = items?.find?.(
			(item: any) => item.get?.('propertyName') === 'vocabularies/name'
		);
		const name = (nameItem?.get?.('value') as string) ?? id;

		return [{id, name}];
	}

	return [];
}

const VOCABULARY_PAGE_SIZE = 12;

const PROPERTY_KEY_TO_GROUP: Record<string, string> = {
	account: 'attributes',
	individual: 'attributes',
	interest: 'page-topics',
	organization: 'attributes',
	session: 'attributes',
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

	const [vocabularyQuery, setVocabularyQuery] = useState<{
		keywords: string;
		page: number;
	}>({keywords: '', page: 1});
	const [vocabularyItems, setVocabularyItems] = useState<List<Property>>(
		List()
	);
	const [vocabularyTotalCount, setVocabularyTotalCount] = useState(0);

	const {addProperty} = useContext(ReferencedObjectsContext);

	useEffect(() => {
		if (type !== SegmentTypes.Batch || !criteriaString || !addProperty) {
			return;
		}

		const vocabularies = extractVocabularies(
			translateQueryToCriteria(criteriaString)
		);

		if (!vocabularies.length) return;

		vocabularies.forEach(({id, name}) => {
			addProperty(createVocabularyProperty({id, name}));
		});
	}, []);

	const isVocabularySection = selectedPropertyKey === 'vocabulary';
	const vocabularyKeywords = isVocabularySection ? searchValue : '';

	useEffect(() => {
		setVocabularyQuery(q =>
			q.keywords === vocabularyKeywords
				? q
				: {keywords: vocabularyKeywords, page: 1}
		);
	}, [vocabularyKeywords]);

	useEffect(() => {
		if (
			type !== SegmentTypes.Batch ||
			selectedPropertyKey !== 'vocabulary'
		) {
			return;
		}

		API.vocabularies
			.search({
				channelId,
				groupId,
				keywords: vocabularyQuery.keywords,
				page: vocabularyQuery.page,
				pageSize: VOCABULARY_PAGE_SIZE
			})
			.then(
				(result: {
					items: Array<{id: string; name: string}>;
					totalCount: number;
				}) => {
					const properties: List<Property> = List(
						(result.items ?? []).map(createVocabularyProperty)
					);

					setVocabularyItems(properties);
					setVocabularyTotalCount(result.totalCount ?? 0);

					if (addProperty) {
						properties.forEach(
							property => property && addProperty(property)
						);
					}
				}
			);
	}, [channelId, groupId, type, selectedPropertyKey, vocabularyQuery]);

	const effectivePropertyGroupsIList = useMemo(
		() =>
			propertyGroupsIList
				.map(group => {
					if (!group || group.propertyKey !== 'vocabulary') {
						return group as PropertyGroup;
					}

					return group.set(
						'propertySubgroups',
						List([
							new PropertySubgroup({properties: vocabularyItems})
						])
					) as PropertyGroup;
				})
				.toList(),
		[propertyGroupsIList, vocabularyItems]
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
					searchValue={isVocabularySection ? '' : searchValue}
				/>

				{isVocabularySection && vocabularyTotalCount > 0 && (
					<PaginationBar className='justify-content-center sidebar-pagination mt-2'>
						<ClayPaginationWithBasicItems
							active={vocabularyQuery.page}
							onActiveChange={page =>
								setVocabularyQuery(q => ({...q, page}))
							}
							totalPages={Math.ceil(
								vocabularyTotalCount / VOCABULARY_PAGE_SIZE
							)}
						/>
					</PaginationBar>
				)}
			</div>
		</div>
	);
}
