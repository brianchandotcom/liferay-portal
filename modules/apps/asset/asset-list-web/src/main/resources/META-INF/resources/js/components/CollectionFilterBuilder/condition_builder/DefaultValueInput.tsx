/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import React from 'react';

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
		return (
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('value')}
				onChange={(e) => onChange(e.target.value)}
				options={[
					{
						label: `-- ${Liferay.Language.get('select')} --`,
						value: '',
					},
					{label: Liferay.Language.get('true'), value: 'true'},
					{label: Liferay.Language.get('false'), value: 'false'},
				]}
				value={value ?? ''}
			/>
		);
	}

	if (type === 'picklist' && options?.length) {
		return (
			<ClaySelectWithOption
				aria-label={Liferay.Language.get('value')}
				onChange={(e) => onChange(e.target.value)}
				options={[
					{
						label: `-- ${Liferay.Language.get('select')} --`,
						value: '',
					},
					...options,
				]}
				value={value ?? ''}
			/>
		);
	}

	if (type === 'integer' || type === 'double') {
		return (
			<ClayInput
				aria-label={Liferay.Language.get('value')}
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
				onChange={(e) => onChange(e.target.value)}
				type={type === 'date-time' ? 'datetime-local' : 'date'}
				value={value ?? ''}
			/>
		);
	}

	// string / id
	return (
		<ClayInput
			aria-label={Liferay.Language.get('value')}
			onChange={(e) => onChange(e.target.value)}
			placeholder={Liferay.Language.get('enter-value')}
			type="text"
			value={value ?? ''}
		/>
	);
};
