/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as d3 from 'd3';

import D3OrganizationChart from '../src/main/resources/META-INF/resources/js/D3OrganizationChart';
import {
	ACCOUNTS_PROPERTY_NAME,
	BRIEFS_KEYS_MAP,
	ORGANIZATIONS_PROPERTY_NAME,
	USERS_PROPERTY_NAME_IN_ORGANIZATION,
} from '../src/main/resources/META-INF/resources/js/utils/constants';

const actions = {
	delete: {
		href: '#',
		method: 'test',
	},
	update: {
		href: '#',
		method: 'test',
	},
};

jest.mock(
	'../src/main/resources/META-INF/resources/js/data/organizations',
	() => ({
		getOrganization: (id) => {
			return Promise.resolve({
				childOrganizations: [
					{
						id: '9099',
						name: 'Organization Child 1',
					},
					{
						id: '9100',
						name: `Organization Child 2`,
					},
					{
						id: '9101',
						name: `Organization Child 3`,
					},
				],
				id,
				organizationAccounts: [
					{
						id: '1000',
						name: `Account Child 1`,
					},
				],
				type: 'organization',
				userAccounts: [
					{
						accountBriefs: [],
						id: '2000',
						name: `User Child 1`,
						organizationBriefs: [],
					},
					{
						accountBriefs: [],
						id: '2001',
						name: `User Child 2`,
						organizationBriefs: [],
					},
					{
						accountBriefs: [],
						id: '2002',
						name: `User Child 3`,
						organizationBriefs: [],
					},
				],
			});
		},
	})
);

jest.mock('../src/main/resources/META-INF/resources/js/data/accounts', () => ({
	getAccount: () =>
		Promise.resolve({
			type: 'accountUserAccounts',
			userAccounts: [
				{
					accountBriefs: [],
					id: '2000',
					name: `User Child 1`,
					organizationBriefs: [],
				},
				{
					accountBriefs: [],
					id: '2001',
					name: `User Child 2`,
					organizationBriefs: [],
				},
				{
					accountBriefs: [],
					id: '2002',
					name: `User Child 3`,
					organizationBriefs: [],
				},
			],
		}),
}));

const countNodes = (root) => {
	const structure = d3.hierarchy(root, (d) => d.children);

	return structure.descendants().length;
};

const countLinks = (root) => {
	const structure = d3.hierarchy(root, (d) => d.children);

	return structure.links().length;
};

const INITIAL_DATA = {
	[ACCOUNTS_PROPERTY_NAME]: [
		{
			actions,
			id: '1',
			name: `Auto Care`,
		},
		{
			actions,
			id: '0000',
			name: `CC West`,
		},
		{
			actions,
			id: '3',
			name: `Leo Auto`,
		},
		{
			actions,
			id: '5',
			name: `Repair World`,
		},
	],
	[ORGANIZATIONS_PROPERTY_NAME]: [
		{
			actions,
			id: '999',
			name: `Canada`,
		},
		{
			actions,
			id: '2',
			name: `Italy`,
		},
		{
			actions,
			id: '40',
			name: `Spain`,
		},
	],
	[USERS_PROPERTY_NAME_IN_ORGANIZATION]: [
		{
			actions,
			id: '1',
			name: `Mark`,
			[BRIEFS_KEYS_MAP.organization]: [],
			[BRIEFS_KEYS_MAP.account]: [],
		},
		{
			actions,
			id: '2',
			name: `John`,
			[BRIEFS_KEYS_MAP.organization]: [],
			[BRIEFS_KEYS_MAP.account]: [],
		},
	],
	actions,
	id: '0',
	name: `Root`,
};

const getNodes = (wrapper, value, propertyName) => {
	const nodes = Array.from(wrapper.querySelectorAll('.chart-item'));

	return nodes.filter((node) => node.__data__.data[propertyName] === value);
};

const wait = (time = 0) =>
	new Promise((resolve) =>
		setTimeout(() => {
			resolve();
		}, time)
	);

const d3click = async (node) => {
	node.dispatchEvent(
		new MouseEvent('mousedown', {
			view: global.window,
		})
	);

	global.window.dispatchEvent(
		new MouseEvent('mouseup', {
			view: global.window,
		})
	);

	await wait();
};

