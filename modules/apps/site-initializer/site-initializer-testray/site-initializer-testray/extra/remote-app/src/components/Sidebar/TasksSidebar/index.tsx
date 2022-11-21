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
import {TestraySubTask, TestrayTask} from '../../../services/rest';
import {StatusesProgressScore, chartClassNames} from '../../../util/constants';
import TaskbarProgress from '../../ProgressBar/TaskbarProgress';

type QuantityTaskBadgeProps = {
	classStyle?: string;
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

const QuantityTaskBadge: React.FC<QuantityTaskBadgeProps> = ({
	classStyle,
	count,
}) => {
	return (
		<div
			className={classNames(
				`align-items-center d-flex justify-content-center quantity-task-badge `,
				classStyle
			)}
		>
			{count}
		</div>
	);
};

const SubtaskCard: React.FC<SubtaskCardProps> = ({expanded, subtask}) => {
	return (
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
};

const TaskSidebar: React.FC<TaskSidebarProps> = ({expanded}) => {
	const {displayTask, setSelectedTask, subTasks, tasks} = useSidebarTask();

	return (
		<>
			{tasks && (
				<div className="task-sidebar">
					<div
						className={classNames(
							'align-items-center d-flex  task-sidebar-title',
							{
								'justify-content-between': expanded,
								'justify-content-center': !expanded,
							}
						)}
					>
						{expanded && (
							<span className="font-weight-bold">
								{i18n.translate('tasks')}
							</span>
						)}

						<QuantityTaskBadge count={tasks.length} />
					</div>

					<div className="d-flex flex-column mb-1">
						<div
							className={classNames('d-flex mb-2', {
								'justify-content-between': expanded,
								'justify-content-center': !expanded,
							})}
						>
							<QuantityTaskBadge
								classStyle={classNames({
									'mr-3': expanded,
								})}
								count={4}
							/>

							<div>{displayTask?.name}</div>
						</div>

						<div>{displayTask?.name}</div>

						<TaskbarProgress
							displayTotalCompleted
							items={[
								[StatusesProgressScore.SELF, 60],
								[StatusesProgressScore.INCOMPLETE, Number(30)],
							]}
							taskbarClassNames={chartClassNames}
						/>
					</div>

					{subTasks &&
						subTasks.map(
							(subtask: TestraySubTask, index: number) => {
								return (
									<div key={index}>
										<SubtaskCard
											expanded={expanded}
											subtask={{
												name: subtask?.name,
												score: subtask.score,
											}}
										/>
									</div>
								);
							}
						)}

					{tasks.map((task: TestrayTask, index: number) => (
						<div className="mb-5 mt-6" key={index}>
							{expanded && (
								<div
									className="d-flex justify-content-between"
									onClick={() => setSelectedTask(task.id)}
								>
									<span>{task.name}</span>
								</div>
							)}

							{expanded && <div>{task?.build?.name}</div>}
						</div>
					))}
				</div>
			)}
		</>
	);
};
export default TaskSidebar;
