/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import Card from '@clayui/card/src/Card';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {DateRenderer} from '@liferay/frontend-data-set-web';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {displayErrorToast} from '@liferay/site-cms-site-initializer';
import classNames from 'classnames';
import {navigate} from 'frontend-js-web';
import React, {useContext} from 'react';
import {useDrag} from 'react-dnd';

import {
	deleteTaskById,
	getUserAccount,
	patchTaskById,
} from '../../../../../utils/api';
import {openCMPModal} from '../../../../../utils/openCMPModal';
import {
	displayAssignSuccessToast,
	displayDeleteSuccessToast,
} from '../../../../../utils/toastUtil';
import {ITask} from '../../../../../utils/types';
import StateLabel from '../../../../StateLabel';
import DeleteTaskModal from '../../../../modal/DeleteTaskModal';
import EditAssigneeModalContent from '../../../../modal/EditAssigneeModalContent';
import {KanbanViewContext} from '../context';
import {ItemTypes} from './Column';

import './Task.scss';

export default function Task(task: ITask) {
	const {currentURL, itemsActions, loadData} = useContext(KanbanViewContext);

	const [{isDragging}, drag] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		item: {task, type: ItemTypes.TASK},
	});

	return (
		<div
			className={classNames('lfr__kaban-task-card', {
				'lfr__kaban-task-card-dragging': isDragging,
			})}
			ref={drag}
		>
			<Card>
				<Card.Body>
					<Card.Row>
						<div className="lfr__kaban-task-card-row">
							<strong className="lfr__kaban-task-card-row-text-content">
								{task.embedded.title}
							</strong>

							<ClayDropDownWithItems
								items={[
									{
										label: Liferay.Language.get('edit'),
										onClick: () => {
											const editURL = itemsActions
												.find(
													(action) =>
														action.data.id ===
														'edit'
												)
												?.href.replace(
													'{embedded.id}',
													String(task.embedded.id)
												);

											if (editURL) {
												navigate(editURL);
											}
										},
										symbolLeft: 'pencil',
									},
									{
										label: Liferay.Language.get('view'),
										onClick: () => {
											const viewURL = itemsActions
												.find(
													(action) =>
														action.data.id ===
														'actionLink'
												)
												?.href.replace(
													'{embedded.id}',
													String(task.embedded.id)
												);

											if (viewURL) {
												navigate(viewURL);
											}
										},
										symbolLeft: 'view',
									},
									{
										type: 'divider',
									},
									{
										label: Liferay.Language.get(
											'assign-to-me'
										),
										onClick: async () => {
											const user = (await getUserAccount(
												Liferay.ThemeDisplay.getUserId().toString()
											)) as {
												externalReferenceCode: string;
												name: string;
											};

											const {error} = await patchTaskById(
												{
													body: {
														assignTo: {
															externalReferenceCode:
																user.externalReferenceCode,
															name: user.name,
															type: 'User',
														},
													},
													taskId: String(
														task.embedded.id
													),
												}
											);

											if (!error) {
												loadData();

												displayAssignSuccessToast(
													task.embedded.title,
													user.name
												);
											}
											else {
												displayErrorToast(error);
											}
										},
									},
									{
										label: Liferay.Language.get(
											'assign-to-...'
										),
										onClick: async () => {
											await openCMPModal({
												center: true,
												contentComponent: ({
													closeModal,
												}: {
													closeModal: () => void;
												}) => (
													<EditAssigneeModalContent
														closeModal={closeModal}
														loadData={loadData}
														taskId={String(
															task.embedded.id
														)}
														taskTitle={
															task.embedded.title
														}
														value={
															task.embedded
																.assignTo
														}
													/>
												),
												size: 'md',
											});
										},
									},
									{
										type: 'divider',
									},
									{

										// @ts-ignore

										className: 'text-danger',
										label: Liferay.Language.get('delete'),
										onClick: async () => {
											await openCMPModal({
												center: true,
												contentComponent: ({
													closeModal,
												}: {
													closeModal: () => void;
												}) => (
													<DeleteTaskModal
														closeModal={closeModal}
														onSubmit={async () => {
															const {error} =
																await deleteTaskById(
																	{
																		taskId: String(
																			task
																				.embedded
																				.id
																		),
																	}
																);

															if (!error) {
																loadData();

																displayDeleteSuccessToast(
																	task
																		.embedded
																		.title
																);
															}
															else {
																displayErrorToast(
																	error
																);
															}

															closeModal();
														}}
														title={
															task.embedded.title
														}
													/>
												),
												size: 'md',
												status: 'danger',
											});
										},
										symbolLeft: 'trash',
									},
								]}
								trigger={
									<ClayButton
										aria-label={Liferay.Language.get(
											'actions'
										)}
										className="component-action"
										displayType="unstyled"
										monospaced
									>
										<ClayIcon symbol="ellipsis-v" />
									</ClayButton>
								}
							/>
						</div>
					</Card.Row>

					<Card.Row>
						<Card.Description
							className="lfr__kaban-task-card-row-text-content"
							displayType="subtitle"
						>
							{!currentURL.includes('project')
								? task.embedded.cmpProjectToCMPTasks.title
								: DateRenderer({value: task.embedded.dueDate})}
						</Card.Description>
					</Card.Row>

					<Card.Row>
						<div className="lfr__kaban-task-card-row">
							<StateLabel
								dueDate={task.embedded.dueDate}
								state={{
									key: task.embedded.state.key,
									name: task.embedded.state.name,
								}}
							/>

							<div className="lfr__kaban-task-card-assignee">
								<AssigneeAvatar
									name={task.embedded.assignTo.name}
									portrait={task.embedded.assignTo.portrait}
								/>
							</div>
						</div>
					</Card.Row>
				</Card.Body>
			</Card>
		</div>
	);
}