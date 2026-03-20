/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import RoomStatistics from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/RoomStatistics';
import {mockRoomStatisticsData} from './__mocks__';

describe('RoomStatistics', () => {
	it('matches snapshot', () => {
		const {container} = render(
			<RoomStatistics data={mockRoomStatisticsData} isLoading={false} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with provided data', () => {
		const {getByText} = render(
			<RoomStatistics data={mockRoomStatisticsData} isLoading={false} />
		);

		expect(getByText('45-min')).toBeInTheDocument();
		expect(getByText('100')).toBeInTheDocument();
		expect(getByText('20')).toBeInTheDocument();
		expect(getByText('10')).toBeInTheDocument();
		expect(getByText('5')).toBeInTheDocument();
	});

	it('renders loading state', () => {
		const {container} = render(<RoomStatistics isLoading={true} />);

		expect(
			container.querySelector('.loading-animation')
		).toBeInTheDocument();
	});
});
