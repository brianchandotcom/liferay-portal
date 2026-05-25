/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import AverageResponseTimeCard from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/components/AverageResponseTimeCard';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

describe('AverageResponseTimeCard', () => {
	it('renders the average response time heading and the value in seconds', () => {
		render(<AverageResponseTimeCard value={2600} />);

		expect(
			screen.getByRole('heading', {
				level: 2,
				name: 'average-response-time',
			})
		).toBeInTheDocument();
		expect(screen.getByText('2.6s')).toBeInTheDocument();
	});
});
