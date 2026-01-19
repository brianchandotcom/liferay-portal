/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {useEffect, useId, useState} from 'react';

import {getWorkflowDefinitions} from '../../common/services/WorkflowService';

interface WorkflowOption {
	label: string;
	value: string;
}

export interface StructureWorkflowItem {
	id: string;
	workflow: string;
}

export default function AssignDefaultWorkflowModalContent({
	closeModal,
	structureWorkflows,
}: {
	closeModal: () => void;
	structureWorkflows: StructureWorkflowItem[];
}) {
	const [selectedWorkflow, setSelectedWorkflow] = useState('');

	const [workflows, setWorkflows] = useState<WorkflowOption[]>();

	const onAssignWorkflowButtonClick = () => {
		console.log('selected workflow: ' + selectedWorkflow);
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

			// Select first option by default (No Workflow)

			setSelectedWorkflow(options[0].value);
		};

		fetchWorkflows();
	}, []);

	const selectId = useId();

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{structureWorkflows.length === 1
					? Liferay.Language.get(
							'assign-default-workflow-to-basic-content'
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
							options={workflows as any[]}
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
