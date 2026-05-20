/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {translateConditionsToScript} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/translateConditionsToScript';
import {Condition} from '../../../../src/main/resources/META-INF/resources/page_editor/types/Rule';

describe('translateConditionsToScript', () => {
	describe('field conditions', () => {
		it('emits an equality check', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('publishDate', 'equal', '2026-05-11')],
					'all'
				)
			).toBe('publishDate == "2026-05-11"');
		});

		it('emits a not-equal check', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'publishDate',
							'not-equal',
							'2026-05-11'
						),
					],
					'all'
				)
			).toBe('publishDate != "2026-05-11"');
		});

		it('emits futureDates with an inequality for strict greater-than', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'publishDate',
							'greater-than',
							'2026-05-11'
						),
					],
					'all'
				)
			).toBe(
				'(futureDates(publishDate, "2026-05-11") AND publishDate != "2026-05-11")'
			);
		});

		it('emits futureDates for greater-than-or-equals', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'publishDate',
							'greater-than-or-equals',
							'2026-05-11'
						),
					],
					'all'
				)
			).toBe('futureDates(publishDate, "2026-05-11")');
		});

		it('emits pastDates with an inequality for strict less-than', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'publishDate',
							'less-than',
							'2026-05-11'
						),
					],
					'all'
				)
			).toBe(
				'(pastDates(publishDate, "2026-05-11") AND publishDate != "2026-05-11")'
			);
		});

		it('emits pastDates for less-than-or-equals', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'publishDate',
							'less-than-or-equals',
							'2026-05-11'
						),
					],
					'all'
				)
			).toBe('pastDates(publishDate, "2026-05-11")');
		});

		it('quotes a missing value as an empty string', () => {
			expect(
				translateConditionsToScript(
					[
						{
							field: 'title',
							id: 'a',
							options: {type: 'equal'},
							type: 'field',
						},
					],
					'all'
				)
			).toBe('title == ""');
		});
	});

	describe('text field conditions', () => {
		it('emits isEmpty for is-empty', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('title', 'is-empty', '')],
					'all'
				)
			).toBe('isEmpty(title)');
		});

		it('wraps isEmpty in NOT for is-not-empty', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('title', 'is-not-empty', '')],
					'all'
				)
			).toBe('NOT(isEmpty(title))');
		});

		it('emits contains for contains', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('title', 'contains', 'foo')],
					'all'
				)
			).toBe('contains(title, "foo")');
		});

		it('wraps contains in NOT for does-not-contain', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('title', 'does-not-contain', 'foo')],
					'all'
				)
			).toBe('NOT(contains(title, "foo"))');
		});
	});

	describe('numeric field conditions', () => {
		const fieldTypes = {budget: 'number'};

		it('emits an unquoted equality check', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('budget', 'equal', '10')],
					'all',
					fieldTypes
				)
			).toBe('budget == 10');
		});

		it('emits an unquoted inequality check', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('budget', 'not-equal', '10')],
					'all',
					fieldTypes
				)
			).toBe('budget != 10');
		});

		it('emits a numeric strict greater-than', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('budget', 'greater-than', '10')],
					'all',
					fieldTypes
				)
			).toBe('budget > 10');
		});

		it('emits a numeric greater-than-or-equals', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'budget',
							'greater-than-or-equals',
							'10'
						),
					],
					'all',
					fieldTypes
				)
			).toBe('budget >= 10');
		});

		it('emits a numeric strict less-than', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('budget', 'less-than', '10')],
					'all',
					fieldTypes
				)
			).toBe('budget < 10');
		});

		it('emits a numeric less-than-or-equals', () => {
			expect(
				translateConditionsToScript(
					[getFieldCondition('budget', 'less-than-or-equals', '10')],
					'all',
					fieldTypes
				)
			).toBe('budget <= 10');
		});

		it('defaults a missing value to 0', () => {
			expect(
				translateConditionsToScript(
					[
						{
							field: 'budget',
							id: 'a',
							options: {type: 'equal'},
							type: 'field',
						},
					],
					'all',
					fieldTypes
				)
			).toBe('budget == 0');
		});
	});

	describe('select field conditions', () => {
		const fieldTypes = {status: 'select'};

		it('emits an equality check for equal', () => {
			expect(
				translateConditionsToScript(
					[getSelectCondition('status', 'equal', 'active')],
					'all',
					fieldTypes
				)
			).toBe('status == "active"');
		});

		it('emits an inequality check for not-equal', () => {
			expect(
				translateConditionsToScript(
					[getSelectCondition('status', 'not-equal', 'archived')],
					'all',
					fieldTypes
				)
			).toBe('status != "archived"');
		});

		it('emits isEmpty for is-empty', () => {
			expect(
				translateConditionsToScript(
					[getSelectCondition('status', 'is-empty', '')],
					'all',
					fieldTypes
				)
			).toBe('isEmpty(status)');
		});

		it('wraps isEmpty in NOT for is-not-empty', () => {
			expect(
				translateConditionsToScript(
					[getSelectCondition('status', 'is-not-empty', '')],
					'all',
					fieldTypes
				)
			).toBe('NOT(isEmpty(status))');
		});
	});

	describe('multiselect field conditions', () => {
		const fieldTypes = {tags: 'multiselect'};

		it('emits a single-key contains wrapped in parentheses', () => {
			expect(
				translateConditionsToScript(
					[getMultiselectCondition('tags', 'contains', ['news'])],
					'all',
					fieldTypes
				)
			).toBe('(contains(tags, "news"))');
		});

		it('joins multiple keys with OR for contains', () => {
			expect(
				translateConditionsToScript(
					[
						getMultiselectCondition('tags', 'contains', [
							'news',
							'press',
						]),
					],
					'all',
					fieldTypes
				)
			).toBe('(contains(tags, "news") OR contains(tags, "press"))');
		});

		it('wraps the OR expression in NOT for does-not-contain', () => {
			expect(
				translateConditionsToScript(
					[
						getMultiselectCondition('tags', 'does-not-contain', [
							'news',
							'press',
						]),
					],
					'all',
					fieldTypes
				)
			).toBe(
				'NOT((contains(tags, "news") OR contains(tags, "press")))'
			);
		});

		it('emits isEmpty for is-empty', () => {
			expect(
				translateConditionsToScript(
					[getMultiselectCondition('tags', 'is-empty', [])],
					'all',
					fieldTypes
				)
			).toBe('isEmpty(tags)');
		});

		it('wraps isEmpty in NOT for is-not-empty', () => {
			expect(
				translateConditionsToScript(
					[getMultiselectCondition('tags', 'is-not-empty', [])],
					'all',
					fieldTypes
				)
			).toBe('NOT(isEmpty(tags))');
		});
	});

	describe('form conditions', () => {
		it('rewrites the field name with the input__ prefix and underscores', () => {
			expect(
				translateConditionsToScript(
					[getFormCondition('field-name', 'equal', 'true')],
					'all'
				)
			).toBe('input__field_name == "true"');
		});
	});

	describe('user conditions', () => {
		it('emits a contains check for role equality', () => {
			expect(
				translateConditionsToScript(
					[getUserCondition('role', 'equal', '42')],
					'all'
				)
			).toBe('contains(roleIds, 42)');
		});

		it('wraps role not-equal in NOT(contains(...))', () => {
			expect(
				translateConditionsToScript(
					[getUserCondition('role', 'not-equal', '42')],
					'all'
				)
			).toBe('NOT(contains(roleIds, 42))');
		});

		it('emits a contains check for segment equality', () => {
			expect(
				translateConditionsToScript(
					[getUserCondition('segment', 'equal', '99')],
					'all'
				)
			).toBe('contains(segmentsEntryIds, 99)');
		});

		it('emits a userId equality check for the user field', () => {
			expect(
				translateConditionsToScript(
					[getUserCondition('user', 'equal', '7')],
					'all'
				)
			).toBe('userId == 7');
		});
	});

	describe('combinators', () => {
		it('joins multiple conditions with AND for conditionType "all"', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition(
							'publishDate',
							'greater-than-or-equals',
							'2026-01-01'
						),
						getFieldCondition(
							'publishDate',
							'less-than-or-equals',
							'2027-01-01'
						),
					],
					'all'
				)
			).toBe(
				'futureDates(publishDate, "2026-01-01") AND pastDates(publishDate, "2027-01-01")'
			);
		});

		it('joins multiple conditions with OR for conditionType "any"', () => {
			expect(
				translateConditionsToScript(
					[
						getFieldCondition('title', 'equal', 'foo'),
						getFieldCondition('title', 'equal', 'bar'),
					],
					'any'
				)
			).toBe('title == "foo" OR title == "bar"');
		});

		it('returns an empty string when there are no conditions', () => {
			expect(translateConditionsToScript([], 'all')).toBe('');
		});
	});
});

