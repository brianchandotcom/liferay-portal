/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {hierarchy, tree as d3Tree} from 'd3';

import {changeOrganizationParent} from '../data/accounts';
import {updateOrganization} from '../data/organizations';
import {
	ACCOUNTS_PROPERTY_NAME,
	ACTION_KEYS,
	BRIEFS_KEYS_MAP,
	COUNTER_KEYS_MAP,
	DX,
	DY,
	MAX_NAME_LENGTH,
	ORGANIZATIONS_PROPERTY_NAME,
	RECT_SIZES,
	USERS_PROPERTY_NAME_IN_ACCOUNT,
	USERS_PROPERTY_NAME_IN_ORGANIZATION,
} from './constants';
import {PERMISSION_CHECK_ON_HEADLESS_API_ACTIONS} from './flags';

let chartNodesCounter = 0;

export function formatItem(item, type) {
	item.type = type;
	item.children = [];
	item.chartNodeNumber = ++chartNodesCounter;
	item.chartNodeId = getChartNodeId(item);

	const definitionsMap = [
		[item[ORGANIZATIONS_PROPERTY_NAME], formatOrganizationChild],
		[item[ACCOUNTS_PROPERTY_NAME], formatAccountChild],
		[
			item[USERS_PROPERTY_NAME_IN_ORGANIZATION] ||
				item[USERS_PROPERTY_NAME_IN_ACCOUNT],
			formatUserChild,
		],
	];

	definitionsMap.forEach(([value, formatter]) => {
		if (value) {
			item.children.push(...value.map(formatter));
		}
	});

	return item;
}

export function formatAccountChild(child) {
	return formatChild(child, 'account');
}
export function formatOrganizationChild(child) {
	return formatChild(child, 'organization');
}
export function formatUserChild(child) {
	return formatChild(child, 'user');
}

export function formatChild(child, entityType = null) {
	if (entityType) {
		child.type = entityType;
	}
	child.chartNodeNumber = ++chartNodesCounter;
	child.chartNodeId = getChartNodeId(child);

	return child;
}

export function showChildren(d) {
	if (d.children && !d._children) {
		return;
	}
	d.children = d._children;
	d.data.children = d.data._children;
	d._children = null;
	d.data._children = null;
}

export function hideChildren(d) {
	if (d._children && !d.children) {
		return;
	}
	d._children = d.children;
	d.data._children = d.data.children;
	d.children = null;
	d.data.children = null;
}

export function insertChildrenIntoNode(children, parentNode) {
	if (!children.length) {
		return;
	}

	const hiddenChildren = !!parentNode._children;

	if (hiddenChildren) {
		showChildren(parentNode);
	}

	if (!parentNode.children) {
		parentNode.children = [];
		parentNode.data.children = [];
	}

	children.forEach((child) => {
		const newNode = hierarchy(child, (node) => node.children);

		newNode.parent = parentNode;

		newNode.each((node) => {
			node.depth = node.depth + parentNode.depth + 1;
		});

		parentNode.children.push(newNode);
		parentNode.data.children.push(newNode.data);

		return newNode;
	});

	if (hiddenChildren) {
		hideChildren(parentNode);
	}

	return parentNode;
}

function removeAddButton(node) {
	const children = node.children || node._children;
	const dataChildren = node.data.children || node.data._children;

	if (!children) {
		return;
	}

	if (children[0]?.data?.type === 'add') {
		children.shift();
		dataChildren.shift();
	}

	if (Array.isArray(node.children) && !node.children.length) {
		node.children = null;
		node.data.children = null;
	}
	if (Array.isArray(node._children) && !node._children.length) {
		node._children = null;
		node.data._children = null;
	}
}

