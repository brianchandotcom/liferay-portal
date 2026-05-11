/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {EEntityFieldType} from '../utils/types';

import type {
	FilterImplementation,
	FilterImplementationArgs,
	IOdataStringArgs,
	ISelectedItemsLabelArgs,
} from '../Filter';

export interface DateTimeRangeFilterImplementationArgs
	extends FilterImplementationArgs<SelectedData> {
	entityFieldType: EEntityFieldType;
	max?: DateTime;
	min?: DateTime;
	placeholder?: string;
}

interface DateTime {
	day: number;
	hour: number;
	minute: number;
	month: number;
	year: number;
}

interface SelectedData {
	from: DateTime | null;
	to: DateTime | null;
}

const pad2 = (value: number) => String(value).padStart(2, '0');

function formatDateTimeObject(dateTime: DateTime): string {
	return `${dateTime.year}-${pad2(dateTime.month)}-${pad2(dateTime.day)}T${pad2(
		dateTime.hour
	)}:${pad2(dateTime.minute)}`;
}

function getDateTimeFromDateTimeString(value: string): DateTime | null {
	const match = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2})$/.exec(value);

	if (!match) {
		return null;
	}

	return {
		day: Number(match[3]),
		hour: Number(match[4]),
		minute: Number(match[5]),
		month: Number(match[2]),
		year: Number(match[1]),
	};
}

function getTimeZoneOffset(timeZone: string, atDate: Date): string {
	try {
		const formatter = new Intl.DateTimeFormat('en-US', {
			timeZone,
			timeZoneName: 'longOffset',
		});

		const part = formatter
			.formatToParts(atDate)
			.find((p) => p.type === 'timeZoneName');

		if (!part) {
			return '+00:00';
		}

		const match = /^GMT([+-])(\d{1,2})(?::(\d{2}))?$/.exec(part.value);

		if (match) {
			const sign = match[1];
			const hours = match[2].padStart(2, '0');
			const minutes = (match[3] || '00').padStart(2, '0');

			return `${sign}${hours}:${minutes}`;
		}
	}
	catch {

		// Intl.DateTimeFormat may throw for invalid timezones — fall through.

	}

	return '+00:00';
}

function zonedPartsToOdataDateTime(parts: DateTime, timeZone: string): string {
	const asUTC = new Date(
		Date.UTC(
			parts.year,
			parts.month - 1,
			parts.day,
			parts.hour,
			parts.minute
		)
	);

	const offset = getTimeZoneOffset(timeZone, asUTC);

	return `${parts.year}-${pad2(parts.month)}-${pad2(parts.day)}T${pad2(
		parts.hour
	)}:${pad2(parts.minute)}:00${offset}`;
}

function getSelectedItemsLabel({
	selectedData,
}: ISelectedItemsLabelArgs): string {
	const {from, to} = selectedData as unknown as SelectedData;

	const format = (parts: DateTime) =>
		`${parts.year}-${pad2(parts.month)}-${pad2(parts.day)} ${pad2(
			parts.hour
		)}:${pad2(parts.minute)}`;

	if (from && to) {
		return `${format(from)} - ${format(to)}`;
	}

	if (from) {
		return `${Liferay.Language.get('from')} ${format(from)}`;
	}

	if (to) {
		return `${Liferay.Language.get('to[date-time]')} ${format(to)}`;
	}

	return '';
}

function getOdataString({id, selectedData}: IOdataStringArgs): string {
	const {from, to} = selectedData as unknown as SelectedData;

	const timeZone = Liferay.ThemeDisplay.getTimeZone();

	const fromOdataString = from && zonedPartsToOdataDateTime(from, timeZone);
	const toOdataString = to && zonedPartsToOdataDateTime(to, timeZone);

	if (from && to) {
		return `${id} ge ${fromOdataString}) and (${id} le ${toOdataString}`;
	}

	if (from) {
		return `${id} ge ${fromOdataString}`;
	}

	if (to) {
		return `${id} le ${toOdataString}`;
	}

	return '';
}

