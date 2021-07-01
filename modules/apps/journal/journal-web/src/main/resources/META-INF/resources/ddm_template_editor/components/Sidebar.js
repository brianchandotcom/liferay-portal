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
import classNames from 'classnames';
import React from 'react';

import {ElementsSidebarPanel} from './ElementsSidebarPanel';

export default function Sidebar({
	inputChannel,
	setSidebarOpen,
	sidebarOpen,
	templateVariableGroups,
}) {
	return (
		<div
			className={classNames('ddm_template_editor__App-sidebar', {
				open: sidebarOpen,
			})}
		>
			<div
				className={classNames(
					'ddm_template_editor__App-sidebar-content',
					{
						open: sidebarOpen,
					}
				)}
			>
				<ElementsSidebarPanel
					onButtonClick={(item) =>
						inputChannel.sendData(item.content)
					}
					templateVariableGroups={templateVariableGroups}
				/>
			</div>
			<div className="ddm_template_editor__App-sidebar-buttons pt-1">
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					monospaced
					onClick={() =>
						setSidebarOpen((sidebarOpen) => !sidebarOpen)
					}
					outline
					symbol="list-ul"
				/>
			</div>
		</div>
	);
}
