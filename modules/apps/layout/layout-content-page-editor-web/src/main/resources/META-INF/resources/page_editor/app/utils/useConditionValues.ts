/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CONDITION_TYPE_ITEMS,
	Condition,
	FORM_FRAGMENT_CONDITION_ITEMS,
	TYPE_VALUES,
	USER_CONDITION_ITEMS,
	convertOptionsToConditionValue,
} from '../../plugins/page_rules/components/Condition';
import {ConditionType} from '../../plugins/page_rules/components/RuleBuilderSection';
import {config} from '../config/index';
import RulesService from '../services/RulesService';
import {CACHE_KEYS} from './cache';
import useCache from './useCache';

type Role = {name: string; roleId: string};
type User = {screenName: string; userId: string};
type Segment = {name: string};

type Item = {label: string; value: string};

type Props = {
	conditionType?: ConditionType;
	conditions: Condition[];
	items: Item[];
};

export default function useConditionValues({
	conditionType,
	conditions,
	items,
}: Props) {
	const roles = useCache({
		fetcher: () => RulesService.getRoles(),
		key: [CACHE_KEYS.roles],
	});

	const users = useCache({
		fetcher: () => RulesService.getUsers(),
		key: [CACHE_KEYS.users],
	});

	const segments = config.availableSegmentsEntries;

	return conditions.map((_condition, index) => {
		const condition = getCondition(_condition);
		const prefix = getPrefix(index, conditionType);
		const type = getType(_condition, items);
		const value = getValue(roles, segments, users, _condition);

		const description = getDescription(condition, prefix, type, value);

		return {
			condition,
			description,
			id: _condition.id,
			prefix,
			type,
			value,
		};
	});
}

function getCondition(condition: Condition) {
	if (!condition.type || !condition.field) {
		return '';
	}

	const conditionValue = convertOptionsToConditionValue(condition);

	return condition.type === TYPE_VALUES.user
		? USER_CONDITION_ITEMS.find(({value}) => value === conditionValue)
				?.label
		: FORM_FRAGMENT_CONDITION_ITEMS.find(
				({value}) => value === condition.options?.type
			)?.label;
}

function getDescription(
	condition?: string,
	prefix?: string,
	type?: string,
	value?: string,
	item?: string
) {
	return [prefix, type, condition, value, item]
		.filter((item) => item)
		.join(' ');
}

function getPrefix(index: number, conditionType?: ConditionType) {
	if (!conditionType) {
		return '';
	}

	if (!index) {
		return Liferay.Language.get('if');
	}

	return conditionType === 'all'
		? Liferay.Language.get('and')
		: Liferay.Language.get('or');
}

function getType(condition: Condition, items: Item[]) {
	if (!condition.type) {
		return '';
	}

	return condition.type === TYPE_VALUES.user
		? CONDITION_TYPE_ITEMS.find(({value}) => value === condition.type)
				?.label
		: items.find(({value}) => value === condition.field)?.label;
}

function getValue(
	roles: Role[] | null,
	segments: Record<string, Segment>,
	users: User[] | null,
	condition?: Condition
) {
	const value = condition?.options?.value;

	if (!value) {
		return '';
	}

	if (condition?.type === TYPE_VALUES.formFragment) {
		return value;
	}

	switch (condition?.field) {
		case 'role':
			return roles?.find(({roleId}) => roleId === value)?.name;
		case 'segment':
			return segments[value]?.name;
		case 'user':
			return users?.find(({userId}) => userId === value)?.screenName;
		default:
			return '';
	}
}
