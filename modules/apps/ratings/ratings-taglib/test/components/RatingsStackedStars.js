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
import {cleanup, fireEvent, queryByRole, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

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
		let starsRadiosFieldset;

		beforeEach(() => {
			result = renderComponent();
			starsRadios = result.getAllByRole('radio');
			starsRadiosFieldset = result.getByRole('group');
		});

		it('is enabled', () => {
			expect(starsRadios[0].disabled).toBe(false);
		});

		it('has vote title', () => {
			expect(starsRadiosFieldset.title).toBe('vote');
		});

		it('without vote has not render delete button', () => {
			expect(
				queryByRole(result.baseElement, 'button')
			).not.toBeInTheDocument();
		});
	});

	describe('when rendered with enabled = false', () => {
		let starsRadios;
		let result;
		let starsRadiosFieldset;

		beforeEach(() => {
			result = renderComponent({enabled: false});
			starsRadios = result.getAllByRole('radio');
			starsRadiosFieldset = result.getByRole('group');
		});

		it('is disabled', () => {
			expect(starsRadios[0].disabled).toBe(true);
		});

		it('has disabled title', () => {
			expect(starsRadiosFieldset.title).toBe(
				'ratings-are-disabled-in-staging'
			);
		});
	});

	describe('when there is no server response', () => {
		beforeEach(() => {
			fetch.mockResponse(JSON.stringify({}));
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('and the user votes 1/5 stars', () => {
			let starsRadios;
			let starsRadiosFieldset;
			let result;

			beforeEach(() => {
				result = renderComponent({
					userScore: 0,
				});
				starsRadios = result.getAllByRole('radio');
				starsRadiosFieldset = result.getByRole('group');

				act(() => {
					fireEvent.click(starsRadios[4]);
				});
			});

			it('increases the user score', () => {
				expect(result.getByRole('group')).toHaveFormValues({
					_random_namespace_rating: '0.2',
				});
			});

			it('has voted singular title', () => {
				expect(starsRadiosFieldset.title).toBe(
					'you-have-rated-this-x-star-out-of-x'
				);
			});

			it('with vote has render delete button', () => {
				expect(
					queryByRole(result.baseElement, 'button')
				).toBeInTheDocument();
			});

			describe('later the user vote 5/5 stars', () => {
				beforeEach(() => {
					act(() => {
						fireEvent.click(starsRadios[0]);
					});
				});

				it('increases the user score', () => {
					expect(result.getByRole('group')).toHaveFormValues({
						_random_namespace_rating: '1',
					});
				});

				it('has voted plural title', () => {
					expect(starsRadiosFieldset.title).toBe(
						'you-have-rated-this-x-stars-out-of-x'
					);
				});
			});

			describe('finally the user unvote', () => {
				beforeEach(() => {
					act(() => {
						fireEvent.click(
							queryByRole(result.baseElement, 'button')
						);
					});
				});

				it('deletes the user score', () => {
					expect(result.getByRole('group')).toHaveFormValues({
						_random_namespace_rating: undefined,
					});
				});

				it('has not render delete button', () => {
					expect(
						queryByRole(result.baseElement, 'button')
					).not.toBeInTheDocument();
				});
			});
		});
	});
});
