/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, getByText, render} from '@testing-library/react';
import ErrorList from 'dynamic-data-mapping-form-web/admin/js/pages/ErrorList.tsx';
import React from 'react';

describe('Error List', () => {
	afterEach(cleanup);

	it('shows an error message when trying to preview a form without fields', () => {
		const {container} = render(
			<ErrorList
				errorMessages={['Please add at least one field']}
				onRemove={null}
				sidebarOpen={true}
			/>
		);

		const errorList = container.querySelector(
			'.ddm-form-web__exception-container'
		);

		expect(errorList).toBeInTheDocument();
		expect(
			getByText(container, 'Please add at least one field')
		).toBeInTheDocument();
	});

	it('does not show an error message when trying to preview a form with fields', () => {
		const {container} = render(
			<ErrorList errorMessages={[]} onRemove={null} sidebarOpen={true} />
		);

		const errorList = container.querySelector(
			'.ddm-form-web__exception-container'
		);

		expect(errorList).toBeNull();
	});
});
