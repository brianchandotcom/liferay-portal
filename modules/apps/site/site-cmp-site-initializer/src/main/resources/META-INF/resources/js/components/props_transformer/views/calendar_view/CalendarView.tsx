/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dayGridPlugin from '@fullcalendar/daygrid';
import FullCalendar from '@fullcalendar/react';
import React, {useEffect} from 'react';

import {ITask} from '../../../../utils/types';
import {UPDATE_TASKS_QUICK_FILTER_VISIBILITY} from '../../../task/TasksQuickFilters';

import './CalendarView.scss';

interface CalendarViewProps {
	items: ITask[];
}

export default function CalendarView({items}: CalendarViewProps) {
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

	return (
		<div className="lfr__calendar-view">
			<FullCalendar
				dayHeaderFormat={{weekday: 'long'}}
				events={events}
				initialView="dayGridMonth"
				plugins={[dayGridPlugin]}
			/>
		</div>
	);
}
