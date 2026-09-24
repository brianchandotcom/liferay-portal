/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import DateFilter from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/date_filter';
import {
	DateFilterValues,
	LastRange,
	Range,
} from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/date_filter/types';

const DAY = 24 * 60 * 60 * 1000;

const FUTURE_DATE = new Date(Date.now() + DAY);

const PAST_DATE = new Date(Date.now() - DAY);

const PAST_DATE_STRING = PAST_DATE.toISOString().slice(0, 10);

const TODAY = new Date();

function toDisplayDateString(dateString: string) {
	const [year, month, day] = dateString.split('-');

	return `${month}/${day}/${year}`;
}

function ControlledDateFilter({
	appliedValue: initialAppliedValue,
	lastPublishDate,
	onApplyFilter,
}: {
	appliedValue?: DateFilterValues;
	lastPublishDate?: string;
	onApplyFilter: (dateFilterValues: DateFilterValues) => void;
}) {
	const [appliedValue, setAppliedValue] = useState<DateFilterValues>(
		initialAppliedValue ?? {range: Range.All}
	);

	return (
		<DateFilter
			appliedValue={appliedValue}
			lastPublishDate={lastPublishDate}
			onApplyFilter={(dateFilterValues) => {
				setAppliedValue(dateFilterValues);
				onApplyFilter(dateFilterValues);
			}}
			timeZoneId="UTC"
		/>
	);
}

