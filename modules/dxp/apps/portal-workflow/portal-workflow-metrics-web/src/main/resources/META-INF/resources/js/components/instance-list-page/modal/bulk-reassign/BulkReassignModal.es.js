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

import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../../shared/hooks/useFilter.es';
import {usePatch} from '../../../../shared/hooks/usePatch.es';
import {sub} from '../../../../shared/util/lang.es';
import {InstanceListContext} from '../../store/InstanceListPageStore.es';
import {ModalContext} from '../ModalContext.es';
import {BulkReassignSelectAssigneesStep} from './select-assignees-step/BulkReassignSelectAssigneesStep.es';
import {BulkReassignSelectTasksStep} from './select-tasks-step/BulkReassignSelectTasksStep.es';

const BulkReassignModal = () => {
	const {bulkModal, setBulkModal, setSingleModal, singleModal} = useContext(
		ModalContext
	);
	const {
		selectAll: selectAllInstances,
		selectedItems,
		setSelectAll,
		setSelectedItems
	} = useContext(InstanceListContext);

	const {
		dispatch,
		filterState,
		filterValues: {bulkAssigneeUserIds: userIds, bulkTaskKeys: taskNames}
	} = useFilter({withoutRouteParams: true});

	const {
		processId,
		reassignedTasks,
		reassigning,
		selectAll,
		selectedTasks,
		visible
	} = bulkModal;

	const [currentStep, setCurrentStep] = useState('selectTasks');
	const [errorToast, setErrorToast] = useState(null);
	const [fetching, setFetching] = useState(false);

	const toaster = useToaster();

	const {observer, onClose} = useModal({
		onClose: () => {
			setBulkModal({
				processId,
				reassignedTasks: [],
				reassigning: false,
				selectAll: false,
				selectedAssignee: null,
				selectedTasks: [],
				useSameAssignee: false,
				visible: false
			});

			dispatch({
				...filterState,
				bulkAssigneeUserIds: [],
				bulkTaskKeys: []
			});

			setCurrentStep('selectTasks');

			setErrorToast(false);

			setSingleModal({selectedItem: undefined});
		}
	});

	const {patchData} = usePatch({
		admin: true,
		body: reassignedTasks,
		url: 'workflow-tasks/assign-to-user'
	});

	const params = useMemo(() => {
		const filterByUser = userIds && userIds.length;

		const assigneeIds = filterByUser
			? userIds.filter(id => id !== '-1')
			: undefined;

		const searchByRoles = filterByUser && userIds.includes('-1');

		const params = {
			assigneeIds,
			completed: false,
			page: 1,
			pageSize: -1,
			searchByRoles,
			sort: 'workflowInstanceId:asc',
			taskNames
		};

		if (selectAllInstances) {
			params.workflowDefinitionId = processId;
		}
		else {
			const {selectedItem = {}} = singleModal || {};

			params.workflowInstanceIds = selectedItems.length
				? selectedItems.map(item => item.id)
				: selectedItem.id;
		}

		return params;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [taskNames, userIds, visible]);

	const {fetchData} = useFetch({
		admin: true,
		params,
		url: '/workflow-tasks'
	});

	const handleNext = useCallback(() => {
		if (selectAll) {
			setFetching(true);
			fetchData()
				.then(({items}) => {
					setBulkModal({
						...bulkModal,
						selectAll: true,
						selectedTasks: items
					});
					setCurrentStep('selectAssignees');
					setFetching(false);
				})
				.catch(() => {
					setErrorToast(
						Liferay.Language.get('your-request-has-failed')
					);
					setFetching(false);
				});
		}
		else {
			setCurrentStep('selectAssignees');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [bulkModal, fetchData, selectAll]);

	const handlePrevious = () => {
		setBulkModal({
			...bulkModal,
			reassignedTasks: [],
			reassigning: false,
			selectedAssignee: null,
			useSameAssignee: false
		});
		setCurrentStep('selectTasks');
		setErrorToast(false);
	};

	const handleReassign = useCallback(() => {
		if (
			reassignedTasks.length > 0 &&
			reassignedTasks.length === selectedTasks.length
		) {
			setBulkModal({
				...bulkModal,
				reassigning: true
			});

			setErrorToast(false);

			patchData()
				.then(() => {
					onClose();

					toaster.success(
						reassignedTasks.length > 1
							? sub(
									Liferay.Language.get(
										'x-tasks-have-been-reassigned'
									),
									[reassignedTasks.length]
							  )
							: Liferay.Language.get(
									'this-task-has-been-reassigned'
							  )
					);

					setSelectedItems([]);
					setSelectAll(false);
				})
				.catch(() => {
					const error = `${Liferay.Language.get(
						'your-request-has-failed'
					)} ${Liferay.Language.get('select-reassign-to-retry')}`;

					setBulkModal({
						...bulkModal,
						reassigning: false
					});

					setErrorToast(error);
				});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [patchData]);

	const getStep = useCallback(
		step => {
			const steps = {
				selectAssignees: {
					component: BulkReassignSelectAssigneesStep,
					nextBtn: {
						disabled:
							reassignedTasks.length !== selectedTasks.length,
						handle: handleReassign,
						text: Liferay.Language.get('reassign')
					},
					order: 2,
					previousBtn: true,
					subtitle: Liferay.Language.get('select-assignees'),
					title: Liferay.Language.get('select-new-assignees')
				},
				selectTasks: {
					component: BulkReassignSelectTasksStep,
					nextBtn: {
						disabled: selectedTasks.length === 0 || fetching,
						handle: handleNext,
						text: Liferay.Language.get('next')
					},
					order: 1,
					previousBtn: false,
					subtitle: Liferay.Language.get('select-tasks'),
					title: Liferay.Language.get('select-tasks-to-reassign')
				}
			};

			return steps[step];
		},
		[
			fetching,
			handleNext,
			handleReassign,
			reassignedTasks.length,
			selectedTasks.length
		]
	);

	const step = useMemo(() => getStep(currentStep), [currentStep, getStep]);

	return (
		<>
			{visible && (
				<ClayModal
					data-testid="bulkReassignModal"
					observer={observer}
					size="lg"
				>
					<ClayModal.Header>{step.title}</ClayModal.Header>

					{errorToast && (
						<ClayAlert
							className="mb-0"
							data-testid="alertError"
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{errorToast}
						</ClayAlert>
					)}

					<StepOfBar
						current={step.order}
						title={step.subtitle}
						total={2}
					/>

					<step.component
						processId={processId}
						setErrorToast={setErrorToast}
					/>

					<ClayModal.Footer
						first={
							<ClayButton
								data-testid="cancelButton"
								disabled={reassigning}
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						}
						last={
							<>
								{step.previousBtn && (
									<ClayButton
										className="mr-3"
										data-testid="previousButton"
										disabled={reassigning}
										displayType="secondary"
										onClick={handlePrevious}
									>
										{Liferay.Language.get('previous')}
									</ClayButton>
								)}

								<ClayButton
									data-testid="nextButton"
									disabled={
										reassigning || step.nextBtn.disabled
									}
									onClick={step.nextBtn.handle}
								>
									{step.nextBtn.text}
								</ClayButton>
							</>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};

const StepOfBar = ({current, title, total}) => {
	return (
		<div className="step-of-bar" data-testid="stepOfBar">
			<small>{title}</small>
			<small>
				{sub(Liferay.Language.get('step-x-of-x'), [current, total])}
			</small>
		</div>
	);
};

export {BulkReassignModal};
