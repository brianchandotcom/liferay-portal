/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTimePicker from '@clayui/time-picker';
import {FieldBase} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {
	is12HourLocale,
	isCompleteTime,
	to12HourTime,
	to24HourTime,
} from '../../utils/dateTime';

import type {Input} from '@clayui/time-picker';

export type FieldTimePickerProps = {
	disabled?: boolean;
	errorMessage?: string;
	id?: string;
	label: string;
	name: string;
	onBlur?: () => void;
	onChange?: (value: string) => void;
	required?: boolean;
	value?: string;
};

function toInput(time: string, use12Hours: boolean): Input {
	if (!isCompleteTime(time)) {
		return use12Hours
			? {ampm: '--', hours: '--', minutes: '--'}
			: {hours: '--', minutes: '--'};
	}

	const [hours, minutes, ampm] = (
		use12Hours ? to12HourTime(time) : time
	).split(/[: ]/);

	return use12Hours
		? {ampm: ampm as Input['ampm'], hours, minutes}
		: {hours, minutes};
}

function toTime(input: Input, use12Hours: boolean): string {
	const time = use12Hours
		? to24HourTime(`${input.hours}:${input.minutes} ${input.ampm}`)
		: `${input.hours}:${input.minutes}`;

	return isCompleteTime(time) ? time : '';
}

const FieldTimePicker = ({
	disabled,
	errorMessage,
	id,
	label,
	name,
	onBlur,
	onChange,
	required,
	value = '',
}: FieldTimePickerProps) => {
	const fieldId = id ?? name;

	const use12Hours = is12HourLocale(
		Liferay.ThemeDisplay.getBCP47LanguageId()
	);

	const [input, setInput] = useState(() => toInput(value, use12Hours));

	useEffect(() => {
		setInput((input) =>
			toTime(input, use12Hours) === value
				? input
				: toInput(value, use12Hours)
		);
	}, [use12Hours, value]);

	return (
		<FieldBase
			disabled={disabled}
			errorMessage={errorMessage}
			id={fieldId}
			label={label}
			required={required}
		>
			<div onBlur={onBlur}>
				<ClayTimePicker
					ariaLabels={{
						ampm: Liferay.Language.get('am-pm'),
						clear: Liferay.Language.get('clear-time'),
						hours: Liferay.Language.get('hours'),
						minutes: Liferay.Language.get('minutes'),
						timeDown: Liferay.Language.get('decrease-time'),
						timeUp: Liferay.Language.get('increase-time'),
					}}
					disabled={disabled}
					icon
					id={fieldId}
					name={name}
					onChange={(input) => {
						setInput(input);

						onChange?.(toTime(input, use12Hours));
					}}
					use12Hours={use12Hours}
					value={input}
				/>
			</div>
		</FieldBase>
	);
};

export default FieldTimePicker;