function getFieldCondition(
	field: string,
	type: NonNullable<Condition['options']>['type'],
	value: string
): Condition {
	return {
		field,
		id: `condition-${field}-${type}`,
		options: {type, value},
		type: 'field',
	};
}

function getSelectCondition(
	field: string,
	type: 'equal' | 'is-empty' | 'is-not-empty' | 'not-equal',
	value: string
): Condition {
	return {
		field,
		fieldType: 'select',
		id: `condition-${field}-${type}`,
		options: {type, value},
		type: 'field',
	};
}

function getMultiselectCondition(
	field: string,
	type: 'contains' | 'does-not-contain' | 'is-empty' | 'is-not-empty',
	value: string[]
): Condition {
	return {
		field,
		fieldType: 'multiselect',
		id: `condition-${field}-${type}`,
		options: {type, value},
		type: 'field',
	};
}

function getFormCondition(
	field: string,
	type: NonNullable<Condition['options']>['type'],
	value: string
): Condition {
	return {
		field,
		id: `condition-${field}-${type}`,
		options: {type, value},
		type: 'form',
	};
}

function getUserCondition(
	field: string,
	type: NonNullable<Condition['options']>['type'],
	value: string
): Condition {
	return {
		field,
		id: `condition-${field}-${type}`,
		options: {type, value},
		type: 'user',
	};
}
