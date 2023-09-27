/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
