/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {configure} from '@testing-library/dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {SideNavigation} from '../src/main/resources/META-INF/resources/js';

configure({
	testIdAttribute: 'data-qa-id',
});

const ITEMS = [
	{
		id: 'content',
		items: [
			{
				href: 'assetsHref',
				id: 'assets',
				label: 'Assets',
				leadingIcon: 'assetsIcon',
			},
			{
				href: 'dashboardHref',
				id: 'dashboard',
				label: 'Dashboard',
				leadingIcon: 'dashboardIcon',
			},
		],
		label: 'Content',
	},
	{
		id: 'workflow',
		items: [
			{
				href: 'metricsHref',
				id: 'metrics',
				label: 'Metrics',
				leadingIcon: 'metricsIcon',
			},
		],
		label: 'Workflow',
	},
];

const renderComponent = ({expandedKeys = ['content', 'workflow']} = {}) =>
	render(
		<SideNavigation
			categoryImageUrl="categoryImageUrl"
			expandedKeys={expandedKeys}
			expandedKeysSessionKey="expandedKeysSessionKey"
			items={ITEMS}
			label="Applications"
			portletId="assets"
			siteAdministrationItemSelectedEventName="siteAdministrationItemSelectedEventName"
			siteAdministrationItemSelectorUrl="siteAdministrationItemSelectorUrl"
			visible
			visibleSessionKey="visibleSessionKey"
		/>
	);

describe('SideNavigation', () => {
	it('renders the side navigation header', () => {
		renderComponent();

		const title = screen.getByText('Applications');

		expect(title).toBeInTheDocument();

		const icon = screen.getByTestId('sideNavigationProductIcon');

		expect(icon).toHaveAttribute('src', 'categoryImageUrl');
	});

	it('renders each navigation item', () => {
		const {baseElement} = renderComponent();

		const menuItems = screen.getAllByRole('menuitem');

		expect(menuItems).toHaveLength(5);

		['Content', 'Workflow'].forEach((label) => {
			expect(screen.getByText(label)).toHaveAttribute(
				'aria-expanded',
				'true'
			);
		});

		['Assets', 'Dashboard', 'Metrics'].forEach((label) => {
			expect(screen.getByText(label)).toHaveAttribute(
				'href',
				`${label.toLowerCase()}Href`
			);

			const icon = baseElement.querySelector(
				`.lexicon-icon-${label.toLowerCase()}Icon`
			);

			expect(icon).toBeInTheDocument();
		});

		expect(screen.getByText('Assets')).toHaveClass('active');
		expect(screen.getByText('Workflow')).not.toHaveClass('active');
		expect(screen.getByText('Metrics')).not.toHaveClass('active');
	});

	it('shows only the navigation items from the expanded keys', () => {
		renderComponent({expandedKeys: ['workflow']});

		const menuItems = screen.getAllByRole('menuitem');

		expect(menuItems).toHaveLength(3);

		expect(screen.getByText('Content')).toHaveAttribute(
			'aria-expanded',
			'false'
		);

		expect(screen.getByText('Workflow')).toHaveAttribute(
			'aria-expanded',
			'true'
		);
	});
});
