/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DROP_POSITIONS} from '../../../../src/main/resources/META-INF/resources/js/miller_columns/constants/dropPositions';
import {setMovementText} from '../../../../src/main/resources/META-INF/resources/js/miller_columns/contexts/KeyboardMovementContext';
import {MillerColumnItem} from '../../../../src/main/resources/META-INF/resources/js/miller_columns/types/MillerColumnItem';

const getItem = ({
	active,
	columnIndex,
	hasChild,
	id,
	itemIndex,
	parentId,
	parentIndex,
	parentable,
	title,
	url,
}: MillerColumnItem) => {
	return {
		active,
		columnIndex,
		hasChild,
		id,
		itemIndex,
		parentId,
		parentIndex,
		parentable,
		title,
		url,
	};
};

const getItems = () => {
	const items = new Map();

	for (let i = 0; i <= 2; i++) {
		items.set(
			i.toString(),
			getItem({
				active: false,
				columnIndex: i,
				hasChild: false,
				id: i.toString(),
				itemIndex: i,
				parentId: '',
				parentIndex: 0,
				parentable: false,
				title: `Page ${i}`,
				url: '',
			})
		);
	}

	return items;
};

const items = getItems();

const sources = [items.get('1')];

describe('setMovementText', () => {
	it('explains how to use the keys when activating it', () => {
		const setText = jest.fn();

		const target = {
			columnIndex: 0,
			itemIndex: 0,
			position: DROP_POSITIONS.middle,
		};

		setMovementText({
			isInitialPosition: true,
			items,
			setText,
			sources,
			target,
		});

		expect(setText).toHaveBeenCalledWith(
			'use-arrows-to-move-it-and-press-enter-to-select-the-new-position-press-esc-to-cancel move-page-x inside-page-x'
		);
	});

	it('explains the top position', () => {
		const setText = jest.fn();

		const target = {
			columnIndex: 0,
			itemIndex: 0,
			position: DROP_POSITIONS.top,
		};

		setMovementText({
			items,
			setText,
			sources,
			target,
		});

		expect(setText).toHaveBeenCalledWith(
			'move-page-x at-the-top-of-the-page-x'
		);
	});

	it('explains the bottom position', () => {
		const setText = jest.fn();

		const target = {
			columnIndex: 0,
			itemIndex: 0,
			position: DROP_POSITIONS.bottom,
		};

		setMovementText({
			items,
			setText,
			sources,
			target,
		});

		expect(setText).toHaveBeenCalledWith(
			'move-page-x at-the-bottom-of-the-page-x'
		);
	});

	it('explains the final position', () => {
		const setText = jest.fn();

		const target = {
			columnIndex: 0,
			itemIndex: 0,
			position: DROP_POSITIONS.middle,
		};

		setMovementText({
			isFinalPosition: true,
			items,
			setText,
			sources,
			target,
		});

		expect(setText).toHaveBeenCalledWith('page-x-placed inside-page-x');
	});
});
