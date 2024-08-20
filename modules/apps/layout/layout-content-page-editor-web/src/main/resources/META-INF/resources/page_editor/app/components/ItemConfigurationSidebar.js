/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React from 'react';

import MultiSelectMessage from '../../common/components/MultiSelectMessage';
import {config} from '../config/index';
import {useActiveItemIds, useActiveItemType} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectItemConfigurationOpen from '../selectors/selectItemConfigurationOpen';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import ItemConfiguration from './ItemConfiguration';

export default function ItemConfigurationSidebar() {
	const activeItemIds = useActiveItemIds();
	const activeItemType = useActiveItemType();
	const dispatch = useDispatch();

	const [activeItemId] = activeItemIds;

	const itemConfigurationOpen = useSelector(selectItemConfigurationOpen);

	const ItemConfigurationSidebarContent = () => {
		if (Liferay.FeatureFlags['LPD-18221'] && activeItemIds.length > 1) {
			return <MultiSelectMessage />;
		}
		else if (activeItemId) {
			return (
				<ItemConfiguration
					activeItemId={activeItemId}
					activeItemType={activeItemType}
				/>
			);
		}
		else {
			return (
				<ClayEmptyState
					className="p-5"
					description={Liferay.Language.get(
						'select-a-page-element-to-activate-this-panel'
					)}
					imgSrc={`${config.imagesPath}/no_item.svg`}
					imgSrcReducedMotion={null}
					small
					title={Liferay.Language.get('select-a-page-element')}
				/>
			);
		}
	};

	return (
		<ReactPortal className="cadmin">
			<div
				aria-label={Liferay.Language.get('configuration-panel')}
				className={classNames(
					'flex-column page-editor__item-configuration-sidebar',
					{
						'page-editor__item-configuration-sidebar--open':
							itemConfigurationOpen,
					}
				)}
				tabIndex={activeItemId ? null : 0}
			>
				<div className="d-flex d-md-none justify-content-end mr-2 mt-3">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('close')}
						borderless
						displayType="unstyled"
						monospaced
						onClick={() => {
							dispatch(
								switchSidebarPanel({
									itemConfigurationOpen: false,
								})
							);

							document
								.getElementById(
									'page-editor__toolbar__configuration-button'
								)
								?.focus();
						}}
						size="sm"
						symbol="times"
						title={Liferay.Language.get('close')}
					/>
				</div>

				<ItemConfigurationSidebarContent />
			</div>
		</ReactPortal>
	);
}
