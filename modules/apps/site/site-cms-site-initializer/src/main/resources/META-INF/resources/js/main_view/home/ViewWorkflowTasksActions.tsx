/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import AssignToMeModalContent from './modal/AssignToMeModalContent';
import AssignToModalContent from './modal/AssignToModalContent';
import TransitionWorkflowStateModelContent from './modal/TransitionWorkflowStateModalContent';
import UpdateDueDateModalContent from './modal/UpdateDueDateModalContent';
import {WorkflowTask} from './types/WorkflowTask';

export default function ViewWorkflowTasks({
	filterType,
	loadData,
	workflowTask,
}: {
	filterType: string;
	loadData: () => Promise<void>;
	workflowTask: WorkflowTask;
}) {
	return (
		<ClayDropdown
			trigger={
				<ClayButton className="m-0" displayType="unstyled" size="sm">
					<span>
						<ClayIcon symbol="ellipsis-v" />
					</span>
				</ClayButton>
			}
		>
			{filterType === 'assigned-to-me' ? (
				<>
					<ClayDropdown.Item
						onClick={() => {
							openModal({
								contentComponent: ({
									closeModal,
								}: {
									closeModal: () => void;
								}) =>
									TransitionWorkflowStateModelContent({
										closeModal,
										loadData,
										transitionName: 'approve',
										workflowTaskId: Number(
											workflowTask.workflowTaskId
										),
									}),
								size: 'md',
							});
						}}
					>
						{Liferay.Language.get('approve')}
					</ClayDropdown.Item>

					<ClayDropdown.Item
						onClick={() => {
							openModal({
								contentComponent: ({
									closeModal,
								}: {
									closeModal: () => void;
								}) =>
									TransitionWorkflowStateModelContent({
										closeModal,
										loadData,
										transitionName: 'reject',
										workflowTaskId: Number(
											workflowTask.workflowTaskId
										),
									}),
								size: 'md',
							});
						}}
					>
						{Liferay.Language.get('reject')}
					</ClayDropdown.Item>
				</>
			) : (
				<>
					<ClayDropdown.Item
						onClick={() => {
							openModal({
								contentComponent: ({
									closeModal,
								}: {
									closeModal: () => void;
								}) =>
									AssignToMeModalContent({
										closeModal,
										loadData,
										workflowTaskId: Number(
											workflowTask.workflowTaskId
										),
									}),
								size: 'md',
							});
						}}
					>
						{Liferay.Language.get('assign-to-me')}
					</ClayDropdown.Item>
				</>
			)}

			<ClayDropdown.Item
				onClick={() => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: () => void;
						}) =>
							AssignToModalContent({
								closeModal,
								loadData,
								workflowTaskId: Number(
									workflowTask.workflowTaskId
								),
							}),
						size: 'md',
					});
				}}
			>
				{Liferay.Language.get('assign-to-...')}
			</ClayDropdown.Item>

			<ClayDropdown.Item
				onClick={() => {
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: () => void;
						}) =>
							UpdateDueDateModalContent({
								closeModal,
								dueDate: workflowTask.dueDate,
								loadData,
								workflowTaskId: Number(
									workflowTask.workflowTaskId
								),
							}),
						size: 'md',
					});
				}}
			>
				{Liferay.Language.get('update-due-date')}
			</ClayDropdown.Item>
		</ClayDropdown>
	);
}
