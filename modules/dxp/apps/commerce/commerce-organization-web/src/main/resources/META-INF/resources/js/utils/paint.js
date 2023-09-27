/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {event as d3event, linkHorizontal, select as d3select} from 'd3';

import {
	ACTION_KEYS,
	COUNTER_KEYS_MAP,
	NODE_BUTTON_WIDTH,
	NODE_PADDING,
	RECT_SIZES,
	SYMBOLS_MAP,
} from './constants';
import {formatItemName, formatUserDescription, hasPermissions} from './index';

export function appendIcon(node, symbol, size, className) {
	return node
		.append('use')
		.attr('class', className)
		.attr('width', size)
		.attr('height', size)
		.attr('href', symbol);
}

export const getLinkDiagonal = linkHorizontal()
	.source(({source}, origin, state) => {
		let sourceRectWidth = origin.data.type
			? RECT_SIZES[origin.data.type].width
			: 0;

		switch (state) {
			case 'enter':
				return [sourceRectWidth + origin.y0, origin.x0];
			case 'exit':
				return [sourceRectWidth + origin.y, origin.x];
			default:
				sourceRectWidth = source.data.type
					? RECT_SIZES[source.data.type].width
					: 0;

				return [sourceRectWidth + source.y, source.x];
		}
	})
	.target(({target}, origin, state) => {
		const sourceRectWidth = origin.data.type
			? RECT_SIZES[origin.data.type].width
			: 0;

		switch (state) {
			case 'enter':
				return [sourceRectWidth + origin.y0, origin.x0];
			case 'exit':
				return [sourceRectWidth + origin.y, origin.x];
			default:
				return [target.y, target.x];
		}
	});

export function appendCircle(node, size, className) {
	return node.append('circle').attr('r', size).attr('class', className);
}

export function createAddActionButton(wrapper, type, openModal, spritemap) {
	const newButtonContainer = wrapper
		.append('g')
		.attr('class', `add-action-wrapper ${type}`)
		.on('mousedown', (node) => {
			openModal(node.parent.data, type);
		});

	appendCircle(newButtonContainer, 16, 'action-circle');
	appendIcon(
		newButtonContainer,
		`${spritemap}#${SYMBOLS_MAP[type]}`,
		16,
		'action-icon'
	);
}

export function fillAddButtons(nodeEnter, spritemap, openModal) {
	const actionsWrapper = nodeEnter
		.append('g')
		.attr('class', 'actions-wrapper');

	const openActionsWrapper = actionsWrapper
		.append('g')
		.attr('class', 'open-actions-wrapper')
		.on('mousedown', (node) => {
			if (node.parent.data.type === 'account') {
				openModal(node.parent.data, 'user');
			}
			else {
				actionsWrapper.node().classList.toggle('menu-open');
			}
		});

	appendCircle(openActionsWrapper, 36, 'action-circle');
	appendIcon(openActionsWrapper, `${spritemap}#plus`, 18, 'action-icon');

	createAddActionButton(actionsWrapper, 'account', openModal, spritemap);
	createAddActionButton(actionsWrapper, 'organization', openModal, spritemap);
	createAddActionButton(actionsWrapper, 'user', openModal, spritemap);
}

export function printDescription(d, element, spritemap) {
	const descritionWrapper = d3select(element)
		.append('g')
		.attr('class', 'node-description');

	if (d.data.type === 'user') {
		descritionWrapper
			.append('text')
			.attr('class', 'node-description-content')
			.text(formatUserDescription);

		return;
	}

	const entities =
		d.data.type === 'organization'
			? ['organization', 'account', 'user']
			: ['user'];

	entities.reduce((x, nodeType) => {
		const entityWrapper = descritionWrapper
			.append('g')
			.attr('class', `${nodeType}-wrapper`)
			.attr('transform', `translate(${x}, 5)`);

		appendIcon(
			entityWrapper,
			`${spritemap}#${SYMBOLS_MAP[nodeType]}`,
			12,
			'entity-icon'
		);

		const entityText = entityWrapper
			.append('text')
			.attr('class', 'entity-description')
			.attr('x', 18)
			.attr('y', 10)
			.text(d.data[COUNTER_KEYS_MAP[nodeType]]);

		if (nodeType !== 'organization' && d.data.type !== 'account') {
			entityWrapper
				.append('line')
				.attr('class', 'entity-divider')
				.attr('x1', -7)
				.attr('x2', -7)
				.attr('y1', -2)
				.attr('y2', 12);
		}

		const textNode = entityText.node();
		let textWidth = 0;

		/*
		 * getBBox method is not supported in JSDom tests.
		 * The following condition is mandatory to make tests work.
		 */

		if (textNode.getBBox) {
			textWidth = textNode.getBBox().width;
		}

		return x + textWidth + 32;
	}, 64);
}

export function fillEntityNode(nodeEnter, spritemap, openMenu) {
	nodeEnter
		.append('rect')
		.attr('width', (d) => RECT_SIZES[d.data.type].width)
		.attr('height', (d) => RECT_SIZES[d.data.type].height)
		.attr(
			'transform',
			(d) => `translate(0, ${RECT_SIZES[d.data.type].height * -0.5})`
		)
		.attr('rx', (d) => RECT_SIZES[d.data.type].height / 2)
		.attr('class', 'chart-rect');

	const iconWrapper = nodeEnter.append('g').attr('class', 'icon-wrapper');

	iconWrapper.append('circle').attr('class', 'icon-circle');

	appendIcon(
		iconWrapper,
		(d) => `${spritemap}#${SYMBOLS_MAP[d.data.type]}`,
		16,
		'node-type-icon'
	);

	const infos = nodeEnter.append('g').attr('class', 'chart-item-info');

	infos.append('text').attr('class', 'node-title').text(formatItemName);

	infos.each((d, index, nodes) =>
		printDescription(d, nodes[index], spritemap)
	);

	const nodesWithMenu = nodeEnter.filter((chartItem) => {
		if (!chartItem.parent || chartItem.parent.data.type === 'fakeRoot') {
			return false;
		}

		return hasPermissions(chartItem.data, [
			ACTION_KEYS[chartItem.data.type].REMOVE,
			ACTION_KEYS[chartItem.data.type].DELETE,
		]);
	});

	const menuWrapper = nodesWithMenu
		.append('g')
		.attr('class', 'node-menu-wrapper')
		.attr('transform', (d) => {
			const x =
				RECT_SIZES[d.data.type].width -
				NODE_BUTTON_WIDTH -
				NODE_PADDING;

			return `translate(${x}, -14)`;
		})
		.on('mousedown', (d) => {
			d3event.stopPropagation();

			openMenu(d3event.currentTarget, d.data, d.parent?.data);
		});

	menuWrapper.append('rect').attr('class', 'node-menu-btn');

	appendIcon(menuWrapper, `${spritemap}#ellipsis-v`, 16, 'node-menu-icon');
}
