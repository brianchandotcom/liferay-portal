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
