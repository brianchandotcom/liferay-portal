/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import {fetch, navigate, objectToFormData, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import getSearchItems from '../utils/getSearchItems';
import normalizeItems from '../utils/normalizeItems';
import showSuccessMessage from '../utils/showSuccessMessage';
import ActionsDropdown from './ActionsDropdown';
import SearchField from './SearchField';

const ITEM_TYPES_SYMBOL = {
	KBArticle: 'document-text',
	KBFolder: 'folder',
};

const ITEM_TYPES = {
	KBArticle: 'article',
	KBFolder: 'folder',
};

export default function NavigationPanel({
	items: initialItems,
	moveKBObjectURL,
	portletNamespace,
	selectedItemId,
}) {
	const items = useMemo(
		() => normalizeItems(initialItems, portletNamespace),
		[initialItems, portletNamespace]
	);
	const searchItems = useMemo(() => getSearchItems(initialItems), [
		initialItems,
	]);

	const [searchActive, setSearchActive] = useState(false);

	const handleClickItem = (event, item) => {
		if (event.defaultPrevented) {
			return;
		}

		event.stopPropagation();
		event.preventDefault();

		navigate(item.href);
	};

	const handleItemMove = (item, parentItem, index) => {
		if (
			item.type === ITEM_TYPES.folder &&
			parentItem.type === ITEM_TYPES.article
		) {
			openToast({
				message: Liferay.Language.get(
					'folders-cannot-be-moved-into-articles'
				),
				type: 'danger',
			});

			return false;
		}

		fetch(moveKBObjectURL, {
			body: objectToFormData({
				[`${portletNamespace}dragAndDrop`]: true,
				[`${portletNamespace}position`]: index?.next ?? -1,
				[`${portletNamespace}resourceClassNameId`]: item.classNameId,
				[`${portletNamespace}resourcePrimKey`]: item.id,
				[`${portletNamespace}parentResourceClassNameId`]: parentItem.classNameId,
				[`${portletNamespace}parentResourcePrimKey`]: parentItem.id,
			}),
			method: 'POST',
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error();
				}

				return response.json();
			})
			.then((response) => {
				if (!response.success) {
					throw new Error(response.errorMessage);
				}

				showSuccessMessage(portletNamespace);
			})
			.catch(
				({
					message = Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				}) => {
					openToast({
						message,
						type: 'danger',
					});
				}
			);

		return true;
	};

	const handleSearchChange = ({isSearchActive}) => {
		setSearchActive(isSearchActive);
	};

	return (
		<>
			<SearchField
				handleSearchChange={handleSearchChange}
				items={searchItems}
			/>

			{!searchActive && (
				<ClayTreeView
					defaultItems={items}
					defaultSelectedKeys={new Set([selectedItemId])}
					dragAndDrop
					nestedKey="children"
					onItemMove={handleItemMove}
					showExpanderOnHover={false}
				>
					{(item) => {
						return (
							<ClayTreeView.Item
								actions={ActionsDropdown({
									actions: item.actions,
								})}
								onClick={(event) => {
									handleClickItem(event, item);
								}}
							>
								<ClayTreeView.ItemStack
									className={classnames({
										'knowledge-base-navigation-item-active':
											item.id === selectedItemId,
									})}
								>
									<ClayIcon
										aria-label={item.name}
										symbol={ITEM_TYPES_SYMBOL[item.type]}
									/>

									{item.name}
								</ClayTreeView.ItemStack>

								<ClayTreeView.Group items={item.children}>
									{(item) => {
										return (
											<ClayTreeView.Item
												actions={ActionsDropdown({
													actions: item.actions,
												})}
												onClick={(event) => {
													handleClickItem(
														event,
														item
													);
												}}
											>
												<ClayIcon
													aria-label={item.name}
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
		</>
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

NavigationPanel.propTypes = {
	items: PropTypes.arrayOf(PropTypes.shape(itemShape)),
	selectedItemId: PropTypes.string,
};
