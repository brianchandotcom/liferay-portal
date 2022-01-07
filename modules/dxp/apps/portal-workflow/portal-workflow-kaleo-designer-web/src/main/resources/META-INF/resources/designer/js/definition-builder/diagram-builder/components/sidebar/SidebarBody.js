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
