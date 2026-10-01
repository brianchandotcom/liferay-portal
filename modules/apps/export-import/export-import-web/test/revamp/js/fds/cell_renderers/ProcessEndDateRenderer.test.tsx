/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DateTimeRenderer} from '@liferay/frontend-data-set-web';
import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import ProcessEndDateRenderer from '../../../../../src/main/resources/META-INF/resources/revamp/js/fds/cell_renderers/ProcessEndDateRenderer';

jest.mock('@liferay/frontend-data-set-web', () => ({
	DateTimeRenderer: jest.fn(() => 'date-time'),
}));

const OPTIONS = {format: {timeZone: 'UTC'}};

describe('ProcessEndDateRenderer', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('names the missing end date instead of rendering one', () => {
		render(<>{ProcessEndDateRenderer({options: OPTIONS})}</>);

		expect(screen.getByText('no-end-date')).toBeInTheDocument();
		expect(DateTimeRenderer).not.toHaveBeenCalled();
	});

	it('hands the end date and the column options to the date time renderer', () => {
		render(
			<>
				{ProcessEndDateRenderer({
					options: OPTIONS,
					value: '2026-10-01T08:00:00Z',
				})}
			</>
		);

		expect(screen.getByText('date-time')).toBeInTheDocument();
		expect(DateTimeRenderer).toHaveBeenCalledWith({
			options: OPTIONS,
			value: '2026-10-01T08:00:00Z',
		});
	});
});
