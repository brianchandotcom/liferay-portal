/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import StructureTreeNodeActions from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page_structure/components/StructureTreeNodeActions';

const mockOpenRulesModal = jest.fn();

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/RulesModalContext',
	() => ({
		useRulesModal: () => ({
			openRulesModal: mockOpenRulesModal,
		}),
	})
);

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/isAllowedInRules',
	() => ({
		isAllowedInRules: () => true,
	})
);

const CONTAINER_ITEM = {
	children: [],
	config: {styles: {}},
	itemId: 'container',
	type: LAYOUT_DATA_ITEM_TYPES.container,
};

const LAYOUT_DATA = {
	deletedItems: [],
	items: {
		[CONTAINER_ITEM.itemId]: CONTAINER_ITEM,
	},
};

const renderComponent = (treeItem) =>
	render(
		<StoreAPIContextProvider
			getState={() => ({
				fragmentEntryLinks: {},
				layoutData: LAYOUT_DATA,
			})}
		>
			<StructureTreeNodeActions item={treeItem} visible={true} />
		</StoreAPIContextProvider>
	);

describe('StructureTreeNodeActions', () => {
	afterEach(() => {
		mockOpenRulesModal.mockClear();
	});

	it('calls openRulesModal with a readOnly action when clicking add-rule', async () => {
		const TREE_ITEM = {
			...CONTAINER_ITEM,
			id: CONTAINER_ITEM.itemId,
		};

		renderComponent(TREE_ITEM);

		await userEvent.click(screen.getByLabelText('options'));

		const addRuleButton = await screen.findByText('add-rule');

		await userEvent.click(addRuleButton);

		expect(mockOpenRulesModal).toHaveBeenCalledTimes(1);

		expect(mockOpenRulesModal).toHaveBeenCalledWith(
			expect.objectContaining({
				rule: expect.objectContaining({
					actions: [
						expect.objectContaining({
							itemId: CONTAINER_ITEM.itemId,
							readOnly: true,
							type: 'show',
						}),
					],
				}),
			})
		);
	});
});
