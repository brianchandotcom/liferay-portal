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

import RatingsThumbs from '../../src/main/resources/META-INF/resources/js/components/RatingsThumbs.es';

const formDataToObj = formData =>
	Array.from(formData.entries()).reduce((accumulator, [key, value]) => {
		accumulator[key] = value;

		return accumulator;
	}, {});

themeDisplay.getPlid = themeDisplay.getPlid || jest.fn(() => 'plid');

const defaultProps = {
	className: 'com.liferay.model.RateableEntry',
	classPK: 'classPK',
	enabled: true,
	signedIn: true,
	url: 'http://url',
};

const renderComponent = (props = defaultProps) =>
	render(<RatingsThumbs {...props} />);

describe('RatingsThumbs', () => {
	afterEach(cleanup);

	describe('render', () => {
		it('disabled', () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				enabled: false,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			expect(thumbUpButton.disabled).toBe(true);
			expect(thumbDownButton.disabled).toBe(true);
		});

		it('enabled and default votes', () => {
			const {getAllByRole} = renderComponent();

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			expect(thumbUpButton.value).toBe('0');
			expect(thumbDownButton.value).toBe('0');
			expect(thumbUpButton.disabled).toBe(false);
			expect(thumbDownButton.disabled).toBe(false);
		});
	});

	describe('render without server response', () => {
		beforeEach(() => {
			// Return a empty obj response and component ignore the server update
			fetch.mockResponseOnce(JSON.stringify({}));
		});

		it('vote up then increments UI', async () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				initialNegativeVotes: 10,
				initialPositiveVotes: 26,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbUpButton);
			});

			expect(thumbUpButton.value).toBe('27');
			expect(thumbDownButton.value).toBe('10');
		});

		it('vote down then increments UI', async () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				initialNegativeVotes: 10,
				initialPositiveVotes: 26,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbDownButton);
			});

			expect(thumbUpButton.value).toBe('26');
			expect(thumbDownButton.value).toBe('11');
		});

		it('vote up in voted up then unvote', async () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				initialNegativeVotes: 10,
				initialPositiveVotes: 26,
				thumbUp: true,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbUpButton);
			});

			expect(thumbUpButton.value).toBe('25');
			expect(thumbDownButton.value).toBe('10');
		});

		it('vote down in voted down then unvote', async () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				initialNegativeVotes: 10,
				initialPositiveVotes: 26,
				thumbDown: true,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbDownButton);
			});

			expect(thumbUpButton.value).toBe('26');
			expect(thumbDownButton.value).toBe('9');
		});

		it('vote up in voted down and increase and unvote', async () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				initialNegativeVotes: 10,
				initialPositiveVotes: 26,
				thumbDown: true,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbUpButton);
			});

			expect(thumbUpButton.value).toBe('27');
			expect(thumbDownButton.value).toBe('9');
		});

		it('vote down in voted up and increase and unvote', async () => {
			const {getAllByRole} = renderComponent({
				...defaultProps,
				initialNegativeVotes: 10,
				initialPositiveVotes: 26,
				thumbUp: true,
			});

			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbDownButton);
			});

			expect(thumbUpButton.value).toBe('25');
			expect(thumbDownButton.value).toBe('11');
		});
	});

	describe('render with server response', () => {
		beforeEach(() => {
			fetch.mockResponseOnce(
				JSON.stringify({totalEntries: 59 + 27, totalScore: 59})
			);
		});

		it('vote up and POST to server url', async () => {
			const {getAllByRole} = renderComponent();
			const [thumbUpButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbUpButton);
			});

			const [url, {body}] = fetch.mock.calls[0];
			const objFormData = formDataToObj(body);

			expect(url).toBe(defaultProps.url);
			expect(objFormData.className).toBe(defaultProps.className);
			expect(objFormData.score).toBe('1');
		});

		it('vote up and update the counters with server response', async () => {
			fetch.mockResponseOnce(
				JSON.stringify({totalEntries: 59 + 27, totalScore: 59})
			);

			const {getAllByRole} = renderComponent();
			const [thumbUpButton, thumbDownButton] = getAllByRole('button');

			await act(async () => {
				fireEvent.click(thumbUpButton);
			});

			expect(thumbUpButton.value).toBe('59');
			expect(thumbDownButton.value).toBe('27');
		});
	});
});
