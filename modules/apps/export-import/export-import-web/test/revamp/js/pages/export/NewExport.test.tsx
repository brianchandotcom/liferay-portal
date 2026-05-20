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
import {mockExportPreview} from '../../mocks/mockExportPreview';

const renderComponent = () => {
	return render(
		<NewExport
			backURL="/some/back/url"
			exportPreviewAPIURL="/o/export-import/v1.0/export-preview"
			exportProcessAPIURL="/o/export-import/v1.0/export-processes"
			pageTreeModalConfiguration={{
				liveGroupId: 20121,
				pageSize: 20,
				privateLayoutsEnabled: false,
			}}
		/>
	);
};

describe('NewExport', () => {
	beforeEach(() => {
		fetch.resetMocks();
		fetch.mockResponse(JSON.stringify(mockExportPreview));
	});

	it('renders the export form', async () => {
		const {container} = renderComponent();

		const fileNameInput = await screen.findByRole('textbox', {
			name: /file-name/,
		});
		expect(fileNameInput).toBeInTheDocument();

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

	it('shows a required error on filename when blurred empty', async () => {
		renderComponent();

		const fileNameInput = await screen.findByRole('textbox', {
			name: /file-name/,
		});

		await userEvent.click(fileNameInput);
		fileNameInput.blur();

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

		const fileNameInput = await screen.findByRole('textbox', {
			name: /file-name/,
		});
		await userEvent.type(fileNameInput, 'test-file');

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

		const fileNameInput = await screen.findByRole('textbox', {
			name: /file-name/,
		});
		await userEvent.type(fileNameInput, 'test-file');

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

		expect(screen.getByRole('textbox', {name: /file-name/})).toHaveValue(
			'test-file'
		);
	});

	it('enables the export button once filename and contentSelection are set', async () => {
		renderComponent();

		const fileNameInput = await screen.findByRole('textbox', {
			name: /file-name/,
		});

		await userEvent.type(fileNameInput, 'test-file');

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
});
