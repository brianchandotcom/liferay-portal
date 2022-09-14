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

import TemplatesPanel from '../../../../src/main/resources/META-INF/resources/admin/js/components/TemplatesPanel';

const EMPTY_STATE_TEXT = 'there-are-no-article-templates';

describe('Templates Panel', () => {
	beforeEach(() => {
		cleanup();
	});

	it('renders two navigation items', () => {
		const items = [
			{
				href: 'template1_url',
				name: 'Template 1',
			},
			{
				href: 'template2_url',
				name: 'Template 2',
			},
		];

		const {getAllByRole} = render(<TemplatesPanel items={items} />);

		expect(getAllByRole('link').length).toBe(2);
	});

	it('renders empty message if there are no items', () => {
		const {getAllByRole, getByText} = render(<TemplatesPanel />);

		expect(getAllByRole('img').length).toBe(1);
		expect(getByText(EMPTY_STATE_TEXT)).toBeInTheDocument();
	});
});
