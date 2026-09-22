/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayTimePicker from '@clayui/time-picker';
import {FieldBase} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import {
	getTimePlaceholder,
	is12HourLocale,
	isCompleteTime,
	to12HourTime,
	toCanonicalTime,
} from '../../utils/dateTime';

import type {Input as TimePickerInput} from '@clayui/time-picker';

const UNSET_SEGMENT = '--';

export type FieldTimePickerProps = {
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	id?: string;
	label: string;
	name?: string;
	onBlur?: () => void;
	onChange?: (value: string) => void;
	required?: boolean;
	value?: string;
};

function fromTimePickerInput(
	input: TimePickerInput,
	use12Hours: boolean
): string | null {
	const segments = use12Hours
		? [input.hours, input.minutes, input.ampm ?? UNSET_SEGMENT]
		: [input.hours, input.minutes];

	if (segments.every((segment) => segment === UNSET_SEGMENT)) {
		return '';
	}

	if (segments.some((segment) => segment === UNSET_SEGMENT)) {
		return null;
	}

	const [hours, minutes, ampm] = segments;

	return (
		toStorageValue(
			use12Hours ? `${hours}:${minutes} ${ampm}` : `${hours}:${minutes}`,
			use12Hours
		) || null
	);
}

function toStorageValue(text: string, use12Hours: boolean): string {
	const time = toCanonicalTime(text.trim(), use12Hours);

	return isCompleteTime(time) ? time : '';
}

function toTimePickerInput(
	value: string,
	use12Hours: boolean
): TimePickerInput {
	const match = (use12Hours ? to12HourTime(value) : value).match(
		/^(\d{2}):(\d{2})(?: (AM|PM))?$/
	);

	if (!match) {
		return use12Hours
			? {
					ampm: UNSET_SEGMENT,
					hours: UNSET_SEGMENT,
					minutes: UNSET_SEGMENT,
				}
			: {hours: UNSET_SEGMENT, minutes: UNSET_SEGMENT};
	}

	const [, hours, minutes, ampm] = match;

	return use12Hours
		? {ampm: ampm as TimePickerInput['ampm'], hours, minutes}
		: {hours, minutes};
}

const FieldTimePicker = ({
	disabled,
	errorMessage: externalErrorMessage,
	helpMessage,
	id,
	label,
	name,
	onBlur,
	onChange,
	required,
	value = '',
}: FieldTimePickerProps) => {
	const use12Hours = is12HourLocale(
		Liferay.ThemeDisplay.getBCP47LanguageId()
	);

	const [draft, setDraft] = useState<string | null>(null);
	const [expanded, setExpanded] = useState(false);
	const [internalErrorMessage, setInternalErrorMessage] =
		useState<string>('');
	const [timePickerInput, setTimePickerInput] = useState<TimePickerInput>(
		() => toTimePickerInput(value, use12Hours)
	);

	const buttonRef = useRef<HTMLButtonElement>(null);
	const inputGroupRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		setDraft(null);
		setExpanded(false);
		setInternalErrorMessage('');
	}, [disabled]);

	useEffect(() => {
		setTimePickerInput(toTimePickerInput(value, use12Hours));
	}, [use12Hours, value]);

	const fieldId = id ?? name;
	const timePickerId = `${fieldId}TimePicker`;

	const handleBlur = (event: React.FocusEvent<HTMLInputElement>) => {
		const text = event.target.value;

		const storageValue = toStorageValue(text, use12Hours);

		if (!text.trim() || storageValue) {
			setDraft(null);
		}

		setInternalErrorMessage(
			text.trim() && !storageValue
				? Liferay.Language.get('please-enter-a-valid-time')
				: ''
		);

		onBlur?.();
	};

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const text = event.target.value;

		const storageValue = toStorageValue(text, use12Hours);

		setDraft(text);

		if (internalErrorMessage && (!text.trim() || storageValue)) {
			setInternalErrorMessage('');
		}

		onChange?.(storageValue);
	};

	const handleTimePickerChange = (input: TimePickerInput) => {
		setTimePickerInput(input);

		const storageValue = fromTimePickerInput(input, use12Hours);

		if (storageValue === null) {
			return;
		}

		setDraft(null);
		setInternalErrorMessage('');

		onChange?.(storageValue);
	};

	const shownDraft =
		draft !== null && toStorageValue(draft, use12Hours) === value
			? draft
			: null;

	const displayValue =
		shownDraft ?? (use12Hours ? to12HourTime(value) : value);

	const hidesExternalError =
		shownDraft !== null && shownDraft.trim() !== '' && value === '';

	const errorMessage =
		internalErrorMessage ||
		(hidesExternalError ? undefined : externalErrorMessage);

	return (
		<FieldBase
			disabled={disabled}
			errorMessage={errorMessage}
			helpMessage={helpMessage}
			id={fieldId}
			label={label}
			required={required}
		>
			<ClayInput.Group ref={inputGroupRef}>
				<ClayInput.GroupItem className="input-group-item-focusable">
					<ClayInput
						aria-describedby={
							errorMessage || helpMessage
								? `${fieldId}fieldFeedback`
								: undefined
						}
						aria-invalid={!!errorMessage}
						aria-required={required}
						disabled={disabled}
						id={fieldId}
						insetAfter
						name={name}
						onBlur={handleBlur}
						onChange={handleChange}
						placeholder={getTimePlaceholder(use12Hours)}
						required={required}
						type="text"
						value={displayValue}
					/>

					<ClayInput.GroupInsetItem after>
						<ClayButtonWithIcon
							aria-controls={timePickerId}
							aria-expanded={expanded}
							aria-haspopup="dialog"
							aria-label={Liferay.Language.get('select-time')}
							disabled={disabled}
							displayType="unstyled"
							onClick={() => setExpanded(!expanded)}
							ref={buttonRef}
							symbol="time"
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<DropDown.Menu
				active={expanded}
				alignElementRef={inputGroupRef}
				aria-label={Liferay.Language.get('select-time')}
				className="px-3 py-2"
				id={timePickerId}
				lock
				onActiveChange={setExpanded}
				role="dialog"
				triggerRef={buttonRef}
			>
				<ClayTimePicker
					ariaLabels={{
						ampm: Liferay.Language.get('am-pm'),
						clear: Liferay.Language.get('clear-time'),
						hours: Liferay.Language.get('hours'),
						minutes: Liferay.Language.get('minutes'),
						timeDown: Liferay.Language.get('decrease-time'),
						timeUp: Liferay.Language.get('increase-time'),
					}}
					icon
					onChange={handleTimePickerChange}
					use12Hours={use12Hours}
					value={timePickerInput}
				/>
			</DropDown.Menu>
		</FieldBase>
	);
};

export default FieldTimePicker;
