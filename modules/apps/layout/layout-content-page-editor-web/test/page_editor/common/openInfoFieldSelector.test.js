/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-components-web';

import {config} from '../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {openInfoFieldSelector} from '../../../src/main/resources/META-INF/resources/page_editor/common/openInfoFieldSelector';

jest.mock('frontend-js-components-web');

const openModal = ({onSave = () => {}, selectedFields}) => {
	openInfoFieldSelector({
		formItemId: 'form',
		itemType: 'className',
		onCancel: () => {},
		onSave,
		segmentsExperienceId: '0',
		selectedFields,
	});

	const [firstCall = []] = openSelectionModal.mock.calls;
	const [firstArgument = {}] = firstCall;

	return firstArgument;
};

const toItem = (uniqueId, checked) => ({
	checked,
	value: JSON.stringify({label: uniqueId, uniqueId}),
});

describe('openInfoFieldSelector', () => {
	beforeEach(() => {
		config.infoFieldItemSelectorURL = 'http://example.com';

		global.Liferay = {
			...global.Liferay,
			PortletKeys: {
				ITEM_SELECTOR: '',
			},
		};
	});

	afterEach(() => {
		openSelectionModal.mockReset();
	});

	it('requests every item rendered in the modal', () => {
		openModal({});

		expect(openSelectionModal).toHaveBeenCalledWith(
			expect.objectContaining({getSelectedItemsOnly: false})
		);
	});

	it('keeps selected fields that are not rendered in the modal', () => {
		const onSave = jest.fn();

		const {onSelect} = openModal({
			onSave,
			selectedFields: ['field1', 'field3'],
		});

		onSelect([toItem('field1', true), toItem('field2', true)]);

		expect(onSave).toHaveBeenCalledWith([
			{label: 'field1', uniqueId: 'field1'},
			{label: 'field2', uniqueId: 'field2'},
			{uniqueId: 'field3'},
		]);
	});

	it('removes selected fields that are unchecked in the modal', () => {
		const onSave = jest.fn();

		const {onSelect} = openModal({
			onSave,
			selectedFields: ['field1', 'field2'],
		});

		onSelect([toItem('field1', true), toItem('field2', false)]);

		expect(onSave).toHaveBeenCalledWith([
			{label: 'field1', uniqueId: 'field1'},
		]);
	});

	it('uses the value as unique id for items without payload', () => {
		const onSave = jest.fn();

		const {onSelect} = openModal({onSave, selectedFields: []});

		onSelect([{checked: true, value: 'field1'}]);

		expect(onSave).toHaveBeenCalledWith([{uniqueId: 'field1'}]);
	});
});
