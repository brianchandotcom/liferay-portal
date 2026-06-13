/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import React from 'react';

import {ITaskObjectEntry} from '../../../../utils/types';
import StateLabel from '../../../StateLabel';

import './CalendarMoreLinkPopover.scss';

const STATE_PRIORITY: {[key: string]: number} = {
	blocked: 1,
	done: 4,
	inProgress: 2,
	notStarted: 3,
};

const OVERDUE_PRIORITY = 0;

const UNKNOWN_PRIORITY = 5;

function isTaskOverdue(task: ITaskObjectEntry): boolean {
	return (
		Boolean(task.dueDate) &&
		task.state?.key !== 'done' &&
		task.dueDate.slice(0, 10) < new Date().toISOString().slice(0, 10)
	);
}

function getDisplayState(task: ITaskObjectEntry) {
	if (isTaskOverdue(task)) {
		return {key: 'overdue', name: Liferay.Language.get('overdue')};
	}

	return task.state;
}

function getTaskPriority(task: ITaskObjectEntry): number {
	if (isTaskOverdue(task)) {
		return OVERDUE_PRIORITY;
	}

	return STATE_PRIORITY[task.state?.key] ?? UNKNOWN_PRIORITY;
}

interface CalendarMoreLinkPopoverProps {
	alignElement: HTMLElement;
	onClose: () => void;
	tasks: ITaskObjectEntry[];
}

export default function CalendarMoreLinkPopover({
	alignElement,
	onClose,
	tasks,
}: CalendarMoreLinkPopoverProps) {
	const sortedTasks = [...tasks].sort(
		(taskA, taskB) => getTaskPriority(taskA) - getTaskPriority(taskB)
	);

	return (
		<ClayDropDown.Menu
			active
			alignElementRef={{current: alignElement}}
			className="lfr__cmp-calendar-more-link-popover"
			data-testid="calendarMoreLinkPopover"
			onActiveChange={onClose}
		>
			<div className="lfr__cmp-calendar-more-link-popover-tasks">
				{sortedTasks.map((task) => (
					<div
						className="lfr__cmp-calendar-more-link-popover-task"
						key={task.id}
					>
						<span
							className="lfr__cmp-calendar-more-link-popover-task-title"
							data-testid="calendarMoreLinkPopoverTaskTitle"
						>
							{task.title}
						</span>

						<span className="lfr__cmp-calendar-more-link-popover-task-state">
							<StateLabel state={getDisplayState(task)} />
						</span>

						<span className="lfr__cmp-calendar-more-link-popover-task-assignee">
							<AssigneeAvatar
								name={task.assignTo?.name}
								portrait={task.assignTo?.portrait}
							/>
						</span>
					</div>
				))}
			</div>
		</ClayDropDown.Menu>
	);
}
