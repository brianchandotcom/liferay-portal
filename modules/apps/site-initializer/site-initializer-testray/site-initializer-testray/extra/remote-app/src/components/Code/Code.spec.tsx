/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';

import Code from './';

describe('Code', () => {
	it('renders code with children', () => {
		const {asFragment, container} = render(
			<Code>com.liferay.testray.user</Code>
		);

		expect(container.querySelector('code')).toHaveTextContent(
			'com.liferay.testray.user'
		);
		expect(asFragment()).toMatchSnapshot();
	});
});
