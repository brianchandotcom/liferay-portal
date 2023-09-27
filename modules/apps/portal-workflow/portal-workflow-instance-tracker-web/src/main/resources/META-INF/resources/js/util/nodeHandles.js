/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const nodeHandles = [
	{id: 'leftSource1', position: 'left', style: {top: '20%'}, type: 'source'},
	{id: 'leftTarget1', position: 'left', style: {top: '40%'}, type: 'target'},
	{id: 'leftSource2', position: 'left', style: {top: '60%'}, type: 'source'},
	{id: 'leftTarget2', position: 'left', style: {top: '80%'}, type: 'target'},
	{id: 'topSource1', position: 'top', style: {left: '20%'}, type: 'source'},
	{id: 'topTarget1', position: 'top', style: {left: '40%'}, type: 'target'},
	{id: 'topSource2', position: 'top', style: {left: '60%'}, type: 'source'},
	{id: 'topTarget2', position: 'top', style: {left: '80%'}, type: 'target'},
	{
		id: 'rightSource1',
		position: 'right',
		style: {top: '20%'},
		type: 'source',
	},
	{
		id: 'rightTarget1',
		position: 'right',
		style: {top: '40%'},
		type: 'target',
	},
	{
		id: 'rightSource2',
		position: 'right',
		style: {top: '60%'},
		type: 'source',
	},
	{
		id: 'rightTarget2',
		position: 'right',
		style: {top: '80%'},
		type: 'target',
	},
	{
		id: 'bottomTarget1',
		position: 'bottom',
		style: {left: '20%'},
		type: 'target',
	},
	{
		id: 'bottomSource1',
		position: 'bottom',
		style: {left: '40%'},
		type: 'source',
	},
	{
		id: 'bottomTarget2',
		position: 'bottom',
		style: {left: '60%'},
		type: 'target',
	},
	{
		id: 'bottomSource2',
		position: 'bottom',
		style: {left: '80%'},
		type: 'source',
	},
];

const hexagonNodeHandlesStyles = [
	{style: {top: '20%'}},
	{style: {left: '-26px', top: '40%'}},
	{style: {top: '60%'}},
	{
		style: {left: '-26px', top: '80%'},
	},
	{style: {left: '20%'}},
	{
		style: {left: '40%', top: '-10px'},
	},
	{style: {left: '60%'}},
	{
		style: {left: '80%', top: '-10px'},
	},
	{style: {top: '20%'}},
	{
		style: {right: '-26px', top: '40%'},
	},
	{
		style: {top: '60%'},
	},
	{
		style: {right: '-26px', top: '80%'},
	},
	{
		style: {bottom: '-10px', left: '20%'},
	},
	{
		style: {left: '40%'},
	},
	{
		style: {bottom: '-10px', left: '60%'},
	},
	{
		style: {left: '80%'},
	},
];

const hexagonNodeHandles = hexagonNodeHandlesStyles.map(({style}, index) => ({
	...nodeHandles[index],
	style,
}));

export {nodeHandles, hexagonNodeHandles};
