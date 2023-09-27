/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {AddPanelContext} from './AddPanel';
import TabItem from './TabItem';

const Collection = ({collection, isContentTab, isSearchResult, open}) => {
	const {displayGrid} = useContext(AddPanelContext);

	return (
		<div className="panel-group-sm">
			<ClayPanel
				collapsable
				defaultExpanded={isSearchResult || open}
				displayTitle={collection.label}
				displayType="unstyled"
				key={collection.collectionId}
				showCollapseIcon
			>
				<ClayPanel.Body>
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
				</ClayPanel.Body>
			</ClayPanel>
		</div>
	);
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
