/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import ClayIcon from '@clayui/icon';
import dayGridPlugin from '@fullcalendar/daygrid';
import FullCalendar from '@fullcalendar/react';
import classNames from 'classnames';
import {dateUtils} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {ITask, ITaskObjectEntry} from '../../../../utils/types';
import {UPDATE_TASKS_QUICK_FILTER_VISIBILITY} from '../../../task/TasksQuickFilters';
import CalendarMoreLinkPopover from './CalendarMoreLinkPopover';
import CalendarTaskCard from './CalendarTaskCard';

import './CalendarView.scss';

import type {FirstDayOfWeekLocale} from 'frontend-js-web';

interface CalendarViewProps {
	items: ITask[];
}

interface MoreLinkPopover {
	alignElement: HTMLElement;
	date: Date;
	tasks: ITaskObjectEntry[];
}

export default function CalendarView({items}: CalendarViewProps) {
	const calendarRef = useRef<FullCalendar>(null);

	const [datePickerExpanded, setDatePickerExpanded] = useState(false);
	const [datePickerValue, setDatePickerValue] = useState('');
	const [moreLinkPopover, setMoreLinkPopover] =
		useState<MoreLinkPopover | null>(null);
	const [title, setTitle] = useState('');

	const events = useMemo(
		() =>
			items
				.filter((item) => item.embedded?.dueDate)
				.map((item) => ({
					allDay: true,

					// Attach the full task entry to the event so the custom
					// renderers (eventContent and the "more" popover) can read
					// it back through event.extendedProps.

					extendedProps: {task: item.embedded},
					id: String(item.embedded.id),
					start: item.embedded.dueDate.slice(0, 10),
					title: item.embedded.title,
				})),
		[items]
	);

	const currentYear = new Date().getFullYear();
	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	useEffect(() => {
		Liferay.fire(UPDATE_TASKS_QUICK_FILTER_VISIBILITY, {visible: false});

		return () => {
			Liferay.fire(UPDATE_TASKS_QUICK_FILTER_VISIBILITY, {visible: true});
		};
	}, []);

	return (
		<div className="lfr__calendar-view">
			<div className="lfr__calendar-view-toolbar">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('previous-month')}
					borderless
					displayType="secondary"
					onClick={() => calendarRef.current?.getApi().prev()}
					size="sm"
					symbol="angle-left"
				/>

				<div className="lfr__calendar-view-toolbar-date-picker">
					<ClayButton
						aria-expanded={datePickerExpanded}
						aria-haspopup="dialog"
						borderless
						className={classNames(
							'lfr__calendar-view-toolbar-title',
							{
								active: datePickerExpanded,
							}
						)}
						data-testid="calendarTitle"
						displayType="secondary"
						onClick={() =>
							setDatePickerExpanded((expanded) => !expanded)
						}
					>
						{title}

						<span className="inline-item inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</ClayButton>

					{/* "inert" is spread because React 18.2 lacks JSX support for it (added in 18.3) and the build's DOM types omit the property. */}

					<div {...{inert: ''}}>
						<ClayDatePicker
							ariaLabels={{
								buttonChooseDate:
									Liferay.Language.get('select-date'),
								buttonDot: Liferay.Language.get(
									'select-current-date'
								),
								buttonNextMonth:
									Liferay.Language.get('select-next-month'),
								buttonPreviousMonth: Liferay.Language.get(
									'select-previous-month'
								),
								dialog: Liferay.Language.get('select-date'),
								selectMonth:
									Liferay.Language.get('select-a-month'),
								selectYear:
									Liferay.Language.get('select-a-year'),
							}}
							dateFormat="yyyy-MM-dd"
							expanded={datePickerExpanded}
							firstDayOfWeek={dateUtils.getFirstDayOfWeek(
								locale as FirstDayOfWeekLocale
							)}
							months={dateUtils.getMonthsLong(locale)}
							onChange={(value) => {
								setDatePickerValue(value);

								if (value) {
									calendarRef.current
										?.getApi()
										.gotoDate(value);

									setDatePickerExpanded(false);
								}
							}}
							onExpandedChange={setDatePickerExpanded}
							value={datePickerValue}
							weekdaysShort={dateUtils.getWeekdaysShort(locale)}
							years={{
								end: currentYear + 10,
								start: currentYear - 10,
							}}
						/>
					</div>
				</div>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('next-month')}
					borderless
					displayType="secondary"
					onClick={() => calendarRef.current?.getApi().next()}
					symbol="angle-right"
				/>

				<ClayButton
					displayType="secondary"
					onClick={() => calendarRef.current?.getApi().today()}
					size="sm"
				>
					{Liferay.Language.get('today')}
				</ClayButton>
			</div>

			<FullCalendar
				datesSet={({view}) => setTitle(view.title)}
				dayHeaderFormat={{weekday: 'long'}}
				dayMaxEvents
				eventContent={(arg) => (
					<CalendarTaskCard task={arg.event.extendedProps.task} />
				)}
				events={events}
				headerToolbar={false}
				initialView="dayGridMonth"
				moreLinkClick={(arg) => {
					setMoreLinkPopover({
						alignElement: arg.jsEvent.currentTarget as HTMLElement,
						date: arg.date,
						tasks: arg.hiddenSegs.map(
							(seg) => seg.event.extendedProps.task
						),
					});

					// Prevent FullCalendar's built-in popover from opening.
					// It stays closed only when the handler returns a truthy
					// value other than "popover". The return type is
					// "string | void", which rejects a boolean, so "true" is
					// force-cast to void for the compiler; at runtime the
					// value is still true.

					return true as unknown as void;
				}}
				moreLinkContent={(arg) => (
					<ClayButton borderless displayType="secondary" size="xs">
						{`${arg.num} ${Liferay.Language.get('more')}`}

						<span className="inline-item inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</ClayButton>
				)}
				plugins={[dayGridPlugin]}
				ref={calendarRef}
			/>

			{moreLinkPopover && (
				<CalendarMoreLinkPopover
					alignElement={moreLinkPopover.alignElement}
					onClose={() => setMoreLinkPopover(null)}
					tasks={moreLinkPopover.tasks}
				/>
			)}
		</div>
	);
}
