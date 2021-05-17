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

import PropTypes from 'prop-types';
import React from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import Collapse from '../../../common/components/Collapse';
import TabItem from './TabItem';

export default function TabCollection({
	collection,
	displayStyle,
	isSearchResult,
	open,
}) {
	return (
		<Collapse
			key={collection.collectionId}
			label={collection.label}
			open={isSearchResult || open}
		>
			{collection.collections &&
				collection.collections.map((collection, index) => (
					<TabCollection
						collection={collection}
						isSearchResult={isSearchResult}
						key={index}
					/>
				))}

			<ul className="d-flex flex-wrap list-unstyled">
				{collection.children.map((item) => (
					<React.Fragment key={item.itemId}>
						<TabItem
							displayStyle={displayStyle}
							item={item}
							key={item.itemId}
						/>
						{item.portletItems?.length && (
							<TabPortletItems item={item} />
						)}
					</React.Fragment>
				))}
			</ul>
		</Collapse>
	);
}

TabCollection.proptypes = {
	collection: PropTypes.shape({
		children: PropTypes.array,
		collectionId: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
	displayStyle: PropTypes.oneOf(Object.values(FRAGMENTS_DISPLAY_STYLES)),
	isSearchResult: PropTypes.bool,
	open: PropTypes.bool,
};

const TabPortletItems = ({item}) => (
	<ul className="d-flex list-unstyled">
		{item.portletItems.map((portlet, index) => (
			<TabItem item={portlet} key={index} />
		))}
	</ul>
);

TabPortletItems.proptypes = {
	data: PropTypes.shape({
		instanceable: PropTypes.bool,
		portletId: PropTypes.string,
		portletItemId: PropTypes.string,
		used: PropTypes.bool,
	}).isRequired,
	disabled: PropTypes.bool,
	icon: PropTypes.string,
	itemId: PropTypes.string,
	label: PropTypes.string,
	portletItems: PropTypes.array,
	preview: PropTypes.string,
	type: PropTypes.string,
};
