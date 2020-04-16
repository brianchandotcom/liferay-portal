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
import moment from 'moment';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {createAutoCorrectedDatePipe} from 'text-mask-addons';
import {createTextMaskInputElement} from 'text-mask-core';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';

const getInputMask = (dateFormat, dateDelimiter) => {
	const inputMaskArray = [];

	dateFormat.split('').forEach(item => {
		if (item === dateDelimiter) {
			inputMaskArray.push(dateDelimiter);
		}
		else if (item === 'Y') {
			inputMaskArray.push(/\d/);
			inputMaskArray.push(/\d/);
			inputMaskArray.push(/\d/);
			inputMaskArray.push(/\d/);
		}
		else if (item === 'd' || item === 'm') {
			inputMaskArray.push(/\d/);
			inputMaskArray.push(/\d/);
		}
	});

	return inputMaskArray;
};

const getDateMask = (dateFormat, dateDelimiter) => {
	return dateFormat
		.split(dateDelimiter)
		.map(item => {
			let currentFormat;

			if (item === '%Y') {
				currentFormat = 'YYYY';
			}
			else if (item === '%m') {
				currentFormat = 'MM';
			}
			else {
				currentFormat = 'DD';
			}

			return currentFormat;
		})
		.join(dateDelimiter);
};

const getDelimiter = dateFormat => {
	let dateDelimiter = '/';

	if (dateFormat.indexOf('.') != -1) {
		dateDelimiter = '.';
	}

	if (dateFormat.indexOf('-') != -1) {
		dateDelimiter = '-';
	}

	return dateDelimiter;
};

const useDateFormat = () => {
	return useMemo(() => {
		const dateFormat = Liferay.AUI.getDateFormat();
		const dateDelimiter = getDelimiter(dateFormat);

		return {
			dateMask: getDateMask(dateFormat, dateDelimiter),
			inputMask: getInputMask(dateFormat, dateDelimiter),
		};
	}, []);
};

const transformToDate = date => {
	if (typeof date === 'string' && date.indexOf('_') === -1 && date !== '') {
		return moment(date).toDate();
	}

	return date;
};

const getInitialMonth = value => {
	if (moment(value).isValid()) {
		return moment(value).toDate();
	}

	return moment().toDate();
};

const getValueForHidden = value => {
	if (moment(value).isValid()) {
		return moment(value).format('YYYY-MM-DD');
	}

	return null;
};

const DatePicker = ({
	disabled,
	name,
	onChange,
	spritemap,
	value: initialValue,
}) => {
	const inputRef = useRef(null);
	const maskInstance = useRef(null);

	const initialValueMemoized = useMemo(() => transformToDate(initialValue), [
		initialValue,
	]);

	const [value, setValue] = useSyncValue(initialValueMemoized);
	const [years, setYears] = useState(() => {
		const currentYear = new Date().getFullYear();

		return {
			end: currentYear + 5,
			start: currentYear - 5,
		};
	});

	const {dateMask, inputMask} = useDateFormat();

	useEffect(() => {
		if (inputRef.current && inputMask && dateMask) {
			maskInstance.current = createTextMaskInputElement({
				guide: true,
				inputElement: inputRef.current,
				keepCharPositions: true,
				mask: inputMask,
				pipe: createAutoCorrectedDatePipe(dateMask.toLowerCase()),
				showMask: true,
			});
			maskInstance.current.update(inputRef.current.value);
		}
	}, [inputMask, dateMask, inputRef]);

	const handleNavigation = date => {
		const currentYear = date.getFullYear();

		setYears({
			end: currentYear + 5,
			start: currentYear - 5,
		});
	};

	return (
		<>
			<input
				aria-label="date_picker_hidden"
				name={name}
				type="hidden"
				value={getValueForHidden(value)}
			/>
			<ClayDatePicker
				dateFormat={dateMask}
				disabled={disabled}
				initialMonth={getInitialMonth(value)}
				onInput={event => {
					maskInstance.current.update(event.target.value);
				}}
				onNavigation={handleNavigation}
				onValueChange={value => {
					setValue(value);

					if (moment(value).isValid()) {
						onChange(value);
					}
				}}
				ref={inputRef}
				spritemap={spritemap}
				value={value}
				years={years}
			/>
		</>
	);
};

const DatePickerProxy = connectStore(
	({
		emit,
		name,
		placeholder,
		predefinedValue,
		readOnly,
		spritemap,
		value,
		...otherProps
	}) => (
		<FieldBaseProxy
			{...otherProps}
			name={name}
			readOnly={readOnly}
			spritemap={spritemap}
		>
			<DatePicker
				disabled={readOnly}
				name={name}
				onChange={value => emit('fieldEdited', {}, value)}
				placeholder={placeholder}
				spritemap={spritemap}
				value={value ? value : predefinedValue}
			/>
		</FieldBaseProxy>
	)
);

const ReactDatePickerAdapter = getConnectedReactComponentAdapter(
	DatePickerProxy,
	'date'
);

export {ReactDatePickerAdapter};

export default ReactDatePickerAdapter;
