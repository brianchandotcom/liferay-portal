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
import {render} from '@testing-library/react';
import React from 'react';

import DefaultPageHeader from '../../../src/main/resources/META-INF/resources/admin/js/components/DefaultPageHeader';

describe('DefaultPageHeader', () => {
	it('shows the back button, page description and page title', () => {
		const {getByText} = render(
			<DefaultPageHeader
				description="Page description"
				hideBackButton
				title="Page title"
			/>
		);
		expect(getByText('back')).toBeInTheDocument();
		expect(getByText('Page description')).toBeInTheDocument();
		expect(getByText('Page title')).toBeInTheDocument();
	});
});
