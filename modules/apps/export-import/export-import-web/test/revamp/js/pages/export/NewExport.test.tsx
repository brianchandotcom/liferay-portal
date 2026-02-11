/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {NewExport} from '../../../../../src/main/resources/META-INF/resources/revamp/js/pages/export/NewExport';

const renderComponent = () => {
	return render(<NewExport backURL="/some/back/url" />);
};

describe('NewExport', () => {
	it('renders the SetupStep', async () => {
		const {container} = renderComponent();

		const fileNameInput = screen.getByRole('textbox', {
			name: /file-name/,
		});
		expect(fileNameInput).toBeInTheDocument();

		const designCheckbox = screen.getByRole('checkbox', {
			name: /Design/,
		});
		expect(designCheckbox).toBeInTheDocument();

		const continueButton = screen.getByRole('button', {name: /continue/i});
		expect(continueButton).toBeDisabled();

		await checkAccessibility({context: container});
	});

	it('enables the continue button when the SetupStep is valid', async () => {
		renderComponent();

		const continueButton = screen.getByRole('button', {name: /continue/i});
		expect(continueButton).toBeDisabled();

		const fileNameInput = await screen.findByRole('textbox', {
			name: /file-name/,
		});

		await userEvent.click(fileNameInput);
		fileNameInput.blur();

		await screen.findByText('this-field-is-required');

		await userEvent.type(fileNameInput, 'test-file');

		await waitFor(() => {
			expect(
				screen.queryByText('this-field-is-required')
			).not.toBeInTheDocument();
		});

		const designCheckbox = await screen.findByRole('checkbox', {
			name: /Design/,
		});

		await userEvent.click(designCheckbox);

		await waitFor(() => {
			expect(continueButton).toBeEnabled();
		});
	});
});
