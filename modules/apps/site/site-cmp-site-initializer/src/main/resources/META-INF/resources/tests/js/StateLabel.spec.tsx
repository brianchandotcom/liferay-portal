/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import StateLabel from '../../js/components/StateLabel';

describe('StateLabel', () => {
	it('renders the name text', () => {
		const name = 'In Progress';

		const {getByText} = render(<StateLabel key="inProgress" name={name} />);

		expect(getByText(name)).toBeInTheDocument();
	});
});
