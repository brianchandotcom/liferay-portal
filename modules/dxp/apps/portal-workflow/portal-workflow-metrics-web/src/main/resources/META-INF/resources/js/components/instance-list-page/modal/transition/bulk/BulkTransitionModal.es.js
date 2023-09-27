/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {useCallback, useContext, useState} from 'react';

import ModalWithSteps from '../../../../../shared/components/modal-with-steps/ModalWithSteps.es';
import {useToaster} from '../../../../../shared/components/toaster/hooks/useToaster.es';
import {usePatch} from '../../../../../shared/hooks/usePatch.es';
import {InstanceListContext} from '../../../InstanceListPageProvider.es';
import {ModalContext} from '../../ModalProvider.es';
import SelectTasksStep from '../../shared/select-tasks-step/SelectTasksStep.es';
import {useFetchTasks} from '../../shared/select-tasks-step/hooks/useFetchTasks.es';
import SelectTransitionStep from './select-transition-step/SelectTransitionStep.es';

export default function BulkTransitionModal() {
	const {setSelectAll, setSelectedItems} = useContext(InstanceListContext);
	const {
		bulkTransition: {
			transition: {errors},
			transitionTasks,
		},
		closeModal,
		selectTasks: {selectAll, tasks},
		setBulkTransition,
		setSelectTasks,
		visibleModal,
	} = useContext(ModalContext);
	const {clearFilters, fetchTasks} = useFetchTasks({
		callback: ({items}) => {
			setFetching(false);
			setSelectTasks({selectAll, tasks: items});

			setCurrentStep('selectTransitions');
		},
		withoutUnassigned: true,
	});
	const [currentStep, setCurrentStep] = useState('selectTasks');
	const [errorToast, setErrorToast] = useState(null);
	const [fetching, setFetching] = useState(false);
	const [transitioning, setTransitioning] = useState(false);
	const toaster = useToaster();

	const clearContext = useCallback(() => {
		setBulkTransition({
			transition: {errors: [], onGoing: false},
			transitionTasks: [],
		});
	}, [setBulkTransition]);

	const onCloseModal = (refetch) => {
		clearContext();
		clearFilters();
		closeModal(refetch);
		setSelectTasks({selectAll: false, tasks: []});
		setCurrentStep('selectTasks');
		setErrorToast(false);
		window.location.reload();
	};

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const {patchData} = usePatch({
		admin: true,
		body: transitionTasks,
		callback: () => {
			toaster.success(
				transitionTasks.length > 1
					? Liferay.Language.get(
							'the-selected-steps-have-transitioned-successfully'
					  )
					: Liferay.Language.get(
							'the-selected-step-has-transitioned-successfully'
					  )
			);

			onCloseModal(true);
			setSelectedItems([]);
			setSelectAll(false);
			setTransitioning(false);
		},
		url: '/workflow-tasks/change-transition',
	});

	const handleDone = useCallback(() => {
		setBulkTransition({
			transition: {errors, onGoing: true},
			transitionTasks,
		});

		if (!Object.values(errors).some((error) => error)) {
			setTransitioning(true);

			patchData().catch(() => {
				setErrorToast(
					`${Liferay.Language.get(
						'your-request-has-failed'
					)} ${Liferay.Language.get('select-done-to-retry')}`
				);
				setTransitioning(false);
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

			fetchTasks().catch(() => {
				setErrorToast(
					`${Liferay.Language.get('your-request-has-failed')}`
				);
				setFetching(false);
			});
		}
		else {
			setCurrentStep('selectTransitions');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectAll]);

	const handlePrevious = useCallback(() => {
		clearContext();
		setErrorToast(false);
		setCurrentStep('selectTasks');
	}, [clearContext, setErrorToast, setCurrentStep]);

	const steps = {
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
			props: {setErrorToast, withoutUnassigned: true},
			subtitle: Liferay.Language.get('select-steps'),
			title: Liferay.Language.get('select-steps-to-transition'),
		},
		selectTransitions: {
			cancelBtn: {
				disabled: transitioning,
				handle: onClose,
			},
			component: SelectTransitionStep,
			nextBtn: {
				disabled: transitioning,
				handle: handleDone,
				text: Liferay.Language.get('done'),
			},
			order: 2,
			previousBtn: {
				disabled: transitioning,
				handle: handlePrevious,
			},
			props: {setErrorToast},
			subtitle: Liferay.Language.get('choose-transition'),
			title: Liferay.Language.get('choose-transition-per-step'),
		},
	};

	return (
		<ModalWithSteps
			error={errorToast}
			observer={observer}
			step={steps[currentStep]}
			visible={visibleModal === 'bulkTransition'}
		/>
	);
}