export function insertAddButtons(root, selectedNodesIds) {
	if (!selectedNodesIds.size) {
		return;
	}

	root.each((d) => {
		if (
			selectedNodesIds.has(d.data.chartNodeId) &&
			d.data.type !== 'user' &&
			hasPermission(d.data, ACTION_KEYS[d.data.type].ADD_ENTITIES)
		) {
			showChildren(d);

			if (!d.children) {
				d.children = [];
				d.data.children = [];
			}

			if (d.children.length && d.children[0].data.type === 'add') {
				return;
			}

			const id = Math.random();

			const newNode = hierarchy(
				{
					chartNodeId: `add_${id}`,
					chartNodeNumber: ++chartNodesCounter,
					id,
					type: 'add',
				},
				(node) => node.children
			);

			newNode.parent = d;
			newNode.depth = d.depth + 1;

			d.children.unshift(newNode);
			d.data.children.unshift(newNode.data);
		}
		else {
			removeAddButton(d);
		}
	});
}

export const tree = d3Tree().nodeSize([DX, DY]);

export function getChartNodeId(data) {
	if (!(data.id || data.id === 0) || !data.type) {
		throw new Error(
			`type or id properties not defined in entity: ${JSON.stringify(
				data
			)}`
		);
	}

	return `${data.type}_${data.id}`;
}

export function formatRootData(rootData) {
	if (Array.isArray(rootData)) {
		const fakeRoot = {
			[ORGANIZATIONS_PROPERTY_NAME]: rootData,
			id: 0,
		};

		formatItem(fakeRoot, 'fakeRoot');
		fakeRoot.fetched = true;

		return fakeRoot;
	}

	formatItem(rootData, 'organization');
	rootData.fetched = true;

	return rootData;
}

export function formatAccountDescription(d) {
	return `${d.data[COUNTER_KEYS_MAP.user]} ${Liferay.Language.get('users')}`;
}

export function hasPermission(data, actionKey) {
	if (!PERMISSION_CHECK_ON_HEADLESS_API_ACTIONS) {
		return true;
	}

	return Boolean(
		data.actions &&
			data.actions[actionKey] &&
			data.actions[actionKey].href &&
			data.actions[actionKey].method
	);
}

export function hasPermissions(data, actionsKeys) {
	return actionsKeys.reduce(
		(result, key) => result && hasPermission(data, key),
		true
	);
}

export function formatUserDescription(d) {
	const parentBriefsKey = BRIEFS_KEYS_MAP[d.parent.data.type];

	const parentBrief = d.data[parentBriefsKey].find(
		(parent) => Number(parent.id) === Number(d.parent.data.id)
	);

	let description = Liferay.Language.get('guest');

	if (parentBrief?.roleBriefs?.length) {
		description = trimString(parentBrief.roleBriefs[0].name, 'user');
	}

	if (parentBrief?.roleBriefs?.length > 1) {
		description += ` (+${parentBrief.roleBriefs.length - 1})`;
	}

	return description;
}

export function trimString(string, nodeType) {
	return string.length > MAX_NAME_LENGTH[nodeType]
		? string.slice(0, MAX_NAME_LENGTH[nodeType] - 1).trim() + '…'
		: string;
}

export function formatItemName(d) {
	const name = d.data.name || d.data.emailAddress;

	return trimString(name, d.data.type);
}

export function getMinWidth(nodes) {
	return nodes.reduce((maxWidth, node) => {
		const width = RECT_SIZES[node.data.type].width;

		return maxWidth > width ? maxWidth : width;
	}, 0);
}

export function changeNodesParentOrganization(nodes, target) {
	const movings = [];

	nodes.forEach((node) => {
		switch (node.data.type) {
			case 'organization':
				movings.push(
					updateOrganization(node.data.id, {
						parentOrganization: {id: Number(target.data.id)},
					})
				);
				break;
			case 'account':
				movings.push(
					changeOrganizationParent(
						node.data.id,
						node.parent.data.id,
						target.data.id
					)
				);
				break;
			default:
				throw new Error(`Node type '${node.data.type}' not draggable`);
		}
	});

	return Promise.allSettled(movings).then((results) => {
		const formatted = results.reduce(
			(formattedNodes, result, i) => ({
				...formattedNodes,
				[result.status]: [...formattedNodes[result.status], nodes[i]],
			}),
			{
				fulfilled: [],
				rejected: [],
			}
		);

		return formatted;
	});
}
