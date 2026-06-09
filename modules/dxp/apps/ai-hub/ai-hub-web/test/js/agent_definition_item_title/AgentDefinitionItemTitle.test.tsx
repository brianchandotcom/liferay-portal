/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import AgentDefinitionItemTitle from '../../../src/main/resources/META-INF/resources/js/agent_definition_item_title/AgentDefinitionItemTitle';

jest.mock('@liferay/frontend-data-set-web', () => ({
	getFDSInternalRenderer: () => undefined,
}));

(global as any).Liferay = {
	Icons: {spritemap: 'icons.svg'},
	Language: {
		get: (key: string) => key,
	},
};

function renderItemTitle(
	itemData: React.ComponentProps<typeof AgentDefinitionItemTitle>['itemData']
) {
	render(
		<AgentDefinitionItemTitle
			actions={[]}
			itemData={itemData}
			itemId="itemId"
			value="Agent"
		/>
	);
}

describe('AgentDefinitionItemTitle', () => {
	it('does not render the model line when the model name is missing', () => {
		renderItemTitle({
			active: true,
			model: {
				label: 'Gemini 2.5 Flash',
				providerLabel: 'Google',
			},
		});

		expect(
			screen.queryByText('Google - Gemini 2.5 Flash')
		).not.toBeInTheDocument();
	});

	it('renders a custom label when the agent is not a system agent', () => {
		renderItemTitle({
			active: true,
			system: false,
		});

		expect(screen.getByText('custom')).toBeVisible();
		expect(
			screen.queryByText('provided-by-liferay')
		).not.toBeInTheDocument();
	});

	it('renders a running label when the agent is active', () => {
		renderItemTitle({
			active: true,
		});

		expect(screen.getByText('running')).toBeVisible();
		expect(screen.queryByText('stopped')).not.toBeInTheDocument();
	});

	it('renders a stopped label when the agent is inactive', () => {
		renderItemTitle({
			active: false,
		});

		expect(screen.getByText('stopped')).toBeVisible();
		expect(screen.queryByText('running')).not.toBeInTheDocument();
	});

	it('renders the provided-by-liferay and minimum-risk labels for a system agent', () => {
		renderItemTitle({
			active: true,
			system: true,
		});

		expect(screen.getByText('provided-by-liferay')).toBeVisible();
		expect(screen.getByText('minimum-risk')).toBeVisible();
		expect(screen.queryByText('custom')).not.toBeInTheDocument();
	});

	it('renders the provider and model labels joined by a dash', () => {
		renderItemTitle({
			active: true,
			model: {
				label: 'Gemini 2.5 Flash',
				name: 'gemini-2.5-flash',
				providerLabel: 'Google',
			},
		});

		expect(screen.getByText('Google - Gemini 2.5 Flash')).toBeVisible();
	});
});
