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

const VerticalNavigationBar = ({items}) => {
	return (
		<VerticalBar absolute className="kbVerticalBar" position="left">
			<VerticalBar.Bar displayType="light" items={items}>
				{(item) => (
					<VerticalBar.Item key={item.key}>
						<ClayButtonWithIcon
							displayType="unstyled"
							onClick={() => {
								navigate(item.href);
							}}
							symbol={item.icon}
						/>
					</VerticalBar.Item>
				)}
			</VerticalBar.Bar>

			<VerticalBar.Content items={items}>
				{(item) => (
					<VerticalBar.Panel key={item.key}>
						<div className="sidebar-header">
							<div className="component-title">
								{item.title} Tree View
							</div>
						</div>
					</VerticalBar.Panel>
				)}
			</VerticalBar.Content>
		</VerticalBar>
	);
};

export default VerticalNavigationBar;
