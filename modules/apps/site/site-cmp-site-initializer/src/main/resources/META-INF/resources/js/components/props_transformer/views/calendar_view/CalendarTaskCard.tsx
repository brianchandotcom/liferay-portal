/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import classNames from 'classnames';
import React from 'react';

import {ITaskObjectEntry} from '../../../../utils/types';

import './CalendarTaskCard.scss';

interface CalendarTaskCardProps {
	task: ITaskObjectEntry;
}

export default function CalendarTaskCard({task}: CalendarTaskCardProps) {
	const {assignTo, dueDate, state, title} = task;

	const isBlocked = state?.key === 'blocked';

	const isOverdue =
		Boolean(dueDate) &&
		state?.key !== 'done' &&
		dueDate.slice(0, 10) < new Date().toISOString().slice(0, 10);

	return (
		<div
			className={classNames(
				'lfr__cmp-calendar-task-card',
				isOverdue
					? 'lfr__cmp-calendar-task-card-overdue'
					: state?.key && `lfr__cmp-calendar-task-card-${state.key}`
			)}
		>
			<span className="lfr__cmp-calendar-task-card-title">{title}</span>

			{isBlocked && !isOverdue && (
				<ClayIcon
					className="lfr__cmp-calendar-task-card-icon lfr__cmp-calendar-task-card-icon-blocked"
					symbol="block"
				/>
			)}

			{isOverdue && (
				<ClayIcon
					className="lfr__cmp-calendar-task-card-icon lfr__cmp-calendar-task-card-icon-overdue"
					symbol="exclamation-full"
				/>
			)}

			<span className="lfr__cmp-calendar-task-card-assignee">
				<AssigneeAvatar
					name={assignTo?.name}
					portrait={assignTo?.portrait}
				/>
			</span>
		</div>
	);
}
