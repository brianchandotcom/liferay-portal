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

import Collection from './Collection';

const CONTENT_TAB_ID = 'content';

const SearchResultsPanel = ({alertTitle, filteredTabs}) =>
	filteredTabs.map((tab, index) =>
		tab.collections.length ? (
			tab.collections.map((collection, index) => (
				<Collection
					collection={collection}
					isContentTab={tab.id === CONTENT_TAB_ID}
					isSearchResult
					key={index}
					open
				/>
			))
		) : (
			<ClayAlert
				displayType="info"
				key={index}
				title={Liferay.Language.get('info')}
			>
				{alertTitle}
			</ClayAlert>
		)
	);

SearchResultsPanel.proptypes = {
	alertTitle: PropTypes.string.isRequired,
	filteredTabs: PropTypes.object.isRequired,
};

export default SearchResultsPanel;
