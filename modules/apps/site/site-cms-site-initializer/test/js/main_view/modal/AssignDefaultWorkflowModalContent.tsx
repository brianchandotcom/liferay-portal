/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, waitFor} from '@testing-library/react';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import StructureService from '../../../../src/main/resources/META-INF/resources/js/common/services/StructureService';
import {getWorkflowDefinitions} from '../../../../src/main/resources/META-INF/resources/js/common/services/WorkflowService';
import AssignDefaultWorkflowModalContent from '../../../../src/main/resources/META-INF/resources/js/main_view/modal/AssignDefaultWorkflowModalContent';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/WorkflowService',
	() => ({
		getWorkflowDefinitions: jest.fn(),
	})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/StructureService',
	() => ({
		updateStructureWorkflow: jest.fn(),
	})
);

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

(getWorkflowDefinitions as jest.Mock).mockResolvedValue([
	{name: 'Single Approver'},
]);

const mockCloseModal = jest.fn();

const DEFAULT_PROPS = {
	closeModal: mockCloseModal,
	structureWorkflows: [{id: '1', name: 'Structure A', workflow: ''}],
};

const renderComponent = (props = DEFAULT_PROPS) => {
	return render(<AssignDefaultWorkflowModalContent {...props} />);
};

describe('AssignDefaultWorkflowModalContent', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('checks the accessibility of the modal component', async () => {
		const {container} = renderComponent();

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('renders and loads workflow options', async () => {
		const {getAllByRole, getByLabelText, getByRole} = renderComponent();

		expect(
			getByRole('heading', {
				level: 1,
				name: 'assign-default-workflow-to-x',
			})
		).toBeInTheDocument();

		expect(getByLabelText('default-workflow')).toBeInTheDocument();

		await waitFor(() => {
			expect(getWorkflowDefinitions).toHaveBeenCalledTimes(1);

			// No Workflow + Single Approver options

			expect(getAllByRole('option').length).toBe(2);
		});
	});

	it('calls closeModal when cancel is clicked', async () => {
		const {getByRole} = renderComponent();

		fireEvent.click(getByRole('button', {name: 'cancel'}));

		expect(mockCloseModal).toHaveBeenCalledTimes(1);
	});

	it('calls StructureService.updateStructureWorkflow for each structure on assign workflow click', async () => {
		(
			StructureService.updateStructureWorkflow as jest.Mock
		).mockResolvedValue({error: false});

		const {getByRole} = renderComponent({
			closeModal: mockCloseModal,
			structureWorkflows: [
				{id: '1', name: 'Structure A', workflow: ''},
				{id: '2', name: 'Structure B', workflow: ''},
			],
		});

		fireEvent.click(getByRole('button', {name: 'assign-workflow'}));

		await waitFor(() => {
			expect(
				StructureService.updateStructureWorkflow
			).toHaveBeenCalledTimes(2);

			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message:
						'x-workfow-was-successfully-assigned-to-multiple-content-structure',
					type: 'success',
				})
			);
		});
	});

	it('shows an error toast if there are errors', async () => {
		(
			StructureService.updateStructureWorkflow as jest.Mock
		).mockResolvedValue({error: true});

		const {getByRole} = renderComponent();

		fireEvent.click(getByRole('button', {name: 'assign-workflow'}));

		await waitFor(() => {
			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({
					type: 'danger',
				})
			);
		});
	});
});
