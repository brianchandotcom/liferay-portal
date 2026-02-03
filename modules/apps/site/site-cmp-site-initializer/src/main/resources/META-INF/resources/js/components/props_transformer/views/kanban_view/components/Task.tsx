/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import Card from '@clayui/card/src/Card';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import Label from '@clayui/label';
import {AssigneeAvatar} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {displayErrorToast} from '@liferay/site-cms-site-initializer';
import {navigate} from 'frontend-js-web';
import React, {useContext} from 'react';
import {useDrag} from 'react-dnd';

import {
	deleteTaskById,
	getUserAccount,
	patchTaskById,
} from '../../../../../utils/api';
import {mapStateKeyToDisplayType} from '../../../../../utils/constants';
import {openCMPModal} from '../../../../../utils/openCMPModal';
import {
	displayAssignSuccessToast,
	displayDeleteSuccessToast,
} from '../../../../../utils/toastUtil';
import {ITask} from '../../../../../utils/types';
import DeleteTaskModal from '../../../../modal/DeleteTaskModal';
import EditAssigneeModalContent from '../../../../modal/EditAssigneeModalContent';
import {KanbanViewContext} from '../context';
import {ItemTypes} from './Column';

import './Task.scss';

export default function Task(props: ITask) {
	const {itemsActions, loadData} = useContext(KanbanViewContext);

	const [_, drag] = useDrag<{task: ITask; type: string}, void, {}>({
		item: {task: props, type: ItemTypes.TASK},
	});

	return (
		<div className="lfr__kaban-task-card" ref={drag}>
			<Card>
				<Card.Body>
					<Card.Row>
						<div className="lfr__kaban-task-card-row">
							<strong className="lfr__kaban-task-card-row-text-content">
								{props.embedded.title}
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
													String(props.embedded.id)
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
													String(props.embedded.id)
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
														props.embedded.id
													),
												}
											);

											if (!error) {
												loadData();

												displayAssignSuccessToast(
													props.embedded.title,
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
															props.embedded.id
														)}
														taskTitle={
															props.embedded.title
														}
														value={
															props.embedded
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
																			props
																				.embedded
																				.id
																		),
																	}
																);

															if (!error) {
																loadData();

																displayDeleteSuccessToast(
																	props
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
															props.embedded.title
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
							{props.embedded.cmpProjectToCMPTasks.title}
						</Card.Description>
					</Card.Row>

					<Card.Row>
						<div className="lfr__kaban-task-card-row">
							<Label
								displayType={
									mapStateKeyToDisplayType[
										props.embedded.state.key
									]
								}
							>
								{props.embedded.state.name}
							</Label>

							<div className="lfr__kaban-task-card-assignee">
								<AssigneeAvatar
									name={props.embedded.assignTo.name}
									portrait={props.embedded.assignTo.portrait}
								/>
							</div>
						</div>
					</Card.Row>
				</Card.Body>
			</Card>
		</div>
	);
}