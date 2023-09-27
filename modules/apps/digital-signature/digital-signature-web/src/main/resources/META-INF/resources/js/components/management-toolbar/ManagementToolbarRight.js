/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
