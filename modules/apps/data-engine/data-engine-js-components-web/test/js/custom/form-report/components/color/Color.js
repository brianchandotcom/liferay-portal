/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Color from '../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/color/Color';

describe('Color', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Color hexColor="#2BA676" />);

		const colorText = container.querySelector('.color-text');

		expect(colorText.textContent).toBe('#2BA676');

		const colorViewer = container.querySelector('.color-viewer');

		expect(colorViewer).toBeDefined();
	});
});
