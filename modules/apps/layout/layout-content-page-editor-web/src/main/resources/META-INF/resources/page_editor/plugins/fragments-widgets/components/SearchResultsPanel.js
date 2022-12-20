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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React from 'react';

import TabCollection from './TabCollection';

export default function SearchResultsPanel({filteredTabs, loading = false}) {
	if (loading) {
		return <ClayLoadingIndicator className="mt-3" size="sm" />;
	}

	return filteredTabs.length ? (
		<div className="overflow-auto px-3">
			{filteredTabs.map((tab, index) => (
				<div key={index}>
					<div className="font-weight-semi-bold page-editor__fragments-widgets__search-results-panel__filter-subtitle py-2">
						{tab.label}
					</div>

					{tab.collections.map((collection, index) => (
						<TabCollection
							collection={collection}
							initialOpen
							isSearchResult
							key={index}
						/>
					))}
				</div>
			))}
		</div>
	) : (
		<ClayEmptyState
			description={Liferay.Language.get(
				'try-again-with-a-different-search'
			)}
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
			small
			title={Liferay.Language.get('no-results-found')}
		/>
	);
}

SearchResultsPanel.proptypes = {
	filteredTabs: PropTypes.object.isRequired,
	loading: PropTypes.bool,
};
