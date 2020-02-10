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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {act} from 'react-dom/test-utils';

import ItemSelectorUrl from '../src/main/resources/META-INF/resources/js/ItemSelectorUrl.es';

describe('ItemSelectorUrl', () => {
	afterEach(() => {
		jest.clearAllTimers();
		cleanup();
	});

	let itemSelectorUrl;
	beforeEach(() => {
		jest.useFakeTimers();
		itemSelectorUrl = render(<ItemSelectorUrl eventName="eventName" />);
	});

	it('renders a disabled button ', () => {
		expect(itemSelectorUrl.getByRole('button')).toBeDisabled();
	});

	describe('when the user types an invalid url', () => {
		beforeEach(() => {
			fireEvent.change(itemSelectorUrl.getByLabelText('url'), {
				target: {value: 'test'}
			});
		});

		it('renders a disabled button ', () => {
			expect(itemSelectorUrl.getByRole('button')).toBeDisabled();
		});

		describe('and after 5 seconds', () => {
			beforeEach(() => {
				act(() => {
					jest.advanceTimersByTime(5000);
				});
			});

			it('renders no preview available message', () => {
				expect(
					itemSelectorUrl.getByText('there-is-no-preview-available')
				);
			});

			it('enables the button', () => {
				expect(itemSelectorUrl.getByRole('button')).toBeEnabled();
			});
		});
	});
});
