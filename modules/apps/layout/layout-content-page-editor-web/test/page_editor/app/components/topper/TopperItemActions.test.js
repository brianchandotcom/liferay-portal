/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import TopperItemActions from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/topper/TopperItemActions';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {
	ClipboardContextProvider,
	useSetCopiedItemIds,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext';
import deleteItem from '../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem';
import pasteItem from '../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/pasteItem';

const LAYOUT_DATA = {
	items: {
		itemId: {
			children: [],
			config: {styles: {}},
			itemId: 'itemId',
			parentId: null,
			type: LAYOUT_DATA_ITEM_TYPES.row,
		},
	},
};

const renderTopperItemActions = ({
	isDisabled = false,
	itemId = 'itemId',
	layoutData = LAYOUT_DATA,
} = {}) => {
	const item = layoutData.items[itemId];

	return render(
		<ClipboardContextProvider>
			<TopperItemActions disabled={isDisabled} item={item} />
		</ClipboardContextProvider>
	);
};

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext',
	() => {
		const setCopiedItemIds = jest.fn();

		return {
			...jest.requireActual(
				'../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ClipboardContext'
			),
			useCopiedItemIds: () => ['item-1'],
			useSetCopiedItemIds: () => setCopiedItemIds,
		};
	}
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/deleteItem',
	() => jest.fn()
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/pasteItem',
	() => jest.fn()
);

describe('TopperItemActions', () => {
	it('does not open TopperItemActions if disabled', () => {
		const {baseElement} = renderTopperItemActions({isDisabled: true});

		expect(baseElement.querySelector('.dropdown')).toBeInTheDocument();
		expect(baseElement.querySelector('.dropdown-toggle')).toHaveAttribute(
			'disabled'
		);
	});

	it('opens TopperItemActions if not disabled', () => {
		const {baseElement} = renderTopperItemActions();

		userEvent.click(baseElement.querySelector('.dropdown-toggle'));

		expect(
			baseElement.querySelector('.dropdown-menu.show')
		).toBeInTheDocument();
	});

	it('calls setCopiedItemIds and deleteItem when Cut action is pressed', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const setCopiedItemIds = useSetCopiedItemIds();

		renderTopperItemActions();

		userEvent.click(screen.getByText('cut'));

		expect(deleteItem).toBeCalledWith(
			expect.objectContaining({
				itemIds: ['itemId'],
			})
		);

		expect(setCopiedItemIds).toBeCalledWith(
			expect.objectContaining(['itemId'])
		);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('calls setCopiedItemIds when Copy action is pressed', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		const setCopiedItemIds = useSetCopiedItemIds();

		renderTopperItemActions();

		userEvent.click(screen.getByText('copy'));

		expect(setCopiedItemIds).toBeCalledWith(
			expect.objectContaining(['itemId'])
		);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});

	it('calls pasteItem when Paste action is pressed', () => {
		Liferay.FeatureFlags['LPD-18221'] = true;

		renderTopperItemActions();

		userEvent.click(screen.getByText('paste'));

		expect(pasteItem).toBeCalledWith(
			expect.objectContaining({
				copiedItemIds: ['item-1'],
				parentItemId: 'itemId',
			})
		);

		Liferay.FeatureFlags['LPD-18221'] = false;
	});
});
