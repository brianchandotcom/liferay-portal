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

import StepBar from '../../../../../shared/components/step-bar/StepBar.es';
import {useToaster} from '../../../../../shared/components/toaster/hooks/useToaster.es';
import {useFilter} from '../../../../../shared/hooks/useFilter.es';
import {usePatch} from '../../../../../shared/hooks/usePatch.es';
import {usePost} from '../../../../../shared/hooks/usePost.es';
import {InstanceListContext} from '../../../InstanceListPageProvider.es';
import {ModalContext} from '../../ModalProvider.es';
import {SelectTasksStep} from '../../shared/select-tasks-step/SelectTasksStep.es';
import SelectTransitionStep from './select-transition-step/SelectTransitionStep.es';

const BulkTransitionModal = () => {
	const {
		selectAll: selectAllInstances,
		selectedItems,
		setSelectAll,
		setSelectedItems,
	} = useContext(InstanceListContext);
	const {
		dispatch,
		filterState,
		filterValues: {bulkAssigneeUserIds: userIds, bulkTaskKeys: taskNames},
	} = useFilter({withoutRouteParams: true});
	const {
		bulkTransition,
		processId,
		selectTasks: {selectAll, tasks},
		setBulkTransition,
		setSelectTasks,
		setVisibleModal,
		visibleModal,
	} = useContext(ModalContext);
	const [currentStep, setCurrentStep] = useState('selectTasks');
	const [errorToast, setErrorToast] = useState(null);
	const [fetching, setFetching] = useState(false);
	const toaster = useToaster();

	const {
		transition: {errors},
		transitionTasks,
	} = bulkTransition;

	const {observer, onClose} = useModal({
		onClose: () => {
			setSelectTasks({selectAll: false, tasks: []});
			setBulkTransition({
				transition: {errors: [], onGoing: false},
				transitionTasks: [],
			});
			setCurrentStep('selectTasks');
			setVisibleModal('');

			dispatch({
				...filterState,
				bulkAssigneeUserIds: [],
				bulkTaskKeys: [],
			});

			setErrorToast(false);
		},
	});

	const handleDone = useCallback(() => {
		setBulkTransition({
			transition: {errors, onGoing: true},
			transitionTasks,
		});

		if (!errors.some(error => error)) {
			patchData()
				.then(() => {
					onClose();

					toaster.success(
						transitionTasks.length > 1
							? Liferay.Language.get(
									'the-selected-steps-have-transitioned-successfully'
							  )
							: Liferay.Language.get(
									'the-selected-step-has-transitioned-successfully'
							  )
					);

					setSelectedItems([]);
					setSelectAll(false);
				})
				.catch(() => {
					setErrorToast(
						`${Liferay.Language.get(
							'your-connection-has-been-unexpectedly-lost'
						)} ${Liferay.Language.get('select-done-to-retry')}`
					);
				});
		}
		else {
			setErrorToast(
				Liferay.Language.get(
					'all-steps-require-a-transition-to-be-selected-to-complete-this-action'
				)
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [errors, transitionTasks]);

	const handleNext = useCallback(() => {
		if (selectAll) {
			setFetching(true);
			postData()
				.then(({items}) => {
					setSelectTasks({selectAll, tasks: items});
					setCurrentStep('selectTransitions');
					setFetching(false);
				})
				.catch(() => {
					setErrorToast(
						`${Liferay.Language.get('your-request-has-failed')}`
					);
					setFetching(false);
				});
		}
		else {
			setCurrentStep('selectTransitions');
		}
	}, [postData, selectAll, setSelectTasks]);

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
			taskNames,
		};

		if (selectAllInstances) {
			params.workflowDefinitionId = processId;
		}
		else {
			params.workflowInstanceIds = selectedItems.map(({id}) => id);
		}

		return params;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedItems, taskNames, userIds]);

	const handlePrevious = useCallback(() => {
		setErrorToast(false);
		setCurrentStep('selectTasks');
		setBulkTransition({
			transition: {errors: [], onGoing: false},
			transitionTasks: [],
		});
	}, [setErrorToast, setCurrentStep, setBulkTransition]);

	const {postData} = usePost({
		admin: true,
		params,
		url: '/workflow-tasks',
	});

	const {patchData} = usePatch({
		admin: true,
		body: transitionTasks,
		url: '/workflow-tasks/change-transition',
	});

	const getStep = useCallback(
		step => {
			const steps = {
				selectTasks: {
					component: SelectTasksStep,
					nextBtn: {
						disabled: tasks.length === 0 || fetching,
						handle: handleNext,
						text: Liferay.Language.get('next'),
					},
					order: 1,
					previousBtn: false,
					subtitle: Liferay.Language.get('select-steps'),
					title: Liferay.Language.get('select-steps-to-transition'),
				},
				selectTransitions: {
					component: SelectTransitionStep,
					nextBtn: {
						handle: handleDone,
						text: Liferay.Language.get('done'),
					},
					order: 2,
					previousBtn: true,
					subtitle: Liferay.Language.get('choose-transition'),
					title: Liferay.Language.get('choose-transition-per-step'),
				},
			};

			return steps[step];
		},
		[fetching, handleDone, handleNext, tasks.length]
	);

	const step = useMemo(() => getStep(currentStep), [currentStep, getStep]);

	return (
		<>
			{visibleModal === 'bulkTransition' && (
				<ClayModal
					data-testid="bulkTransitionModal"
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

					<StepBar
						current={step.order}
						title={step.subtitle}
						total={2}
					/>

					<div className="fixed-height modal-metrics-content">
						<step.component
							processId={processId}
							setErrorToast={setErrorToast}
						/>
					</div>

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
							<>
								{step.previousBtn && (
									<ClayButton
										className="mr-3"
										data-testid="previousButton"
										displayType="secondary"
										onClick={handlePrevious}
									>
										{Liferay.Language.get('previous')}
									</ClayButton>
								)}

								<ClayButton
									data-testid="nextButton"
									disabled={step.nextBtn.disabled}
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

export default BulkTransitionModal;
