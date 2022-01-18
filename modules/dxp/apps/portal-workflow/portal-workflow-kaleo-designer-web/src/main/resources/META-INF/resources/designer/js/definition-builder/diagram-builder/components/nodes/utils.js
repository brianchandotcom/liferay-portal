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

import {defaultLanguageId} from '../../../constants';
import ConditionNode from './ConditionNode';
import ForkNode from './ForkNode';
import JoinNode from './JoinNode';
import JoinXorNode from './JoinXorNode';
import TaskNode from './TaskNode';
import EndNode from './state/EndNode';
import StartNode from './state/StartNode';
import StateNode from './state/StateNode';

const defaultNodes = [
	{
		data: {
			description: Liferay.Language.get('begin-a-workflow'),
			label: {[defaultLanguageId]: Liferay.Language.get('start')},
		},
		id: 'item_0',
		position: {x: 300, y: 100},
		type: 'start',
	},
	{
		data: {
			description: Liferay.Language.get('conclude-the-workflow'),
			label: {[defaultLanguageId]: Liferay.Language.get('end')},
		},
		id: 'item_1',
		position: {x: 300, y: 400},
		type: 'end',
	},
];

const nodeDescription = {
	'condition': Liferay.Language.get('execute-conditional-logic'),
	'end': Liferay.Language.get('conclude-the-workflow'),
	'fork': Liferay.Language.get('split-the-workflow-into-multiple-paths'),
	'join': Liferay.Language.get('all-interactions-need-to-be-closed'),
	'join-xor': Liferay.Language.get('only-one-interaction-needs-to-be-closed'),
	'start': Liferay.Language.get('begin-a-workflow'),
	'state': Liferay.Language.get('execute-actions-in-the-workflow'),
	'task': Liferay.Language.get('ask-a-user-to-work-on-the-item'),
};

const nodeTypes = {
	'condition': ConditionNode,
	'end': EndNode,
	'fork': ForkNode,
	'join': JoinNode,
	'join-xor': JoinXorNode,
	'start': StartNode,
	'state': StateNode,
	'task': TaskNode,
};

export {defaultNodes, nodeDescription, nodeTypes};