describe('DateFilter', () => {
	const renderDateFilter = ({
		appliedValue,
		lastPublishDate,
		onApplyFilter = jest.fn(),
	}: {
		appliedValue?: DateFilterValues;
		lastPublishDate?: string;
		onApplyFilter?: jest.Mock;
	} = {}) => {
		const user = userEvent.setup({delay: null});

		render(
			<ControlledDateFilter
				appliedValue={appliedValue}
				lastPublishDate={lastPublishDate}
				onApplyFilter={onApplyFilter}
			/>
		);

		return {onApplyFilter, user};
	};

	const pickCalendarDay = async (
		user: ReturnType<typeof userEvent.setup>,
		label: string,
		date: Date
	) => {
		const chooseDateButton = within(
			screen.getByLabelText(label).closest('.date-picker') as HTMLElement
		).getByRole('button', {name: 'Choose date'});

		await user.click(chooseDateButton);

		const calendar = document.getElementById(
			chooseDateButton.getAttribute('aria-controls') as string
		) as HTMLElement;

		if (!within(calendar).queryByLabelText(date.toDateString())) {
			await user.click(
				within(calendar).getByRole('button', {
					name:
						date.getTime() < Date.now()
							? 'Select the previous month'
							: 'Select the next month',
				})
			);
		}

		await user.click(within(calendar).getByLabelText(date.toDateString()));

		await user.click(document.body);
	};

	it('renders in initial state without Show Results button', () => {
		renderDateFilter();

		expect(screen.getByLabelText('filter-content-by')).toHaveValue(
			Range.All
		);
		expect(screen.queryByText('show-results')).not.toBeInTheDocument();
	});

	it('shows Modified Last options and enables the apply button when selected', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.Last
		);

		expect(screen.getByLabelText('modified-last')).toBeInTheDocument();
		expect(screen.getByText('show-results')).toBeInTheDocument();
	});

	it('shows Date Range fields when selected', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		expect(screen.getByLabelText('from')).toBeInTheDocument();
		expect(screen.getByLabelText('to[date-time]')).toBeInTheDocument();
	});

	it('shows the portal locale clock in the date range placeholders', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		expect(screen.getByLabelText('from')).toHaveAttribute(
			'placeholder',
			'MM/DD/YYYY HH:MM AM'
		);
		expect(screen.getByLabelText('to[date-time]')).toHaveAttribute(
			'placeholder',
			'MM/DD/YYYY HH:MM AM'
		);
	});

	it('fills the start and end of the day when the date range bounds are picked without a time', async () => {
		const {onApplyFilter, user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		fireEvent.change(screen.getByLabelText('from'), {
			target: {
				value: `${toDisplayDateString(PAST_DATE_STRING)} --:-- --`,
			},
		});

		expect(screen.getByLabelText('from')).toHaveValue(
			`${toDisplayDateString(PAST_DATE_STRING)} 12:00 AM`
		);

		fireEvent.change(screen.getByLabelText('to[date-time]'), {
			target: {
				value: `${toDisplayDateString(PAST_DATE_STRING)} --:-- --`,
			},
		});

		expect(screen.getByLabelText('to[date-time]')).toHaveValue(
			`${toDisplayDateString(PAST_DATE_STRING)} 11:59 PM`
		);

		await user.click(screen.getByText('show-results'));

		expect(onApplyFilter).toHaveBeenCalledWith({
			endDate: `${PAST_DATE_STRING} 23:59`,
			range: Range.DateRange,
			startDate: `${PAST_DATE_STRING} 00:00`,
		});
	});

	it('flags both bounds as soon as the range is complete and inverted', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		await user.click(screen.getByLabelText('from'));

		await user.paste('01/02/2026 08:00 AM');
		await user.click(screen.getByLabelText('to[date-time]'));

		await user.paste('01/01/2026 08:00 AM');

		expect(screen.getAllByText('date-range-is-invalid')).toHaveLength(2);
		expect(screen.getByText('show-results')).toBeDisabled();
	});

	it('flags a future day picked from the calendar', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		await pickCalendarDay(user, 'from', FUTURE_DATE);

		expect(
			screen.getByText('dates-must-not-be-in-the-future')
		).toBeInTheDocument();
		expect(screen.getByText('show-results')).toBeDisabled();
	});

	it('flags the end of today that the To calendar fills in', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		await pickCalendarDay(user, 'from', PAST_DATE);
		await pickCalendarDay(user, 'to[date-time]', TODAY);

		expect(
			screen.getByText('dates-must-not-be-in-the-future')
		).toBeInTheDocument();
		expect(screen.getByText('show-results')).toBeDisabled();
	});

	it('explains why an incomplete bound blocks the results', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		await user.click(screen.getByLabelText('from'));

		await user.paste('01/01/2026');

		expect(
			screen.getByText('please-enter-a-valid-date')
		).toBeInTheDocument();
		expect(screen.getByText('show-results')).toBeDisabled();
	});

	it('calls onApplyFilter with correct values when applying a Modified Last filter', async () => {
		const {onApplyFilter, user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.Last
		);
		await user.selectOptions(
			screen.getByLabelText('modified-last'),
			LastRange.H24
		);

		await user.click(screen.getByText('show-results'));

		expect(onApplyFilter).toHaveBeenCalledWith({
			last: LastRange.H24,
			range: Range.Last,
		});

		expect(screen.getByText('show-results')).toBeDisabled();
	});

	it('resets the date range fields when filters are cleared', async () => {
		const {onApplyFilter, user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		await user.click(screen.getByLabelText('from'));

		await user.paste('01/01/2026 08:00 AM');
		await user.click(screen.getByLabelText('to[date-time]'));

		await user.paste('01/02/2026 08:00 AM');

		await user.click(screen.getByText('show-results'));

		expect(onApplyFilter).toHaveBeenCalledWith({
			endDate: '2026-01-02 08:00',
			range: Range.DateRange,
			startDate: '2026-01-01 08:00',
		});

		expect(screen.getByLabelText('from')).toHaveValue(
			'01/01/2026 08:00 AM'
		);
		expect(screen.getByLabelText('from')).toBeEnabled();
		expect(screen.getByLabelText('to[date-time]')).toHaveValue(
			'01/02/2026 08:00 AM'
		);
		expect(screen.getByLabelText('to[date-time]')).toBeEnabled();

		await user.click(screen.getByText('clear-filters'));

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.DateRange
		);

		expect(screen.getByLabelText('from')).toHaveValue('');
		expect(screen.getByLabelText('to[date-time]')).toHaveValue('');
	});

	it('shows an alert summary and clears filters correctly', async () => {
		const {onApplyFilter, user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.Last
		);
		await user.click(screen.getByText('show-results'));

		expect(screen.getByRole('alert')).toBeInTheDocument();

		await user.click(screen.getByText('clear-filters'));

		expect(onApplyFilter).toHaveBeenLastCalledWith({
			range: Range.All,
		});

		expect(screen.getByLabelText('filter-content-by')).toHaveValue(
			Range.All
		);
		expect(screen.queryByRole('alert')).not.toBeInTheDocument();
	});

	it('does not offer the from last publish date range without a last publish date', () => {
		renderDateFilter();

		expect(
			screen.queryByRole('option', {name: 'from-last-publish-date'})
		).not.toBeInTheDocument();
	});

	it('keeps a seeded from last publish date range selectable without a last publish date', () => {
		renderDateFilter({appliedValue: {range: Range.FromLastPublishDate}});

		expect(screen.getByLabelText('filter-content-by')).toHaveValue(
			Range.FromLastPublishDate
		);
		expect(
			screen.getByRole('option', {name: 'from-last-publish-date'})
		).toBeInTheDocument();
	});

	it('applies the from last publish date range', async () => {
		const {onApplyFilter, user} = renderDateFilter({
			lastPublishDate: '2026-07-20T15:30:00Z',
		});

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			Range.FromLastPublishDate
		);

		await user.click(screen.getByText('show-results'));

		expect(onApplyFilter).toHaveBeenCalledWith({
			range: Range.FromLastPublishDate,
		});
	});
});
