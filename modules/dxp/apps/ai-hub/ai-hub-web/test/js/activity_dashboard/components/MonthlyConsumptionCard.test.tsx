/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import MonthlyConsumptionCard from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/components/MonthlyConsumptionCard';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

describe('MonthlyConsumptionCard', () => {
	it('applies the exceeded threshold treatment at 100% consumption', () => {
		render(<MonthlyConsumptionCard allowance={10000} consumed={10000} />);

		const container =
			screen.getByRole('progressbar').parentElement?.parentElement;

		expect(container).toHaveClass(
			'ai-hub-monthly-consumption-card-progress-exceeded'
		);
	});

	it('applies the exceeded threshold treatment when the allowance is zero', () => {
		render(<MonthlyConsumptionCard allowance={0} consumed={500} />);

		const container =
			screen.getByRole('progressbar').parentElement?.parentElement;

		expect(container).toHaveClass(
			'ai-hub-monthly-consumption-card-progress-exceeded'
		);
	});

	it('applies the normal threshold treatment below 75% consumption', () => {
		render(<MonthlyConsumptionCard allowance={10000} consumed={5000} />);

		const container =
			screen.getByRole('progressbar').parentElement?.parentElement;

		expect(container).not.toHaveClass(
			'ai-hub-monthly-consumption-card-progress-exceeded'
		);
		expect(container).not.toHaveClass('progress-warning');
	});

	it('applies the warning threshold treatment between 75% and 99% consumption', () => {
		render(<MonthlyConsumptionCard allowance={10000} consumed={9000} />);

		const container =
			screen.getByRole('progressbar').parentElement?.parentElement;

		expect(container).toHaveClass('progress-warning');
	});

	it('caps the progress bar at 100% when consumption exceeds the allowance', () => {
		render(<MonthlyConsumptionCard allowance={10000} consumed={12000} />);

		const progressBar = screen.getByRole('progressbar');

		expect(progressBar).toHaveAttribute('aria-valuenow', '100');
	});

	it('renders a progress bar with the consumed percentage', () => {
		render(<MonthlyConsumptionCard allowance={10000} consumed={5000} />);

		const progressBar = screen.getByRole('progressbar');

		expect(progressBar).toHaveAttribute('aria-valuenow', '50');
	});

	it('renders the consumed value and the allowance', () => {
		render(<MonthlyConsumptionCard allowance={10000} consumed={4972} />);

		expect(
			screen.getByRole('heading', {
				level: 2,
				name: 'monthly-lrt-consumed',
			})
		).toBeInTheDocument();
		expect(screen.getByText(/4,972 LRT/)).toBeInTheDocument();
		expect(screen.getByText(/of 10,000/)).toBeInTheDocument();
	});
});
