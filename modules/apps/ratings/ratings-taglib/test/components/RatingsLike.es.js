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

const formDataToObj = formData =>
	Array.from(formData.entries()).reduce((accumulator, [key, value]) => {
		accumulator[key] = value;

		return accumulator;
	}, {});

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

			describe('and the user unlike', () => {
				it('decreases the like counter', async () => {
					const LikeButton = getByRole('button');

					await act(async () => {
						fireEvent.click(LikeButton);
					});

					expect(LikeButton.value).toBe('26');
				});
			});
		});

		describe('and the user clicks unlike', () => {
			let getByRole;

			beforeEach(async () => {
				getByRole = renderComponent({
					...defaultProps,
					initialLiked: true,
					positiveVotes: 26,
				}).getByRole;

				const LikeButton = getByRole('button');

				await act(async () => {
					fireEvent.click(LikeButton);
				});
			});

			it('decreases the likes counter', async () => {
				const LikeButton = getByRole('button');

				expect(LikeButton.value).toBe('25');
			});

			describe('and the user like', () => {
				it('increases the like counter', async () => {
					const LikeButton = getByRole('button');

					await act(async () => {
						fireEvent.click(LikeButton);
					});

					expect(LikeButton.value).toBe('26');
				});
			});
		});
	});

	describe('when there is a valid server response', () => {
		beforeEach(() => {
			fetch.mockResponseOnce(JSON.stringify({totalScore: 27}));
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('and the user like', () => {
			let getByRole;

			beforeEach(async () => {
				getByRole = renderComponent({
					...defaultProps,
					positiveVotes: 26,
				}).getByRole;

				const LikeButton = getByRole('button');

				await act(async () => {
					fireEvent.click(LikeButton);
				});
			});

			it('sends a POST request to the server', async () => {
				const [url, {body}] = fetch.mock.calls[0];
				const objFormData = formDataToObj(body);

				expect(url).toBe(defaultProps.url);
				expect(objFormData.className).toBe(defaultProps.className);
				expect(objFormData.score).toBe('1');
			});

			it('updates the counters with the ones from the server', async () => {
				const LikeButton = getByRole('button');

				expect(LikeButton.value).toBe('27');
			});
		});
	});
});
