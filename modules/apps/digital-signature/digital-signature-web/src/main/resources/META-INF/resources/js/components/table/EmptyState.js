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

import ClayEmptyState from '@clayui/empty-state';
import {sub} from 'frontend-js-web';
import React from 'react';

const DEFAULT_EMPTY = {
	empty: {
		className: 'taglib-empty-state',
		title: Liferay.Language.get('there-are-no-entries'),
	},
	search: {
		className: 'taglib-search-state',
		title: Liferay.Language.get('no-results-were-found'),
	},
};

const EmptyState = ({
	button,
	className = '',
	description = null,
	title = DEFAULT_EMPTY.empty.title,
}) => {
	return (
		<ClayEmptyState
			className={className}
			description={description}
			imgSrc={
				className === DEFAULT_EMPTY.search.className
					? `${themeDisplay.getPathThemeImages()}/states/search_state.gif`
					: `${themeDisplay.getPathThemeImages()}/states/empty_state.gif`
			}
			title={title}
		>
			{button && button()}
		</ClayEmptyState>
	);
};

export function FilteredEmpty(props) {
	const description = Liferay.Language.get(
		'there-are-no-envelopes-with-these-attributes'
	);

	return <EmptyState description={description} {...props} />;
}

export function SearchEmpty({keywords, ...otherProps}) {
	const description = sub(
		Liferay.Language.get('there-are-no-envelopes-for-x'),
		keywords
	);

	return <EmptyState description={description} {...otherProps} />;
}

export function SearchAndFilteredEmpty({keywords, ...otherProps}) {
	const description = sub(
		Liferay.Language.get(
			'there-are-no-envelopes-for-x-with-these-attributes'
		),
		keywords
	);

	return <EmptyState description={description} {...otherProps} />;
}

export function withEmpty(Component) {
	const Wrapper = ({
		emptyState,
		isEmpty,
		isFiltered,
		keywords,
		...restProps
	}) => {
		if (isEmpty) {
			if (keywords.length) {
				if (isFiltered) {
					return (
						<SearchAndFilteredEmpty
							keywords={keywords}
							{...DEFAULT_EMPTY.search}
							{...emptyState?.searchAndFiltered}
						/>
					);
				}

				return (
					<SearchEmpty
						keywords={keywords}
						{...DEFAULT_EMPTY.search}
						{...emptyState?.search}
					/>
				);
			}

			if (isFiltered) {
				return (
					<FilteredEmpty
						{...DEFAULT_EMPTY.search}
						{...emptyState?.filtered}
					/>
				);
			}

			return <EmptyState {...emptyState} />;
		}

		return <Component {...restProps} />;
	};

	return Wrapper;
}

export default EmptyState;
