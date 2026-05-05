/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import React from 'react';

import {TriggerLabel} from './ConditionBuilder';

import type {GenericProperty} from './types';

/**
 * Standard value input renderer for the common PropertyTypes.
 *
 * Pass this as renderValueInput when no domain-specific value input is needed.
 * Consumers that require async data loading (e.g. Objects picklist/relationship
 * entries) should provide their own renderValueInput instead.
 */
export function DefaultValueInput(
	index: number,
	property: GenericProperty,
	operator: string,
	value: string | Array<string | object> | undefined,
	onChange: (value: string | Array<string | object>) => void
): React.ReactNode {
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
				selectedKey={value as string}
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
				selectedKey={value as string}
			>
				{(item) => <Option key={item.value}>{item.label}</Option>}
			</Picker>
		);
	}

	if (type === 'integer' || type === 'numeric' || type === 'decimal') {
		if (operator === 'between') {
			const [from, to] = (value as string[]) || [];

			return (
				<div className="c-gap-2 d-flex flex-grow-1">
					<ClayInput
						aria-label={Liferay.Language.get('from')}
						className="form-control-sm"
						id={`${property.name}-from-${index}`}
						onChange={(event) =>
							onChange([event.target.value, to] as string[])
						}
						placeholder={Liferay.Language.get('from')}
						step={type === 'decimal' ? '0.001' : '1'}
						type="number"
						value={from}
					/>

					<ClayInput
						aria-label={Liferay.Language.get('to')}
						className="form-control-sm"
						id={`${property.name}-to-${index}`}
						onChange={(event) =>
							onChange([from, event.target.value] as string[])
						}
						placeholder={Liferay.Language.get('to')}
						step={type === 'decimal' ? '0.001' : '1'}
						type="number"
						value={to}
					/>
				</div>
			);
		}

		return (
			<ClayInput
				aria-label={Liferay.Language.get('value')}
				className="form-control-sm"
				id={`${property.name}-${index}`}
				onChange={(event) => onChange(event.target.value)}
				placeholder={Liferay.Language.get('enter-value')}
				step={type === 'decimal' ? '0.001' : '1'}
				type="number"
				value={(value as string) ?? ''}
			/>
		);
	}

	if (type === 'date' || type === 'date-time') {
		return (
			<ClayInput
				aria-label={Liferay.Language.get('value')}
				className="form-control-sm"
				id={`${property.name}-${index}`}
				onChange={(event) => onChange(event.target.value)}
				type={type === 'date-time' ? 'datetime-local' : 'date'}
				value={(value as string) ?? ''}
			/>
		);
	}

	return (
		<ClayInput
			aria-label={Liferay.Language.get('value')}
			className="form-control-sm"
			id={`${property.name}-${index}`}
			onChange={(event) => onChange(event.target.value)}
			placeholder={Liferay.Language.get('enter-value')}
			type="text"
			value={(value as string) ?? ''}
		/>
	);
}
