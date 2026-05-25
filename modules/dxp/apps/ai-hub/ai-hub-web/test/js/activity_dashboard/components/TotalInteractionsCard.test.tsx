/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import TotalInteractionsCard from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/components/TotalInteractionsCard';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

describe('TotalInteractionsCard', () => {
	it('renders the total interactions heading and the value with a thousands separator', () => {
		render(<TotalInteractionsCard value={1245} />);

		expect(
			screen.getByRole('heading', {level: 2, name: 'total-interactions'})
		).toBeInTheDocument();
		expect(screen.getByText('1,245')).toBeInTheDocument();
	});
});
