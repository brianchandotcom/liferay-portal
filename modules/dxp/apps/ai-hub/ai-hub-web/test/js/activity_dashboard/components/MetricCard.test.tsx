/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import MetricCard from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/components/MetricCard';

describe('MetricCard', () => {
	it('renders the children area when children are provided', () => {
		render(
			<MetricCard title="Prepaid Balance" value="2,500 LRT">
				<span>Expires on May 21, 2027</span>
			</MetricCard>
		);

		expect(screen.getByText('Expires on May 21, 2027')).toBeInTheDocument();
	});

	it('renders the icon when provided', () => {
		render(
			<MetricCard icon={<span>sticker</span>} title="Agents" value="18" />
		);

		expect(screen.getByText('sticker')).toBeInTheDocument();
	});

	it('renders the title as a level two heading', () => {
		render(<MetricCard title="Agents" value="18" />);

		expect(
			screen.getByRole('heading', {level: 2, name: 'Agents'})
		).toBeInTheDocument();
	});

	it('renders the value', () => {
		render(<MetricCard title="Agents" value="18" />);

		expect(screen.getByText('18')).toBeInTheDocument();
	});
});
