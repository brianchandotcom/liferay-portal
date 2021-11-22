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

import React from 'react';

import {nodeDescription, nodeTypes} from '../nodes/utils';

export default function Sidebar() {
	const onDragStart = (event, nodeType) => {
		event.dataTransfer.setData('application/reactflow', nodeType);
		event.dataTransfer.effectAllowed = 'move';
	};

	return (
		<div className="sidebar">
			<div className="sidebar-header">
				<span className="title">{Liferay.Language.get('nodes')}</span>
			</div>

			<div className="sidebar-body">
				{Object.entries(nodeTypes).map(([key, Component], index) => (
					<Component
						descriptionSidebar={nodeDescription[key]}
						draggable
						key={index}
						onDragStart={(event) => onDragStart(event, key)}
					/>
				))}
			</div>
		</div>
	);
}
