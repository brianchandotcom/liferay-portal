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
import {VerticalBar} from '@clayui/core';
import {navigate} from 'frontend-js-web';
import React from 'react';

import NavigationPanel from './NavigationPanel';

const CSS_EXPANDED = 'expanded';

const VerticalNavigationBar = ({items, parentContainerId}) => {
	const parentContainer = document.getElementById(parentContainerId);

	const activeItem = items.find((item) => item.active);

	const onIconClick = (item) => {
		if (item.key !== activeItem.key) {
			parentContainer.classList.add(CSS_EXPANDED);

			navigate(item.href);
		}
		else {
			parentContainer.classList.toggle(CSS_EXPANDED);
		}
	};

	const VerticalBarPanels = {
		article: NavigationPanel,
		suggestion: NavigationPanel,
		template: NavigationPanel,
	};

	return (
		<VerticalBar absolute defaultActive={activeItem?.key} position="left">
			<VerticalBar.Bar displayType="light" items={items}>
				{(item) => (
					<VerticalBar.Item key={item.key}>
						<ClayButtonWithIcon
							data-tooltip-align="right"
							displayType="unstyled"
							onClick={() => {
								onIconClick(item);
							}}
							symbol={item.icon}
							title={Liferay.Language.get(item.title)}
						/>
					</VerticalBar.Item>
				)}
			</VerticalBar.Bar>

			<VerticalBar.Content items={items}>
				{(item) => {
					const PanelComponent = VerticalBarPanels[item.key];

					return (
						<VerticalBar.Panel key={item.key}>
							<PanelComponent />
						</VerticalBar.Panel>
					);
				}}
			</VerticalBar.Content>
		</VerticalBar>
	);
};

export default VerticalNavigationBar;
