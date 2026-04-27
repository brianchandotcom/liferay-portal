/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import React from 'react';
import {TriggerLabel} from './ConditionBuilder';

import type {ValueInputRenderer} from './types';

/**
 * Standard value input renderer for the common PropertyTypes.
 *
 * Pass this as renderValueInput when no domain-specific value input is needed.
 * Consumers that require async data loading (e.g. Objects picklist/relationship
 * entries) should provide their own renderValueInput instead.
 */
export const DefaultValueInput: ValueInputRenderer = (
	property,
	_operator,
	value,
	onChange
) => {
	const {options, type} = property;

	if (type === 'boolean') {
		const items = [
			{label: Liferay.Language.get('is-true'), value: 'true'},
			{label: Liferay.Language.get('is-false'), value: 'false'},
		];

		return (
			<Picker
				aria-label={Liferay.Language.get('value')}
				as={TriggerLabel}
				items={items}
				onSelectionChange={(key) => onChange(key as string)}
				placeholder={Liferay.Language.get('select')}
				selectedKey={value}
			>
				{(item) => <Option key={item.value}>{item.label}</Option>}
			</Picker>
		);
	}

	if (type === 'picklist' && options?.length) {
		return (
			<Picker
				aria-label={Liferay.Language.get('value')}
				as={TriggerLabel}
				items={options}
				onSelectionChange={(key) => onChange(key as string)}
				placeholder={Liferay.Language.get('select')}
				selectedKey={value}
			>
				{(item) => <Option key={item.value}>{item.label}</Option>}
			</Picker>
		);
	}

	if (type === 'integer' || type === 'double') {
		return (
			<ClayInput
				aria-label={Liferay.Language.get('value')}
				className="form-control-sm"
				onChange={(e) => onChange(e.target.value)}
				placeholder={Liferay.Language.get('enter-value')}
				type="number"
				value={value ?? ''}
			/>
		);
	}

	if (type === 'date' || type === 'date-time') {
		return (
			<ClayInput
				aria-label={Liferay.Language.get('value')}
				className="form-control-sm"
				onChange={(e) => onChange(e.target.value)}
				type={type === 'date-time' ? 'datetime-local' : 'date'}
				value={value ?? ''}
			/>
		);
	}

	return (
		<ClayInput
			aria-label={Liferay.Language.get('value')}
			className="form-control-sm"
			onChange={(e) => onChange(e.target.value)}
			placeholder={Liferay.Language.get('enter-value')}
			type="text"
			value={value ?? ''}
		/>
	);
};
