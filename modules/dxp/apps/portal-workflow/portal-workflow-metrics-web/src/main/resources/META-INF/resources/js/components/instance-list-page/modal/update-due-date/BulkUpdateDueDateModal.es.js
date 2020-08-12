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

import {useModal} from '@clayui/modal';
import React, {useCallback, useContext, useMemo, useState} from 'react';

import ModalWithSteps from '../../../../shared/components/modal-with-steps/ModalWithSteps.es';
import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {usePatch} from '../../../../shared/hooks/usePatch.es';
import {InstanceListContext} from '../../InstanceListPageProvider.es';
import {ModalContext} from '../ModalProvider.es';
import SelectTasksStep from '../shared/select-tasks-step/SelectTasksStep.es';
import {useFetchTasks} from '../shared/select-tasks-step/hooks/useFetchTasks.es';
import UpdateDueDateStep from './UpdateDueDateStep.es';

const BulkUpdateDueDateModal = () => {
	const {setSelectAll, setSelectedItems} = useContext(InstanceListContext);
	const {
		selectTasks: {selectAll, tasks},
		setSelectTasks,
		setUpdateDueDate,
		setVisibleModal,
		updateDueDate: {comment, dueDate},
		visibleModal,
	} = useContext(ModalContext);
	const {clearFilters, fetchTasks} = useFetchTasks();
	const [currentStep, setCurrentStep] = useState('selectTasks');
	const [errorToast, setErrorToast] = useState(null);
	const [fetching, setFetching] = useState(false);
	const [updating, setUpdating] = useState(false);
	const toaster = useToaster();

	const clearContext = useCallback(() => {
		setUpdateDueDate({
			comment: undefined,
			dueDate: undefined,
		});
	}, [setUpdateDueDate]);

	const {observer, onClose} = useModal({
		onClose: () => {
			clearContext();
			clearFilters();
			setSelectTasks({selectAll: false, tasks: []});
			setVisibleModal('');
			setCurrentStep('selectTasks');
			setErrorToast(false);
		},
	});

	const body = useMemo(() => {
		if (dueDate) {
			return tasks.map(({id: workflowTaskId}) => ({
				comment,
				dueDate,
				workflowTaskId,
			}));
		}

		return [];
	}, [comment, dueDate, tasks]);

	const {patchData} = usePatch({
		admin: true,
		body,
		url: '/workflow-tasks/update-due-date',
	});

	const handleDone = useCallback(() => {
		if (dueDate) {
			setUpdating(true);

			patchData()
				.then(() => {
					onClose();
					setUpdating(false);

					toaster.success(
						tasks.length > 1
							? Liferay.Language.get(
									'the-due-dates-for-these-tasks-have-been-updated'
							  )
							: Liferay.Language.get(
									'the-due-date-for-this-task-has-been-updated'
							  )
					);

					setSelectedItems([]);
					setSelectAll(false);
				})
				.catch(({response}) => {
					const errorMessage = `${Liferay.Language.get(
						'your-request-has-failed'
					)} ${Liferay.Language.get('select-done-to-retry')}`;

					setErrorToast(response?.data.title ?? errorMessage);
					setUpdating(false);
				});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [patchData, tasks]);

	const handleNext = useCallback(() => {
		if (selectAll) {
			setFetching(true);

			fetchTasks()
				.then(({items}) => {
					setCurrentStep('selectDueDate');
					setFetching(false);
					setSelectTasks({selectAll, tasks: items});
				})
				.catch(() => {
					setErrorToast(
						`${Liferay.Language.get('your-request-has-failed')}`
					);
					setFetching(false);
				});
		}
		else {
			setCurrentStep('selectDueDate');
		}
	}, [fetchTasks, selectAll, setSelectTasks]);

	const handlePrevious = useCallback(() => {
		clearContext();
		setCurrentStep('selectTasks');
		setErrorToast(false);
	}, [clearContext]);

	const getStep = useCallback(
		(step) => {
			const steps = {
				selectDueDate: {
					cancelBtn: {
						disabled: updating,
						handle: onClose,
					},
					component: UpdateDueDateStep,
					nextBtn: {
						disabled: !dueDate || updating,
						handle: handleDone,
						text: Liferay.Language.get('done'),
					},
					order: 2,
					previousBtn: {
						disabled: updating,
						handle: handlePrevious,
					},
					props: {
						className: 'fixed-height modal-metrics-content',
					},
					subtitle: Liferay.Language.get('update-due-date'),
					title: Liferay.Language.get('update-tasks-due-dates'),
				},
				selectTasks: {
					cancelBtn: {
						disabled: fetching,
						handle: onClose,
					},
					component: SelectTasksStep,
					nextBtn: {
						disabled: tasks.length === 0 || fetching,
						handle: handleNext,
						text: Liferay.Language.get('next'),
					},
					order: 1,
					previousBtn: false,
					props: {setErrorToast},
					subtitle: Liferay.Language.get('select-tasks'),
					title: Liferay.Language.get('select-tasks-to-update'),
				},
			};

			return steps[step];
		},
		[
			dueDate,
			fetching,
			handleDone,
			handleNext,
			handlePrevious,
			onClose,
			updating,
			tasks.length,
		]
	);

	const step = useMemo(() => getStep(currentStep), [currentStep, getStep]);

	return (
		<ModalWithSteps
			dataTestId="bulkUpdateDueDateModal"
			error={errorToast}
			observer={observer}
			step={step}
			visible={visibleModal === 'bulkUpdateDueDate'}
		/>
	);
};

export default BulkUpdateDueDateModal;
