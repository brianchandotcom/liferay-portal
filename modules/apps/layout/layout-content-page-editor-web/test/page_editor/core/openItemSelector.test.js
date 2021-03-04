/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {openSelectionModal} from 'frontend-js-web';

import {openItemSelector} from '../../../src/main/resources/META-INF/resources/page_editor/core/openItemSelector';

jest.mock('frontend-js-web');

const openModal = ({
	callback = () => {},
	destroyedCallback = null,
	transformValueCallback = (item) => item,
}) => {
	openItemSelector({
		callback,
		destroyedCallback,
		eventName: '',
		itemSelectorURL: '',
		transformValueCallback,
	});

	const [firstCall = []] = openSelectionModal.mock.calls;
	const [firstArgument = {}] = firstCall;

	return firstArgument;
};

describe('openItemSelector', () => {
	afterEach(() => {
		openSelectionModal.mockReset();
	});

	it('calls destroyCallback on modal close event', () => {
		const destroyedCallback = jest.fn();

		openModal({destroyedCallback});

		expect(openSelectionModal).toHaveBeenCalledWith(
			expect.objectContaining({onClose: destroyedCallback})
		);
	});

	it('uses selectedItem.returnType as type', () => {
		const callback = jest.fn();
		const {onSelect} = openModal({callback});

		onSelect({returnType: 'custom'});

		expect(callback).toHaveBeenCalledWith({
			returnType: 'custom',
		});
	});

	it('injects selectedItem.value into infoItem', () => {
		const callback = jest.fn();
		const {onSelect} = openModal({callback});

		onSelect({
			returnType: 'custom',
			value: {some: {object: 'yep'}},
		});

		expect(callback).toHaveBeenCalledWith({
			returnType: 'custom',
			some: {object: 'yep'},
		});
	});

	it('tries to parse value as object', () => {
		const callback = jest.fn();
		const {onSelect} = openModal({callback});

		onSelect({
			returnType: 'custom',
			value: JSON.stringify({some: {object: 'yep'}}),
		});

		expect(callback).toHaveBeenCalledWith({
			returnType: 'custom',
			some: {object: 'yep'},
		});
	});

	it('keeps raw value if it cannot be parsed', () => {
		const callback = jest.fn();
		const {onSelect} = openModal({callback});

		onSelect({
			returnType: 'custom',
			value: 'notAnObject',
		});

		expect(callback).toHaveBeenCalledWith({
			returnType: 'custom',
			value: 'notAnObject',
		});
	});
});
