/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useCallback, useContext, useState} from 'react';

import ModalWithSteps from '../../../../../shared/components/modal-with-steps/ModalWithSteps.es';
import {useToaster} from '../../../../../shared/components/toaster/hooks/useToaster.es';
import {usePatch} from '../../../../../shared/hooks/usePatch.es';
import {sub} from '../../../../../shared/util/lang.es';
import {InstanceListContext} from '../../../InstanceListPageProvider.es';
import {ModalContext} from '../../ModalProvider.es';
import SelectTasksStep from '../../shared/select-tasks-step/SelectTasksStep.es';
import {useFetchTasks} from '../../shared/select-tasks-step/hooks/useFetchTasks.es';
import SelectAssigneesStep from './select-assignees-step/SelectAssigneesStep.es';

export default function BulkReassignModal() {
	const {
		bulkReassign: {reassignedTasks, reassigning},
		closeModal,
		selectTasks,
		setBulkReassign,
		setSelectTasks,
		visibleModal,
	} = useContext(ModalContext);
	const {clearFilters, fetchTasks} = useFetchTasks({
		callback: ({items}) => {
			setFetching(false);
			setSelectTasks({selectAll, tasks: items});

			setCurrentStep('selectAssignees');
		},
	});
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
		callback: () => {
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
			window.location.reload();
		},
		url: '/workflow-tasks/assign-to-user',
	});

	const handleNext = useCallback(() => {
		if (selectAll) {
			setFetching(true);

			fetchTasks().catch(() => {
				setErrorToast(Liferay.Language.get('your-request-has-failed'));
				setFetching(false);
			});
		}
		else {
			setCurrentStep('selectAssignees');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectAll]);

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

		patchData().catch(() => {
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
	}, [reassignedTasks]);

	const steps = {
		selectAssignees: {
			cancelBtn: {
				disabled: reassigning,
				handle: onClose,
			},
			component: SelectAssigneesStep,
			nextBtn: {
				disabled:
					reassigning || reassignedTasks.length !== tasks.length,
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
				disabled: !tasks.length || fetching,
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

	return (
		<ModalWithSteps
			error={errorToast}
			observer={observer}
			step={steps[currentStep]}
			visible={visibleModal === 'bulkReassign'}
		/>
	);
}
