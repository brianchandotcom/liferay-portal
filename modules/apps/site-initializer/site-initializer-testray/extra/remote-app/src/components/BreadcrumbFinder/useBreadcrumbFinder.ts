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

import {useCallback, useEffect, useMemo, useState} from 'react';
import {useHotkeys} from 'react-hotkeys-hook';
import {useNavigate} from 'react-router-dom';

import useBreadcrumb, {defaultEntities} from '../../hooks/useBreadcrumb';

const ON_CLICK_ROW_ENABLED = false;

const useBreadcrumbFinder = () => {
	const navigate = useNavigate();
	const {
		breadCrumb,
		inputRef,
		items,
		onBackscape,
		search,
		setBreadCrumb,
		setSearch,
	} = useBreadcrumb(defaultEntities);

	const [active, setActive] = useState(false);
	const [index, setIndex] = useState(0);

	const baseOptions = {enabled: active};
	const currentEntity = defaultEntities[breadCrumb.length];
	const tabDisabled = breadCrumb.length + 1 === defaultEntities.length;

	const activeItem = useMemo(
		() => ({...items[index], entity: currentEntity}),
		[currentEntity, index, items]
	);

	const itemsLength = items.length;

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
	}, [activeItem, inputRef, setBreadCrumb, setSearch]);

	useEffect(() => {
		setTimeout(() => inputRef.current?.focus(), 1500);
	}, [inputRef]);

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
