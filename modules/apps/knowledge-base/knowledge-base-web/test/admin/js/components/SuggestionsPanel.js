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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import SuggestionsPanel from '../../../../src/main/resources/META-INF/resources/admin/js/components/SuggestionsPanel';

const EMPTY_STATE_TEXT = 'there-are-no-suggestions';

describe('Suggestions Panel', () => {
	beforeEach(() => {
		cleanup();
	});

	it('renders empty message if there are no items', () => {
		const {getAllByRole, getByText} = render(<SuggestionsPanel />);

		expect(getAllByRole('img').length).toBe(1);
		expect(getByText(EMPTY_STATE_TEXT)).toBeInTheDocument();
	});
});
