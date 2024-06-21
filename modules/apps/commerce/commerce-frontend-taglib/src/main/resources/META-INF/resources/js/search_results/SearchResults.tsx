/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import {debounce, fetch, sub} from 'frontend-js-web';
import React, {
	RefObject,
	useCallback,
	useEffect,
	useRef,
	useState,
} from 'react';

const RESULT_TYPES = {
	category: 'category',
	item: 'item',
	label: 'label',
} as const;

type ResultType = (typeof RESULT_TYPES)[keyof typeof RESULT_TYPES];

interface Result {
	icon?: string;
	image?: string;
	selected: boolean;
	subtitle: string;
	title: string;
	type: ResultType;
	url: string;
}

interface SearchResultsProps {
	commerceAccountId: string;
	groupId: string;
	plid: string;
	searchURL: string;
}

export default function SearchResults({
	commerceAccountId,
	groupId,
	plid,
	searchURL,
}: SearchResultsProps) {
	const [lock, setLock] = useState(false);
	const [loading, setLoading] = useState(false);
	const [query, setQuery] = useState('');
	const [results, setResults] = useState<Result[]>([]);
	const [selectedIndex, setSelectedIndex] = useState(-1);

	const selectedRef = useRef<HTMLAnchorElement>(null);

	const search = useCallback(
		(searchQuery: string) => {
			if (lock) {
				return;
			}

			setLock(true);

			const url = new URL(`${searchURL}${plid}`);

			url.searchParams.append('commerceAccountId', commerceAccountId);
			url.searchParams.append('groupId', groupId);
			url.searchParams.append('q', searchQuery);

			fetch(url.toString())
				.then((response) => response.json())
				.then((nextResults) => {
					setLock(false);
					setLoading(false);
					setResults(nextResults);

					const firstIndex = getNextIndex(-1, nextResults);

					setSelectedIndex(firstIndex);
				});
		},
		[commerceAccountId, groupId, lock, plid, searchURL]
	);

	const updateQuery = useCallback(
		(event: {term: string}) => {
			if (event.term === '' || event.term.length > 1) {
				setLoading(true);
				setQuery(event.term);

				debounce(() => search(event.term), 500)();
			}
		},
		[search]
	);

	const onKeyDown = useCallback(
		(event: KeyboardEvent) => {
			if (!query) {
				return;
			}

			if (event.key === 'ArrowDown') {
				event.preventDefault();

				const nextIndex = getNextIndex(selectedIndex, results);
				setSelectedIndex(nextIndex);
			}
			else if (event.key === 'ArrowUp') {
				event.preventDefault();

				const previousIndex = getPreviousIndex(selectedIndex, results);
				setSelectedIndex(previousIndex);
			}
		},
		[query, results, selectedIndex]
	);

	useEffect(() => {
		Liferay.on('search-term-update', updateQuery);
		Liferay.on('search-term-submit', () => selectedRef.current?.click());
		document.addEventListener('keydown', onKeyDown);

		return () => {
			Liferay.detach('search-term-update');
			Liferay.detach('search-term-submit');
			document.removeEventListener('keydown', onKeyDown);
		};
	}, [onKeyDown, updateQuery]);

	useEffect(() => {
		selectedRef.current?.scrollIntoView({
			behavior: 'smooth',
			block: 'nearest',
		});
	}, [selectedIndex]);

	if (!query) {
		return null;
	}

	return (
		<div
			className={classNames('commerce-suggestions', {
				'is-loading': loading,
			})}
		>
			<div className="commerce-suggestions__wrapper">
				{results.map((result, index) => (
					<Result
						index={index}
						item={result}
						key={result.url}
						query={query}
						selectedIndex={selectedIndex}
						selectedRef={selectedRef}
						setSelectedIndex={setSelectedIndex}
					/>
				))}
			</div>

			<div className="commerce-suggestions__hints">
				<ClayIcon symbol="order-arrow" />

				<span>{Liferay.Language.get('to-navigate')}</span>

				<ClayIcon symbol="enter" />

				<span>{Liferay.Language.get('to-select')}</span>

				<ClayIcon symbol="esc" />

				<span>{Liferay.Language.get('to-dismiss')}</span>
			</div>
		</div>
	);
}

interface ResultProps {
	index: number;
	item: Result;
	query: string;
	selectedIndex: number;
	selectedRef: RefObject<HTMLAnchorElement>;
	setSelectedIndex: (index: number) => void;
}

function Result({
	index,
	item,
	query,
	selectedIndex,
	selectedRef,
	setSelectedIndex,
}: ResultProps) {
	const {icon, image, subtitle, title, type, url} = item;
	const isSelected = selectedIndex === index;

	const onHover = () => setSelectedIndex(index);

	if (type === RESULT_TYPES.label) {
		return <div className="commerce-suggestions__label">{title}</div>;
	}

	if (type === RESULT_TYPES.category) {
		return (
			<ClayLink
				className={classNames('commerce-suggestions__item', {
					'is-selected': isSelected,
				})}
				displayType="unstyled"
				href={url}
				onMouseOver={onHover}
				ref={isSelected ? selectedRef : null}
			>
				{sub(Liferay.Language.get('search-x-in-x'), query, title)}
			</ClayLink>
		);
	}

	return (
		<ClayLink
			className={classNames('commerce-suggestions__item', {
				'is-selected': isSelected,
			})}
			href={url}
			onMouseOver={onHover}
			ref={isSelected ? selectedRef : null}
		>
			<div className="commerce-item commerce-item--search">
				{image ? (
					<img
						alt={title}
						className="commerce-item__image"
						src={image}
					/>
				) : null}

				{icon ? (
					<div className="commerce-item__icon">
						<ClayIcon symbol={icon} />
					</div>
				) : null}

				<div className="commerce-item__content">
					<p className="mb-0">{title}</p>

					<p className="mb-0">{subtitle}</p>
				</div>
			</div>
		</ClayLink>
	);
}

function getNextIndex(currentIndex: number, results: Result[]) {
	const nextIndex = results.findIndex(
		(result, index) =>
			result.type !== RESULT_TYPES.label && index > currentIndex
	);

	return nextIndex > -1 ? nextIndex : currentIndex;
}

function getPreviousIndex(currentIndex: number, results: Result[]) {
	let previousIndex = -1;

	results
		.filter((result, index) => index < currentIndex)
		.forEach((result, index) => {
			if (result.type !== RESULT_TYPES.label) {
				previousIndex = index;
			}
		});

	return previousIndex > -1 ? previousIndex : currentIndex;
}
