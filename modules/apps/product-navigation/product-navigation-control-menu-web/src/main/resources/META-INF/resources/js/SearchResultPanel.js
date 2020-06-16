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
import React from 'react';

import TabItem from './TabItem';

export default function SearchResultsPanel({
	alertTitle,
	displayGrid,
	filteredTabs,
}) {
	return filteredTabs.map((tab, index) =>
		tab.collections.length ? (
			<ul
				className={classNames('list-unstyled', {
					grid: displayGrid,
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
}

SearchResultsPanel.proptypes = {
	filteredTabs: PropTypes.object.isRequired,
};
