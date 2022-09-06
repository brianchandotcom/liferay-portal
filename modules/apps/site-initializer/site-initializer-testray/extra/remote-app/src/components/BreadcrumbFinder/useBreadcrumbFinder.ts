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

import {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import {useHotkeys} from 'react-hotkeys-hook';
import {useNavigate} from 'react-router-dom';

import useDebounce from '../../hooks/useDebounce';
import {useFetch} from '../../hooks/useFetch';
import {
	APIResponse,
	TestrayCaseResult,
	testrayCaseResultRest,
} from '../../services/rest';
import {searchUtil} from '../../util/search';

const ON_CLICK_ROW_ENABLED = false;

type Resource =
	| 'TestrayProject'
	| 'TestrayRoutine'
	| 'TestrayBuild'
	| 'TestrayCaseResult';

type Entity = {
	entity: string;
	getPage?: (ids: number[]) => string;
	getResource: (ids: number[], search: string) => string;
	transformer?: (response: APIResponse<any>) => any;
};

const entities: Entity[] = [
	{
		entity: 'projects',
		getResource: (_, search) =>
			`/projects?filter=${searchUtil.contains('name', search)}`,
	},
	{
		entity: 'routines',
		getPage: ([projectId]) => `/project/${projectId}/routines`,
		getResource: ([projectId], search) =>
			`/routines?filter=${searchUtil.eq(
				'projectId',
				projectId
			)} and ${searchUtil.contains('name', search)}`,
	},
	{
		entity: 'builds',
		getResource: ([, routineId], search) =>
			`/builds?filter=${searchUtil.eq(
				'routineId',
				routineId
			)} and ${searchUtil.contains('name', search)}`,
	},
	{
		entity: 'caseresults',
		getResource: ([, , buildId]) =>
			`/caseresults?filter=${searchUtil.eq(
				'buildId',
				buildId
			)}&nestedFields=case`,
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
	{getResource, transformer}: Entity,
	ids: number[],
	search: string
) => ({
	transformer: transformer ?? normalizeItems,
	url: getResource(ids, search),
});

const useBreadcrumbFinder = () => {
	const inputRef = useRef<HTMLInputElement>(null);
	const navigate = useNavigate();

	const [active, setActive] = useState(false);
	const [breadCrumb, setBreadCrumb] = useState<BreadCrumb[]>([]);
	const [index, setIndex] = useState(0);
	const [search, setSearch] = useState('');

	const debouncedSearch = useDebounce(search, 1000);

	const baseOptions = {enabled: active};
	const currentEntity = entities[breadCrumb.length];
	const ids = breadCrumb.map(({value}) => value);
	const tabDisabled = breadCrumb.length + 1 === entities.length;
	const {transformer, url} = getEntityUrlAndNormalizer(
		currentEntity,
		ids,
		debouncedSearch
	);

	const {data} = useFetch<APIResponse<any>>(url, transformer);

	const items = useMemo(() => data?.items || [], [data?.items]);

	const activeItem = useMemo(
		() => ({...items[index], entity: currentEntity}),
		[currentEntity, index, items]
	);

	const itemsLength = items.length;

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

	const onEnter = useCallback(() => {
		navigate(`/project/${activeItem.value}/overview`);
		setActive(false);
	}, [activeItem, navigate]);

	const onClickRow = (rowIndex: number) => {
		if (ON_CLICK_ROW_ENABLED) {
			const currentItem = items[rowIndex];

			navigate(`/project/${currentItem.value}/overview`);
			setActive(false);
		}
	};

	const onKeyDown = useCallback(() => {
		setIndex((prevIndex) => {
			let currentIndex = prevIndex + 1;

			if (currentIndex + 1 > itemsLength) {
				currentIndex = 0;
			}

			return currentIndex;
		});
	}, [itemsLength]);

	const onKeyUp = useCallback(() => {
		setIndex((prevIndex) => {
			let currentIndex = prevIndex - 1;

			if (currentIndex === -1) {
				currentIndex = itemsLength - 1;
			}

			return currentIndex;
		});
	}, [itemsLength]);

	const onTab = useCallback(() => {
		setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, activeItem]);
		setIndex(0);
		setSearch('');
		inputRef.current?.focus();
	}, [activeItem]);

	useEffect(() => {
		setTimeout(() => inputRef.current?.focus(), 1500);
	}, []);

	useHotkeys('shift+/', () => setActive(true), {enabled: !active});
	useHotkeys('enter', onEnter, baseOptions, [index, activeItem]);
	useHotkeys('down', onKeyDown, baseOptions, [itemsLength]);
	useHotkeys(
		'tab',
		onTab,
		{...baseOptions, enabled: baseOptions.enabled && !tabDisabled},
		[index, activeItem]
	);
	useHotkeys('up', onKeyUp, baseOptions, [itemsLength]);

	return {
		active,
		breadCrumb,
		index,
		inputRef,
		items,
		onBackscape,
		onClickRow,
		search,
		setSearch,
		tabDisabled,
	};
};

export default useBreadcrumbFinder;
