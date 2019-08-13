/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import FilterDisplay from '../../../../src/main/resources/META-INF/resources/js/components/list/FilterDisplay.es';
import {fireEvent, render} from '@testing-library/react';

describe('FilterDisplay', () => {
	it('has the correct description', () => {
		const {getByText} = render(
			<FilterDisplay
				onClear={jest.fn()}
				searchBarTerm={'example'}
				totalResultsCount={250}
			/>
		);

		expect(getByText('250 Results for example')).toBeInTheDocument();
	});

	it('calls the onClear function when clicking on Clear', () => {
		const onClear = jest.fn();

		const {getByText} = render(
			<FilterDisplay
				onClear={onClear}
				searchBarTerm={'example'}
				totalResultsCount={250}
			/>
		);

		fireEvent.click(getByText('Clear'));

		expect(onClear).toHaveBeenCalledTimes(1);
	});
});
