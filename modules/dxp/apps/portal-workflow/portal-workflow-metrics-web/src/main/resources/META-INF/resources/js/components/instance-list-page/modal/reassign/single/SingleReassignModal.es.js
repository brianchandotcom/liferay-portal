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

import ContentView from '../../../../../shared/components/content-view/ContentView.es';
import RetryButton from '../../../../../shared/components/list/RetryButton.es';
import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useToaster} from '../../../../../shared/components/toaster/hooks/useToaster.es';
import {useFetch} from '../../../../../shared/hooks/useFetch.es';
import {usePost} from '../../../../../shared/hooks/usePost.es';
import {InstanceListContext} from '../../../InstanceListPageProvider.es';
import {ModalContext} from '../../ModalProvider.es';
import Table from './SingleReassignModalTable.es';

function SingleReassignModal() {
	const [errorToast, setErrorToast] = useState(false);
	const [assigneeId, setAssigneeId] = useState();
	const [retry, setRetry] = useState(0);
	const [sendingPost, setSendingPost] = useState(false);

	const toaster = useToaster();

	const {closeModal, visibleModal} = useContext(ModalContext);
	const {selectedInstance, setSelectedItems} = useContext(
		InstanceListContext
	);

	const onCloseModal = (refetch) => {
		closeModal(refetch);
		setAssigneeId();
		setSelectedItems([]);
	};
	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const {data, fetchData} = useFetch({
		admin: true,
		params: {completed: false, page: 1, pageSize: 1},
		url: `/workflow-instances/${selectedInstance?.id}/workflow-tasks`,
	});

	const taskId = data?.items?.[0].id;

	const {postData} = usePost({
		admin: true,
		body: {assigneeId},
		url: `/workflow-tasks/${taskId}/assign-to-user`,
	});

	const reassignButtonHandler = useCallback(() => {
		setErrorToast(false);
		setSendingPost(true);
		postData()
			.then(() => {
				toaster.success(
					Liferay.Language.get('this-task-has-been-reassigned')
				);

				onCloseModal(true);
				setErrorToast(false);
				setSendingPost(false);
			})
			.catch(() => {
				setErrorToast(true);
				setSendingPost(false);
			});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [postData]);

	const promises = useMemo(() => {
		setErrorToast(false);

		if (selectedInstance?.id && visibleModal === 'singleReassign') {
			return [
				fetchData().catch((error) => {
					setErrorToast(true);

					return Promise.reject(error);
				}),
			];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData, retry, visibleModal]);

	const statesProps = {
		errorProps: {
			actionButton: (
				<RetryButton onClick={() => setRetry((retry) => retry + 1)} />
			),
			className: 'py-7',
			hideAnimation: true,
			message: Liferay.Language.get('unable-to-retrieve-data'),
			messageClassName: 'small',
		},
		loadingProps: {className: 'pt-7'},
	};

	return (
		<>
			<PromisesResolver promises={promises}>
				{visibleModal === 'singleReassign' && (
					<ClayModal observer={observer} size="lg">
						<ClayModal.Header>
							{Liferay.Language.get('select-new-assignee')}
						</ClayModal.Header>

						{errorToast && (
							<ClayAlert
								className="mb-0"
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
								<ContentView {...statesProps}>
									<SingleReassignModal.Table
										setAssigneeId={setAssigneeId}
										{...data}
									/>
								</ContentView>
							</ClayModal.Body>
						</div>

						<ClayModal.Footer
							first={
								<ClayButton
									disabled={sendingPost}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							}
							last={
								<ClayButton
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
}

SingleReassignModal.Table = Table;

export default SingleReassignModal;
