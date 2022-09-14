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

type Key = string;
type Value = string | number | boolean;
type Filter = {
	[key: string]: string | number | string[] | number[];
};

/**
 * @description
 * Based in the following article https://help.liferay.com/hc/pt/articles/360031163631-Filter-Sort-and-Search
 */

export const searchUtil = {

	/**
	 * @description Contains
	 * @example contains(title,'edmon')
	 */

	contains: (key: Key, value: Value) => `contains(${key}, '${value}')`,

	/**
	 * @description Equal
	 * @example addressLocality eq 'Redmond'
	 */

	eq: (key: Key, value: Value) =>
		`${key} eq ${typeof value === 'boolean' ? value : `'${value}'`}`,

	/**
	 * @description In [values]
	 * @example addressLocality in ('London', 'Recife')
	 */
	in: (key: Key, values: Value[]) => {
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
	},

	/**
	 * @description Not equal
	 * @example addressLocality ne 'London'
	 */
	ne: (key: Key, value: Value) => `${key} ne '${value}'`,
};

export class SearchBuilder {
	#query: string = '';

	#setContext(query: string) {
		this.#query += ` ${query}`;

		return this;
	}

	and() {
		return this.#setContext('and');
	}

	build() {
		const query = this.#query.trim();

		if (query.endsWith('or') || query.endsWith('and')) {
			return query.substring(0, query.length - 3);
		}

		return query;
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

	static createFilter(filter: Filter, baseFilters?: string) {
		const _filter = [baseFilters];

		for (const key in filter) {
			const value = filter[key];

			if (!value) {
				continue;
			}

			const _value = Array.isArray(value)
				? searchUtil.in(key, value)
				: searchUtil.eq(key, value);

			_filter.push(_value);
		}

		return _filter.join(' and ');
	}

	contains(key: Key, value: Value) {
		return this.#setContext(searchUtil.contains(key, value));
	}

	eq(key: Key, value: Value) {
		return this.#setContext(searchUtil.eq(key, value));
	}

	in(key: Key, values: Value[]) {
		return this.#setContext(searchUtil.in(key, values));
	}

	ne(key: Key, value: Value) {
		return this.#setContext(searchUtil.ne(key, value));
	}

	or() {
		return this.#setContext('or');
	}
}
