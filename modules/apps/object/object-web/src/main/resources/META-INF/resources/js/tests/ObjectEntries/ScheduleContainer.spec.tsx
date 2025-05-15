/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ScheduleContainer from '../../../object_entries/object_entry/ScheduleContainer';

function renderScheduleContainer(
	scheduledProperties = {
		reviewDate: {
			checked: false,
			value: '',
		},
	},
	portletNamespace = ''
) {
	return render(
		<>
			<ScheduleContainer
				portletNamespace={portletNamespace}
				scheduleProperties={scheduledProperties}
			/>
			<button data-testid="outside-click-target">
				Outside Click Target
			</button>
		</>
	);
}

describe('ScheduleContainer component', () => {
	beforeAll(() => {
		global.Liferay = {
			...global.Liferay,
			FeatureFlags: {
				...global.Liferay?.FeatureFlags,
				'LPD-17564': true,
			},
		};
	});

	it('reviewDate is disabled when checked is true', async () => {
		const {container} = renderScheduleContainer({
			reviewDate: {
				checked: true,
				value: '',
			},
		});

		const checkbox = screen.getByRole('checkbox');
		const reviewDateInput = container.querySelector(
			'input[id$="reviewDate"]'
		);

		expect(reviewDateInput).toBeDisabled();
		expect(checkbox).toBeChecked();
	});

	it('shows required error on blur when no value is provided', async () => {
		const {container} = renderScheduleContainer({
			reviewDate: {
				checked: false,
				value: '',
			},
		});

		const reviewDateInput = container.querySelector(
			'input[id$="reviewDate"]'
		) as HTMLElement;

		await userEvent.click(reviewDateInput);

		await userEvent.click(screen.getByTestId('outside-click-target'));

		await waitFor(() =>
			expect(
				screen.getByText('this-field-is-required')
			).toBeInTheDocument()
		);
	});

	it('shows required error on change when no value is provided', async () => {
		const {container} = renderScheduleContainer({
			reviewDate: {
				checked: false,
				value: '05/13/2025 02:38 PM',
			},
		});

		const reviewDateInput = container.querySelector(
			'input[id$="reviewDate"]'
		) as HTMLElement;

		await userEvent.click(reviewDateInput);

		await userEvent.type(reviewDateInput, '{selectall}{backspace}');

		await waitFor(() =>
			expect(
				screen.getByText('this-field-is-required')
			).toBeInTheDocument()
		);

		await userEvent.type(reviewDateInput, '05/13/2025 02:38 PM');

		expect(
			screen.queryByText('this-field-is-required')
		).not.toBeInTheDocument();
	});
});
