/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import {nodeDescription, nodeTypes} from '../nodes/utils';

const onDragStart = (event, nodeType, setElementRectangle) => {
	event.dataTransfer.setData('application/reactflow', nodeType);
	event.dataTransfer.effectAllowed = 'move';

	const elementRectangle = event.target.getBoundingClientRect();

	setElementRectangle({
		mouseXInRectangle: event.clientX - elementRectangle.left,
		mouseYInRectangle: event.clientY - elementRectangle.top,
		rectangleHeight: elementRectangle.height,
		rectangleWidth: elementRectangle.width,
	});
};

export default function SidebarBody({children, displayDefaultContent = true}) {
	const {setCollidingElements, setElementRectangle} = useContext(
		DiagramBuilderContext
	);

	return (
		<div className="sidebar-body">
			{displayDefaultContent
				? Object.entries(nodeTypes).map(([key, Component], index) => (
						<Component
							className="btn-sm"
							descriptionSidebar={nodeDescription[key]}
							draggable
							key={index}
							onDragEnd={() => setCollidingElements(null)}
							onDragLeave={() => setCollidingElements(null)}
							onDragStart={(event) =>
								onDragStart(event, key, setElementRectangle)
							}
						/>
				  ))
				: children}
		</div>
	);
}

SidebarBody.protoTypes = {
	children: PropTypes.any,
	displayDefaultContent: PropTypes.bool,
};
