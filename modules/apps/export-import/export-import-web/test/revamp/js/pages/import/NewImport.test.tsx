/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {NewImport} from '../../../../../src/main/resources/META-INF/resources/revamp/js/pages/import/NewImport';

const renderComponent = () => {
	return render(
		<NewImport
			backURL="/some/back/url"
			groupId={20120}
			isCompanyGroup={false}
		/>
	);
};

describe('NewImport', () => {
	it('renders the FileSelectionStep', async () => {
		const {container} = renderComponent();

		expect(screen.getByText('file-upload')).toBeInTheDocument();
		expect(
			screen.getByText('select-and-upload-your-prepared-file')
		).toBeInTheDocument();

		expect(
			screen.getByRole('button', {name: /drag-and-drop-to-upload/i})
		).toBeInTheDocument();

		await checkAccessibility({context: container});
	});
});
