/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';
import {Handle} from 'react-flow-renderer';

import {hexagonNodeHandles} from '../../../util/nodeHandles';

export default function HexagonNode({children, notifyVisibilityChange, title}) {
	return (
		<>
			{hexagonNodeHandles.map((handle, index) => (
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
					className="hexagon-node node text-secondary"
					onMouseEnter={notifyVisibilityChange(true)}
					onMouseLeave={notifyVisibilityChange(false)}
				>
					<div
						className="truncate-container"
						data-tooltip-align="top"
						title={title}
					>
						{children}
					</div>
				</div>
			</ClayTooltipProvider>
		</>
	);
}
