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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import RatingsLike from '../../src/main/resources/META-INF/resources/js/components/RatingsLike.es';

themeDisplay.getPlid = themeDisplay.getPlid || jest.fn(() => 'plid');

const defaultProps = {
	className: 'com.liferay.model.RateableEntry',
	classPK: 'classPK',
	enabled: true,
	signedIn: true,
	url: 'http://url',
};

const renderComponent = (props = defaultProps) =>
	render(<RatingsLike {...props} />);

describe('RatingsLike', () => {
	afterEach(cleanup);

	describe('when rendered with the default props', () => {
		it('is enabled and has default votes', () => {
			const {getByRole} = renderComponent();

			const LikeButton = getByRole('button');

			expect(LikeButton.textContent).toBe('0');
			expect(LikeButton.disabled).toBe(false);
		});
	});

	describe('when rendered with enabled = false', () => {
		it('is disabled', () => {
			const {getByRole} = renderComponent({
				...defaultProps,
				enabled: false,
			});

			const LikeButton = getByRole('button');

			expect(LikeButton.disabled).toBe(true);
		});
	});

	describe('when there is no server response', () => {
		beforeEach(() => {
			fetch.mockResponse(JSON.stringify({}));
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('and the user clicks like', () => {
			let getByRole;

			beforeEach(async () => {
				getByRole = renderComponent({
					...defaultProps,
					initialLiked: false,
					positiveVotes: 26,
				}).getByRole;

				const LikeButton = getByRole('button');

				await act(async () => {
					fireEvent.click(LikeButton);
				});
			});

			it('increases the likes counter', async () => {
				const LikeButton = getByRole('button');

				expect(LikeButton.value).toBe('27');
			});
		});
	});
});
