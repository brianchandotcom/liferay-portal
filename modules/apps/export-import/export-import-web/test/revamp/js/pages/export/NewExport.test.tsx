/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {NewExport} from '../../../../../src/main/resources/META-INF/resources/revamp/js/pages/export/NewExport';

describe('NewExport', () => {
	it('renders the SetupStep', async () => {
		const {container} = render(<NewExport backURL="/some/back/url" />);

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
});
