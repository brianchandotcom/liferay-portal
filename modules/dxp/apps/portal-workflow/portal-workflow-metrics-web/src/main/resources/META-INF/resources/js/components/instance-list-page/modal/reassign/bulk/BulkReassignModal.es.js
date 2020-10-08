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

import ModalWithSteps from '../../../../../shared/components/modal-with-steps/ModalWithSteps.es';
import {useToaster} from '../../../../../shared/components/toaster/hooks/useToaster.es';
import {usePatch} from '../../../../../shared/hooks/usePatch.es';
import {sub} from '../../../../../shared/util/lang.es';
import {InstanceListContext} from '../../../InstanceListPageProvider.es';
import {ModalContext} from '../../ModalProvider.es';
import SelectTasksStep from '../../shared/select-tasks-step/SelectTasksStep.es';
import {useFetchTasks} from '../../shared/select-tasks-step/hooks/useFetchTasks.es';
import SelectAssigneesStep from './select-assignees-step/SelectAssigneesStep.es';

const BulkReassignModal = () => {
	const {
		bulkReassign: {reassignedTasks, reassigning},
		closeModal,
		selectTasks,
		setBulkReassign,
		setSelectTasks,
		visibleModal,
	} = useContext(ModalContext);
	const {clearFilters, fetchTasks} = useFetchTasks();
	const {setSelectAll, setSelectedItems} = useContext(InstanceListContext);
	const [currentStep, setCurrentStep] = useState('selectTasks');
	const [errorToast, setErrorToast] = useState(null);
	const [fetching, setFetching] = useState(false);
	const {selectAll, tasks} = selectTasks;
	const toaster = useToaster();

	const clearContext = useCallback(() => {
		setBulkReassign({
			reassignedTasks: [],
			reassigning: false,
			selectedAssignee: null,
			useSameAssignee: false,
		});
	}, [setBulkReassign]);

	const onCloseModal = (refetch) => {
		clearContext();
		clearFilters();
		closeModal(refetch);
		setSelectTasks({selectAll: false, tasks: []});
		setCurrentStep('selectTasks');
		setErrorToast(false);
	};

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const {patchData} = usePatch({
		admin: true,
		body: reassignedTasks,
		url: 'workflow-tasks/assign-to-user',
	});

	const handleNext = useCallback(() => {
		if (selectAll) {
			setFetching(true);
			fetchTasks()
				.then(({items}) => {
					setFetching(false);
					setSelectTasks({selectAll, tasks: items});

					setCurrentStep('selectAssignees');
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
	}, [setBulkReassign, fetchTasks, selectAll]);

	const handlePrevious = useCallback(() => {
		clearContext();
		setCurrentStep('selectTasks');
		setErrorToast(false);
	}, [clearContext]);

	const handleReassign = useCallback(() => {
		setBulkReassign((bulkReassign) => ({
			...bulkReassign,
			reassigning: true,
		}));

		setErrorToast(false);

		patchData()
			.then(() => {
				toaster.success(
					reassignedTasks.length > 1
						? sub(
								Liferay.Language.get(
									'x-tasks-have-been-reassigned'
								),
								[reassignedTasks.length]
						  )
						: Liferay.Language.get('this-task-has-been-reassigned')
				);

				onCloseModal(true);
				setSelectedItems([]);
				setSelectAll(false);
			})
			.catch(() => {
				const error = `${Liferay.Language.get(
					'your-request-has-failed'
				)} ${Liferay.Language.get('select-reassign-to-retry')}`;

				setBulkReassign((bulkReassign) => ({
					...bulkReassign,
					reassigning: false,
				}));

				setErrorToast(error);
			});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [patchData, setBulkReassign]);

	const getStep = useCallback(
		(step) => {
			const steps = {
				selectAssignees: {
					cancelBtn: {
						disabled: reassigning,
						handle: onClose,
					},
					component: SelectAssigneesStep,
					nextBtn: {
						disabled:
							reassigning ||
							reassignedTasks.length !== tasks.length,
						handle: handleReassign,
						text: Liferay.Language.get('reassign'),
					},
					order: 2,
					previousBtn: {
						disabled: reassigning,
						handle: handlePrevious,
					},
					props: {setErrorToast},
					subtitle: Liferay.Language.get('select-assignees'),
					title: Liferay.Language.get('select-new-assignees'),
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
					title: Liferay.Language.get('select-tasks-to-reassign'),
				},
			};

			return steps[step];
		},
		[
			reassigning,
			onClose,
			reassignedTasks,
			tasks,
			handleReassign,
			handlePrevious,
			fetching,
			handleNext,
		]
	);

	const step = useMemo(() => getStep(currentStep), [currentStep, getStep]);

	return (
		<ModalWithSteps
			error={errorToast}
			observer={observer}
			step={step}
			visible={visibleModal === 'bulkReassign'}
		/>
	);
};

export default BulkReassignModal;
