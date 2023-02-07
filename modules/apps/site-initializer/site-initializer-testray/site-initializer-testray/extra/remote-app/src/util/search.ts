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

type Filter = {
	[key: string]: string | number | string[] | number[];
};
type Key = string;
type Value = string | number | boolean;

export interface SearchBuilderConstructor {
	useURIEncode?: boolean;
}

/**
 * @description
 * Based in the following article https://help.liferay.com/hc/pt/articles/360031163631-Filter-Sort-and-Search
 */

export class SearchBuilder {
	private lock: boolean = false;
	private query: string = '';
	private useURIEncode?: boolean = true;

	constructor({useURIEncode}: SearchBuilderConstructor = {}) {
		this.useURIEncode = useURIEncode;
	}

	/**
	 * @description Contains
	 * @example contains(title,'edmon')
	 */

	static contains(key: Key, value: Value) {
		return `contains(${key}, '${value}')`;
	}

	static eq(key: Key, value: Value) {
		return `${key} eq ${typeof value === 'boolean' ? value : `'${value}'`}`;
	}

	/**
	 * @description In [values]
	 * @example addressLocality in ('London', 'Recife')
	 */
	static in(key: Key, values: Value[]) {
		if (values) {
			const operator = `${key} in ({values})`;

			return operator
				.replace(
					'{values}',
					values.map((value) => `'${value}'`).join(',')
				)
				.trim();
		}

		return '';
	}

	/**
	 * @description Not equal
	 * @example addressLocality ne 'London'
	 */
	static ne(key: Key, value: Value) {
		return `${key} ne '${value}'`;
	}

	public and() {
		return this.setContext('and');
	}

	public build() {
		const query = this.query.trim();

		if (query.endsWith('or') || query.endsWith('and')) {
			return query.substring(0, query.length - 3);
		}

		this.lock = true;

		return this.useURIEncode ? encodeURIComponent(query) : query;
	}

	static removeEmptyFilter(filter: Filter) {
		const _filter: Filter = {};

		for (const key in filter) {
			const value = filter[key];

			if (!value) {
				continue;
			}

			_filter[key] = value;
		}

		return _filter;
	}

	static createFilter(filter: Filter, baseFilters: string = '') {
		const _filter = [baseFilters];

		for (const key in filter) {
			const value = filter[key];

			if (!value) {
				continue;
			}

			const _value = Array.isArray(value)
				? SearchBuilder.in(key, value)
				: SearchBuilder.eq(key, value);

			_filter.push(_value);
		}

		return _filter.join(' and ');
	}

	public contains(key: Key, value: Value) {
		return this.setContext(SearchBuilder.contains(key, value));
	}

	public eq(key: Key, value: Value) {
		return this.setContext(SearchBuilder.eq(key, value));
	}

	public in(key: Key, values: Value[]) {
		return this.setContext(SearchBuilder.in(key, values));
	}

	public ne(key: Key, value: Value) {
		return this.setContext(SearchBuilder.ne(key, value));
	}

	private setContext(query: string) {
		if (!this.lock) {
			this.query += ` ${query}`;
		}

		return this;
	}

	public or() {
		return this.setContext('or');
	}
}