const DateTimeRangeFilter = ({
	id,
	max,
	min,
	placeholder,
	selectedData,
	setFilter,
}: DateTimeRangeFilterImplementationArgs) => {
	const [fromValue, setFromValue] = useState(
		selectedData?.from && formatDateTimeObject(selectedData.from)
	);
	const [toValue, setToValue] = useState(
		selectedData?.to && formatDateTimeObject(selectedData.to)
	);

	const [fromDateTimeValid, setFromDateTimeValid] = useState(true);
	const [toDateTimeValid, setToDateTimeValid] = useState(true);

	let actionType = 'edit';

	if (selectedData && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	const isChanged =
		actionType === 'delete' ||
		((!selectedData || !selectedData.from) && fromValue) ||
		((!selectedData || !selectedData.to) && toValue) ||
		(selectedData &&
			selectedData.from &&
			fromValue !== formatDateTimeObject(selectedData.from)) ||
		(selectedData &&
			selectedData.to &&
			toValue !== formatDateTimeObject(selectedData.to));

	const submitDisabled = !isChanged || !fromDateTimeValid || !toDateTimeValid;

	return (
		<>
			<ClayDropDown.Caption>
				<div className="fds-date-time-range form-group">
					<ClayForm.Group className="form-group-sm">
						<label htmlFor={`from-${id}`}>
							{Liferay.Language.get('from')}
						</label>

						<input
							className="fds-from-date-time form-control"
							id={`from-${id}`}
							max={toValue || (max && formatDateTimeObject(max))}
							min={min && formatDateTimeObject(min)}
							onBlur={(event) => {
								event.target.reportValidity();

								setFromDateTimeValid(
									event.target.checkValidity()
								);
							}}
							onChange={(event) => {
								setFromValue(event.target.value);

								setFromDateTimeValid(
									event.target.checkValidity()
								);
							}}
							pattern="\d{4}-\d{2}-\d{2}T\d{2}:\d{2}"
							placeholder={placeholder || 'yyyy-mm-ddThh:mm'}
							type="datetime-local"
							value={fromValue || ''}
						/>
					</ClayForm.Group>

					<ClayForm.Group className="form-group-sm mt-2">
						<label htmlFor={`to-${id}`}>
							{Liferay.Language.get('to[date-time]')}
						</label>

						<input
							className="fds-to-date-time form-control"
							id={`to-${id}`}
							max={max && formatDateTimeObject(max)}
							min={
								fromValue || (min && formatDateTimeObject(min))
							}
							onBlur={(event) => {
								event.target.reportValidity();

								setToDateTimeValid(
									event.target.checkValidity()
								);
							}}
							onChange={(event) => {
								setToValue(event.target.value);

								setToDateTimeValid(
									event.target.checkValidity()
								);
							}}
							pattern="\d{4}-\d{2}-\d{2}T\d{2}:\d{2}"
							placeholder={placeholder || 'yyyy-mm-ddThh:mm'}
							type="datetime-local"
							value={toValue || ''}
						/>
					</ClayForm.Group>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() => {
						if (actionType === 'delete') {
							setFilter({active: false});
						}
						else {
							const newSelectedData = {
								from: fromValue
									? getDateTimeFromDateTimeString(fromValue)
									: null,
								to: toValue
									? getDateTimeFromDateTimeString(toValue)
									: null,
							};

							setFilter({
								active: true,
								selectedData: newSelectedData,
							});
						}
					}}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}

					{actionType === 'edit' &&
						Liferay.Language.get('show-results')}

					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
};

const dateTimeShape = PropTypes.shape({
	day: PropTypes.number,
	hour: PropTypes.number,
	minute: PropTypes.number,
	month: PropTypes.number,
	year: PropTypes.number,
});

DateTimeRangeFilter.propTypes = {
	id: PropTypes.string.isRequired,
	max: dateTimeShape,
	min: dateTimeShape,
	placeholder: PropTypes.string,
	selectedData: PropTypes.shape({
		from: dateTimeShape,
		to: dateTimeShape,
	}),
};

const filterImplementation: FilterImplementation<DateTimeRangeFilterImplementationArgs> =
	{
		Component: DateTimeRangeFilter,
		getOdataString,
		getSelectedItemsLabel,
	};

export default filterImplementation;
