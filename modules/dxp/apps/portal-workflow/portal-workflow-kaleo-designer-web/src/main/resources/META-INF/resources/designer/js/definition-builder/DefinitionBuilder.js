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

import '../../css/definition-builder/main.scss';
import UpperToolbar from './shared/components/toolbar/UpperToolbar';

const onLoad = (reactFlowInstance) => {
	reactFlowInstance.fitView();
};

export default function (props) {
	return (
		<div className="definition-builder-app">
			<UpperToolbar {...props} />

			<div className="definition-canvas">
				<ReactFlowProvider>
					<ReactFlow elements={[]} minZoom="0.1" onLoad={onLoad} />

					<Controls showInteractive={false} />
					<Background size={1} />
				</ReactFlowProvider>
			</div>
		</div>
	);
}
