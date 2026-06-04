/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import FDSSourceDataRenderer from '../../components/FDSPropsTransformer/FDSSourceDataRenderer';

describe('FDSSourceDataRenderer', () => {
	it('labels custom fields with the "Custom" source', () => {
		render(<FDSSourceDataRenderer itemData={{system: false}} />);

		expect(screen.getByText('custom')).toBeInTheDocument();

		expect(screen.queryByText('system')).not.toBeInTheDocument();
	});

	it('labels system fields with the "System" source', () => {
		render(<FDSSourceDataRenderer itemData={{system: true}} />);

		expect(screen.getByText('system')).toBeInTheDocument();

		expect(screen.queryByText('custom')).not.toBeInTheDocument();
	});
});
