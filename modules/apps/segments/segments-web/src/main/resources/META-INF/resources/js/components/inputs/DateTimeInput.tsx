/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayDatePicker from '@clayui/date-picker';
import {usePrevious} from '@liferay/frontend-js-react-web';
import {format, isValid, parse, parseISO} from 'date-fns';
import {default as React, useState} from 'react';

import {PROPERTY_TYPES} from '../../utils/constants';

const OUTPUT_DATE_FORMAT = 'yyyy-MM-dd';
const INPUT_DATE_FORMAT = 'yyyy/MM/dd';

interface Props {
	disabled?: boolean;
	onChange: (payload: {type: string; value: string}) => void;
	propertyLabel: string;
	propertyType: string;
	value?: string;
}

function DateTimeInput({
	disabled,
	onChange,
	propertyLabel,
	propertyType,
	value: initialValue,
}: Props) {
	const [expanded, setExpanded] = useState(false);

	const [value, setValue] = useState(() => {
		let nextValue = initialValue || '';

		if (propertyType !== PROPERTY_TYPES.DATE_TIME) {
			nextValue = parse(
				nextValue,
				OUTPUT_DATE_FORMAT,
				new Date()
			).toISOString();
		}

		return format(new Date(nextValue), INPUT_DATE_FORMAT);
	});

	const previousValue = usePrevious(value);

	const saveDateTimeValue = () => {
		const dateObj = parseISO(value.replace(/\//g, '-'));

		let dateInput = '';
		let dateOutput = '';

		if (isValid(dateObj)) {
			dateInput = format(new Date(value), INPUT_DATE_FORMAT);
			dateOutput = format(new Date(value), OUTPUT_DATE_FORMAT);
		}
		else {
			dateInput = format(new Date(), INPUT_DATE_FORMAT);
			dateOutput = format(new Date(), OUTPUT_DATE_FORMAT);
		}

		if (previousValue !== dateInput || !isValid(dateObj)) {
			setValue(dateInput);

			if (propertyType === PROPERTY_TYPES.DATE_TIME) {
				dateOutput = parse(
					dateOutput,
					OUTPUT_DATE_FORMAT,
					new Date()
				).toISOString();
			}

			onChange({
				type: propertyType,
				value: dateOutput,
			});
		}
	};

	const onExpandedChange = (nextExpanded: boolean) => {
		setExpanded(nextExpanded);

		if (!nextExpanded) {
			saveDateTimeValue();
		}
	};

	return (
		<div className="criterion-input date-input">
			<ClayDatePicker
				ariaLabels={{
					buttonChooseDate: `${propertyLabel}: ${Liferay.Language.get(
						'select-date'
					)}`,
					buttonDot: `${Liferay.Language.get('select-current-date')}`,
					buttonNextMonth: `${Liferay.Language.get(
						'select-next-month'
					)}`,
					buttonPreviousMonth: `${Liferay.Language.get(
						'select-previous-month'
					)}`,
					dialog: `${Liferay.Language.get('select-date')}`,
					input: `${propertyLabel}: ${Liferay.Language.get(
						'input-a-value'
					)}`,
				}}
				data-testid="date-input"
				dateFormat="yyyy/MM/dd"
				disabled={disabled}
				expanded={expanded}
				months={[
					`${Liferay.Language.get('january')}`,
					`${Liferay.Language.get('february')}`,
					`${Liferay.Language.get('march')}`,
					`${Liferay.Language.get('april')}`,
					`${Liferay.Language.get('may')}`,
					`${Liferay.Language.get('june')}`,
					`${Liferay.Language.get('july')}`,
					`${Liferay.Language.get('august')}`,
					`${Liferay.Language.get('september')}`,
					`${Liferay.Language.get('october')}`,
					`${Liferay.Language.get('november')}`,
					`${Liferay.Language.get('december')}`,
				]}
				onBlur={saveDateTimeValue}
				onChange={setValue}
				onExpandedChange={onExpandedChange}
				value={value}
				years={{
					end: new Date().getFullYear(),
					start: 1900,
				}}
			/>
		</div>
	);
}

export default DateTimeInput;
