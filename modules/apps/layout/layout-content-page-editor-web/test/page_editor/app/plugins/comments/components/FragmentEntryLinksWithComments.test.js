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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render, wait} from '@testing-library/react';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {
	useHoverItem,
	useSelectItem,
} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import FragmentEntryLinksWithComments from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/comments/components/FragmentEntryLinksWithComments';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/comments/components/NoCommentsMessage',
	() => () => <h1>no-comments-message</h1>
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext',
	() => {
		const hoverItem = jest.fn();
		const selectItem = jest.fn();

		return {
			useHoverItem: () => hoverItem,
			useSelectItem: () => selectItem,
		};
	}
);

const NO_COMMENTS_STATE = {
	layoutData: {items: {}},
};

const COMMENTS_STATE = {
	fragmentEntryLinks: {
		'sandro-fragment': {
			comments: [{resolved: false}],
			fragmentEntryLinkId: 'sandro-fragment',
			name: 'Sandro Fragment',
		},

		'veronica-fragment': {
			comments: [{resolved: false}],
			fragmentEntryLinkId: 'veronica-fragment',
			name: 'Verónica Fragment',
		},

		'victor-fragment': {
			comments: [{resolved: true}],
			fragmentEntryLinkId: 'victor-fragment',
			name: 'Víctor Fragment',
		},
	},

	layoutData: {
		items: {
			'sandro-item': {
				config: {fragmentEntryLinkId: 'sandro-fragment'},
				itemId: 'sandro-item',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},

			'veronica-item': {
				config: {fragmentEntryLinkId: 'veronica-fragment'},
				itemId: 'veronica-item',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},

			'victor-item': {
				config: {fragmentEntryLinkId: 'victor-fragment'},
				itemId: 'victor-item',
				type: LAYOUT_DATA_ITEM_TYPES.fragment,
			},
		},
	},
};

const renderComponent = (state, dispatch) =>
	render(
		<StoreAPIContextProvider dispatch={dispatch} getState={() => state}>
			<FragmentEntryLinksWithComments />
		</StoreAPIContextProvider>
	);

describe('FragmentEntryLinksWithComments', () => {
	afterEach(cleanup);

	it('shows a NoCommentsMessage if there are no comments', () => {
		const {getByText} = renderComponent(NO_COMMENTS_STATE);
		expect(getByText('no-comments-message')).toBeInTheDocument();
	});

	it('shows a list of comments', () => {
		const {getByText, queryByText} = renderComponent(COMMENTS_STATE);

		expect(getByText('Sandro Fragment')).toBeInTheDocument();
		expect(getByText('Verónica Fragment')).toBeInTheDocument();
		expect(queryByText('Víctor Fragment')).not.toBeInTheDocument();
	});

	it('includes resolved comments if showResolvedComments is true', () => {
		const {getByText} = renderComponent({
			...COMMENTS_STATE,
			showResolvedComments: true,
		});

		expect(getByText('Sandro Fragment')).toBeInTheDocument();
		expect(getByText('Verónica Fragment')).toBeInTheDocument();
		expect(getByText('Víctor Fragment')).toBeInTheDocument();
	});

	it('sets a fragment to hovered on focus', async () => {
		const hoverItem = useHoverItem();

		const {getAllByRole} = renderComponent({
			...COMMENTS_STATE,
			showResolvedComments: true,
		});

		const [sandroFragment] = getAllByRole('link');

		sandroFragment.focus();

		await wait(() => {
			expect(sandroFragment).toHaveTextContent('Sandro Fragment');
			expect(sandroFragment).toHaveFocus();
			expect(hoverItem).toHaveBeenCalledWith('sandro-item');
		});
	});

	it('sets a fragment to hovered on mouseover', async () => {
		const hoverItem = useHoverItem();

		const {getAllByRole} = renderComponent({
			...COMMENTS_STATE,
			showResolvedComments: true,
		});

		const [sandroFragment] = getAllByRole('link');

		fireEvent.mouseOver(sandroFragment);

		await wait(() => {
			expect(sandroFragment).toHaveTextContent('Sandro Fragment');
			expect(hoverItem).toHaveBeenCalledWith('sandro-item');
		});
	});

	it('sets a fragment to not hovered on mouseout', async () => {
		const hoverItem = useHoverItem();

		const {getAllByRole} = renderComponent({
			...COMMENTS_STATE,
			showResolvedComments: true,
		});

		const [sandroFragment] = getAllByRole('link');

		fireEvent.mouseOut(sandroFragment);

		await wait(() => {
			expect(sandroFragment).toHaveTextContent('Sandro Fragment');
			expect(hoverItem).toHaveBeenCalledWith(null);
		});
	});

	it('sets a fragment to selected on click', async () => {
		const selectItem = useSelectItem();

		const {getAllByRole} = renderComponent({
			...COMMENTS_STATE,
			showResolvedComments: true,
		});

		const [sandroFragment] = getAllByRole('link');

		fireEvent.click(sandroFragment);

		await wait(() => {
			expect(sandroFragment).toHaveTextContent('Sandro Fragment');
			expect(selectItem).toHaveBeenCalledWith('sandro-item');
		});
	});

	test.todo('shows the number of comments on each list item');
});
