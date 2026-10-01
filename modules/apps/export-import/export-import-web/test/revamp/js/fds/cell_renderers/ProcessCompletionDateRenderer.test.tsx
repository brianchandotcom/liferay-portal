/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DateTimeRenderer} from '@liferay/frontend-data-set-web';
import {act, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import ProcessCompletionDateRenderer from '../../../../../src/main/resources/META-INF/resources/revamp/js/fds/cell_renderers/ProcessCompletionDateRenderer';
import {publishLiveProcess} from '../../../../../src/main/resources/META-INF/resources/revamp/js/fds/liveProcesses';

jest.mock('@liferay/frontend-data-set-web', () => ({
	DateTimeRenderer: jest.fn(() => 'date-time'),
}));

const OPTIONS = {format: {timeZone: 'UTC'}};

function CompletionDateCell(
	props: Parameters<typeof ProcessCompletionDateRenderer>[0]
) {
	return <>{ProcessCompletionDateRenderer(props)}</>;
}

describe('ProcessCompletionDateRenderer', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('reports a process still running instead of rendering a date', () => {
		render(<CompletionDateCell itemData={{id: 1}} options={OPTIONS} />);

		expect(screen.getByText('processing...')).toBeInTheDocument();
		expect(DateTimeRenderer).not.toHaveBeenCalled();
	});

	it('hands the stored completion date and the column options to the date time renderer', () => {
		render(
			<CompletionDateCell
				itemData={{id: 2}}
				options={OPTIONS}
				value="2026-10-01T08:00:00Z"
			/>
		);

		expect(screen.getByText('date-time')).toBeInTheDocument();
		expect(DateTimeRenderer).toHaveBeenCalledWith({
			options: OPTIONS,
			value: '2026-10-01T08:00:00Z',
		});
	});

	it('switches to the completion date once the live process finishes', () => {
		render(<CompletionDateCell itemData={{id: 3}} options={OPTIONS} />);

		expect(screen.getByText('processing...')).toBeInTheDocument();

		act(() => {
			publishLiveProcess({
				dateCompleted: '2026-10-01T09:30:00Z',
				id: 3,
				status: {code: 0, label: 'successful'},
			});
		});

		expect(screen.getByText('date-time')).toBeInTheDocument();
		expect(DateTimeRenderer).toHaveBeenLastCalledWith({
			options: OPTIONS,
			value: '2026-10-01T09:30:00Z',
		});
	});
});
