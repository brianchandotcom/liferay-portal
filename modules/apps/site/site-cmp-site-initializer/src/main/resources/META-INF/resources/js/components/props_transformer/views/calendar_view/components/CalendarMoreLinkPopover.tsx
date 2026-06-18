/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {Immutable} from '@liferay/frontend-js-state-web';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import React from 'react';

import isOverdue from '../../../../../utils/isOverdue';
import {ITaskObjectEntry} from '../../../../../utils/types';
import StateLabel from '../../../../StateLabel';
import sortTasksByPriority from '../utils/sortTasksByPriority';

import './CalendarMoreLinkPopover.scss';

function getDisplayState(task: Immutable<ITaskObjectEntry>) {
	if (isOverdue(task)) {
		return {key: 'overdue', name: Liferay.Language.get('overdue')};
	}

	return task.state;
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
	const sortedTasks = sortTasksByPriority(tasks);

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
