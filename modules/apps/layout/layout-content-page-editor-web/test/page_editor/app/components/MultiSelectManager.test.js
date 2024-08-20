/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import MultiSelectManager from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/MultiSelectManager';
import {
	useActivateMultiSelect,
	useActiveItemIds,
	useMultiSelectIsActivated,
	useSelectItem,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const activateMultiSelect = jest.fn();
		const selectItem = jest.fn();

		return {
			useActivateMultiSelect: () => activateMultiSelect,
			useActiveItemIds: jest.fn(() => []),
			useMultiSelectIsActivated: jest.fn(() => false),
			useSelectItem: () => selectItem,
		};
	}
);

const renderComponent = () =>
	render(
		<StoreMother.Component>
			<MultiSelectManager />
		</StoreMother.Component>
	);

describe('MultiSelectManager', () => {
	beforeAll(() => {
		Liferay.FeatureFlags['LPD-18221'] = true;
	});

	afterAll(() => {
		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	describe('Simple multiselect', () => {
		it('activates simple multiselect when pressing ctrl + click', () => {
			renderComponent();

			document.body.dispatchEvent(
				new MouseEvent('click', {
					ctrlKey: true,
				})
			);

			const activateMultiSelect = useActivateMultiSelect();

			expect(activateMultiSelect).toBeCalledWith('simple');
		});

		it('activates simple multiselect when pressing ctrl + "Enter"', () => {
			renderComponent();

			document.body.dispatchEvent(
				new KeyboardEvent('keydown', {
					ctrlKey: true,
					key: 'Enter',
				})
			);

			const activateMultiSelect = useActivateMultiSelect();

			expect(activateMultiSelect).toBeCalledWith('simple');
		});

		it('disable simple multiselect when the ctrl key is released', () => {
			useMultiSelectIsActivated.mockImplementation(() => true);

			renderComponent();

			document.body.dispatchEvent(
				new KeyboardEvent('keyup', {
					key: 'Control',
				})
			);

			const activateMultiSelect = useActivateMultiSelect();

			expect(activateMultiSelect).toBeCalledWith(null);

			useMultiSelectIsActivated.mockImplementation(() => false);
		});
	});

	describe('Range multiselect', () => {
		it('activates range multiselect when pressing shift + click', () => {
			renderComponent();

			document.body.dispatchEvent(
				new MouseEvent('click', {
					ctrlKey: false,
					shiftKey: true,
				})
			);

			const activateMultiSelect = useActivateMultiSelect();

			expect(activateMultiSelect).toBeCalledWith('range');
		});

		it('activates range multiselect when pressing shift + "Enter"', () => {
			renderComponent();

			document.body.dispatchEvent(
				new KeyboardEvent('keydown', {
					ctrlKey: false,
					key: 'Enter',
					shiftKey: true,
				})
			);

			const activateMultiSelect = useActivateMultiSelect();

			expect(activateMultiSelect).toBeCalledWith('range');
		});

		it('disable range multiselect when the shift key is released', () => {
			useMultiSelectIsActivated.mockImplementation(() => true);

			renderComponent();

			document.body.dispatchEvent(
				new KeyboardEvent('keyup', {
					key: 'Shift',
				})
			);

			const activateMultiSelect = useActivateMultiSelect();

			expect(activateMultiSelect).toBeCalledWith(null);

			useMultiSelectIsActivated.mockImplementation(() => false);
		});
	});

	it('deselects items when escape is pressed', () => {
		useActiveItemIds.mockImplementation(() => ['item-1', 'item-2']);

		renderComponent();

		document.body.dispatchEvent(
			new KeyboardEvent('keydown', {
				key: 'Escape',
			})
		);

		const selectItem = useSelectItem();

		expect(selectItem).toBeCalledWith(null);
	});
});
