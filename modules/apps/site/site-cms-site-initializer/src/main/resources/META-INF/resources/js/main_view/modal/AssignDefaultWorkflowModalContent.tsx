/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useId, useState} from 'react';

import StructureService from '../../common/services/StructureService';
import {getWorkflowDefinitions} from '../../common/services/WorkflowService';

interface WorkflowOption {
	label: string;
	value: string;
}

export interface StructureWorkflowItem {
	id: string;
	name: string;
	workflow: string;
}

export default function AssignDefaultWorkflowModalContent({
	closeModal,
	structureWorkflows,
}: {
	closeModal: () => void;
	structureWorkflows: StructureWorkflowItem[];
}) {
	const [selectedWorkflow, setSelectedWorkflow] = useState<string>('');

	const [workflows, setWorkflows] = useState<WorkflowOption[]>([]);

	const onAssignWorkflowButtonClick = async () => {
		const {error} = await StructureService.updateStructureWorkflow({
			structureIds: structureWorkflows.map((item) => item.id),
			workflow: selectedWorkflow,
		});

		if (error) {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
				title: Liferay.Language.get('error'),
				type: 'danger',
			});
		}
		else {
			let toastMessage;

			if (structureWorkflows.length === 1) {
				toastMessage = sub(
					Liferay.Language.get(
						'x-workflow-was-successfully-assigned-to-x'
					),
					[
						selectedWorkflow || Liferay.Language.get('no-workflow'),
						structureWorkflows[0].name,
					]
				);
			}
			else {
				toastMessage = sub(
					Liferay.Language.get(
						'x-workflow-was-successfully-assigned-to-multiple-content-structure'
					),
					[selectedWorkflow || Liferay.Language.get('no-workflow')]
				);
			}

			openToast({
				message: toastMessage,
				title: Liferay.Language.get('success'),
				type: 'success',
			});
		}
	};

	useEffect(() => {
		const fetchWorkflows = async () => {
			const data = await getWorkflowDefinitions();

			const options = [
				{label: Liferay.Language.get('no-workflow'), value: ''},
				...data.map((workflow) => ({
					label: workflow.name,
					value: workflow.name,
				})),
			];

			setWorkflows(options);

			if (structureWorkflows.length === 1) {
				setSelectedWorkflow(structureWorkflows[0].workflow);
			}
			else {
				const workflowValues = structureWorkflows.map(
					(item) => item.workflow
				);
				const sameValue = workflowValues.every(
					(value) => value === workflowValues[0]
				);

				setSelectedWorkflow(
					sameValue
						? workflowValues[0]
						: Liferay.Language.get('mixed-workflows')
				);
			}
		};

		fetchWorkflows();
	}, [structureWorkflows]);

	const selectId = useId();

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{structureWorkflows.length === 1
					? sub(
							Liferay.Language.get(
								'assign-default-workflow-to-x'
							),
							structureWorkflows[0].name
						)
					: Liferay.Language.get('assign-workflow')}
			</ClayModal.Header>

			<ClayModal.Body>
				<>
					<p>
						{structureWorkflows.length === 1
							? Liferay.Language.get(
									'set-the-default-workflow-for-entries-created-with-this-content-structure'
								)
							: Liferay.Language.get(
									'set-the-default-workflow-for-the-selected-content-structures'
								)}
					</p>

					<ClayForm.Group className="mb-0">
						<label htmlFor={selectId}>
							{Liferay.Language.get('default-workflow')}
						</label>

						<ClaySelectWithOption
							id={selectId}
							onChange={(event) =>
								setSelectedWorkflow(event.target.value)
							}
							options={workflows}
							value={selectedWorkflow}
						/>
					</ClayForm.Group>
				</>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={onAssignWorkflowButtonClick}
						>
							{Liferay.Language.get('assign-workflow')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
