/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import InfoSummary from '../../js/components/InfoSummary';

describe('InfoSummary', () => {
	it('renders with default props', () => {
		const items = [
			{label: 'Label 1', value: 'Value 1'},
			{label: 'Label 2', value: <span>Value 2</span>},
		];

		const {getByText} = render(<InfoSummary items={items} />);

		expect(getByText('Label 1')).toBeInTheDocument();
		expect(getByText('Value 1')).toBeInTheDocument();
		expect(getByText('Label 2')).toBeInTheDocument();
		expect(getByText('Value 2')).toBeInTheDocument();
	});

	it('renders with custom title', () => {
		const items = [{label: 'Label', value: 'Value'}];
		const title = 'Custom Title';

		const {getByText} = render(<InfoSummary items={items} title={title} />);

		expect(getByText(title)).toBeInTheDocument();
		expect(getByText('Label')).toBeInTheDocument();
		expect(getByText('Value')).toBeInTheDocument();
	});
});
