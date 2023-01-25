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
import React from 'react';

import {sub} from '../../utils/lang.es';

const EmptyState = ({emptyState, keywords = '', small = false}) => {
	const defaultEmpty = {
		description: null,
		title: Liferay.Language.get('there-are-no-entries'),
	};

	const defaultSearch = {
		description: sub(Liferay.Language.get('there-are-no-results-for-x'), [
			keywords,
		]),
		title: Liferay.Language.get('no-results-were-found'),
	};

	emptyState = {
		...defaultEmpty,
		...emptyState,
	};

	const search = {
		...defaultSearch,
		...emptyState.search,
	};

	const isSearch = keywords !== '';
	const {button, description, title} = isSearch ? search : emptyState;

	return (
		<ClayEmptyState
			description={description}
			imgSrc={
				isSearch
					? `${themeDisplay.getPathThemeImages()}/states/search_state.gif`
					: `${themeDisplay.getPathThemeImages()}/states/empty_state.gif`
			}
			small={small}
			title={title}
		>
			{button && button()}
		</ClayEmptyState>
	);
};

export function withEmpty(Component) {
	const Wrapper = ({emptyState, isEmpty, keywords, ...restProps}) => {
		if (isEmpty) {
			return <EmptyState emptyState={emptyState} keywords={keywords} />;
		}

		return <Component {...restProps} />;
	};

	return Wrapper;
}

export default EmptyState;
