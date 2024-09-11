/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import deleteItem from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem';
import duplicateItem from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/duplicateItem';
import updateItemStyle from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle';
import PageStructureSidebarToolbar from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page_structure/components/PageStructureSidebarToolbar';
import StoreMother from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem',
	() => jest.fn()
);

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/duplicateItem',
	() => jest.fn()
);

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

const renderComponent = ({
	activeItemIds = [],
	viewportSize = VIEWPORT_SIZES.desktop,
} = {}) =>
	render(
		<StoreMother.Component
			getState={() => ({
				fragmentEntryLinks: {
					fragment01: {editableValues: {}},
					fragment02: {editableValues: {}},
					fragment03: {editableValues: {}},
				},
				layoutData: {
					items: {
						fragment01: {
							children: [],
							config: {
								fragmentEntryLinkId: 'fragment01',
								styles: {display: 'block'},
							},
							itemId: 'fragment01',
							parentId: 'root',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						fragment02: {
							children: [],
							config: {
								fragmentEntryLinkId: 'fragment02',
								styles: {display: 'none'},
							},
							itemId: 'fragment02',
							parentId: 'root',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						fragment03: {
							children: [],
							config: {
								fragmentEntryLinkId: 'fragment03',
								styles: {display: 'block'},
							},
							itemId: 'fragment03',
							parentId: 'root',
							type: LAYOUT_DATA_ITEM_TYPES.fragment,
						},
						root: {
							children: ['fragment01', 'fragment02'],
							itemId: 'root',
							parentId: null,
							type: LAYOUT_DATA_ITEM_TYPES.root,
						},
					},
				},
				selectedViewportSize: viewportSize,
			})}
		>
			<PageStructureSidebarToolbar activeItemIds={activeItemIds} />
		</StoreMother.Component>
	);

describe('PageStructureSidebarToolbar', () => {
	beforeAll(() => {
		Liferay.FeatureFlags['LPD-18221'] = true;
	});

	afterAll(() => {
		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('shows the number of selected items', () => {
		renderComponent({
			activeItemIds: ['fragment01', 'fragment02'],
		});

		expect(screen.getByText('2-items-selected')).toBeInTheDocument();
	});

	it('calls deleteItem when Delete action is pressed', () => {
		renderComponent({
			activeItemIds: ['fragment01', 'fragment02'],
		});

		userEvent.click(screen.getByText('delete'));

		expect(deleteItem).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['fragment01', 'fragment02'],
			})
		);
	});

	it('calls duplicateItem when Duplicate action is pressed', () => {
		renderComponent({
			activeItemIds: ['fragment01', 'fragment03'],
		});

		userEvent.click(screen.getByText('duplicate'));

		expect(duplicateItem).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['fragment01', 'fragment03'],
			})
		);
	});

	it('calls updateItemStyle when Hide Fragments action is pressed', () => {
		renderComponent({
			activeItemIds: ['fragment03', 'fragment02'],
		});

		userEvent.click(screen.getByText('hide-fragments'));

		expect(updateItemStyle).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['fragment03', 'fragment02'],
				styleName: 'display',
				styleValue: 'none',
			})
		);
	});

	it('does not show the button when it is a viewport other than desktop', () => {
		renderComponent({
			activeItemIds: ['fragment01', 'fragment02'],
			viewportSize: VIEWPORT_SIZES.tablet,
		});

		expect(
			screen.queryByLabelText('actions-for-selected-items')
		).not.toBeInTheDocument();
	});

	it('renders "Show Fragments" when the first element is hidden', () => {
		renderComponent({
			activeItemIds: ['fragment02', 'fragment01'],
		});

		expect(screen.getByText('show-fragments')).toBeInTheDocument();
	});
});
