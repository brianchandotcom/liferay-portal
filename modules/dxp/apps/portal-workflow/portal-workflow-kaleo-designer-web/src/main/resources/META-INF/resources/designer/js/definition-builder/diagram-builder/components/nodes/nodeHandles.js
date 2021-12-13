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

const handleStyle = {
	background: 'transparent',
	border: '2px solid #80ACFF',
	borderRadius: '50%',
	padding: '4px',
};

const sourceHandles = [
	{
		id: 'leftSource',
		position: 'left',
		style: {...handleStyle, left: '4px', top: '50%'},
		type: 'source',
	},
	{
		id: 'topSource',
		position: 'top',
		style: {...handleStyle, left: '50%', top: '4px'},
		type: 'source',
	},
	{
		id: 'rightSource',
		position: 'right',
		style: {...handleStyle, right: '4px', top: '50%'},
		type: 'source',
	},
	{
		id: 'bottomSource',
		position: 'bottom',
		style: {...handleStyle, bottom: '4px', left: '50%'},
		type: 'source',
	},
];

const targetHandles = [
	{
		id: 'leftTarget',
		position: 'left',
		style: {...handleStyle, left: '4px', top: '50%'},
		type: 'target',
	},
	{
		id: 'topTarget',
		position: 'top',
		style: {...handleStyle, left: '50%', top: '4px'},
		type: 'target',
	},
	{
		id: 'rightTarget',
		position: 'right',
		style: {...handleStyle, right: '4px', top: '50%'},
		type: 'target',
	},
	{
		id: 'bottomTarget',
		position: 'bottom',
		style: {...handleStyle, bottom: '4px', left: '50%'},
		type: 'target',
	},
];

export {sourceHandles, targetHandles};
