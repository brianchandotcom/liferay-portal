/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import DatePicker from '@clayui/date-picker';
import React, {useState} from 'react';

import {TriggerLabel} from './ConditionBuilder';
import {VALUE_INPUT_CLASSNAME} from './ValueInput';
import {RELATIVE_DATE_OPTIONS, RELATIVE_DATE_VALUES} from './operators';

import type {ValueInputProps} from './ValueInput';

type DateMode = 'relative' | 'specific';

const DATE_MODE_OPTIONS = [
	{label: Liferay.Language.get('specific-date'), value: 'specific'},
	{label: Liferay.Language.get('relative-date'), value: 'relative'},
];

export default function DateValueInput({
	index,
	onChange,
	operator,
	property,
	value,
}: ValueInputProps) {
	const isDateTime = property.type === 'date-time';

	const datePickerProps = {
		className: 'form-control-sm',
		dateFormat: 'yyyy-MM-dd',
		placeholder: isDateTime ? 'YYYY-MM-DD HH:mm' : 'YYYY-MM-DD',
		time: isDateTime,
		years: {
			end: new Date().getFullYear() + 25,
			start: new Date().getFullYear() - 50,
		},
	};

	// Remembers the user's last mode-picker selection so the UI stays on that
	// mode after onChange('') clears the value during a switch. The derived
	// `mode` (further down) falls back to it whenever the value is empty.

	const [preferredMode, setPreferredMode] = useState<DateMode>('specific');
	const valueStr = (value as string) || '';

	if (operator === 'between') {
		const [from = '', to = ''] = (value as string[]) || [];

		if (isDateTime) {
			return (
				<div className={`c-gap-2 ${VALUE_INPUT_CLASSNAME}`}>
					<DatePicker
						{...datePickerProps}
						inputName={`${property.name}-from-${index}`}
						onChange={(newValue: string) =>
							onChange([newValue, to])
						}
						value={from}
					/>

					<DatePicker
						{...datePickerProps}
						inputName={`${property.name}-to-${index}`}
						onChange={(newValue: string) =>
							onChange([from, newValue])
						}
						value={to}
					/>
				</div>
			);
		}

		return (
			<div className={VALUE_INPUT_CLASSNAME}>
				<DatePicker
					{...datePickerProps}
					inputName={`${property.name}-${index}`}
					onChange={(newValue: string) => {
						const [newFrom = '', newTo = ''] =
							newValue.split(' - ');

						onChange([newFrom, newTo]);
					}}
					placeholder="YYYY-MM-DD - YYYY-MM-DD"
					range
					value={from && to ? `${from} - ${to}` : from}
				/>
			</div>
		);
	}

	if (isDateTime) {
		return (
			<div className={VALUE_INPUT_CLASSNAME}>
				<DatePicker
					{...datePickerProps}
					inputName={`${property.name}-${index}`}
					onChange={(newValue: string) => onChange(newValue)}
					value={valueStr}
				/>
			</div>
		);
	}

	const mode = RELATIVE_DATE_VALUES.includes(valueStr)
		? 'relative'
		: valueStr
			? 'specific'
			: preferredMode;

	return (
		<>
			<div className="condition-builder__select">
				<Picker
					aria-label={Liferay.Language.get('value')}
					as={TriggerLabel}
					items={DATE_MODE_OPTIONS}
					onSelectionChange={(key) => {
						if (key) {
							onChange('');
							setPreferredMode(key as DateMode);
						}
					}}
					placeholder={Liferay.Language.get('select')}
					selectedKey={mode}
				>
					{(item) => <Option key={item.value}>{item.label}</Option>}
				</Picker>
			</div>

			<div className={VALUE_INPUT_CLASSNAME}>
				{mode === 'relative' ? (
					<Picker
						aria-label={Liferay.Language.get('value')}
						as={TriggerLabel}
						items={RELATIVE_DATE_OPTIONS}
						onSelectionChange={(key) => onChange(key as string)}
						placeholder={Liferay.Language.get('select')}
						selectedKey={valueStr}
					>
						{(item) => (
							<Option key={item.value}>{item.label}</Option>
						)}
					</Picker>
				) : (
					<DatePicker
						{...datePickerProps}
						inputName={`${property.name}-${index}`}
						onChange={(newValue: string) => onChange(newValue)}
						value={valueStr}
					/>
				)}
			</div>
		</>
	);
}
