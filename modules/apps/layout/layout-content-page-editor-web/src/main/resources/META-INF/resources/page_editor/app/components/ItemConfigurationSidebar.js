/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React from 'react';

import {config} from '../config/index';
import {useActiveItemId, useActiveItemType} from '../contexts/ControlsContext';
import {useSelector} from '../contexts/StoreContext';
import selectItemConfigurationOpen from '../selectors/selectItemConfigurationOpen';
import ItemConfiguration from './ItemConfiguration';

export default function ItemConfigurationSidebar() {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();

	const itemConfigurationOpen = useSelector(selectItemConfigurationOpen);

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
						title={Liferay.Language.get('select-a-page-element')}
					/>
				)}
			</div>
		</ReactPortal>
	);
}
