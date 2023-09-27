/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

type Key = {
	[key: string]: () => void;
};

const BREADCRUMB_HEIGHT = 90;
const INPUT_FOCUS_DELAY = 10;

const useBreadcrumbFinder = () => {
	const [active, setActive] = useState(false);
	const [index, setIndex] = useState(0);

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
	} = useBreadcrumb(defaultEntities, {active});

	const listRef = useRef<HTMLUListElement>(null);

	const listItemRef = useMemo(
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
		const currentItem = items[rowIndex];

		if (currentEntity.getPage) {
			navigate(currentEntity?.getPage([...ids, currentItem.value]));
			setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, currentItem]);
		}
		setSearch('');
		setActive(false);
	};

	const scrollIntoView = useCallback((liElement: any) => {
		if (liElement.current) {
			listRef.current?.scrollTo({
				behavior: 'smooth',
				left: 0,
				top: liElement.current.offsetTop - BREADCRUMB_HEIGHT,
			});
		}
	}, []);

	const onKeyDown = useCallback(() => {
		if (!listItemRef.length) {
			return;
		}

		setIndex((prevIndex) => {
			let currentIndex = prevIndex + 1;

			if (currentIndex + 1 > itemsLength) {
				currentIndex = 0;
			}

			scrollIntoView(listItemRef[currentIndex]);

			return currentIndex;
		});
	}, [itemsLength, scrollIntoView, listItemRef]);

	const onKeyUp = useCallback(() => {
		if (!listItemRef.length) {
			return;
		}

		setIndex((prevIndex) => {
			let currentIndex = prevIndex - 1;

			if (currentIndex === -1) {
				currentIndex = itemsLength - 1;
			}

			scrollIntoView(listItemRef[currentIndex]);

			return currentIndex;
		});
	}, [itemsLength, scrollIntoView, listItemRef]);

	const onEscape = useCallback(() => setActive(false), []);

	const onTab = useCallback(() => {
		setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, activeItem]);
		setIndex(0);
		setSearch('');
		setTimeout(() => {
			inputRef.current?.focus();
		}, INPUT_FOCUS_DELAY);
	}, [activeItem, inputRef, setBreadCrumb, setSearch]);

	useEffect(() => {
		setTimeout(() => {
			inputRef.current?.focus();
		}, INPUT_FOCUS_DELAY);
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
		[itemsLength, listItemRef]
	);
	useHotkeys(
		'tab',
		onTab,
		{...baseOptions, enabled: baseOptions.enabled && !tabDisabled},
		[index, activeItem]
	);
	useHotkeys('up', onKeyUp, {...baseOptions, filterPreventDefault: true}, [
		itemsLength,
		listItemRef,
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
		listItemRef,
		listRef,
		onBackscape,
		onClickRow,
		onInputKeyPress,
		search,
		setActive,
		setBreadCrumb,
		setSearch,
		tabDisabled,
	};
};

export default useBreadcrumbFinder;
