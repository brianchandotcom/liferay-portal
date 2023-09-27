/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

import {openItemSelector} from '../../../src/main/resources/META-INF/resources/page_editor/common/openItemSelector';

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

	it('uses selection.returnType as type', () => {
		const callback = jest.fn();
		const {onSelect} = openModal({callback});

		onSelect({returnType: 'custom', value: 'value'});

		expect(callback).toHaveBeenCalledWith({
			returnType: 'custom',
			value: 'value',
		});
	});

	it('injects selection.value into infoItem', () => {
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

	it('extracts first item if selection does not have value', () => {
		const callback = jest.fn();
		const {onSelect} = openModal({callback});

		onSelect({'item-1': {name: 'Item 1'}});

		expect(callback).toHaveBeenCalledWith({name: 'Item 1'});
	});
});
