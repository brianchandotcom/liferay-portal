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

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import ActionsDropdown from './ActionsDropdown';

const ITEM_TYPES_SYMBOL = {
	folder: 'folder',
	template: 'document-text',
};

export default function TemplatesPanel({items, selectedItemId}) {
	return items?.length ? (
		<ClayTreeView
			defaultItems={items}
			defaultSelectedKeys={new Set([selectedItemId])}
			nestedKey="children"
			showExpanderOnHover={false}
		>
			{(item) => {
				return (
					<ClayTreeView.Item
						actions={ActionsDropdown({actions: item.actions})}
					>
						<ClayTreeView.ItemStack
							className={classnames({
								'knowledge-base-navigation-item-active':
									item.id === selectedItemId,
							})}
						>
							<ClayLink displayType="secondary" href={item.href}>
								<ClayIcon
									symbol={ITEM_TYPES_SYMBOL[item.type]}
								/>

								<span className="component-text">
									{item.name}
								</span>
							</ClayLink>
						</ClayTreeView.ItemStack>

						<ClayTreeView.Group items={item.children}>
							{(item) => {
								return (
									<ClayTreeView.Item
										actions={ActionsDropdown({
											actions: item.actions,
										})}
										className={classnames({
											'knowledge-base-navigation-item-active':
												item.id === selectedItemId,
										})}
									>
										<ClayLink
											displayType="secondary"
											href={item.href}
										>
											<ClayIcon
												symbol={
													ITEM_TYPES_SYMBOL[item.type]
												}
											/>

											<span className="component-text">
												{item.name}
											</span>
										</ClayLink>
									</ClayTreeView.Item>
								);
							}}
						</ClayTreeView.Group>
					</ClayTreeView.Item>
				);
			}}
		</ClayTreeView>
	) : (
		<ClayEmptyState
			description=""
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
			small
			title={Liferay.Language.get('there-are-no-article-templates')}
		/>
	);
}

const itemShape = {
	href: PropTypes.string.isRequired,
	id: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
	type: PropTypes.oneOf(Object.keys(ITEM_TYPES_SYMBOL)).isRequired,
};

itemShape.children = PropTypes.arrayOf(PropTypes.shape(itemShape));

TemplatesPanel.propTypes = {
	items: PropTypes.arrayOf(PropTypes.shape(itemShape)),
	selectedItemId: PropTypes.string,
};
