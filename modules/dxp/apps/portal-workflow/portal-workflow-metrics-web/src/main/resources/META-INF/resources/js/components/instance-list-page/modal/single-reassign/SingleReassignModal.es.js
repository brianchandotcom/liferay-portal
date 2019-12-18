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
import React, {
	createContext,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState
} from 'react';

import EmptyState from '../../../../shared/components/list/EmptyState.es';
import RetryButton from '../../../../shared/components/list/RetryButton.es';
import LoadingState from '../../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../../shared/components/request/PromisesResolver.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {usePost} from '../../../../shared/hooks/usePost.es';
import {Table} from './SingleReassignModalTable.es';

const SingleReassignModalContext = createContext();

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
	const [visible, setVisible] = useState(false);
	const {setShowModal, showModal} = useContext(SingleReassignModalContext);
	const [reassignedTasks, setReassignedTasks] = useState(() => ({
		tasks: []
	}));
	const [errorToast, setErrorToast] = useState(() => false);
	const [promises, setPromises] = useState([]);
	const [retry, setRetry] = useState(() => false);
	const [sendingPost, setSendingPost] = useState(false);
	const [successToast, setSuccessToast] = useState(() => []);
	const taskItem = useMemo(
		() => (showModal.selectedItem ? showModal.selectedItem : {}),
		[showModal]
	);

	const modalObjString = JSON.stringify(showModal);
	const {observer, onClose} = useModal({
		onClose: () => {
			setShowModal(() => ({selectedItem: undefined, visible: false}));
			setReassignedTasks(() => ({
				tasks: []
			}));
		}
	});

	const {data, fetchData} = useFetch({
		headless: true,
		params: {completed: false, page: 1, pageSize: 1},
		url: `/workflow-instances/${taskItem.id}/workflow-tasks`
	});

	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;
	const itemId = data.items && data.items[0] ? data.items[0].id : undefined;

	const newAssigneeId = useMemo(
		() =>
			reassignedTasks.tasks && reassignedTasks.tasks[0]
				? reassignedTasks.tasks[0].assigneeId
				: undefined,
		[reassignedTasks]
	);

	const {postData} = usePost({
		body: {assigneeId: newAssigneeId},
		headless: true,
		url: `/workflow-tasks/${itemId}/assign-to-user`
	});

	const reassignButtonHandler = useCallback(() => {
		if (
			newAssigneeId !== undefined &&
			(showModal.selectedItem.assigneeUsers.length === 0 ||
				showModal.selectedItem.assigneeUsers[0].id != newAssigneeId)
		) {
			setSendingPost(() => true);
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

	useEffect(() => {
		setVisible(() => showModal.visible);
		if (showModal.visible || retry) {
			setPromises(() => [fetchData()]);
			setRetry(() => false);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [modalObjString, retry]);

	return (
		<>
			<ClayAlert.ToastContainer>
				{successToast.map(value => (
					<ClayAlert
						autoClose={5000}
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
				{visible && (
					<ClayModal
						data-testid="reassignModal"
						observer={observer}
						size="lg"
						spritemap={spritemap}
					>
						<ClayModal.Header>
							{Liferay.Language.get('select-new-assignee')}
						</ClayModal.Header>

						<ClayModal.Body>
							<PromisesResolver.Pending>
								<SingleReassignModal.LoadingView />
							</PromisesResolver.Pending>

							<PromisesResolver.Rejected>
								<SingleReassignModal.ErrorView
									onClick={() => {
										setRetry(() => true);
									}}
								/>
							</PromisesResolver.Rejected>

							{errorToast && (
								<ClayAlert
									displayType="danger"
									spritemap={spritemap}
									title={Liferay.Language.get('error')}
								>
									{Liferay.Language.get(
										'your-connection-was-unexpectedly-lost'
									)}
								</ClayAlert>
							)}

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
									onClick={() => reassignButtonHandler()}
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

export {SingleReassignModalContext};
export {SingleReassignModal};
