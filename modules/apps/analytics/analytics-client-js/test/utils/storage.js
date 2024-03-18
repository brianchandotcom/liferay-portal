/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getItem,
	getItemFromCookiesOrLocalStorage,
	getStorageSizeInKb,
	removeItem,
	setItem,
	verifyStorageLimitForKey,
} from '../../src/utils/storage';

const STORAGE_KEY = 'some-key';

describe('Storage Utils', () => {
	window.Analytics.getCookieManager = jest.fn();

	beforeEach(() => {
		removeItem(STORAGE_KEY);
	});

	describe('getItem', () => {
		it('Retrieves an item from Cookie', () => {
			const expected = {name: 'foo'};

			setItem(STORAGE_KEY, expected);

			expect(getItem(STORAGE_KEY)).toEqual(expected);
		});
	});

	describe('getStorageSizeInKb', () => {
		it('Calculates the kilobyte size of a string', () => {
			const expected = 0.0234375;

			expect(getStorageSizeInKb('0123456789')).toEqual(expected);
		});
	});

	describe('setItem', () => {
		it('Sets an item in Cookie', () => {
			const expected = {name: 'foo'};

			setItem(STORAGE_KEY, expected);

			expect(getItem(STORAGE_KEY)).toEqual(expected);
		});
	});

	describe('verifyStorageLimitForKey', () => {
		it('Removes items in a Cookie queue if the storage limit is exceeded', async () => {
			const queue = [{name: 'foo'}, {name: 'bar'}, {name: 'baz'}];
			const mockStorageLimit = 0.05;

			setItem(STORAGE_KEY, queue);

			await verifyStorageLimitForKey(STORAGE_KEY, mockStorageLimit);

			expect(getItem(STORAGE_KEY)).toEqual(
				expect.arrayContaining(queue.slice(1))
			);
		});

		it('Does not change items in a Cookie queue if the storage limit is not exceeded', async () => {
			const queue = [{name: 'foo'}, {name: 'bar'}, {name: 'baz'}];
			const mockStorageLimit = 100;

			setItem(STORAGE_KEY, queue);

			await verifyStorageLimitForKey(STORAGE_KEY, mockStorageLimit);

			expect(getItem(STORAGE_KEY)).toEqual(expect.arrayContaining(queue));
		});
	});

	describe('3rd party cookie manager', () => {
		it('Retrieves, set and remove items from a 3rd party cookie manager', () => {
			const storage = {name: 'foo'};

			const spy = jest.fn();

			window.Analytics.getCookieManager = () => ({
				actions: {
					getItem: (key) => {
						spy();

						return storage[key];
					},
					removeItem: (key) => {
						spy();

						delete storage[key];
					},
					setItem: (key, value) => {
						spy();

						storage[key] = value;
					},
				},
			});

			setItem(STORAGE_KEY, storage);

			const result = getItem(STORAGE_KEY);

			expect(result).toEqual(storage);

			removeItem('name');

			expect(spy.mock.calls).toHaveLength(3);
		});

		it('Retrieves items from local storage from a 3rd party cookie manager', () => {
			const storage = {name: 'foo'};

			const spy = jest.fn();

			window.Analytics.getCookieManager = () => ({
				actions: {
					getItem: () => {
						return;
					},
					getItemFromLocalStorage: (key) => {
						spy();

						return storage[key];
					},
				},
			});

			const result = getItemFromCookiesOrLocalStorage('name');

			expect(result).toEqual('foo');

			expect(spy.mock.calls).toHaveLength(1);
		});
	});
});
