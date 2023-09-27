/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const DOM_STRUCTURE_FOR_PLACING_STEPS = `
	<div class="logo" />
	<div id="footer" />	
	<button class="btn" data-testid="external-react-tree-button" />
	<div id="app_root" />
`.trim();

export const PAGE_MOCK = {
	pages: {
		'/home': ['step-1', 'step-2'],
		'/home/abc': ['step-1'],
	},
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'step-1',
			nodeToHighlight: '.logo',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			darkbg: true,
			id: 'step-2',
			nodeToHighlight: '#footer',
			positioning: 'top',
			title: 'Title 2',
		},
	],
};

export const PAGE_WITH_PREVIOUS_MOCK = {
	pages: {
		'/home': ['step-1', 'step-2'],
	},
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'step-1',
			nodeToHighlight: '.logo',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			darkbg: true,
			id: 'step-2',
			nodeToHighlight: '#footer',
			positioning: 'top',
			previous: '/fiona',
			title: 'Title 2',
		},
	],
};

export const INVALID_NODE_SELECTOR_MOCK = {
	pages: {
		'/home': ['step-1', 'step-2'],
	},
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'step-1',
			nodeToHighlight: '.fiona',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			darkbg: true,
			id: 'step-2',
			nodeToHighlight: '#footer',
			positioning: 'top',
			title: 'Title 2',
		},
	],
};

export const BOX_SHADOW_ELEMENT_MOCK = {
	pages: {
		'/home': ['step-1', 'step-2'],
	},
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: false,
			id: 'step-1',
			nodeToHighlight: '.btn',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			darkbg: false,
			id: 'step-2',
			nodeToHighlight: '#footer',
			positioning: 'top',
			title: 'Title 2',
		},
	],
};

export const MULTI_PAGES_MOCK = {
	pages: {
		'/home': ['step-1'],
		'/home-2': ['step-2', 'step-3'],
	},
	steps: [
		{
			content: '<span>Content 1</span><br/><code>Hello1</code>',
			darkbg: true,
			id: 'step-1',
			next: '/home-2',
			nodeToHighlight: '.logo',
			title: 'Title 1',
		},
		{
			content: '<span>Content 2</span><br/><code>Hello2</code>',
			darkbg: true,
			id: 'step-2',
			nodeToHighlight: '#footer',
			positioning: 'top',
			previous: '/home',
			title: 'Title 2',
		},
		{
			content: '<span>Content 3</span><br/><code>Hello3</code>',
			darkbg: true,
			id: 'step-3',
			nodeToHighlight: '.btn',
			title: 'Title 3',
		},
	],
};
