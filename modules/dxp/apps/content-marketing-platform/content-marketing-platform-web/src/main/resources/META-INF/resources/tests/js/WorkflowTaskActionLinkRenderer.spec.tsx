/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import WorkflowTaskActionLinkRenderer from '../../js/components/props_transformer/cell_renderers/WorkflowTaskActionLinkRenderer';

jest.mock('@liferay/frontend-data-set-web', () => ({
	...((jest.requireActual('@liferay/frontend-data-set-web') ?? {}) as any),
}));

const TEST_ACTION_ID = 'actionLinkWorkflowTask';

const itemData = {
	embedded: {
		id: 42,
		objectReviewed: {assetTitle: 'My Asset'},
	},
} as any;

const buildAction = (id: string, href: string | undefined) => ({
	data: {id},
	href,
});

describe('WorkflowTaskActionLinkRenderer', () => {
	it('renders the title with a link when an action matches the actionId', () => {
		const actions = [
			buildAction('someOtherAction', '/other/{embedded.id}'),
			buildAction(TEST_ACTION_ID, '/workflow/{embedded.id}'),
		];

		const {getByRole} = render(
			<WorkflowTaskActionLinkRenderer
				actionId={TEST_ACTION_ID}
				actions={actions}
				itemData={itemData}
			/>
		);

		const link = getByRole('link', {name: 'My Asset'});

		expect(link).toHaveAttribute('href', '/workflow/42');
	});

	it('renders the title without a link when no action matches the actionId', () => {
		const actions = [
			buildAction('someOtherAction', '/other/{embedded.id}'),
		];

		const {getByText, queryByRole} = render(
			<WorkflowTaskActionLinkRenderer
				actionId={TEST_ACTION_ID}
				actions={actions}
				itemData={itemData}
			/>
		);

		expect(getByText('My Asset')).toBeInTheDocument();
		expect(queryByRole('link')).not.toBeInTheDocument();
	});

	it('renders the title without a link when the matching action has no href', () => {
		const actions = [buildAction(TEST_ACTION_ID, undefined)];

		const {getByText, queryByRole} = render(
			<WorkflowTaskActionLinkRenderer
				actionId={TEST_ACTION_ID}
				actions={actions}
				itemData={itemData}
			/>
		);

		expect(getByText('My Asset')).toBeInTheDocument();
		expect(queryByRole('link')).not.toBeInTheDocument();
	});

	it('renders the title as "untitled-asset" when assetTitle is empty', () => {
		const actions = [
			buildAction('someOtherAction', '/other/{embedded.id}'),
		];

		const {getByText} = render(
			<WorkflowTaskActionLinkRenderer
				actionId={TEST_ACTION_ID}
				actions={actions}
				itemData={{
					...itemData,
					embedded: {
						...itemData.embedded,
						objectReviewed: {assetTitle: ''},
					},
				}}
			/>
		);

		expect(getByText('untitled-asset')).toBeInTheDocument();
	});
});
