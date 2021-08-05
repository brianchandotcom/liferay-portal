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

import {event as d3event, linkHorizontal} from 'd3';

import {
	ACTION_KEYS,
	NODE_BUTTON_WIDTH,
	NODE_PADDING,
	RECT_SIZES,
	SYMBOLS_MAP,
} from './constants';
import {formatItemDescription, formatItemName, hasPermissions} from './index';

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

	infos
		.append('text')
		.attr('class', 'node-description')
		.text(formatItemDescription);

	const nodesWithMenu = nodeEnter.filter((chartItem) =>
		hasPermissions(chartItem.data, [
			ACTION_KEYS[chartItem.data.type].REMOVE,
			ACTION_KEYS[chartItem.data.type].DELETE,
		])
	);

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
