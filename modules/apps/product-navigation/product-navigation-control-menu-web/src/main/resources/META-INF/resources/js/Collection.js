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

import {Collapse} from '@liferay/layout-content-page-editor-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {AddPanelContext} from './AddPanel';
import TabItem from './TabItem';

const Collection = ({collection, isContentTab, isSearchResult, open}) => {
	const {displayGrid} = useContext(AddPanelContext);

	return (
		<Collapse
			key={collection.collectionId}
			label={collection.label}
			open={isSearchResult || open}
		>
			{collection.collections &&
				collection.collections.map((collection, index) => (
					<Collection
						collection={collection}
						isSearchResult={isSearchResult}
						key={index}
					/>
				))}

			<ul
				className={classNames('list-unstyled', {
					grid: isContentTab && displayGrid,
				})}
			>
				{collection.children.map((item) => (
					<React.Fragment key={item.itemId}>
						<TabItem item={item} />

						{item.portletItems?.length && (
							<TabPortletItem items={item.portletItems} />
						)}
					</React.Fragment>
				))}
			</ul>
		</Collapse>
	);

	// ));

};

const TabPortletItem = ({items}) => (
	<ul className="list-unstyled">
		{items.map((item) => (
			<TabItem item={item} key={item.data.portletItemId} />
		))}
	</ul>
);

Collection.propTypes = {
	collection: PropTypes.shape({}).isRequired,
	isContentTab: PropTypes.bool,
	isSearchResult: PropTypes.bool,
	open: PropTypes.bool,
};

export default Collection;
