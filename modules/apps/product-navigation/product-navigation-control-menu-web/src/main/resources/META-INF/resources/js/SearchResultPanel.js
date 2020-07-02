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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {AddPanelContext} from './AddPanel';
import TabItem from './TabItem';

const CONTENT_TAB_ID = 'content';

const SearchResultsPanel = ({alertTitle, filteredTabs}) => {
	const {displayGrid} = useContext(AddPanelContext);

	return filteredTabs.map((tab, index) =>
		tab.collections.length ? (
			<ul
				className={classNames('list-unstyled', {
					grid: displayGrid && tab.id === CONTENT_TAB_ID,
				})}
				key={index}
			>
				{tab.collections
					.reduce(
						(acc, collection) =>
							acc.concat(
								collection.children.filter(
									(item) =>
										!acc.some(
											({itemId}) => itemId === item.itemId
										)
								)
							),
						[]
					)
					.map((item) => (
						<TabItem item={item} key={item.itemId} />
					))}
			</ul>
		) : (
			<ClayAlert displayType="info" title={Liferay.Language.get('info')}>
				{alertTitle}
			</ClayAlert>
		)
	);
};

SearchResultsPanel.proptypes = {
	alertTitle: PropTypes.string.isRequired,
	filteredTabs: PropTypes.object.isRequired,
};

export default SearchResultsPanel;
