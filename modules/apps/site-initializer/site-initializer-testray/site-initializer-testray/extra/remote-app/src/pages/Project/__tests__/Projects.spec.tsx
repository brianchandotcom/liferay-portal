/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Projects from '..';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {Route} from 'react-router-dom';
import {vi} from 'vitest';

import PageWrapper from '../../../test/PageWrapper';

describe('Projects', () => {
	beforeEach(() => {
		vi.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		vi.clearAllTimers();
	});

	afterAll(() => {
		vi.useRealTimers();
	});

	it('render projects with success', async () => {
		const {asFragment} = render(<Projects />, {
			wrapper: ({children}) => <PageWrapper>{children}</PageWrapper>,
		});

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('check project permissions to open new project page', async () => {
		const {container, queryByText} = render(<Projects />, {
			wrapper: ({children}) => (
				<PageWrapper
					clearCache
					customRoutes={
						<Route
							element={<div>Project Form</div>}
							path="/project/create"
						/>
					}
				>
					{children}
				</PageWrapper>
			),
		});

		await act(async () => {
			await vi.runAllTimers();
		});

		const plusButton = container.querySelector('.lexicon-icon-plus')
			?.parentElement;

		expect(plusButton).toBeTruthy();

		fireEvent.click(plusButton as HTMLButtonElement);

		expect(queryByText('Project Form')).toBeTruthy();
	});
});
