/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {Formik} from 'formik';
import React from 'react';

import {
	DateFilterValues,
	FilterType,
	ModifiedLastType,
} from '../../../../../../src/main/resources/META-INF/resources/revamp/js/components/date_filter';
import {FormikFieldDateFilter} from '../../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/formik/FormikFieldDateFilter';

const renderFormikFieldDateFilter = ({
	initialValue = {filterType: FilterType.All} as DateFilterValues,
	onApplyFilter = jest.fn(),
	onSubmit = jest.fn(),
}: {
	initialValue?: DateFilterValues;
	onApplyFilter?: (filterValues: DateFilterValues) => void;
	onSubmit?: (values: {dateFilter: DateFilterValues}) => void;
} = {}) => {
	const user = userEvent.setup();

	render(
		<Formik initialValues={{dateFilter: initialValue}} onSubmit={onSubmit}>
			<FormikFieldDateFilter
				name="dateFilter"
				onApplyFilter={onApplyFilter}
			/>
		</Formik>
	);

	return {onApplyFilter, onSubmit, user};
};

describe('FormikFieldDateFilter', () => {
	it('forwards the applied value to DateFilter so the alert is shown', async () => {
		renderFormikFieldDateFilter({
			initialValue: {
				filterType: FilterType.Last,
				modifiedLast: ModifiedLastType.H24,
			},
		});

		expect(screen.getByRole('alert')).toBeInTheDocument();
	});

	it('updates the Formik field and invokes onApplyFilter when the user applies', async () => {
		const {onApplyFilter, user} = renderFormikFieldDateFilter();

		await user.selectOptions(
			screen.getByLabelText('filter-content-by'),
			FilterType.Last
		);
		await user.selectOptions(
			screen.getByLabelText('modified-last'),
			ModifiedLastType.H48
		);
		await user.click(screen.getByText('show-results'));

		expect(onApplyFilter).toHaveBeenCalledWith({
			filterType: FilterType.Last,
			modifiedLast: ModifiedLastType.H48,
		});

		expect(screen.getByRole('alert')).toBeInTheDocument();
	});

	it('resets the Formik field to FilterType.All when clear is clicked', async () => {
		const {onApplyFilter, user} = renderFormikFieldDateFilter({
			initialValue: {
				filterType: FilterType.Last,
				modifiedLast: ModifiedLastType.H12,
			},
		});

		await user.click(screen.getByText('clear-filters'));

		expect(onApplyFilter).toHaveBeenLastCalledWith({
			filterType: FilterType.All,
		});

		expect(screen.queryByRole('alert')).not.toBeInTheDocument();
	});
});
