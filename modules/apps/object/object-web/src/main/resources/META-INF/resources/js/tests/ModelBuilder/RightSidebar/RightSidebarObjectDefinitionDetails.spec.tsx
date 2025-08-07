/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';

// @ts-ignore

import fetchMock from 'fetch-mock';
import React from 'react';

import {RightSidebarObjectDefinitionDetails} from '../../../components/ModelBuilder/RightSidebar/RightSidebarObjectDefinitionDetails';
import {objectDefinition} from './__mock__/objectDefinition';

const OBJECT_DEFINITION_URL_REGEX =
	/\/o\/object-admin\/v1\.0\/object-definitions\/by-external-reference-code\/.+/;

let mockUseObjectFolderContextReturnValue = [
	{
		selectedObjectDefinitionNode: {
			data: {externalReferenceCode: '1'},
		},
	},
	jest.fn(),
];

const rightSidebarObjectDefinitionDetailsProps = {
	companies: [],
	sites: [],
};

const renderComponent = (customProps = {}) => {
	fetchMock.get(OBJECT_DEFINITION_URL_REGEX, {
		body: {...objectDefinition, ...customProps},
	});

	render(
		<RightSidebarObjectDefinitionDetails
			{...rightSidebarObjectDefinitionDetailsProps}
		/>
	);
};

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as object),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

jest.mock(
	'../../../components/ModelBuilder/ModelBuilderContext/objectFolderContext',
	() => ({
		useObjectFolderContext: () => mockUseObjectFolderContextReturnValue,
	})
);

jest.mock('react-flow-renderer', () => {
	return {
		useStore() {
			return {
				edges: [],
				nodes: [],
			};
		},
	};
});

afterAll(() => {
	jest.restoreAllMocks();
});

afterEach(() => {
	fetchMock.restore();
	mockUseObjectFolderContextReturnValue = [
		{
			selectedObjectDefinitionNode: {
				data: {externalReferenceCode: '1'},
			},
		},
		jest.fn(),
	];
});

describe('object definition configuration', () => {
	it('enable account restriction is enabled by default', async () => {
		renderComponent({storageType: 'salesforce'});

		const accountRestrictionToggle = await screen.findByRole('switch', {
			name: 'enable-account-restriction',
		});

		expect(accountRestrictionToggle).toBeEnabled();
	});

	it('panel link is enabled by default', async () => {
		renderComponent();

		const panelLink = await screen.findByRole('combobox', {
			name: 'panel-link',
		});

		expect(panelLink).toBeEnabled();
	});

	it('scope is enabled by default', async () => {
		renderComponent();

		const scope = await screen.findByRole('combobox', {
			name: 'scope',
		});

		expect(scope).toBeEnabled();
	});

	it('show widget in page builder is enabled by default', async () => {
		renderComponent();

		const showWidgetToggle = await screen.findByRole('switch', {
			name: 'show-widget-in-page-builder',
		});

		expect(showWidgetToggle).toBeEnabled();
	});
});

describe('when the object definition is approved', () => {
	it('enable account restriction is disabled', async () => {
		renderComponent({
			accountEntryRestricted: true,
			status: {label: 'approved'},
		});

		const accountRestrictionToggle = await screen.findByRole('switch', {
			name: 'enable-account-restriction',
		});

		expect(accountRestrictionToggle).toBeDisabled();
	});

	it('panel link is enabled', async () => {
		renderComponent({status: {label: 'approved'}});

		const panelLink = await screen.findByRole('combobox', {
			name: 'panel-link',
		});

		expect(panelLink).toBeEnabled();
	});

	it('scope is disabled', async () => {
		renderComponent({status: {label: 'approved'}});

		const scope = await screen.findByRole('combobox', {
			name: 'scope',
		});

		expect(scope).toBeDisabled();
	});

	it('show widget in page builder is enabled', async () => {
		renderComponent({status: {label: 'approved'}});

		const showWidgetToggle = await screen.findByRole('switch', {
			name: 'show-widget-in-page-builder',
		});

		expect(showWidgetToggle).toBeEnabled();
	});
});

describe('when the object definition is a system object', () => {
	it('enable account restriction is enabled', async () => {
		renderComponent({system: true});

		const accountRestrictionToggle = await screen.findByRole('switch', {
			name: 'enable-account-restriction',
		});

		expect(accountRestrictionToggle).toBeDisabled();
	});

	it('panel link is disabled', async () => {
		renderComponent({modifiable: false, system: true});

		const panelLink = await screen.findByRole('combobox', {
			name: 'panel-link',
		});

		expect(panelLink).toBeDisabled();
	});

	it('scope is enabled', async () => {
		renderComponent({system: true});

		const scope = await screen.findByRole('combobox', {
			name: 'scope',
		});

		expect(scope).toBeEnabled();
	});

	it('show widget in page builder is disabled', async () => {
		renderComponent({modifiable: false, system: true});

		const showWidgetToggle = await screen.findByRole('switch', {
			name: 'show-widget-in-page-builder',
		});

		expect(showWidgetToggle).toBeDisabled();
	});
});

describe('when the object definition has a root object definition', () => {
	it('configurations are enabled', async () => {
		renderComponent({
			rootObjectDefinitionExternalReferenceCode: '1',
			storageType: 'sugarcrm',
		});

		const accountRestriction = await screen.findByRole('switch', {
			name: 'enable-account-restriction',
		});

		const panelLink = await screen.findByRole('combobox', {
			name: 'panel-link',
		});

		const scope = await screen.findByRole('combobox', {name: 'scope'});

		const showWidget = await screen.findByRole('switch', {
			name: 'show-widget-in-page-builder',
		});

		expect(accountRestriction).toBeEnabled();
		expect(panelLink).toBeEnabled();
		expect(scope).toBeEnabled();
		expect(showWidget).toBeEnabled();
	});
});

describe('when the object definition has storageType salesforce', () => {
	it('enable account restriction is enabled', async () => {
		renderComponent({storageType: 'salesforce'});

		const accountRestrictionToggle = await screen.findByRole('switch', {
			name: 'enable-account-restriction',
		});

		expect(accountRestrictionToggle).toBeEnabled();
	});

	it('panel link is enabled', async () => {
		renderComponent({storageType: 'salesforce'});

		const panelLink = await screen.findByRole('combobox', {
			name: 'panel-link',
		});

		expect(panelLink).toBeEnabled();
	});

	it('scope is disabled', async () => {
		renderComponent({storageType: 'salesforce'});

		const scope = await screen.findByRole('combobox', {
			name: 'scope',
		});

		expect(scope).toBeDisabled();
	});

	it('show widget in page builder is enabled', async () => {
		renderComponent({storageType: 'salesforce'});

		const showWidgetToggle = await screen.findByRole('switch', {
			name: 'show-widget-in-page-builder',
		});

		expect(showWidgetToggle).toBeEnabled();
	});
});
