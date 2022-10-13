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

import {
	createRef,
	useCallback,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';
import {useHotkeys} from 'react-hotkeys-hook';
import {useNavigate} from 'react-router-dom';

import useBreadcrumb, {defaultEntities} from '../../hooks/useBreadcrumb';

const ON_CLICK_ROW_ENABLED = true;

type Key = {
	[key: string]: () => void;
};

const useBreadcrumbFinder = () => {
	const navigate = useNavigate();

	const {
		breadCrumb,
		currentEntity,
		inputRef,
		items,
		onBackscape,
		search,
		setBreadCrumb,
		setSearch,
	} = useBreadcrumb(defaultEntities);

	const [active, setActive] = useState(false);
	const [index, setIndex] = useState(0);

	const ulRef = useRef<HTMLUListElement>(null);

	const tabRefs = useMemo(
		() =>
			Array.from({length: items.length}).map(() =>
				createRef<HTMLLIElement>()
			),
		[items.length]
	);

	const baseOptions = {enabled: active};
	const tabDisabled = breadCrumb.length + 1 === defaultEntities.length;
	const ids = breadCrumb.map(({value}) => value);

	const activeItem = useMemo(
		() => ({
			...items[index],
			entity: currentEntity,
		}),
		[currentEntity, index, items]
	);

	const itemsLength = items.length;

	const onEnter = useCallback(() => {
		if (currentEntity.getPage) {
			setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, activeItem]);
			setIndex(0);
			setSearch('');
			setActive(false);
			navigate(currentEntity?.getPage([...ids, activeItem.value]));
		}
	}, [activeItem, currentEntity, ids, navigate, setBreadCrumb, setSearch]);

	const onClickRow = (rowIndex: number) => {
		if (ON_CLICK_ROW_ENABLED) {
			const currentItem = items[rowIndex];

			if (currentEntity.getPage) {
				navigate(currentEntity?.getPage([...ids, currentItem.value]));
				setBreadCrumb((prevBreadCrumb) => [
					...prevBreadCrumb,
					currentItem,
				]);
			}
			setSearch('');
			setActive(false);
		}
	};

	const scrollIntoView = useCallback((liElement: any) => {
		if (liElement.current) {
			ulRef.current?.scrollTo({
				behavior: 'smooth',
				left: 0,
				top: liElement.current.offsetTop - 90,
			});
		}
	}, []);

	const onKeyDown = useCallback(() => {
		setIndex((prevIndex) => {
			let currentIndex = prevIndex + 1;

			if (currentIndex + 1 > itemsLength) {
				currentIndex = 0;
			}

			scrollIntoView(tabRefs[currentIndex]);

			return currentIndex;
		});
	}, [itemsLength, scrollIntoView, tabRefs]);

	const onKeyUp = useCallback(() => {
		setIndex((prevIndex) => {
			let currentIndex = prevIndex - 1;

			if (currentIndex === -1) {
				currentIndex = itemsLength - 1;
			}

			scrollIntoView(tabRefs[currentIndex]);

			return currentIndex;
		});
	}, [itemsLength, scrollIntoView, tabRefs]);

	const onEscape = useCallback(() => {
		setActive(false);
	}, []);

	const onTab = useCallback(() => {
		setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, activeItem]);
		setIndex(0);
		setSearch('');
		setTimeout(() => {
			inputRef.current?.focus();
		}, 10);
	}, [activeItem, inputRef, setBreadCrumb, setSearch]);

	useEffect(() => {
		setTimeout(() => {
			inputRef.current?.focus();
		}, 10);
	}, [inputRef, active]);

	useHotkeys('/', () => setActive(true), {enabled: !active});
	useHotkeys('enter', onEnter, baseOptions, [index, activeItem]);
	useHotkeys('Escape', () => setActive(false), {
		enabled: true,
	});
	useHotkeys(
		'down',
		onKeyDown,
		{...baseOptions, filterPreventDefault: true},
		[itemsLength, tabRefs]
	);
	useHotkeys(
		'tab',
		onTab,
		{...baseOptions, enabled: baseOptions.enabled && !tabDisabled},
		[index, activeItem]
	);
	useHotkeys('up', onKeyUp, {...baseOptions, filterPreventDefault: true}, [
		itemsLength,
		tabRefs,
	]);

	const onInputKeyPress = (key: string) => {
		const keys: Key = {
			ArrowDown: onKeyDown,
			ArrowUp: onKeyUp,
			Enter: onEnter,
			Escape: onEscape,
			Tab: onTab,
		};

		if (!keys[key]) {
			return null;
		}

		return keys[key]();
	};

	return {
		active,
		breadCrumb,
		index,
		inputRef,
		items,
		onBackscape,
		onClickRow,
		onInputKeyPress,
		search,
		setActive,
		setBreadCrumb,
		setSearch,
		tabDisabled,
		tabRefs,
		ulRef,
	};
};

export default useBreadcrumbFinder;
