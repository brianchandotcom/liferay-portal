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
type Value = string | number;

export const searchUtil = {
	eq: (key: Key, value: Value) => `${key} eq '${value}'`,
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
};

export class SearchBuilder {
	#query: string = '';

	build() {
		const query = this.#query.trim();

		if (query.endsWith('or') || query.endsWith('and')) {
			return query.substring(0, query.length - 3);
		}

		return query;
	}

	#setContext(query: string) {
		this.#query += ` ${query}`;

		return this;
	}

	and() {
		return this.#setContext('and');
	}

	eq(key: Key, value: Value) {
		return this.#setContext(searchUtil.eq(key, value));
	}

	in(key: Key, values: Value[]) {
		return this.#setContext(searchUtil.in(key, values));
	}

	or() {
		return this.#setContext('or');
	}
}
