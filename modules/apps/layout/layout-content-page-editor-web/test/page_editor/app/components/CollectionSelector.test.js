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

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {CollectionItemContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/CollectionItemContext';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import CollectionSelector from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/CollectionSelector';
import {openItemSelector} from '../../../../src/main/resources/META-INF/resources/page_editor/core/openItemSelector';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/core/openItemSelector',
	() => ({
		openItemSelector: jest.fn(() => {}),
	})
);

describe('CollectionSelector', () => {
	afterEach(() => {
		cleanup();
		openItemSelector.mockClear();
	});

	it('uses custom item selector URL when present in the collection item context', () => {
		const CUSTOM_COLLECTION_SELECTOR_URL = 'CUSTOM_COLLECTION_SELECTOR_URL';
		const DEFAULT_ITEM_SELECTOR_URL = 'DEFAULT_ITEM_SELECTOR_URL';

		Liferay.Util.sub.mockImplementation((langKey, args) =>
			[langKey, ...args].join('-')
		);

		const {getByLabelText} = render(
			<StoreAPIContextProvider dispatch={() => {}} getState={() => ({})}>
				<CollectionItemContextProvider
					value={{
						customCollectionSelectorURL: CUSTOM_COLLECTION_SELECTOR_URL,
					}}
				>
					<CollectionSelector
						itemSelectorURL={DEFAULT_ITEM_SELECTOR_URL}
						label=""
						onCollectionSelect={() => {}}
					/>
				</CollectionItemContextProvider>
			</StoreAPIContextProvider>
		);

		const button = getByLabelText('select-x');

		userEvent.click(button);

		expect(openItemSelector).toBeCalledWith(
			expect.objectContaining({
				itemSelectorURL: CUSTOM_COLLECTION_SELECTOR_URL,
			})
		);
	});

	it('uses passed item selector URL when not inside a collection item context', () => {
		const DEFAULT_ITEM_SELECTOR_URL = 'DEFAULT_ITEM_SELECTOR_URL';

		Liferay.Util.sub.mockImplementation((langKey, args) =>
			[langKey, ...args].join('-')
		);

		const {getByLabelText} = render(
			<StoreAPIContextProvider dispatch={() => {}} getState={() => ({})}>
				<CollectionSelector
					itemSelectorURL={DEFAULT_ITEM_SELECTOR_URL}
					label=""
					onCollectionSelect={() => {}}
				/>
			</StoreAPIContextProvider>
		);

		const button = getByLabelText('select-x');

		userEvent.click(button);

		expect(openItemSelector).toBeCalledWith(
			expect.objectContaining({
				itemSelectorURL: DEFAULT_ITEM_SELECTOR_URL,
			})
		);
	});
});
