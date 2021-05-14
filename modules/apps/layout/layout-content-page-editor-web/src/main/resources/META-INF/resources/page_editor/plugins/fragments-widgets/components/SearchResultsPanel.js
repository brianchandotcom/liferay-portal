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

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import TabCollection from './TabCollection';

export default function SearchResultsPanel({displayStyle, filteredTabs}) {
	return filteredTabs.length ? (
		filteredTabs.map((tab, index) => (
			<div key={index}>
				<div className="page-editor__fragments-widgets__search-results-panel__filter-subtitle">
					{tab.label}
				</div>
				{tab.collections.map((collection, index) => (
					<TabCollection
						collection={collection}
						displayStyle={displayStyle}
						isSearchResult
						key={index}
						open
					/>
				))}
			</div>
		))
	) : (
		<ClayAlert displayType="info" title={Liferay.Language.get('info')}>
			{Liferay.Language.get(
				'there-are-no-fragments-or-widgets-on-this-page'
			)}
		</ClayAlert>
	);
}

SearchResultsPanel.proptypes = {
	displayStyle: PropTypes.oneOf(Object.values(FRAGMENTS_DISPLAY_STYLES)),
	filteredTabs: PropTypes.object.isRequired,
};
