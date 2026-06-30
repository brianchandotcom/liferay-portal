/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {IItemsActions} from '@liferay/frontend-data-set-web';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import classNames from 'classnames';
import {navigate} from 'frontend-js-web';
import React from 'react';

import getActionURL from '../../../../../utils/getActionURL';
import isOverdue from '../../../../../utils/isOverdue';
import {ITaskObjectEntry} from '../../../../../utils/types';

import './CalendarTaskCard.scss';

interface CalendarTaskCardProps {
	itemsActions?: IItemsActions[];
	task: ITaskObjectEntry;
}

export default function CalendarTaskCard({
	itemsActions = [],
	task,
}: CalendarTaskCardProps) {
	const {assignTo, dueDate, state, title} = task;

	const blocked = state?.key === 'blocked';
	const overdue = isOverdue({dueDate, state});

	const hasViewPermission = Boolean(task.actions?.get);

	const handleViewTask = () => {
		const viewURL = getActionURL({
			actionId: 'actionLink',
			itemsActions,
			task: {embedded: task},
		});

		if (viewURL) {
			navigate(viewURL);
		}
	};

	return (
		<div
			className={classNames('lfr__cmp-calendar-task-card', {
				'lfr__cmp-calendar-task-card-clickable': hasViewPermission,
				'lfr__cmp-calendar-task-card-state-overdue': overdue,
				[`lfr__cmp-calendar-task-card-state-${state?.key}`]:
					!overdue && state?.key,
			})}
			onClick={hasViewPermission ? handleViewTask : undefined}
			onKeyDown={
				hasViewPermission
					? (event) => {
							if (event.key === 'Enter' || event.key === ' ') {
								event.preventDefault();

								handleViewTask();
							}
						}
					: undefined
			}
			role={hasViewPermission ? 'button' : undefined}
			tabIndex={hasViewPermission ? 0 : undefined}
		>
			<span className="lfr__cmp-calendar-task-card-title">{title}</span>

			{blocked && !overdue && (
				<ClayIcon
					className="lfr__cmp-calendar-task-card-icon lfr__cmp-calendar-task-card-icon-blocked"
					symbol="block"
				/>
			)}

			{overdue && (
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
