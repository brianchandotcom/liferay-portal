/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useCallback, useMemo, useRef, useState} from 'react';

import i18n from '../i18n';
import {
	APIResponse,
	TestrayCaseResult,
	testrayCaseResultRest,
} from '../services/rest';
import {searchUtil} from '../util/search';
import useDebounce from './useDebounce';
import {useFetch} from './useFetch';

type Resource =
	| 'TestrayProject'
	| 'TestrayRoutine'
	| 'TestrayBuild'
	| 'TestrayCaseResult';

export type Entity = {
	entity: string;
	getPage?: (ids: number[]) => string;
	getResource: (ids: number[], search: string) => string | null;
	name: string;
	transformer?: (response: APIResponse<any>) => any;
};

type BreadCrumb = {
	entity: Resource;
	label: string;
	value: number;
};

const normalizeItems = (response: APIResponse<any>) => ({
	...response,
	items: response.items.map(({id, name}) => ({
		label: name,
		value: id,
	})),
});

const getEntityUrlAndNormalizer = (
	{getResource = () => null, transformer}: Entity = {} as Entity,
	ids: number[],
	search: string
) => ({
	transformer: transformer ?? normalizeItems,
	url: getResource(ids, search),
});

const useBreadcrumb = (entities: Entity[]) => {
	const inputRef = useRef<HTMLInputElement>(null);

	const [breadCrumb, setBreadCrumb] = useState<BreadCrumb[]>([]);
	const [search, setSearch] = useState('');

	const debouncedSearch = useDebounce(search, 1000);

	const currentEntity = entities[breadCrumb.length];
	const ids = breadCrumb.map(({value}) => value);
	const {transformer, url} = getEntityUrlAndNormalizer(
		currentEntity,
		ids,
		debouncedSearch
	);

	const {data} = useFetch<APIResponse<any>>(url, transformer);

	const items = useMemo(() => data?.items || [], [data?.items]);

	const onBackscape = useCallback(() => {
		if (breadCrumb.length) {
			const lastBreadcrumb = breadCrumb[breadCrumb.length - 1];

			setBreadCrumb((prevBreadcrumb) =>
				prevBreadcrumb.filter(
					(_, index) => breadCrumb.length !== index + 1
				)
			);

			setTimeout(() => {
				setSearch(lastBreadcrumb.label);
			}, 10);

			setTimeout(() => {
				inputRef.current?.select();
			}, 20);
		}
	}, [breadCrumb]);

	const onClickRow = (rowIndex: number) => {
		setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, items[rowIndex]]);
		setSearch('');
	};

	return {
		breadCrumb,
		entities,
		inputRef,
		items,
		onBackscape,
		onClickRow,
		search,
		setBreadCrumb,
		setSearch,
	};
};

const defaultEntities: Entity[] = [
	{
		entity: 'projects',
		getResource: (_, search) =>
			`/projects?filter=${searchUtil.contains(
				'name',
				search
			)}&pageSize=1000`,
		name: i18n.translate('project'),
	},
	{
		entity: 'routines',
		getPage: ([projectId]) => `/project/${projectId}/routines`,
		getResource: ([projectId], search) =>
			`/routines?filter=${searchUtil.eq(
				'projectId',
				projectId
			)} and ${searchUtil.contains('name', search)}&pageSize=1000`,
		name: i18n.translate('routine'),
	},
	{
		entity: 'builds',
		getResource: ([, routineId], search) =>
			`/builds?filter=${searchUtil.eq(
				'routineId',
				routineId
			)} and ${searchUtil.contains('name', search)}&pageSize=1000`,
		name: i18n.translate('build'),
	},
	{
		entity: 'caseresults',
		getResource: ([, , buildId]) =>
			`/caseresults?filter=${searchUtil.eq(
				'buildId',
				buildId
			)}&nestedFields=case&pageSize=1000`,
		name: i18n.translate('case-result'),
		transformer: (response: APIResponse<TestrayCaseResult>) => {
			const transformedResponse = testrayCaseResultRest.transformDataFromList(
				response
			);

			return {
				...transformedResponse,
				items: transformedResponse.items.map((caseResult) => ({
					...caseResult,
					label: caseResult.case?.name,
				})),
			};
		},
	},
];

export {defaultEntities};

export default useBreadcrumb;
