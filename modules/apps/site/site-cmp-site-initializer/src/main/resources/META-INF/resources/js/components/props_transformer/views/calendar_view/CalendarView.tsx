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
import React, {useEffect, useRef, useState} from 'react';

import {ITask} from '../../../../utils/types';
import {UPDATE_TASKS_QUICK_FILTER_VISIBILITY} from '../../../task/TasksQuickFilters';

import './CalendarView.scss';

interface CalendarViewProps {
	items: ITask[];
}

export default function CalendarView({items}: CalendarViewProps) {
	const calendarRef = useRef<FullCalendar>(null);

	const [datePickerExpanded, setDatePickerExpanded] = useState(false);
	const [datePickerValue, setDatePickerValue] = useState('');
	const [title, setTitle] = useState('');

	const events = items
		.filter((item) => item.embedded?.dueDate)
		.map((item) => ({
			allDay: true,
			id: String(item.embedded.id),
			start: item.embedded.dueDate.slice(0, 10),
			title: item.embedded.title,
		}));

	useEffect(() => {
		Liferay.fire(UPDATE_TASKS_QUICK_FILTER_VISIBILITY, {visible: false});

		return () => {
			Liferay.fire(UPDATE_TASKS_QUICK_FILTER_VISIBILITY, {visible: true});
		};
	}, []);

	const currentYear = new Date().getFullYear();

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

					<ClayDatePicker
						dateFormat="yyyy-MM-dd"
						expanded={datePickerExpanded}
						onChange={(value) => {
							setDatePickerValue(value);

							if (value) {
								calendarRef.current?.getApi().gotoDate(value);

								setDatePickerExpanded(false);
							}
						}}
						onExpandedChange={setDatePickerExpanded}
						value={datePickerValue}
						years={{
							end: currentYear + 10,
							start: currentYear - 10,
						}}
					/>
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
				events={events}
				headerToolbar={false}
				initialView="dayGridMonth"
				plugins={[dayGridPlugin]}
				ref={calendarRef}
			/>
		</div>
	);
}
