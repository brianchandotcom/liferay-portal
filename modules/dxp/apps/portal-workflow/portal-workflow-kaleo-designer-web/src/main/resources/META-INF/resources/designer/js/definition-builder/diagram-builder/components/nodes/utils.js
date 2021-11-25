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

import EndNode from './state/EndNode';
import StartNode from './state/StartNode';
import StateNode from './state/StateNode';

const defaultNodes = [
	{
		id: 'node_0',
		position: {x: 300, y: 100},
		type: 'start',
	},
	{
		id: 'node_1',
		position: {x: 300, y: 400},
		type: 'end',
	},
];

const nodeDescription = {
	end: Liferay.Language.get('conclude-the-workflow'),
	start: Liferay.Language.get('begin-a-workflow'),
	state: Liferay.Language.get('execute-actions-in-the-workflow'),
};

const nodeTypes = {
	end: EndNode,
	start: StartNode,
	state: StateNode,
};

export {defaultNodes, nodeDescription, nodeTypes};
