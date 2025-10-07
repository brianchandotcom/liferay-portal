/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import StatusRenderer from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/cell_renderers/StatusRenderer';

describe('StatusRenderer', () => {
	it.each([
		['approved', 'success'],
		['denied', 'danger'],
		['draft', 'secondary'],
		['expired', 'danger'],
		['in-trash', 'info'],
		['inactive', 'secondary'],
		['incomplete', 'warning'],
		['pending', 'info'],
		['scheduled', 'info'],
	])('renders with the "%s" status', (label, displayType) => {
		const value = {
			label,
			label_i18n: 'i18n-key-for-' + label,
		};

		render(<StatusRenderer value={value} />);

		const labelElement = screen.getByText(value.label_i18n);

		expect(labelElement).toBeInTheDocument();
		expect(labelElement.parentElement).toHaveClass(
			'label',
			`label-${displayType}`
		);
	});
});
