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
import ReactFlow, {
	Background,
	Controls,
	ReactFlowProvider,
} from 'react-flow-renderer';

import EndNode from './components/nodes/state/EndNode';
import StartNode from './components/nodes/state/StartNode';
import StateNode from './components/nodes/state/StateNode';
import Sidebar from './components/sidebar/Sidebar';

const onLoad = (reactFlowInstance) => {
	reactFlowInstance.fitView();
};

const nodeTypes = {
	end: EndNode,
	start: StartNode,
	state: StateNode,
};

export default function DiagramBuilder() {
	const startNode = {
		data: {
			description: 'Your flow starts here',
		},
		id: '0',
		position: {x: 100, y: 100},
		type: 'start',
	};
	const endNode = {
		data: {
			description: 'Your flow ends here',
		},
		id: '1',
		position: {x: 100, y: 400},
		type: 'end',
	};

	const elements = [startNode, endNode];

	return (
		<div className="diagram-builder">
			<div className="diagram-area">
				<ReactFlowProvider>
					<ReactFlow
						elements={elements}
						minZoom="0.1"
						nodeTypes={nodeTypes}
						onLoad={onLoad}
					/>
					<Controls showInteractive={false} />
					<Background size={1} />
				</ReactFlowProvider>
			</div>

			<Sidebar />
		</div>
	);
}
