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
import {formDataToObj} from '../utils';

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
	});

	describe('when rendered with enabled = false', () => {
		it('is disabled', () => {
			const LikeButton = renderComponent({
				...defaultProps,
				enabled: false,
			}).getByRole('button');

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
			let LikeButton;

			beforeEach(() => {
				LikeButton = renderComponent({
					...defaultProps,
					positiveVotes: 26,
				}).getByRole('button');

				act(() => {
					fireEvent.click(LikeButton);
				});
			});

			it('increases the likes counter', () => {
				expect(LikeButton.value).toBe('27');
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
					...defaultProps,
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
			fetch.mockResponseOnce(JSON.stringify({totalScore: 27}));
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('and the user clicks like', () => {
			let LikeButton;

			beforeEach(() => {
				LikeButton = renderComponent({
					...defaultProps,
					positiveVotes: 26,
				}).getByRole('button');

				act(() => {
					fireEvent.click(LikeButton);
				});
			});

			it('sends a POST request to the server', () => {
				const [url, {body}] = fetch.mock.calls[0];
				const objFormData = formDataToObj(body);

				expect(url).toBe(defaultProps.url);
				expect(objFormData.className).toBe(defaultProps.className);
				expect(objFormData.score).toBe('1');
			});

			it('updates the counters with the ones from the server', () => {
				expect(LikeButton.value).toBe('27');
			});
		});
	});
});
