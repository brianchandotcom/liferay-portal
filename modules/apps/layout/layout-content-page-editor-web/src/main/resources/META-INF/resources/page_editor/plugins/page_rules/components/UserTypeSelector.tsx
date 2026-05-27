/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React from 'react';

import {config} from '../../../app/config/index';
import RulesService from '../../../app/services/RulesService';
import {CACHE_KEYS} from '../../../app/utils/cache';
import useCache from '../../../app/utils/useCache';
import {Condition as ConditionType, RuleError} from '../../../types/Rule';
import RuleSelect from './RuleSelect';

const USER_CONDITION_VALUES = {
	not_role: 'not_role',
	not_segment: 'not_segment',
	not_user: 'not_user',
	role: 'role',
	segment: 'segment',
	user: 'user',
} as const;

export const USER_CONDITION_ITEMS = [
	{
		label: Liferay.Language.get('is-the-user'),
		value: USER_CONDITION_VALUES.user,
	},
	{
		label: Liferay.Language.get('is-not-the-user'),
		value: USER_CONDITION_VALUES.not_user,
	},
	{
		label: Liferay.Language.get('has-the-role-of'),
		value: USER_CONDITION_VALUES.role,
	},
	{
		label: Liferay.Language.get('does-not-have-the-role-of'),
		value: USER_CONDITION_VALUES.not_role,
	},
	{
		label: Liferay.Language.get('belongs-to-segment'),
		value: USER_CONDITION_VALUES.segment,
	},
	{
		label: Liferay.Language.get('does-not-belong-to-segment'),
		value: USER_CONDITION_VALUES.not_segment,
	},
];

export function convertConditionValueToOptions(
	field: keyof typeof USER_CONDITION_VALUES
): Partial<ConditionType> {
	if (field === USER_CONDITION_VALUES.not_user) {
		return {
			field: USER_CONDITION_VALUES.user,
			options: {
				type: 'not-equal',
			},
		};
	}

	if (field === USER_CONDITION_VALUES.not_role) {
		return {
			field: USER_CONDITION_VALUES.role,
			options: {
				type: 'not-equal',
			},
		};
	}

	if (field === USER_CONDITION_VALUES.not_segment) {
		return {
			field: USER_CONDITION_VALUES.segment,
			options: {
				type: 'not-equal',
			},
		};
	}

	return {
		field,
		options: {
			type: 'equal',
		},
	};
}

export function convertOptionsToConditionValue(
	condition: ConditionType
): keyof typeof USER_CONDITION_VALUES | undefined {
	if (condition.field === USER_CONDITION_VALUES.user) {
		if (condition.options?.type === 'equal') {
			return USER_CONDITION_VALUES.user;
		}
		else {
			return USER_CONDITION_VALUES.not_user;
		}
	}
	else if (condition.field === USER_CONDITION_VALUES.role) {
		if (condition.options?.type === 'equal') {
			return USER_CONDITION_VALUES.role;
		}
		else {
			return USER_CONDITION_VALUES.not_role;
		}
	}
	else if (condition.field === USER_CONDITION_VALUES.segment) {
		if (condition.options?.type === 'equal') {
			return USER_CONDITION_VALUES.segment;
		}
		else {
			return USER_CONDITION_VALUES.not_segment;
		}
	}

	return undefined;
}

const VALUE_SELECTOR_COMPONENTS = {
	[USER_CONDITION_VALUES.not_user]: UserSelector,
	[USER_CONDITION_VALUES.not_role]: RolesSelector,
	[USER_CONDITION_VALUES.not_segment]: SegmentsSelector,
	[USER_CONDITION_VALUES.user]: UserSelector,
	[USER_CONDITION_VALUES.role]: RolesSelector,
	[USER_CONDITION_VALUES.segment]: SegmentsSelector,
};

export default function UserTypeSelector({
	condition,
	onConditionChange,
	onErrorChange,
	sendMessage,
}: {
	condition: ConditionType;
	onConditionChange: (condition: ConditionType) => void;
	onErrorChange: (error: RuleError | null) => void;
	sendMessage: (message: string) => void;
}) {
	const ValueSelectorComponent =
		VALUE_SELECTOR_COMPONENTS[
			condition.field as keyof typeof USER_CONDITION_VALUES
		];

	return (
		<>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x'),
					Liferay.Language.get('condition')
				)}
				items={USER_CONDITION_ITEMS}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedCondition) => {
					onConditionChange({
						...condition,
						...convertConditionValueToOptions(selectedCondition),
					} as ConditionType);
				}}
				selectedKey={convertOptionsToConditionValue(condition)}
			/>

			{ValueSelectorComponent ? (
				<ValueSelectorComponent
					onErrorChange={onErrorChange}
					onValueChanged={(value) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
								value,
							},
						} as ConditionType);

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					value={
						typeof condition.options?.value === 'string'
							? condition.options.value
							: undefined
					}
				/>
			) : null}
		</>
	);
}

type ValueSelectorProps = {
	onErrorChange: (error: RuleError | null) => void;
	onValueChanged: (value: string) => void;
	value: string | undefined;
};

function RolesSelector({
	onErrorChange,
	onValueChanged,
	value,
}: ValueSelectorProps) {
	const roles = useCache({
		fetcher: () => RulesService.getRoles(),
		key: [CACHE_KEYS.roles],
	});

	if (!roles) {
		return null;
	}

	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('role')
			)}
			items={roles.map((role) => ({
				label: role.name,
				value: role.roleId,
			}))}
			onErrorChange={onErrorChange}
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function SegmentsSelector({
	onErrorChange,
	onValueChanged,
	value,
}: ValueSelectorProps) {
	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('segment')
			)}
			items={Object.values(config.availableSegmentsEntries).map(
				(segmentsEntry) => ({
					label: segmentsEntry.name,
					value: segmentsEntry.segmentsEntryId,
				})
			)}
			onErrorChange={onErrorChange}
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}

function UserSelector({
	onErrorChange,
	onValueChanged,
	value,
}: ValueSelectorProps) {
	const users = useCache({
		fetcher: () => RulesService.getUsers(),
		key: [CACHE_KEYS.users],
	});

	if (!users) {
		return null;
	}

	return (
		<RuleSelect
			aria-label={sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('user')
			)}
			items={users.map((user) => ({
				label: user.screenName,
				value: user.userId,
			}))}
			onErrorChange={onErrorChange}
			onSelectionChange={(value: React.Key) =>
				onValueChanged(value as string)
			}
			selectedKey={value}
		/>
	);
}
