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

import classNames from 'classnames';
import {Fragment} from 'react';

import {useSidebarTask} from '../../../hooks/useSidebarTask';
import i18n from '../../../i18n';
import {TestraySubTask, TestrayTaskUser} from '../../../services/rest';
import {StatusesProgressScore, chartClassNames} from '../../../util/constants';
import TaskbarProgress from '../../ProgressBar/TaskbarProgress';

type TaskBadgeProps = {
	className?: string;
	count: number;
};

type TaskSidebarProps = {
	expanded: boolean;
};

type SubtaskCardProps = {
	expanded: boolean;
	subtask: {
		name: string;
		score: number;
	};
};

const TaskBadge: React.FC<TaskBadgeProps> = ({className, count}) => (
	<div
		className={classNames(
			'align-items-center d-flex justify-content-center quantity-task-badge',
			className
		)}
	>
		{count}
	</div>
);

const SubtaskCard: React.FC<SubtaskCardProps> = ({expanded, subtask}) => (
	<div className="align-items-center d-flex justify-content-between mb-2 px-2 py-3 subtask-sidebar text-nowrap">
		<div>{subtask.name}</div>

		{expanded && (
			<>
				<div>{i18n.translate('score')}</div>

				<div>{subtask.score}</div>
			</>
		)}
	</div>
);

const TaskSidebar: React.FC<TaskSidebarProps> = ({expanded}) => {
	const {displayTask, setSelectedTask, subTasks, tasks} = useSidebarTask();

	const justifyContentSidebar = {
		'justify-content-between': expanded,
		'justify-content-center': !expanded,
	};
	if (!tasks) {
		return null;
	}

	return (
		<div className="task-sidebar">
			<div
				className={classNames(
					'align-items-center d-flex task-sidebar-title',
					justifyContentSidebar
				)}
			>
				{expanded && (
					<span className="font-weight-bold">
						{i18n.translate('tasks')}
					</span>
				)}

				<TaskBadge count={tasks.length} />
			</div>

			<div className="d-flex flex-column mb-1">
				<div
					className={classNames('d-flex mb-2', justifyContentSidebar)}
				>
					<TaskBadge
						className={classNames({
							'mr-3': expanded,
						})}
						count={subTasks.length as number}
					/>

					<div>{displayTask?.task?.name}</div>
				</div>

				<div>{displayTask?.task?.build?.name}</div>

				<TaskbarProgress
					displayTotalCompleted
					items={[
						[StatusesProgressScore.SELF, 60],
						[StatusesProgressScore.INCOMPLETE, Number(30)],
					]}
					taskbarClassNames={chartClassNames}
				/>
			</div>

			{subTasks.map((subtask: TestraySubTask, index) => (
				<div key={index}>
					<SubtaskCard
						expanded={expanded}
						subtask={{
							name: subtask?.name,
							score: subtask.score,
						}}
					/>
				</div>
			))}

			{tasks.map((taskUser: TestrayTaskUser, index) => (
				<div className="mb-5 mt-6" key={index}>
					{expanded && (
						<div
							className="d-flex justify-content-between"
							onClick={() => setSelectedTask(taskUser.id)}
						>
							<span>{taskUser?.task?.name}</span>
						</div>
					)}

					{expanded && <div>{taskUser?.task?.build?.name}</div>}
				</div>
			))}
		</div>
	);
};
export default TaskSidebar;
