/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {useId} from 'frontend-js-components-web';
import {dateUtils} from 'frontend-js-web';
import moment from 'moment';
import React, {useCallback, useRef, useState} from 'react';

import {useRuleValidation} from '../../../app/contexts/RulesModalContext';
import {RuleError} from '../../../types/Rule';
import RuleField from './RuleField';

const DATE_FORMAT = 'YYYY-MM-DD';
const DATE_TIME_FORMAT = 'YYYY-MM-DD HH:mm';

interface RuleDatePickerFieldProps {
	isDateTime?: boolean;
	onBlur: () => void;
	onChange: (value: string) => void;
	onErrorChange: (error: RuleError | null) => void;
	value: string;
}

export default function RuleDatePickerField({
	isDateTime,
	onBlur,
	onChange,
	onErrorChange,
	value,
}: RuleDatePickerFieldProps) {
	const [hasError, setHasError] = useState(false);
	const dateFormat = isDateTime ? DATE_TIME_FORMAT : DATE_FORMAT;
	const id = useId();
	const inputRef = useRef<HTMLInputElement | null>(null);

	const errorMessage = Liferay.Language.get('please-enter-a-valid-date');

	const handleError = useCallback(
		(value: string) => {
			const isValid =
				!value || moment(value, dateFormat, true).isValid();

			setHasError(!isValid);

			if (inputRef.current) {
				onErrorChange(
					isValid
						? null
						: {element: inputRef.current, message: errorMessage}
				);
			}
		},
		[dateFormat, errorMessage, onErrorChange]
	);

	useRuleValidation(() => handleError(value));

	return (
		<RuleField
			className="mb-0 page-editor__rule-builder-date-picker w-100"
			errorMessage={errorMessage}
			fieldId={id}
			hasError={hasError}
		>
			<ClayDatePicker
				firstDayOfWeek={dateUtils.getFirstDayOfWeek()}
				id={id}
				months={dateUtils.getMonthsLong()}
				onBlur={() => {
					handleError(value);

					onBlur();
				}}
				onChange={(nextValue) => {
					handleError(nextValue as string);

					onChange(nextValue as string);
				}}
				placeholder={dateFormat}
				ref={inputRef}
				time={isDateTime}
				value={value}
				weekdaysShort={dateUtils.getWeekdaysShort() as string[]}
				{...({sizing: 'sm'} as {sizing: 'sm'})}
				years={{
					end: new Date().getFullYear() + 25,
					start: new Date().getFullYear() - 50,
				}}
			/>
		</RuleField>
	);
}
