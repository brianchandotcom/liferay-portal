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

import EmptyState from '../../../../shared/components/empty-state/EmptyState.es';
import RetryButton from '../../../../shared/components/list/RetryButton.es';
import LoadingState from '../../../../shared/components/loading/LoadingState.es';
import PromisesResolver from '../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {usePost} from '../../../../shared/hooks/usePost.es';
import {InstanceListContext} from '../../store/InstanceListPageStore.es';
import {ModalContext} from '../ModalContext.es';
import {Table} from './SingleReassignModalTable.es';

const ErrorView = ({onClick}) => {
	return (
		<EmptyState
			actionButton={<RetryButton onClick={onClick} />}
			className="border-0 pb-5 pt-5 sheet"
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
	return <LoadingState className="border-0 pt-7" />;
};

const SingleReassignModal = () => {
	const [errorToast, setErrorToast] = useState(false);
	const [assigneeId, setAssigneeId] = useState();
	const [retry, setRetry] = useState(0);
	const [sendingPost, setSendingPost] = useState(false);
	const [successToast, setSuccessToast] = useState([]);

	const {setSingleModal, singleModal} = useContext(ModalContext);
	const {setSelectedItems} = useContext(InstanceListContext);
	const {selectedItem = {}} = singleModal;

	const {observer, onClose} = useModal({
		onClose: () => {
			setSingleModal({selectedItem: undefined, visible: false});
			setAssigneeId();
		}
	});

	const {data, fetchData} = useFetch({
		admin: true,
		params: {completed: false, page: 1, pageSize: 1},
		url: `/workflow-instances/${selectedItem.id}/workflow-tasks`
	});

	const taskId = useMemo(
		() => (data.items && data.items[0] ? data.items[0].id : undefined),
		[data]
	);

	const {postData} = usePost({
		admin: true,
		body: {assigneeId},
		url: `/workflow-tasks/${taskId}/assign-to-user`
	});

	const reassignButtonHandler = useCallback(() => {
		if (assigneeId && taskId) {
			setSendingPost(true);
			setErrorToast(false);

			postData()
				.then(() => {
					onClose();
					setSuccessToast([
						...successToast,
						Liferay.Language.get('this-task-has-been-reassigned')
					]);
					setErrorToast(false);
					setSendingPost(false);
					setSelectedItems([]);
				})
				.catch(() => {
					setErrorToast(true);
					setSendingPost(false);
				});
		}
		else {
			onClose();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [postData]);

	const promises = useMemo(() => {
		setErrorToast(false);

		if (singleModal.visible) {
			return [
				fetchData().catch(err => {
					setErrorToast(true);

					return Promise.reject(err);
				})
			];
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData, retry]);

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
					>
						<ClayModal.Header>
							{Liferay.Language.get('select-new-assignee')}
						</ClayModal.Header>

						{errorToast && (
							<ClayAlert
								className="mb-0"
								data-testid="alertError"
								displayType="danger"
								title={Liferay.Language.get('error')}
							>
								{Liferay.Language.get(
									'your-request-has-failed'
								)}
							</ClayAlert>
						)}

						<div
							className="modal-metrics-content"
							style={{height: '20rem'}}
						>
							<ClayModal.Body>
								<PromisesResolver.Pending>
									<SingleReassignModal.LoadingView />
								</PromisesResolver.Pending>

								<PromisesResolver.Rejected>
									<SingleReassignModal.ErrorView
										onClick={() => {
											setRetry(retry => retry + 1);
										}}
									/>
								</PromisesResolver.Rejected>

								<PromisesResolver.Resolved>
									<SingleReassignModal.Table
										data={data}
										setAssigneeId={setAssigneeId}
										{...selectedItem}
									/>
								</PromisesResolver.Resolved>
							</ClayModal.Body>
						</div>

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
									disabled={sendingPost || !assigneeId}
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

SingleReassignModal.ErrorView = ErrorView;
SingleReassignModal.LoadingView = LoadingView;
SingleReassignModal.Table = Table;

export {SingleReassignModal};
