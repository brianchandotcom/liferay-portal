/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import {getOpener} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import getSearchItems from '../utils/getSearchItems';
import normalizeItems from '../utils/normalizeItems';
import SearchField from './SearchField';

const ITEM_TYPES_SYMBOL = {
	KBArticle: 'document-text',
	KBFolder: 'folder',
};

const SELECT_EVENT_NAME = 'selectKBMoveFolder';

export default function MoveModal({items: initialItems, moveParentKBObjectId}) {
	const items = useMemo(() => normalizeItems(initialItems), [initialItems]);

	const searchItems = useMemo(() => getSearchItems(initialItems), [
		initialItems,
	]);

	const [searchActive, setSearchActive] = useState(false);

	const handleItemMove = (currentItem, destinationItem, index) => {
		getOpener().Liferay.fire(SELECT_EVENT_NAME, {
			destinationItem,
			index,
		});
	};

	const onItemClick = (destinationItem) => {
		const index = {next: destinationItem.children.length};
		getOpener().Liferay.fire(SELECT_EVENT_NAME, {destinationItem, index});
	};

	const handleSearchChange = ({isSearchActive}) => {
		setSearchActive(isSearchActive);
	};

	const handleSearchOnclickItem = (searchItem) => {
		const selectedItem = items.reduce(function reducer(acc, item) {
			if (item.id === searchItem.id) {
				acc.push(item);
			}

			if (item.children) {
				item.children.reduce(reducer, acc);
			}

			return acc;
		}, []);

		onItemClick(selectedItem[0]);
	};

	const [selectedItemId, setSelectedItemId] = useState();

	return (
		<div className="container-fluid p-3">
			<SearchField
				handleOnclickItem={handleSearchOnclickItem}
				handleSearchChange={handleSearchChange}
				items={searchItems}
			/>

			{!searchActive && (
				<ClayTreeView
					defaultItems={items}
					defaultSelectedKeys={new Set([moveParentKBObjectId])}
					nestedKey="children"
					onItemMove={handleItemMove}
					showExpanderOnHover={false}
				>
					{(item) => {
						return (
							<ClayTreeView.Item
								className={classnames({
									'knowledge-base-navigation-item-active':
										item.id === selectedItemId,
								})}
								onClick={(event) => {
									event.stopPropagation();

									setSelectedItemId(item.id);

									onItemClick(item);
								}}
							>
								<ClayTreeView.ItemStack>
									<ClayIcon
										symbol={ITEM_TYPES_SYMBOL[item.type]}
									/>

									{item.name}
								</ClayTreeView.ItemStack>

								<ClayTreeView.Group items={item.children}>
									{(item) => {
										return (
											<ClayTreeView.Item>
												<ClayIcon
													symbol={
														ITEM_TYPES_SYMBOL[
															item.type
														]
													}
												/>

												{item.name}
											</ClayTreeView.Item>
										);
									}}
								</ClayTreeView.Group>
							</ClayTreeView.Item>
						);
					}}
				</ClayTreeView>
			)}
		</div>
	);
}

const itemShape = {
	classNameId: PropTypes.string.isRequired,
	href: PropTypes.string.isRequired,
	id: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
	type: PropTypes.oneOf(Object.keys(ITEM_TYPES_SYMBOL)).isRequired,
};

itemShape.children = PropTypes.arrayOf(PropTypes.shape(itemShape));

MoveModal.propTypes = {
	items: PropTypes.arrayOf(PropTypes.shape(itemShape)),
	moveParentKBObjectId: PropTypes.string.isRequired,
};
