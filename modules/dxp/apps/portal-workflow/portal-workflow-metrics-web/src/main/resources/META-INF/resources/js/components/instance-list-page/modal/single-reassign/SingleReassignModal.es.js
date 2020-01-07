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
import ClayModal, {useModal} from '@clayui/modal';
import React, {useCallback, useContext, useMemo, useState} from 'react';

import EmptyState from '../../../../shared/components/list/EmptyState.es';
import RetryButton from '../../../../shared/components/list/RetryButton.es';
import LoadingState from '../../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../../shared/components/request/PromisesResolver.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {usePost} from '../../../../shared/hooks/usePost.es';
import {ModalContext} from '../ModalContext.es';
import {Table} from './SingleReassignModalTable.es';

const ErrorView = ({onClick}) => {
	return (
		<EmptyState
			actionButton={<RetryButton onClick={onClick} />}
			className="border-0"
			hideAnimation={true}
			message={Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			)}
			messageClassName="small"
			type="error"
		/>
	);
};

const LoadingView = () => {
	return <LoadingState className="border-0 pb-6 pt-6 sheet" />;
};

const SingleReassignModal = () => {
	const [errorToast, setErrorToast] = useState(() => false);
	const [reassignedTasks, setReassignedTasks] = useState(() => ({
		tasks: []
	}));
	const [retry, setRetry] = useState(0);
	const [sendingPost, setSendingPost] = useState(false);
	const [successToast, setSuccessToast] = useState(() => []);
	const {setSingleModal, singleModal} = useContext(ModalContext);
	const onClose = () => {
		setSingleModal(() => ({selectedItem: undefined, visible: false}));
		setReassignedTasks(() => ({
			tasks: []
		}));
	};

	const {observer} = useModal({onClose});

	const taskItem = useMemo(
		() => (singleModal.selectedItem ? singleModal.selectedItem : {}),
		[singleModal]
	);

	const {data, fetchData} = useFetch({
		admin: true,
		params: {completed: false, page: 1, pageSize: 1},
		url: `/workflow-instances/${taskItem.id}/workflow-tasks`
	});

	const itemId = useMemo(
		() => (data.items && data.items[0] ? data.items[0].id : undefined),
		[data]
	);

	const newAssigneeId = useMemo(
		() =>
			reassignedTasks.tasks && reassignedTasks.tasks[0]
				? reassignedTasks.tasks[0].assigneeId
				: undefined,
		[reassignedTasks]
	);

	const {postData} = usePost({
		admin: true,
		body: {assigneeId: newAssigneeId},
		url: `/workflow-tasks/${itemId}/assign-to-user`
	});

	const reassignButtonHandler = useCallback(() => {
		if (
			newAssigneeId !== undefined &&
			(singleModal.selectedItem.assigneeUsers.length === 0 ||
				singleModal.selectedItem.assigneeUsers[0].id !== newAssigneeId)
		) {
			setSendingPost(() => true);
			setErrorToast(() => false);
			postData()
				.then(() => {
					onClose();
					setSuccessToast([
						...successToast,
						Liferay.Language.get('this-task-has-been-reassigned')
					]);
					setSendingPost(() => false);
					setErrorToast(() => false);
				})
				.catch(() => {
					setErrorToast(() => true);
					setSendingPost(() => false);
				});
		} else {
			onClose();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [postData]);

	const promises = useMemo(() => {
		setErrorToast(() => false);
		if (singleModal.visible) {
			return [
				fetchData().catch(err => {
					setErrorToast(() => true);
					return Promise.reject(err);
				})
			];
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData, retry]);

	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return (
		<>
			<ClayAlert.ToastContainer data-testid="alertContainer">
				{successToast.map(value => (
					<ClayAlert
						autoClose={5000}
						data-testid="alertSuccess"
						displayType={'success'}
						key={value}
						onClose={() => {
							setSuccessToast(prevItems =>
								prevItems.filter(item => item !== value)
							);
						}}
						spritemap={spritemap}
						title={Liferay.Language.get('success')}
					>
						{value}
					</ClayAlert>
				))}
			</ClayAlert.ToastContainer>

			<PromisesResolver promises={promises}>
				{singleModal.visible && (
					<ClayModal
						data-testid="reassignModal"
						observer={observer}
						size="lg"
						spritemap={spritemap}
					>
						<ClayModal.Header>
							{Liferay.Language.get('select-new-assignee')}
						</ClayModal.Header>

						{errorToast && (
							<ClayAlert
								data-testid="alertError"
								displayType="danger"
								spritemap={spritemap}
								title={Liferay.Language.get('error')}
							>
								{Liferay.Language.get(
									'your-connection-was-unexpectedly-lost'
								)}
							</ClayAlert>
						)}

						<ClayModal.Body>
							<PromisesResolver.Pending>
								<SingleReassignModal.LoadingView />
							</PromisesResolver.Pending>

							<PromisesResolver.Rejected>
								<SingleReassignModal.ErrorView
									onClick={() => {
										setRetry(retry => ++retry);
									}}
								/>
							</PromisesResolver.Rejected>

							<PromisesResolver.Resolved>
								<SingleReassignModal.Table
									data={data}
									reassignedTasks={reassignedTasks}
									setReassignedTasks={setReassignedTasks}
									{...taskItem}
								></SingleReassignModal.Table>
							</PromisesResolver.Resolved>
						</ClayModal.Body>

						<ClayModal.Footer
							first={
								<ClayButton
									data-testid="cancelButton"
									disabled={sendingPost}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							}
							last={
								<ClayButton
									data-testid="reassignButton"
									disabled={sendingPost || !newAssigneeId}
									onClick={reassignButtonHandler}
								>
									{Liferay.Language.get('reassign')}
								</ClayButton>
							}
						/>
					</ClayModal>
				)}
			</PromisesResolver>
		</>
	);
};

const Footer = ({onClose, reassignButtonHandler, sendingPost}) => {
	return (
		<ClayModal.Footer
			first={
				<ClayButton
					data-testid="cancelButton"
					displayType="secondary"
					onClick={onClose}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			}
			last={
				<ClayButton
					data-testid="reassignButton"
					disabled={sendingPost}
					onClick={reassignButtonHandler}
				>
					{Liferay.Language.get('reassign')}
				</ClayButton>
			}
		/>
	);
};

SingleReassignModal.ErrorView = ErrorView;
SingleReassignModal.Footer = Footer;
SingleReassignModal.LoadingView = LoadingView;
SingleReassignModal.Table = Table;

export {SingleReassignModal};
