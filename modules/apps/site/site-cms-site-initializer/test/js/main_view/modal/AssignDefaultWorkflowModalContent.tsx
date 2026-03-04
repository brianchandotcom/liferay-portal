/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';

import {getWorkflowDefinitions} from '../../../../src/main/resources/META-INF/resources/js/common/services/WorkflowService';
import AssignDefaultWorkflowModalContent from '../../../../src/main/resources/META-INF/resources/js/main_view/modal/AssignDefaultWorkflowModalContent';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/WorkflowService',
	() => ({
		getWorkflowDefinitions: jest.fn(),
	})
);

(getWorkflowDefinitions as jest.Mock).mockResolvedValue([
	{name: 'Single Approver'},
]);

const mockCloseModal = jest.fn();
const mockSubmitModal = jest.fn();

const DEFAULT_PROPS = {
	closeModal: mockCloseModal,
	structureWorkflows: [{id: '1', name: 'Structure A', workflow: ''}],
	submitModal: mockSubmitModal,
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

	it('calls StructureService.updateStructureWorkflow only once on assign workflow click', async () => {
		const {getByLabelText, getByRole} = renderComponent({
			closeModal: mockCloseModal,
			structureWorkflows: [
				{id: '1', name: 'Structure A', workflow: ''},
				{id: '2', name: 'Structure B', workflow: ''},
			],
			submitModal: mockSubmitModal,
		});

		await waitFor(() => {
			expect(
				getByRole('option', {name: 'Single Approver'})
			).toBeInTheDocument();
		});

		fireEvent.change(getByLabelText('default-workflow'), {
			target: {value: 'Single Approver'},
		});

		fireEvent.click(getByRole('button', {name: 'assign-workflow'}));

		await waitFor(() => {
			expect(mockSubmitModal).toHaveBeenCalledTimes(1);
			expect(mockSubmitModal).toHaveBeenCalledWith('Single Approver');
			expect(mockCloseModal).toHaveBeenCalledTimes(1);
		});
	});

	it('keeps assign button disabled until workflow is changed', async () => {
		const {getByRole} = renderComponent();

		expect(getByRole('button', {name: 'assign-workflow'})).toBeDisabled();
		expect(mockSubmitModal).not.toHaveBeenCalled();
	});
});
