/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import DateFilter from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/date-filter';
import {
	FilterType,
	ModifiedLastType,
} from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/date-filter/types';

describe('DateFilter', () => {
	beforeAll(() => {
		global.Liferay = {
			...global?.Liferay,
			Language: {
				...global.Liferay?.Language,
				get: (key: string) => key,
			},
			ThemeDisplay: {
				...global.Liferay?.ThemeDisplay,
				getBCP47LanguageId: () => 'en-US',
				getTimeZone: () => 'UTC',
			},
		};
	});

	const renderDateFilter = (onApplyFilter = jest.fn()) => {
		const user = userEvent.setup();

		render(<DateFilter onApplyFilter={onApplyFilter} />);

		return {onApplyFilter, user};
	};

	it('renders in initial state without Show Results button', () => {
		renderDateFilter();

		expect(screen.getByLabelText('filter-content-by')).toHaveValue(
			FilterType.All
		);
		expect(screen.queryByText('show-results')).not.toBeInTheDocument();
	});

	it('shows Modified Last options and enables the apply button when selected', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			FilterType.Last
		);

		expect(screen.getByLabelText('modified-last')).toBeInTheDocument();
		expect(screen.getByText('show-results')).toBeInTheDocument();
	});

	it('shows Date Range fields when selected', async () => {
		const {user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			FilterType.Range
		);

		expect(screen.getByLabelText('from')).toBeInTheDocument();
		expect(screen.getByLabelText('to')).toBeInTheDocument();
	});

	it('calls onApplyFilter with correct values when applying a Modified Last filter', async () => {
		const {onApplyFilter, user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			FilterType.Last
		);
		await user.selectOptions(
			screen.getByLabelText('modified-last'),
			ModifiedLastType.H24
		);

		await user.click(screen.getByText('show-results'));

		expect(onApplyFilter).toHaveBeenCalledWith({
			filterType: FilterType.Last,
			modifiedLast: ModifiedLastType.H24,
		});

		expect(screen.getByText('show-results')).toBeDisabled();
	});

	it('shows an alert summary and clears filters correctly', async () => {
		const {onApplyFilter, user} = renderDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			FilterType.Last
		);
		await user.click(screen.getByText('show-results'));

		expect(screen.getByRole('alert')).toBeInTheDocument();

		await user.click(screen.getByText('clear-filters'));

		expect(onApplyFilter).toHaveBeenLastCalledWith({
			filterType: FilterType.All,
		});

		expect(screen.getByLabelText('filter-content-by')).toHaveValue(
			FilterType.All
		);
		expect(screen.queryByRole('alert')).not.toBeInTheDocument();
	});
});
