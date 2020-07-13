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

import TYPES from '../../src/main/resources/META-INF/resources/js/RATINGS_TYPES';
import Ratings from '../../src/main/resources/META-INF/resources/js/Ratings';
import {formDataToObj} from '../utils';

const baseProps = {
	className: 'com.liferay.model.RateableEntry',
	classPK: 'classPK',
	enabled: true,
	signedIn: true,
	type: TYPES.LIKE,
	url: 'http://url',
};

const renderComponent = (props) =>
	render(<Ratings {...baseProps} {...props} />);

describe('RatingsLike', () => {
	afterEach(cleanup);

	describe('when rendered with the default props', () => {
		let LikeButton;

		beforeEach(() => {
			LikeButton = renderComponent().getByRole('button');
		});

		it('is enabled', () => {
			expect(LikeButton.disabled).toBe(false);
		});

		it('has default votes', () => {
			expect(LikeButton.value).toBe('0');
		});

		it('has like title', () => {
			expect(LikeButton.title).toBe('like-this');
		});
	});

	describe('when rendered with enabled = false', () => {
		let LikeButton;

		beforeEach(() => {
			LikeButton = renderComponent({
				enabled: false,
			}).getByRole('button');
		});

		it('is disabled', () => {
			expect(LikeButton.disabled).toBe(true);
		});

		it('has disabled title', () => {
			expect(LikeButton.title).toBe('ratings-are-disabled-in-staging');
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
			let LikeButton;

			beforeEach(() => {
				LikeButton = renderComponent({
					positiveVotes: 26,
				}).getByRole('button');

				act(() => {
					fireEvent.click(LikeButton);
				});
			});

			it('increases the likes counter', () => {
				expect(LikeButton.value).toBe('27');
			});

			it('has unlike title', () => {
				expect(LikeButton.title).toBe('unlike-this');
			});

			describe('and the user clicks unlike', () => {
				beforeEach(() => {
					act(() => {
						fireEvent.click(LikeButton);
					});
				});

				it('decreases the like counter', () => {
					expect(LikeButton.value).toBe('26');
				});
			});
		});

		describe('and the user clicks unlike', () => {
			let LikeButton;

			beforeEach(() => {
				LikeButton = renderComponent({
					initialLiked: true,
					positiveVotes: 26,
				}).getByRole('button');

				act(() => {
					fireEvent.click(LikeButton);
				});
			});

			it('decreases the likes counter', () => {
				expect(LikeButton.value).toBe('25');
			});

			describe('and the user clicks like', () => {
				beforeEach(() => {
					act(() => {
						fireEvent.click(LikeButton);
					});
				});

				it('increases the like counter', () => {
					expect(LikeButton.value).toBe('26');
				});
			});
		});
	});

	describe('when there is a valid server response', () => {
		beforeEach(() => {
			fetch.mockResponseOnce(JSON.stringify({totalScore: 27.1}));
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('and the user clicks like', () => {
			let LikeButton;

			beforeEach(async () => {
				LikeButton = renderComponent({
					positiveVotes: 3,
				}).getByRole('button');

				await act(async () => {
					fireEvent.click(LikeButton);
				});
			});

			it('sends a POST request to the server', () => {
				const [url, {body}] = fetch.mock.calls[0];
				const objFormData = formDataToObj(body);

				expect(url).toBe(baseProps.url);
				expect(objFormData.className).toBe(baseProps.className);
				expect(objFormData.score).toBe('1');
			});

			it('updates the rounded counters with the ones from the server', () => {
				expect(LikeButton.value).toBe('27');
			});
		});
	});
});
