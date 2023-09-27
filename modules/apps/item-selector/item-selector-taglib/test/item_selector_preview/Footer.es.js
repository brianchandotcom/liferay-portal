/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Footer from '../../src/main/resources/META-INF/resources/item_selector_preview/js/Footer.es';

describe('Footer', () => {
	afterEach(cleanup);

	it('renders the Footer component', () => {
		const props = {
			currentIndex: 0,
			title: 'test image.jpeg',
			totalItems: 1,
		};

		const {asFragment} = render(<Footer {...props} />);

		expect(asFragment()).toMatchSnapshot();
	});
});
