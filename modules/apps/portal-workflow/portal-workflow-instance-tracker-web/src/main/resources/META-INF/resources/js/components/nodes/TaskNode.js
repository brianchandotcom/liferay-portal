/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import React from 'react';
import {Handle} from 'react-flow-renderer';

import {nodeHandles} from '../../util/nodeHandles';

export default function TaskNode({
	data: {current = false, done = false, label, notifyVisibilityChange},
}) {
	return (
		<>
			{nodeHandles.map((handle, index) => (
				<Handle
					id={handle.id}
					key={index}
					position={handle.position}
					style={handle.style}
					type={handle.type}
				/>
			))}

			<ClayTooltipProvider>
				<div
					className={classnames(
						'node task-node',
						current ? 'current-task text-white' : 'text-secondary'
					)}
					data-tooltip-align="top"
					onMouseEnter={notifyVisibilityChange(true)}
					onMouseLeave={notifyVisibilityChange(false)}
					title={label}
				>
					<span className="truncate-container">{label}</span>

					{current ? (
						<ClayIcon className="current-icon ml-2" symbol="live" />
					) : (
						done && (
							<ClayIcon
								className="done-icon ml-2"
								symbol="check"
							/>
						)
					)}
				</div>
			</ClayTooltipProvider>
		</>
	);
}
