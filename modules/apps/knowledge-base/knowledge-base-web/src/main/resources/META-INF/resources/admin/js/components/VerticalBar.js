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
import React from 'react';

const VerticalNavigationBar = ({actions}) => {
	return (
		<VerticalBar absolute className="kbVerticalBar" position="left">
			<VerticalBar.Bar displayType="light">
				<VerticalBar.Item key="artice">
					<ClayButtonWithIcon
						displayType="unstyled"
						onClick={() => {
							console.log('click on articles');
						}}
						symbol="pages-tree"
					/>
				</VerticalBar.Item>

				<VerticalBar.Item key="template">
					<ClayButtonWithIcon
						displayType="unstyled"
						onClick={() => {
							console.log('click on templates');
						}}
						symbol="page-template"
					/>
				</VerticalBar.Item>

				<VerticalBar.Item key="suggestion">
					<ClayButtonWithIcon
						displayType="unstyled"
						onClick={() => {
							console.log('click on suggestions');
						}}
						symbol="message"
					/>
				</VerticalBar.Item>
			</VerticalBar.Bar>
		</VerticalBar>
	);
};

export default VerticalNavigationBar;
