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

import classNames from 'classnames';
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
	description,
	title = DEFAULT_EMPTY.empty.title,
}) => {
	return (
		<div className="taglib-empty-result-message">
			<div className="text-center">
				<div className={classNames('taglib-empty-state', className)} />

				{title && (
					<h1 className="taglib-empty-result-message-title">
						{title}
					</h1>
				)}

				{description && (
					<p className="empty-message-color taglib-empty-result-message-description">
						{description}
					</p>
				)}

				{button && button()}
			</div>
		</div>
	);
};

export const FilteredEmpty = (props) => {
	const description = Liferay.Language.get(
		'there-are-no-envelopes-with-these-attributes'
	);

	return <EmptyState description={description} {...props} />;
};

export const SearchEmpty = ({keywords, ...otherProps}) => {
	const description = Liferay.Util.sub(
		Liferay.Language.get('there-are-no-envelopes-for-x'),
		keywords
	);

	return <EmptyState description={description} {...otherProps} />;
};

export const SearchAndFilteredEmpty = ({keywords, ...otherProps}) => {
	const description = Liferay.Util.sub(
		Liferay.Language.get(
			'there-are-no-envelopes-for-x-with-these-attributes'
		),
		keywords
	);

	return <EmptyState description={description} {...otherProps} />;
};

export const withEmpty = (Component) => {
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
};

export default EmptyState;
