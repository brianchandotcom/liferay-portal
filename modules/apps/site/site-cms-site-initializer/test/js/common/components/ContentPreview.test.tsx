/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render} from '@testing-library/react';
import React from 'react';

import ContentPreview from '../../../../src/main/resources/META-INF/resources/js/common/components/ContentPreview';

describe('ContentPreview', () => {
	it('renders an iframe with the correct src', async () => {
		const {container} = render(
			<ContentPreview url="http://localhost/test" />
		);

		const iframe = container.querySelector('iframe');
		expect(iframe).toBeInTheDocument();
		expect(iframe).toHaveAttribute('src', 'http://localhost/test');
	});

	it('checks the accessibility', async () => {
		const {container} = render(
			<ContentPreview url="http://localhost/test" />
		);

		await checkAccessibility({bestPractices: true, context: container});
	});
});
