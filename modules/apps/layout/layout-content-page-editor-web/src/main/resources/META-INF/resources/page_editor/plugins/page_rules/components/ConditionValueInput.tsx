/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {MappingFieldAttributes} from '../../../types/MappingField';
import {RuleError} from '../../../types/Rule';
import RuleDatePickerField from './RuleDatePickerField';
import RuleMultiSelectField from './RuleMultiSelectField';
import RuleSelect from './RuleSelect';
import RuleTextField from './RuleTextField';

const BOOLEAN_OPTIONS = [
	{label: Liferay.Language.get('true'), value: 'true'},
	{label: Liferay.Language.get('false'), value: 'false'},
] as const;

export default function ConditionValueInput({
	attributes,
	fieldType,
	onBlur,
	onChange,
	onErrorChange,
	value,
}: {
	attributes?: MappingFieldAttributes;
	fieldType: string | undefined;
	onBlur: () => void;
	onChange: (value: string | string[]) => void;
	onErrorChange: (error: RuleError | null) => void;
	value: string | string[] | undefined;
}) {
	const stringValue = typeof value === 'string' ? value : '';

	if (fieldType === 'boolean') {
		return (
			<RuleSelect
				aria-label={Liferay.Language.get('select-a-value')}
				items={BOOLEAN_OPTIONS}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedValue) => {
					onChange(selectedValue);

					onBlur();
				}}
				selectedKey={stringValue || undefined}
			/>
		);
	}

	if (fieldType === 'multiselect') {
		return (
			<RuleMultiSelectField
				onBlur={onBlur}
				onChange={onChange}
				onErrorChange={onErrorChange}
				options={attributes?.options ?? []}
				value={Array.isArray(value) ? value : []}
			/>
		);
	}

	if (fieldType === 'select') {
		return (
			<RuleSelect
				aria-label={Liferay.Language.get('select-a-value')}
				items={attributes?.options ?? []}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedValue) => {
					onChange(selectedValue);

					onBlur();
				}}
				selectedKey={stringValue || undefined}
			/>
		);
	}

	if (fieldType === 'date' || fieldType === 'date-time') {
		return (
			<RuleDatePickerField
				isDateTime={fieldType === 'date-time'}
				onBlur={onBlur}
				onChange={onChange}
				onErrorChange={onErrorChange}
				value={stringValue}
			/>
		);
	}

	return (
		<RuleTextField
			isNumber={fieldType === 'number'}
			onBlur={onBlur}
			onChange={onChange}
			onErrorChange={onErrorChange}
			value={stringValue}
		/>
	);
}
