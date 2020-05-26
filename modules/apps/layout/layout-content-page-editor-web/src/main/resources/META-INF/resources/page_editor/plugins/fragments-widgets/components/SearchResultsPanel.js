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

import TabItem from './TabItem';

export default function SearchResultsPanel({filteredTabs}) {
	return filteredTabs.length ? (
		filteredTabs.map((tab, index) => (
			<div key={index}>
				<div className="page-editor__fragments-widgets__search-results-panel__filter-subtitle">
					{tab.label}
				</div>
				<ul className="list-unstyled">
					{tab.collections
						.reduce(
							(acc, collection) =>
								acc.concat(
									collection.children.filter(
										(item) =>
											!acc.some(
												({itemId}) =>
													itemId === item.itemId
											)
									)
								),
							[]
						)
						.map((item) => (
							<TabItem item={item} key={item.itemId} />
						))}
				</ul>
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
	filteredTabs: PropTypes.object.isRequired,
};
