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

import {fireEvent, render} from '@testing-library/react';
import {vi} from 'vitest';

import Footer from './Footer';

describe('Footer', () => {
	it('renders with success', () => {
		const {asFragment, queryByText} = render(
			<Footer onClose={() => null} onSubmit={() => null} />
		);

		expect(queryByText('Cancel')).toBeTruthy();
		expect(queryByText('Save')).toBeTruthy();
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders and perform actions', () => {
		const cancelFn = vi.fn();
		const submitFn = vi.fn();

		const {queryByText} = render(
			<Footer onClose={cancelFn} onSubmit={submitFn} />
		);

		expect(cancelFn).toHaveBeenCalledTimes(0);
		expect(submitFn).toHaveBeenCalledTimes(0);

		const cancel = queryByText('Cancel');
		const submit = queryByText('Save');

		expect(cancel).toBeTruthy();

		fireEvent.click(cancel as HTMLElement);
		fireEvent.click(submit as HTMLElement);

		expect(cancelFn).toHaveBeenCalledTimes(1);
		expect(submitFn).toHaveBeenCalledTimes(1);
	});
});
