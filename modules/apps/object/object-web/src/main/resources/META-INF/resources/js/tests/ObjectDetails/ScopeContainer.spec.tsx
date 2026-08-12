/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import {Scope} from '../../components/ObjectDetails/EditObjectDetails';
import {ScopeContainer} from '../../components/ObjectDetails/ScopeContainer';

const CONTENT_PANEL_CATEGORY_KEY = 'applications_menu.applications.content';

const SEARCH_TUNING_PANEL_CATEGORY_KEY = 'control_panel.search_tuning';

const COMPANIES: Scope[] = [
	{
		items: [
			{
				label: 'Developer & Integration',
				value: 'applications_menu.applications.developer-integration',
			},
			{
				deprecated: true,
				label: 'Content (Deprecated)',
				value: CONTENT_PANEL_CATEGORY_KEY,
			},
		],
		label: 'Applications',
	},
	{
		items: [
			{
				deprecated: true,
				label: 'Search Tuning (Deprecated)',
				value: SEARCH_TUNING_PANEL_CATEGORY_KEY,
			},
		],
		label: 'Control Panel',
	},
];

function StatefulScopeContainer({
	panelCategoryKey,
}: {
	panelCategoryKey: string;
}) {
	const [values, setValues] = useState<Partial<ObjectDefinition>>({
		externalReferenceCode: 'ERC',
		id: 1,
		panelCategoryKey,
		scope: 'company',
	});

	return (
		<ScopeContainer
			companies={COMPANIES}
			errors={{}}
			hasUpdateObjectDefinitionPermission
			isApproved={false}
			setValues={(newValues) =>
				setValues((previousValues) => ({
					...previousValues,
					...newValues,
				}))
			}
			sites={[]}
			values={values}
		/>
	);
}

const renderComponent = async (panelCategoryKey: string) => {
	render(<StatefulScopeContainer panelCategoryKey={panelCategoryKey} />);

	const panelLink = await screen.findByRole('combobox', {
		name: 'panel-link',
	});

	await userEvent.click(panelLink);

	return panelLink;
};

describe('The ScopeContainer panel link', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('does not offer a deprecated panel category', async () => {
		await renderComponent('');

		expect(
			screen.getByRole('option', {name: 'Developer & Integration'})
		).toBeInTheDocument();
		expect(
			screen.queryByRole('option', {name: 'Content (Deprecated)'})
		).not.toBeInTheDocument();
	});

	it('offers the deprecated panel category the object definition uses', async () => {
		const panelLink = await renderComponent(CONTENT_PANEL_CATEGORY_KEY);

		expect(
			screen.getByRole('option', {name: 'Content (Deprecated)'})
		).toBeInTheDocument();
		expect(panelLink).toHaveTextContent('Content (Deprecated)');
	});

	it('hides a group whose only panel category is deprecated', async () => {
		await renderComponent('');

		expect(screen.getByText('Applications')).toBeInTheDocument();
		expect(screen.queryByText('Control Panel')).not.toBeInTheDocument();
	});

	it('keeps offering the initially selected deprecated panel category after another one is selected', async () => {
		const panelLink = await renderComponent(CONTENT_PANEL_CATEGORY_KEY);

		await userEvent.click(
			screen.getByRole('option', {name: 'Developer & Integration'})
		);

		expect(panelLink).toHaveTextContent('Developer & Integration');

		await userEvent.click(panelLink);

		expect(
			screen.getByRole('option', {name: 'Content (Deprecated)'})
		).toBeInTheDocument();
	});

	it('shows a group whose only panel category is the deprecated one in use', async () => {
		await renderComponent(SEARCH_TUNING_PANEL_CATEGORY_KEY);

		expect(screen.getByText('Control Panel')).toBeInTheDocument();
		expect(
			screen.getByRole('option', {name: 'Search Tuning (Deprecated)'})
		).toBeInTheDocument();
	});
});
