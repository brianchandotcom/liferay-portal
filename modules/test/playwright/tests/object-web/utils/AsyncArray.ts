/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
interface BaseAsyncArrayOperationProps<T, K> {
	array: T[];
	predicate: (value: T) => Promise<K>;
}

interface AsyncArrayOperations<T, K> {
	filter({
		array,
		predicate,
	}: BaseAsyncArrayOperationProps<T, K>): Promise<T[]>;
	find({array, predicate}: BaseAsyncArrayOperationProps<T, K>): Promise<T>;
	map({array, predicate}: BaseAsyncArrayOperationProps<T, K>): Promise<K[]>;
}

export class AsyncArray<T, K> implements AsyncArrayOperations<T, K> {
	async filter({array, predicate}: BaseAsyncArrayOperationProps<T, K>) {
		const results = await this.map({array, predicate});

		return array.filter((_, index) => results[index]);
	}

	async find({array, predicate}: BaseAsyncArrayOperationProps<T, K>) {
		const results = await this.map({array, predicate});

		return array.find((_, index) => results[index]);
	}

	async map({array, predicate}: BaseAsyncArrayOperationProps<T, K>) {
		return await Promise.all(array.map(predicate));
	}
}
