/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import fetch from 'jest-fetch-mock';
import React from 'react';

import {NewExport} from '../../../../../src/main/resources/META-INF/resources/revamp/js/pages/export/NewExport';
import {ExportPreview} from '../../../../../src/main/resources/META-INF/resources/revamp/js/types/exportImportPreview';
import {LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY} from '../../../../../src/main/resources/META-INF/resources/revamp/js/utils/contentSelection';
import {mockExportPreview} from '../../mocks/mockExportPreview';
import {mockPageTreeItems} from '../../mocks/mockPageTreeItems';

jest.mock('staging-taglib', () => ({
	PagesTree: require('../../mocks/MockPagesTree').MockPagesTree,
}));

const DEFAULT_PROPS = {
	backURL: '/some/back/url',
	exportPreviewAPIURL: '/o/export-import/v1.0/export-preview',
	exportProcessAPIURL: '/o/export-import/v1.0/export-processes',
	pageTreeModalConfiguration: {
		liveGroupId: 20121,
		pageSize: 20,
		privateLayoutsEnabled: false,
	},
};

const renderComponent = (
	props: Partial<React.ComponentProps<typeof NewExport>> = {}
) => render(<NewExport {...DEFAULT_PROPS} {...props} />);

describe('NewExport', () => {
	beforeEach(() => {
		fetch.resetMocks();
		fetch.mockResponse(JSON.stringify(mockExportPreview));
	});

	it('renders the export form', async () => {
		const {container} = renderComponent();

		const nameInput = await screen.findByRole('textbox', {
			name: /^name/i,
		});
		expect(nameInput).toBeInTheDocument();

		expect(screen.getByText('filter-content-by')).toBeInTheDocument();

		await screen.findByText('loaded');

		await checkAccessibility({
			context: {

				// TODO Drop exclude once content_selector checkboxes expose labels

				exclude: ['[data-testid="data-selection-section"]'],
				include: [container],
			},
		});
	});

	it('renders the error alert when the API fails', async () => {
		fetch.resetMocks();
		fetch.mockResponseOnce(JSON.stringify({title: 'boom'}), {status: 500});

		renderComponent();

		expect(await screen.findByText('boom')).toBeInTheDocument();
	});

	it('shows a required error on name when blurred empty', async () => {
		renderComponent();

		const nameInput = await screen.findByRole('textbox', {
			name: /^name/i,
		});

		await userEvent.click(nameInput);
		nameInput.blur();

		await screen.findByText('this-field-is-required');
	});

	it('keeps the export button disabled while the form is invalid', async () => {
		renderComponent();

		const exportButton = await screen.findByRole('button', {
			name: /^export$/i,
		});

		await waitFor(() => {
			expect(exportButton).toBeDisabled();
		});
	});

	it('shows and clears the selection error as contentSelection toggles', async () => {
		renderComponent();

		const nameInput = await screen.findByRole('textbox', {
			name: /^name/i,
		});
		await userEvent.type(nameInput, 'test-file');

		const dataSelectionGroup = screen.getByRole('group', {
			name: 'data-selection',
		});
		const checkbox = within(dataSelectionGroup).getAllByRole('checkbox')[0];

		await userEvent.click(checkbox);
		await userEvent.click(checkbox);

		await screen.findByText(
			'please-select-at-least-one-entity-type-to-continue'
		);
		expect(dataSelectionGroup).toHaveAttribute('aria-invalid', 'true');

		await userEvent.click(checkbox);

		await waitFor(() => {
			expect(
				screen.queryByText(
					'please-select-at-least-one-entity-type-to-continue'
				)
			).not.toBeInTheDocument();
		});
		expect(dataSelectionGroup).not.toHaveAttribute('aria-invalid');
	});

	it('keeps form values after applying a filter', async () => {
		renderComponent();

		const nameInput = await screen.findByRole('textbox', {
			name: /^name/i,
		});
		await userEvent.type(nameInput, 'test-file');

		const filterSelect = screen.getByRole('combobox', {
			name: 'filter-content-by',
		});
		await userEvent.selectOptions(filterSelect, 'last');

		await userEvent.click(
			screen.getByRole('button', {name: /show-results/i})
		);

		await waitFor(() => {
			expect(fetch).toHaveBeenCalledTimes(2);
		});

		expect(screen.getByRole('textbox', {name: /^name/i})).toHaveValue(
			'test-file'
		);
	});

	it('enables the export button once name and contentSelection are set', async () => {
		renderComponent();

		const nameInput = await screen.findByRole('textbox', {
			name: /^name/i,
		});

		await userEvent.type(nameInput, 'test-file');

		const dataSelectionGroup = screen.getByRole('group', {
			name: 'data-selection',
		});

		await userEvent.click(
			within(dataSelectionGroup).getAllByRole('checkbox')[0]
		);

		const exportButton = screen.getByRole('button', {name: /^export$/i});

		await waitFor(() => {
			expect(exportButton).toBeEnabled();
		});
	});

	it('hides the deletions checkbox when the preview has no deletions', async () => {
		fetch.resetMocks();
		fetch.mockResponse(
			JSON.stringify({
				...mockExportPreview,
				deletionCount: 0,
			})
		);

		renderComponent();

		await screen.findByText('loaded');

		expect(
			screen.queryByLabelText(/^export-individual-deletions/)
		).not.toBeInTheDocument();
	});

	it('shows the deletions checkbox unchecked and toggles it when the preview has deletions', async () => {
		renderComponent();

		const deletionsCheckbox = await screen.findByLabelText(
			/^export-individual-deletions/
		);

		expect(deletionsCheckbox).not.toBeChecked();

		await userEvent.click(deletionsCheckbox);

		expect(deletionsCheckbox).toBeChecked();

		await userEvent.click(deletionsCheckbox);

		expect(deletionsCheckbox).not.toBeChecked();
	});

	describe('page selection', () => {
		const previewWithPagePicker: ExportPreview = {
			additionCount: 1,
			deletionCount: 0,
			previewPortletDataHandlerSections: [
				{
					label: 'Site Builder',
					name: 'category.site_administration.build',
					previewPortletDataHandlers: [
						{
							label: 'Pages',
							name: LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY,
						},
					],
				},
			],
		};

		const privatePageTreeModalConfiguration = {
			...DEFAULT_PROPS.pageTreeModalConfiguration,
			privateLayoutsEnabled: true,
		};

		const getExportRequest = () => {
			const exportProcessCall = fetch.mock.calls.find(
				([url, init]) =>
					String(url).includes('export-processes') &&
					init?.method === 'POST'
			);

			return JSON.parse(String(exportProcessCall?.[1]?.body));
		};

		const typeFileName = () =>
			userEvent.type(
				screen.getByRole('textbox', {name: /^name/i}),
				'test-file'
			);

		const expandSection = () =>
			userEvent.click(screen.getByRole('button', {name: 'expand-x'}));

		const submitExport = async () => {
			const exportButton = screen.getByRole('button', {
				name: /^export$/i,
			});

			await waitFor(() => expect(exportButton).toBeEnabled());

			await userEvent.click(exportButton);
		};

		beforeEach(() => {
			jest.clearAllMocks();

			fetch.resetMocks();
			fetch.mockResponse(async (request) => {
				if (request.url.includes('get_layouts_tree')) {
					return JSON.stringify({
						hasMoreElements: false,
						items: mockPageTreeItems,
					});
				}

				if (request.url.includes('session_tree_js_click')) {
					return (await request.text()).includes('cmd=layoutCheck')
						? JSON.stringify([1, 2])
						: '[]';
				}

				return JSON.stringify({exportImportConfigurationId: 1});
			});
		});

		it('exports all public pages selected from the section checkbox', async () => {
			renderComponent({exportPreview: previewWithPagePicker});

			await typeFileName();

			await userEvent.click(
				screen.getByRole('checkbox', {name: 'Site Builder'})
			);

			await submitExport();

			await waitFor(() =>
				expect(getExportRequest().requestPortletDataHandlers).toEqual([
					{
						name: LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY,
						requestPortletDataHandlerControls: [
							{name: 'privateLayout', values: ['false']},
						],
					},
				])
			);

			await waitFor(() =>
				expect(Liferay.Util.navigate).toHaveBeenCalledWith(
					'/some/back/url'
				)
			);
		});

		it('hides the visibility radios and exports a public page subset through the modal', async () => {
			renderComponent({exportPreview: previewWithPagePicker});

			await typeFileName();
			await expandSection();

			expect(screen.queryAllByRole('radio')).toHaveLength(0);

			await userEvent.click(
				screen.getByRole('button', {name: 'select-layouts'})
			);

			await userEvent.click(await screen.findByLabelText('page-1'));
			await userEvent.click(screen.getByRole('button', {name: 'select'}));

			await submitExport();

			await waitFor(() =>
				expect(getExportRequest().requestPortletDataHandlers).toEqual([
					{
						name: LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY,
						requestPortletDataHandlerControls: [
							{name: 'privateLayout', values: ['false']},
							{name: 'layoutIds', values: ['1']},
						],
					},
				])
			);
		});

		it('shows the visibility radios and exports all private pages', async () => {
			renderComponent({
				exportPreview: previewWithPagePicker,
				pageTreeModalConfiguration: privatePageTreeModalConfiguration,
			});

			await typeFileName();
			await expandSection();

			const radios = screen.getAllByRole('radio');

			expect(radios).toHaveLength(2);
			expect(radios[0]).toBeChecked();

			await userEvent.click(radios[1]);

			expect(screen.getAllByRole('radio')[1]).toBeChecked();
			expect(screen.getByRole('checkbox', {name: 'Pages'})).toBeChecked();

			await submitExport();

			await waitFor(() =>
				expect(getExportRequest().requestPortletDataHandlers).toEqual([
					{
						name: LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY,
						requestPortletDataHandlerControls: [
							{name: 'privateLayout', values: ['true']},
						],
					},
				])
			);
		});

		it('resets the selection to public when the public radio is selected', async () => {
			renderComponent({
				exportPreview: previewWithPagePicker,
				pageTreeModalConfiguration: privatePageTreeModalConfiguration,
			});

			await expandSection();

			await userEvent.click(screen.getAllByRole('radio')[1]);

			expect(screen.getAllByRole('radio')[1]).toBeChecked();

			await userEvent.click(screen.getAllByRole('radio')[0]);

			expect(screen.getAllByRole('radio')[0]).toBeChecked();
			expect(screen.getAllByRole('radio')[1]).not.toBeChecked();
		});

		it('clears the page selection when the main checkbox is toggled off', async () => {
			renderComponent({
				exportPreview: previewWithPagePicker,
				pageTreeModalConfiguration: privatePageTreeModalConfiguration,
			});

			await expandSection();

			await userEvent.click(screen.getAllByRole('radio')[1]);

			expect(screen.getByRole('checkbox', {name: 'Pages'})).toBeChecked();

			await userEvent.click(
				screen.getByRole('checkbox', {name: 'Pages'})
			);

			expect(
				screen.getByRole('checkbox', {name: 'Pages'})
			).not.toBeChecked();
		});

		it('exports a private page subset selected through the modal', async () => {
			renderComponent({
				exportPreview: previewWithPagePicker,
				pageTreeModalConfiguration: privatePageTreeModalConfiguration,
			});

			await typeFileName();
			await expandSection();

			await userEvent.click(screen.getAllByRole('radio')[1]);
			await userEvent.click(
				screen.getByRole('button', {name: 'select-layouts'})
			);

			expect(await screen.findByLabelText('page-1')).toBeChecked();
			expect(screen.getByLabelText('page-2')).toBeChecked();

			await userEvent.click(screen.getByLabelText('page-2'));
			await userEvent.click(screen.getByRole('button', {name: 'select'}));

			await submitExport();

			await waitFor(() =>
				expect(getExportRequest().requestPortletDataHandlers).toEqual([
					{
						name: LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY,
						requestPortletDataHandlerControls: [
							{name: 'privateLayout', values: ['true']},
							{name: 'layoutIds', values: ['1']},
						],
					},
				])
			);
		});
	});
});
