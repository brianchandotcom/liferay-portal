/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import KeyboardMovementManager from '../../../../src/main/resources/META-INF/resources/js/components/keyboard_movement/KeyboardMovementManager';
import {
	useDisableKeyboardMovement,
	useSetMovementTarget,
} from '../../../../src/main/resources/META-INF/resources/js/contexts/KeyboardMovementContext';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/contexts/KeyboardMovementContext',
	() => {
		const initialTarget = {
			groupId: 'group_1',
			index: 0,
			position: 'middle',
		};

		const source = {
			defaultValue: '',
			icon: 'categories',
			label: 'Category',
			propertyKey: 'user',
			propertyName: 'assetCategoryIds',
			type: 'id',
		};

		const disableMovement = jest.fn();
		const setTarget = jest.fn();
		const sendMessage = jest.fn();

		const POSITIONS = {
			bottom: 'bottom',
			middle: 'middle',
			top: 'top',
		};

		return {
			POSITIONS,
			useDisableKeyboardMovement: () => disableMovement,
			useMovementSource: () => source,
			useMovementTarget: () => initialTarget,
			useSendMovementMessage: () => sendMessage,
			useSetMovementTarget: () => setTarget,
		};
	}
);

const contributors = [
	{
		criteriaMap: {
			conjunctionName: 'and',
			groupId: 'group_1',
			items: [
				{
					operatorName: 'eq',
					propertyName: 'assetCategoryIds',
					type: 'id',
					value: '',
				},
			],
		},
		propertyKey: 'user',
	},
];

const onMove = jest.fn();

const renderComponent = () =>
	render(
		<KeyboardMovementManager contributors={contributors} onMove={onMove} />
	);

describe('KeyboardMovementManager', () => {
	it('calculates previous drop position when pressing up arrow', () => {
		renderComponent();

		const setTarget = useSetMovementTarget();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				key: 'ArrowUp',
			})
		);

		expect(setTarget).toBeCalledWith(
			expect.objectContaining({
				groupId: 'group_1',
				index: 0,
				position: 'top',
			})
		);
	});

	it('calculates next drop position when pressing up down', () => {
		renderComponent();

		const setTarget = useSetMovementTarget();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				key: 'ArrowDown',
			})
		);

		expect(setTarget).toBeCalledWith(
			expect.objectContaining({
				groupId: 'group_1',
				index: 0,
				position: 'bottom',
			})
		);
	});

	it('disables movement when pressing escape', () => {
		renderComponent();

		const disableMovement = useDisableKeyboardMovement();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				key: 'Escape',
			})
		);

		expect(disableMovement).toBeCalled();
	});

	it('calls onMove when pressing enter', () => {
		renderComponent();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				key: 'Enter',
			})
		);

		const createdGroup = {
			conjunctionName: 'and',
			groupId: 'group_1',
			items: [
				{
					operatorName: 'eq',
					propertyName: 'assetCategoryIds',
					type: 'id',
					value: '',
				},
				{
					operatorName: 'eq',
					propertyName: 'assetCategoryIds',
					value: '',
				},
			],
		};

		expect(onMove).toBeCalledWith(createdGroup, 'user');
	});
});
