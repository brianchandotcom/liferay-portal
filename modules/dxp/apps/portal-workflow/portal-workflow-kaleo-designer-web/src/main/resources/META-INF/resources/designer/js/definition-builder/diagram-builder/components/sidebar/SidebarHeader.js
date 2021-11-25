/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

export default function SidebarHeader({
	backButtonFunction,
	showBackButton,
	title,
}) {
	return (
		<div className="sidebar-header">
			{showBackButton && (
				<ClayButtonWithIcon
					className="text-secondary"
					displayType="unstyled"
					onClick={backButtonFunction}
					symbol="angle-left"
				/>
			)}

			<span className="title">{title}</span>
		</div>
	);
}
