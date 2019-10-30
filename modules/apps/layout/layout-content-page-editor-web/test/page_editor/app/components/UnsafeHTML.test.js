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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import UnsafeHTML from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/UnsafeHTML';

describe('UnsafeHTML', () => {
	afterEach(cleanup);

	it('renders the given HTML markup', () => {
		const {getByRole} = render(
			<UnsafeHTML markup="<h1>Hello <strong>Gürjen</strong></h1>" />
		);

		expect(getByRole('heading')).toBeInTheDocument();
	});

	it('allows adding any className', () => {
		const {container} = render(
			<UnsafeHTML className="food" markup="Pi<strong>zz</strong>a" />
		);

		expect(container.querySelector('.food')).toBeInTheDocument();
	});

	it('allows using a custom HTML tag as container', () => {
		const {getByRole} = render(
			<UnsafeHTML TagName="h1" markup="The Title" />
		);

		expect(getByRole('heading')).toBeInTheDocument();
	});
});
