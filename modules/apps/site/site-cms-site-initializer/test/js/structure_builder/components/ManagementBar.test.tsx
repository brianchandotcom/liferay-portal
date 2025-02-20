/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ManagementBar from '../../../../src/main/resources/META-INF/resources/js/structure_builder/components/ManagementBar';
import {State} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/StateContext';
import StructureService from '../../../../src/main/resources/META-INF/resources/js/structure_builder/services/StructureService';
import {MockStateProvider} from '../mocks/MockStateProvider';

type Props = {
	state?: Partial<State>;
};

const renderComponent = ({state}: Props = {}) => {
	return render(
		<MockStateProvider state={state}>
			<ManagementBar />
		</MockStateProvider>
	);
};

describe('ManagementBar', () => {
	beforeAll(() => {
		StructureService.createStructure = jest.fn();
		StructureService.updateStructure = jest.fn();
	});

	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('Publish button is not shown and save button is displayed as primary if structure is published', async () => {
		renderComponent({state: {status: 'published'}});

		const publishButton = screen.queryByText('publish');
		const saveButton = screen.getByText('save');

		expect(publishButton).not.toBeInTheDocument();
		expect(saveButton).toHaveClass('btn-primary');
	});

	it('Save button calls correct endpoint when status is new', async () => {
		renderComponent({state: {status: 'new'}});

		const saveButton = screen.getByText('save');

		await userEvent.click(saveButton);

		expect(StructureService.createStructure).toBeCalled();
		expect(StructureService.updateStructure).not.toBeCalled();
	});

	it('Save button calls correct endpoint when status is draft', async () => {
		renderComponent({state: {status: 'draft'}});

		const saveButton = screen.getByText('save');

		await userEvent.click(saveButton);

		expect(StructureService.createStructure).not.toBeCalled();
		expect(StructureService.updateStructure).toBeCalled();
	});

	it('Save button calls correct endpoint when status is published', async () => {

		// Status is published

		renderComponent({state: {status: 'published'}});

		const saveButton = screen.getByText('save');

		await userEvent.click(saveButton);

		expect(StructureService.createStructure).not.toBeCalled();
		expect(StructureService.updateStructure).toBeCalled();
	});
});
