/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import WorkflowContainer from '../../components/WorkflowContainer';

describe('WorkflowContainer component', () => {
	beforeAll(() => {
		global.Liferay = {
			...global.Liferay,
			FeatureFlags: {
				...global.Liferay?.FeatureFlags,
				'LPD-34594': true,
			},
		};
	});

	it('display on the workflow section', async () => {
		render(
			<WorkflowContainer
				baseResourceURL=""
				className=""
				isRootDescendantNode={false}
				objectDefinitionId={1111}
				workflowLabel="No Workflow"
			/>
		);

		// shows workflow description

		expect(
			screen.getByText(
				'to-set-a-workflow-go-to-applications-process-builder-configuration'
			)
		).toBeVisible();

		// shows process builder configuration button

		expect(
			screen.getByText('process-builder-configurations')
		).toBeVisible();

		// does not show info about root descendant node inheriting workflow configurations

		const info = screen.queryByText(
			'object-definitions-that-inherit-permission-from-a-root-object-definition-also-follow-the-workflow-assigned-to-it'
		);

		expect(info).not.toBeInTheDocument();
	});

	it('changes the display on the workflow section if is a root descendant node', async () => {
		render(
			<WorkflowContainer
				baseResourceURL=""
				className=""
				isRootDescendantNode={true}
				objectDefinitionId={1111}
				workflowLabel="No Workflow"
			/>
		);

		// shows info about root descendant node inheriting workflow configurations

		expect(
			screen.getByText(
				'object-definitions-that-inherit-permission-from-a-root-object-definition-also-follow-the-workflow-assigned-to-it'
			)
		).toBeVisible();

		// does not show workflow description

		const description = screen.queryByText(
			'to-set-a-workflow-go-to-applications-process-builder-configuration'
		);

		expect(description).not.toBeInTheDocument();

		// does not show process builder configuration button

		const processBuilderButton = screen.queryByText(
			'process-builder-configurations'
		);

		expect(processBuilderButton).not.toBeInTheDocument();
	});
});
