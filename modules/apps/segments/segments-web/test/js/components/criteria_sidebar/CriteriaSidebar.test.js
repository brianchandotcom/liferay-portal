/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import CriteriaSidebar from '../../../../src/main/resources/META-INF/resources/js/components/criteria_sidebar/CriteriaSidebar';

describe('CriteriaSidebar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(<CriteriaSidebar propertyGroups={[]} />);

		expect(asFragment()).toMatchSnapshot();
	});
});
