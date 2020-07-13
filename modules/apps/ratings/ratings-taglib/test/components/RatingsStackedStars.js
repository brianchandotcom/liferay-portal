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

import TYPES from '../../src/main/resources/META-INF/resources/js/RATINGS_TYPES';
import Ratings from '../../src/main/resources/META-INF/resources/js/Ratings';

const baseProps = {
	className: 'com.liferay.model.RateableEntry',
	classPK: 'classPK',
	enabled: true,
	numberOfStars: 5,
	randomNamespace: '_random_namespace_',
	signedIn: true,
	type: TYPES.STACKED_STARS,
	url: 'http://url',
};

const renderComponent = (props) =>
	render(<Ratings {...baseProps} {...props} />);

describe('RatingsStackedStars', () => {
	afterEach(cleanup);

	describe('when rendered with the default props', () => {
		let starsRadios;
		let result;

		beforeEach(() => {
			result = renderComponent();
			starsRadios = result.getAllByRole('radio');
		});

		it('is enabled', () => {
			expect(starsRadios[0].disabled).toBe(false);
		});

	});

	describe('when rendered with enabled = false', () => {
		let starsRadios;
		let result;

		beforeEach(() => {
			result = renderComponent({enabled: false});
			starsRadios = result.getAllByRole('radio');
		});

		it('is disabled', () => {
			expect(starsRadios[0].disabled).toBe(true);
		});

	});
});
