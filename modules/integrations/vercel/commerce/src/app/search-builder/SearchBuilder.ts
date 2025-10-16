/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type AnyOptions = {operator: Operators; value: Value};

type Operators =
	| 'contains'
	| 'eq'
	| 'ge'
	| 'gt'
	| 'lambda'
	| 'le'
	| 'lt'
	| 'ne'
	| 'startsWith';

type Value = boolean | number | string | Date;

function formatValue(value: Value): string {
	if (typeof value === 'string') {
		return `'${value.replace(/'/g, "''")}'`;
	}

	if (value instanceof Date) {
		return value.toISOString();
	}

	return String(value);
}

export class SearchBuilder {
	private queryParts: string[] = [];

	private add(condition: string) {
		this.queryParts.push(condition);

		return this;
	}

	public any(field: string, options: AnyOptions) {
		return this.add(SearchBuilder.any(field, options));
	}

	public build(): string {
		return this.queryParts.join(' ');
	}

	public and() {
		return this.add('and');
	}

	public not() {
		return this.add('not');
	}

	public or() {
		return this.add('or');
	}

	public openGroup() {
		return this.add('(');
	}

	public closeGroup() {
		return this.add(')');
	}

	public eq(field: string, value: Value) {
		return this.add(SearchBuilder.eq(field, value));
	}

	public ne(field: string, value: Value) {
		return this.add(SearchBuilder.ne(field, value));
	}

	public gt(field: string, value: Value) {
		return this.add(SearchBuilder.gt(field, value));
	}

	public lt(field: string, value: Value) {
		return this.add(SearchBuilder.lt(field, value));
	}

	public ge(field: string, value: Value) {
		return this.add(SearchBuilder.ge(field, value));
	}

	public le(field: string, value: Value) {
		return this.add(SearchBuilder.le(field, value));
	}

	public in(field: string, values: Value[]) {
		return this.add(SearchBuilder.in(field, values));
	}

	public contains(field: string, value: Value) {
		return this.add(SearchBuilder.contains(field, value));
	}

	public startswith(field: string, value: Value) {
		return this.add(SearchBuilder.startswith(field, value));
	}

	public endswith(field: string, value: Value) {
		return this.add(SearchBuilder.endswith(field, value));
	}

	static any(field: string, options: AnyOptions) {
		const fn = SearchBuilder[
			options.operator as keyof typeof SearchBuilder

			// eslint-disable-next-line @typescript-eslint/no-explicit-any
		] as any;

		if (!fn) {
			throw new Error('Invalid operator');
		}

		return `${field}/any(x:(${fn('x', options.value)}))`;
	}

	static eq(field: string, value: Value) {
		return `${field} eq ${formatValue(value)}`;
	}

	static ne(field: string, value: Value) {
		return `${field} ne ${formatValue(value)}`;
	}

	static gt(field: string, value: Value) {
		return `${field} gt ${formatValue(value)}`;
	}

	static lt(field: string, value: Value) {
		return `${field} lt ${formatValue(value)}`;
	}

	static ge(field: string, value: Value) {
		return `${field} ge ${formatValue(value)}`;
	}

	static le(field: string, value: Value) {
		return `${field} le ${formatValue(value)}`;
	}

	static in(field: string, values: Value[]) {
		if (!Array.isArray(values)) {
			throw new Error(`'in' requires array`);
		}

		return `${field} in (${values.map(formatValue).join(', ')})`;
	}

	static contains(field: string, value: Value) {
		return `contains(${field}, ${formatValue(value)})`;
	}

	static startswith(field: string, value: Value) {
		return `startswith(${field}, ${formatValue(value)})`;
	}

	static endswith(field: string, value: Value) {
		return `endswith(${field}, ${formatValue(value)})`;
	}
}
