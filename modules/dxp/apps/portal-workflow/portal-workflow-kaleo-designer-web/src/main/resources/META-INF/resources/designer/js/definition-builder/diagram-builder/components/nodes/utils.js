/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {v4 as uuidv4} from 'uuid';

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
		id: uuidv4(),
		position: {x: 300, y: 100},
		type: 'start',
	},
	{
		data: {
			description: Liferay.Language.get('conclude-the-workflow'),
			label: {[defaultLanguageId]: Liferay.Language.get('end')},
		},
		id: uuidv4(),
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
