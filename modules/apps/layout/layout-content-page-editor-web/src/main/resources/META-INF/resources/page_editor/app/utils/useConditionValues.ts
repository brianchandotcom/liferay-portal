/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CONDITION_ITEMS,
	CONDITION_TYPE_ITEMS,
	Condition,
} from '../../plugins/page_rules/components/Condition';
import {ConditionType} from '../../plugins/page_rules/components/RuleBuilderSection';
import {config} from '../config/index';
import RulesService from '../services/RulesService';
import {CACHE_KEYS} from './cache';
import useCache from './useCache';

type Role = {name: string; roleId: string};
type User = {screenName: string; userId: string};
type Segment = {name: string};

type Props = {
	conditionType?: ConditionType;
	conditions: Condition[];
};

export default function useConditionValues({conditionType, conditions}: Props) {
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
		const condition = getCondition(_condition.type, _condition.condition);
		const prefix = getPrefix(index, conditionType);
		const type = getType(_condition.type);
		const value = getValue(
			roles,
			segments,
			users,
			_condition.condition,
			_condition.value
		);

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

function getCondition(
	type?: Condition['type'],
	condition?: Condition['condition']
) {
	if (!type || !condition) {
		return '';
	}

	return CONDITION_ITEMS[type].find(({value}) => value === condition)?.label;
}

function getDescription(
	condition?: string,
	prefix?: string,
	type?: string,
	value?: string
) {
	return [prefix, type, condition, value].filter((item) => item).join(' ');
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

function getType(type: Condition['type']) {
	if (!type) {
		return '';
	}

	return CONDITION_TYPE_ITEMS.find(({value}) => value === type)?.label;
}

function getValue(
	roles: Role[] | null,
	segments: Record<string, Segment>,
	users: User[] | null,
	condition?: Condition['condition'],
	value?: Condition['value']
) {
	if (!value) {
		return '';
	}

	switch (condition) {
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
