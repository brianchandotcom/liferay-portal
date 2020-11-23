/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {Loading} from 'app-builder-web/js/components/loading/Loading.es';
import {addItem, getItem} from 'app-builder-web/js/utils/client.es';
import {successToast} from 'app-builder-web/js/utils/toast.es';
import React, {useCallback, useEffect, useState} from 'react';

import SelectDropdown from '../../components/select-dropdown/SelectDropdown.es';

export function AssigneeInput({
	label = Liferay.Language.get('new-assignee'),
	onSelectAssignee,
	required,
	selectedAssignee,
	taskId,
}) {
	const [{hasError, isLoading}, setState] = useState({
		hasError: false,
		isLoading: true,
	});
	const [assignees, setAssignees] = useState([]);

	const fetchAssignees = useCallback(() => {
		if (taskId) {
			setState({hasError: false, isLoading: true});

			getItem(
				`/o/headless-admin-workflow/v1.0/workflow-tasks/${taskId}/assignable-users`,
				{page: -1, pageSize: -1}
			)
				.then(({items}) => {
					setAssignees(items);
					setState({hasError: false, isLoading: false});
				})
				.catch(() => {
					setState({hasError: true, isLoading: false});
				});
		}
	}, [taskId]);

	useEffect(() => {
		fetchAssignees();
	}, [fetchAssignees]);

	return (
		<ClayForm.Group>
			<label>
				{label}{' '}
				{required && (
					<span className="reference-mark">
						<ClayIcon symbol="asterisk" />
					</span>
				)}
			</label>

			<SelectDropdown
				emptyResultMessage={Liferay.Language.get(
					'no-assignees-were-found-with-this-name-try-searching-again-with-a-different-name'
				)}
				error={hasError}
				isLoading={isLoading}
				items={assignees}
				label={Liferay.Language.get('select-a-new-assignee')}
				onSelect={onSelectAssignee}
				selectedValue={selectedAssignee?.name}
				stateProps={{
					emptyProps: {
						label: Liferay.Language.get(
							'there-are-no-assignees-yet'
						),
					},
					errorProps: {
						children: (
							<ClayButton
								displayType="link"
								onClick={fetchAssignees}
								small
							>
								{Liferay.Language.get('retry')}
							</ClayButton>
						),
						label: Liferay.Language.get(
							'unable-to-retrieve-the-assignees'
						),
					},
					loadingProps: {
						label: Liferay.Language.get('retrieving-all-assignees'),
					},
				}}
			/>
		</ClayForm.Group>
	);
}

export default function ReassignEntryModal({entry, onCloseModal, refetch}) {
	const [
		{comment, error, isLoading, isReassigning, selectedAssignee, taskId},
		setState,
	] = useState({
		comment: '',
		error: false,
		isLoading: false,
		isReassigning: false,
		selectedAssignee: null,
		taskId: null,
	});

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const fetchWorkflowTask = (instanceId) => {
		setState((state) => ({...state, error: false, isLoading: true}));

		getItem(
			`/o/headless-admin-workflow/v1.0/workflow-instances/${instanceId}/workflow-tasks`,
			{completed: false, page: 1, pageSize: 1}
		)
			.then(({items}) => {
				const newState = {
					error: false,
					isLoading: false,
				};

				if (items.length) {
					newState.taskId = items.pop().id;
				}

				setState((state) => ({
					...state,
					...newState,
				}));
			})
			.catch(() => {
				setState((state) => ({
					...state,
					error: true,
					isLoading: false,
				}));
			});
	};

	const onDone = (assigneeId) => {
		setState((state) => ({...state, error: false, isReassigning: true}));

		addItem(
			`/o/headless-admin-workflow/v1.0/workflow-tasks/${taskId}/assign-to-user`,
			{assigneeId, comment}
		)
			.then(() => {
				onClose();
				successToast(
					Liferay.Language.get('this-entry-has-been-reassigned')
				);
				refetch(true);
			})
			.catch(() => {
				setState((state) => ({
					...state,
					error: true,
					isReassigning: false,
				}));
			});
	};

	useEffect(() => {
		if (entry?.instanceId) {
			fetchWorkflowTask(entry.instanceId);
		}
	}, [entry]);

	return (
		<ClayModal center observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('assign-to')}
			</ClayModal.Header>

			{error && (
				<ClayAlert
					className="mb-0"
					data-testid="alertError"
					displayType="danger"
					title={Liferay.Language.get('error')}
				>
					{Liferay.Language.get('an-unexpected-error-occurred')}
				</ClayAlert>
			)}

			<ClayModal.Body>
				<Loading isLoading={isLoading}>
					<AssigneeInput
						onSelectAssignee={(selectedAssignee) =>
							setState((state) => ({...state, selectedAssignee}))
						}
						required
						selectedAssignee={selectedAssignee}
						taskId={taskId}
					/>

					<ClayForm.Group>
						<label>{Liferay.Language.get('comment')}</label>
						<ClayInput
							component="textarea"
							onChange={({target}) =>
								setState((state) => ({
									...state,
									comment: target.value,
								}))
							}
							placeholder={Liferay.Language.get('comment')}
							type="text"
							value={comment}
						/>
					</ClayForm.Group>
				</Loading>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-3"
							displayType="secondary"
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!selectedAssignee?.id || isReassigning}
							onClick={() => onDone(selectedAssignee?.id)}
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
