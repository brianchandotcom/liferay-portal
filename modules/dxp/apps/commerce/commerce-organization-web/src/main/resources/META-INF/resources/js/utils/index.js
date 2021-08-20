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

import {hierarchy, tree as d3Tree} from 'd3';

import {changeOrganizationParent} from '../data/accounts';
import {updateOrganization} from '../data/organizations';
import {
	ACCOUNTS_PROPERTY_NAME,
	DX,
	DY,
	ID_PROPERTY_NAME_DEFINITIONS,
	MAX_NAME_LENGTH,
	ORGANIZATIONS_PROPERTY_NAME,
	RECT_SIZES,
	USERS_PROPERTY_NAME_IN_ACCOUNT,
	USERS_PROPERTY_NAME_IN_ORGANIZATION,
} from './constants';

let chartNodesCounter = 0;

export function formatItem(item, type) {
	item.type = type;
	item.children = [];
	item.chartNodeNumber = ++chartNodesCounter;
	item.chartNodeId = getChartNodeId(item);

	if (type === 'account') {
		item.children.push(
			...item[USERS_PROPERTY_NAME_IN_ACCOUNT].map(formatUserChild)
		);
	}

	if (type === 'organization') {
		item.children.push(
			...item[ORGANIZATIONS_PROPERTY_NAME].map(formatOrganizationChild),
			...item[ACCOUNTS_PROPERTY_NAME].map(formatAccountChild),
			...item[USERS_PROPERTY_NAME_IN_ORGANIZATION].map(formatUserChild)
		);
	}

	return item;
}

export const formatAccountChild = (child) => formatChild(child, 'account');
export const formatOrganizationChild = (child) =>
	formatChild(child, 'organization');
export const formatUserChild = (child) => formatChild(child, 'user');

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
			d.data.type !== 'account'
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

export const getEntityId = (data) =>
	data[ID_PROPERTY_NAME_DEFINITIONS[data.type]];

export const getChartNodeId = (data) => {
	if (!data.type || !data.id) {
		throw new Error(
			`type or id properties not defined in entity: ${JSON.stringify(
				data
			)}`
		);
	}

	return `${data.type}_${data.id}`;
};

export const formatRootData = (rootData) => {
	formatItem(rootData, 'organization');
	rootData.fetched = true;

	return rootData;
};

export const formatOrganizationDescription = (d) => {
	return `${d.data.numberOfOrganizations} ${Liferay.Language.get('org')} | ${
		d.data.numberOfAccounts
	} ${Liferay.Language.get('acc')} | ${
		d.data.numberOfUsers
	} ${Liferay.Language.get('users')}`;
};

export const formatAccountDescription = (d) => {
	return `${d.data.numberOfUsers} ${Liferay.Language.get('users')}`;
};

export const formatUserDescription = (d) => {
	return d.data.jobTitle || Liferay.Language.get('user');
};

const formatDescriptionMap = {
	account: formatAccountDescription,
	organization: formatOrganizationDescription,
	user: formatUserDescription,
};

export const formatItemName = (d) => {
	const name = d.data.name || `${d.data.firstName} ${d.data.lastName}`;

	if (name.length > MAX_NAME_LENGTH) {
		return name.slice(0, MAX_NAME_LENGTH - 1).trim() + '…';
	}

	return name;
};

export const formatItemDescription = (d) => {
	return formatDescriptionMap[d.data.type](d);
};

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
						parentOrganization: {id: target.data.id},
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
