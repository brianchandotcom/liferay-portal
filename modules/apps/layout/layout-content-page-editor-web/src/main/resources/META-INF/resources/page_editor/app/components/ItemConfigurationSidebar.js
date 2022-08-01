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

import ClayEmptyState from '@clayui/empty-state';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React from 'react';

import {config} from '../config/index';
import {useActiveItemId, useActiveItemType} from '../contexts/ControlsContext';
import {useSelector} from '../contexts/StoreContext';
import ItemConfiguration from './ItemConfiguration';

export default function ItemConfigurationSidebar() {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();

	const itemConfigurationOpen = useSelector(
		(state) => state.sidebar.itemConfigurationOpen
	);

	return (
		<ReactPortal className="cadmin">
			<div
				className={classNames(
					'page-editor__item-configuration-sidebar',
					{
						[`page-editor__item-configuration-sidebar--open`]: itemConfigurationOpen,
					}
				)}
			>
				{activeItemId ? (
					<ItemConfiguration
						activeItemId={activeItemId}
						activeItemType={activeItemType}
					/>
				) : (
					<ClayEmptyState
						className="p-5"
						description={Liferay.Language.get(
							'select-a-page-element-to-activate-this-panel'
						)}
						imgSrc={`${config.imagesPath}/no_item.svg`}
						small
						title={Liferay.Language.get('no-selection-yet')}
					/>
				)}
			</div>
		</ReactPortal>
	);
}
