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
import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

export default function ManagementToolbarRight({addButton, setShowMobile}) {
	return (
		<ManagementToolbar.ItemList>
			<ManagementToolbar.Item className="navbar-breakpoint-d-none">
				<ClayButton
					className="nav-link nav-link-monospaced"
					displayType="unstyled"
					onClick={() => setShowMobile(true)}
				>
					<ClayIcon symbol="search" />
				</ClayButton>
			</ManagementToolbar.Item>

			{addButton && (
				<ManagementToolbar.Item>{addButton()}</ManagementToolbar.Item>
			)}
		</ManagementToolbar.ItemList>
	);
}
