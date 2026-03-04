/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {Workflow} from '../../../../src/main/resources/META-INF/resources/js/common/types/Workflow';
import WorkflowTab from '../../../../src/main/resources/META-INF/resources/js/structure_builder/components/settings/WorkflowTab';
import {MockCacheProvider} from '../mocks/MockCacheProvider';
import {MockStateProvider} from '../mocks/MockStateProvider';

const WORKFLOWS: Workflow[] = [
	{
		externalReferenceCode: 'workflow-erc-1',
		id: 1,
		name: 'SingleApproverWorkflow',
		title: 'Single Approver Workflow',
	},
];

describe('WorkflowTab', () => {
	it('uses workflow title in default workflow select options', () => {
		render(
			<MockStateProvider>
				<MockCacheProvider spaces={[]} workflows={WORKFLOWS}>
					<WorkflowTab />
				</MockCacheProvider>
			</MockStateProvider>
		);

		expect(
			screen.getByRole('option', {
				name: 'Single Approver Workflow',
			})
		).toBeInTheDocument();

		expect(
			screen.queryByRole('option', {
				name: 'SingleApproverWorkflow',
			})
		).not.toBeInTheDocument();
	});
});
