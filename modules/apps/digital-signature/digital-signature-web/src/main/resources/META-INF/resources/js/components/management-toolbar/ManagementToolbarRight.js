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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

export default ({addButton, setShowMobile}) => {
	return (
		<ClayManagementToolbar.ItemList>
			<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
				<ClayButton
					className="nav-link nav-link-monospaced"
					displayType="unstyled"
					onClick={() => setShowMobile(true)}
				>
					<ClayIcon symbol="search" />
				</ClayButton>
			</ClayManagementToolbar.Item>
			{addButton && (
				<ClayManagementToolbar.Item>
					{addButton()}
				</ClayManagementToolbar.Item>
			)}
		</ClayManagementToolbar.ItemList>
	);
};
