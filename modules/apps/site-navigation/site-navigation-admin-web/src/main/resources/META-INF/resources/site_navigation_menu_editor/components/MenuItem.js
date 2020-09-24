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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';

export const MenuItem = ({item, onSelect, selected}) => {
	const setSidebarPanelId = useSetSidebarPanelId();

	return (
		<ClayCard
			className={classNames('site_navigation_menu_editor_MenuItem', {
				'site_navigation_menu_editor_MenuItem--selected': selected,
			})}
			horizontal
			selectable
		>
			<ClayCheckbox
				checked={selected}
				onChange={(menuItem) => {
					onSelect(menuItem);
					setSidebarPanelId(SIDEBAR_PANEL_IDS.menuItemSettings);
				}}
			>
				<ClayCard.Body className="px-0">
					<ClayCard.Row>
						<ClayLayout.ContentCol gutters>
							<ClayButtonWithIcon
								displayType="unstyled"
								small
								symbol="drag"
							/>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol expand>
							<ClayCard.Description displayType="title">
								{item.title}
							</ClayCard.Description>

							<ClayCard.Description displayType="subtitle">
								{item.type}
							</ClayCard.Description>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol gutters>
							<ClayButtonWithIcon
								displayType="unstyled"
								small
								symbol="times-circle"
							/>
						</ClayLayout.ContentCol>
					</ClayCard.Row>
				</ClayCard.Body>
			</ClayCheckbox>
		</ClayCard>
	);
};

MenuItem.propTypes = {
	item: PropTypes.shape({
		children: PropTypes.array.isRequired,
		siteNavigationMenuItemId: PropTypes.string.isRequired,
		title: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired,
	}),
	onSelect: PropTypes.func.isRequired,
	selected: PropTypes.bool,
};
