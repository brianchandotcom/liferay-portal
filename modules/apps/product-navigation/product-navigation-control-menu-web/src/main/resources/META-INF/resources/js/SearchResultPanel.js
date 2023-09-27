/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
