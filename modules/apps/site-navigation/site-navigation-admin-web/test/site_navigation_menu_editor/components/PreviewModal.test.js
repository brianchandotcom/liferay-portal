/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {PreviewModal} from '../../../src/main/resources/META-INF/resources/js/site_navigation_menu_editor/components/PreviewModal';
import {ConstantsProvider} from '../../../src/main/resources/META-INF/resources/js/site_navigation_menu_editor/contexts/ConstantsContext';

jest.mock('@clayui/modal', () => {
	const ModalShell = ({children}) => (
		<div data-testid="preview-modal">{children}</div>
	);

	ModalShell.Header = ({children}) => <div>{children}</div>;
	ModalShell.Body = ({children}) => <div>{children}</div>;

	return {
		__esModule: true,
		default: ModalShell,
	};
});

jest.mock('frontend-js-web', () => ({
	addParams: (params, url) =>
		`${url}?${new URLSearchParams(params).toString()}`,
}));

function renderComponent({displayTemplateOptions = []} = {}) {
	return render(
		<ConstantsProvider
			constants={{
				displayTemplateOptions,
				portletNamespace: '_test_',
				previewSiteNavigationMenuURL: 'http://localhost/preview',
			}}
		>
			<PreviewModal observer={{}} />
		</ConstantsProvider>
	);
}

describe('PreviewModal', () => {
	beforeEach(() => {
		global.Liferay = {
			Language: {
				get: jest.fn((key) => key),
			},
		};
	});

	afterEach(() => {
		jest.resetAllMocks();
	});

	// LPD-91115

	it('renders the display-template select when options are provided', () => {
		renderComponent({
			displayTemplateOptions: [
				{
					label: 'List Menu',
					selected: false,
					value: 'list-menu-ftl',
				},
				{
					label: 'Bar Minimally Styled',
					selected: true,
					value: 'navbar-blank-ftl',
				},
			],
		});

		expect(screen.getByLabelText('display-template')).toHaveValue(
			'navbar-blank-ftl'
		);
	});

	it('does not render the display-template select when options are empty', () => {
		renderComponent({displayTemplateOptions: []});

		expect(
			screen.queryByLabelText('display-template')
		).not.toBeInTheDocument();
	});

	it('prepends a blank option that renders the default template when no option is marked as selected', () => {
		renderComponent({
			displayTemplateOptions: [
				{
					label: 'Nav Pills',
					selected: false,
					value: 'nav-pills-ftl',
				},
			],
		});

		const select = screen.getByLabelText('display-template');

		expect(select).toHaveValue('');

		const options = Array.from(select.querySelectorAll('option'));

		expect(options).toHaveLength(2);
		expect(options[0]).toHaveValue('');
		expect(options[0]).toHaveTextContent('default');
		expect(options[1]).toHaveValue('nav-pills-ftl');
	});
});