describe('D3OrganizationChart implementation', () => {
	let chartSVGWrapper;
	let zoomInButton;
	let zoomOutButton;
	let defaultParams;
	const getChartNodes = (...args) => getNodes(chartSVGWrapper, ...args);
	let lastActionPerformed = null;

	beforeEach(() => {
		chartSVGWrapper = document.createElement('svg');
		zoomInButton = document.createElement('button');
		zoomOutButton = document.createElement('button');
		defaultParams = [
			{
				svg: chartSVGWrapper,
				zoomIn: zoomInButton,
				zoomOut: zoomOutButton,
			},
			'./assets/clay/icons.svg',
			{
				open: (parentData, type) => {
					lastActionPerformed = {
						details: {parentData, type},
						name: 'modal opened',
					};
				},
			},
			{
				close: () => {
					lastActionPerformed = {
						name: 'menu closed',
					};
				},
				open: (target, data) => {
					lastActionPerformed = {
						details: {data, target},
						name: 'menu opened',
					};
				},
			},
		];
	});

	afterEach(() => {
		lastActionPerformed = null;
	});

	it('Must create a chart', async () => {
		new D3OrganizationChart(INITIAL_DATA, ...defaultParams);

		const chartItemsWrapper = chartSVGWrapper.querySelector(
			'.chart-data-wrapper'
		);
		expect(chartItemsWrapper).toBeDefined();
	});

	it('Must display the right number of nodes', () => {
		new D3OrganizationChart(INITIAL_DATA, ...defaultParams);

		const chartOrganizations = chartSVGWrapper.querySelectorAll(
			'.chart-item-organization'
		);

		expect(chartOrganizations.length).toBe(
			INITIAL_DATA[ORGANIZATIONS_PROPERTY_NAME].length + 1
		);

		const chartAccounts = chartSVGWrapper.querySelectorAll(
			'.chart-item-account'
		);

		expect(chartAccounts.length).toBe(
			INITIAL_DATA[ACCOUNTS_PROPERTY_NAME].length
		);

		const chartUsers = chartSVGWrapper.querySelectorAll('.chart-item-user');

		expect(chartUsers.length).toBe(
			INITIAL_DATA[USERS_PROPERTY_NAME_IN_ORGANIZATION].length
		);

		const chartItems = chartSVGWrapper.querySelectorAll('.chart-item');

		expect(chartItems.length).toBe(countNodes(INITIAL_DATA));
	});

	it('Must display the right number of links', () => {
		new D3OrganizationChart(INITIAL_DATA, ...defaultParams);

		const chartLinks = chartSVGWrapper.querySelectorAll('.chart-link');

		expect(chartLinks.length).toBe(countLinks(INITIAL_DATA));
	});

	describe('Node selection', () => {
		it('Must select the clicked entity', async () => {
			new D3OrganizationChart(INITIAL_DATA, ...defaultParams);

			const rootHTMLNode = getChartNodes('Root', 'name')[0];

			expect(rootHTMLNode.classList.contains('selected')).toBe(false);

			await d3click(rootHTMLNode);

			expect(rootHTMLNode.classList.contains('selected')).toBe(true);
		});

		it('Must display the selected node children', async () => {
			new D3OrganizationChart(INITIAL_DATA, ...defaultParams);

			const parentNode = getChartNodes('Canada', 'name')[0];

			await d3click(parentNode);

			expect(parentNode.classList.contains('selected')).toBe(true);

			[
				'Organization Child 1',
				'Organization Child 2',
				'Organization Child 3',
				'Account Child 1',
				'User Child 1',
				'User Child 2',
				'User Child 3',
			].forEach((name) => {
				const node = getChartNodes(name, 'name')[0];

				expect(node).toBeTruthy();
				expect(node.__data__.parent.data.name).toBe('Canada');
			});
		});
	});

	describe('Node creation', () => {
		let addButton = null;

		beforeAll(async () => {
			new D3OrganizationChart(INITIAL_DATA, ...defaultParams);
			const clickedNode = getChartNodes('Root', 'name')[0];

			await d3click(clickedNode);

			addButton = getChartNodes('add', 'type')[0];
		});

		it('Must display an add button when an organization is clicked', () => {
			expect(addButton).toBeTruthy();
			expect(addButton.__data__.parent.data.name).toBe('Root');
		});

		it('Add button must change its state when clicked', async () => {
			expect(addButton).toBeTruthy();

			const actionsWrapper = addButton.querySelector('.actions-wrapper');
			const openActionsWrapper = addButton.querySelector(
				'.open-actions-wrapper'
			);

			await d3click(openActionsWrapper);

			expect(actionsWrapper.classList).toContain('menu-open');
		});

		it('Must open a modal when a creation button is clicked', async () => {
			const openActionsWrapper = addButton.querySelector(
				'.open-actions-wrapper'
			);

			await d3click(openActionsWrapper);

			const createOrganizationButton = addButton.querySelector(
				'.add-action-wrapper.organization'
			);
			expect(createOrganizationButton).toBeTruthy();

			await d3click(createOrganizationButton);

			expect(lastActionPerformed.name).toBe('modal opened');
		});

		it('Must open a create organization modal when a creation button is clicked', async () => {
			const openActionsWrapper = addButton.querySelector(
				'.open-actions-wrapper'
			);

			await d3click(openActionsWrapper);

			const createOrganizationButton = addButton.querySelector(
				'.add-action-wrapper.organization'
			);
			expect(createOrganizationButton).toBeTruthy();

			await d3click(createOrganizationButton);

			expect(lastActionPerformed.name).toBe('modal opened');
			expect(lastActionPerformed.details.type).toBe('organization');
		});

		it('Must open a create account modal when a creation button is clicked', async () => {
			const openActionsWrapper = addButton.querySelector(
				'.open-actions-wrapper'
			);

			await d3click(openActionsWrapper);

			const createAccountButton = addButton.querySelector(
				'.add-action-wrapper.account'
			);
			expect(createAccountButton).toBeTruthy();

			await d3click(createAccountButton);

			expect(lastActionPerformed.name).toBe('modal opened');
			expect(lastActionPerformed.details.type).toBe('account');
		});

		it('Must open a invite user modal when a creation button is clicked', async () => {
			const openActionsWrapper = addButton.querySelector(
				'.open-actions-wrapper'
			);

			await d3click(openActionsWrapper);

			const inviteUserButton = addButton.querySelector(
				'.add-action-wrapper.user'
			);

			await d3click(inviteUserButton);

			expect(lastActionPerformed.name).toBe('modal opened');
			expect(lastActionPerformed.details.type).toBe('user');
		});
	});

	describe('Node actions', () => {
		it('must open an action menu when a menu button is clicked', async () => {
			new D3OrganizationChart(INITIAL_DATA, ...defaultParams);

			const node = getChartNodes('Italy', 'name')[0];
			const menuButton = node.querySelector('.node-menu-wrapper');

			expect(menuButton).toBeTruthy();

			await d3click(menuButton);

			expect(lastActionPerformed.name).toBe('menu opened');
			expect(lastActionPerformed.details.data.name).toBe('Italy');
		});
	});
});
