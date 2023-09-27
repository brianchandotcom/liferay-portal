/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import navigate from '../../../src/main/resources/META-INF/resources/liferay/util/navigate.es';

describe('Liferay.Util.navigate', () => {
	const externalUrl = 'http://externalurl.com';
	const internalUrl = 'http://internalurl.com';

	describe('when SPA is enabled', () => {
		beforeEach(() => {
			Liferay.SPA = {
				app: {
					canNavigate: jest.fn((url) => url.includes('internal')),
					navigate: jest.fn(),
				},
			};
		});

		it('navigates to internal urls using the provided Liferay.SPA.app.navigate helper', () => {
			navigate(internalUrl);

			expect(Liferay.SPA.app.navigate).toBeCalledWith(internalUrl);
		});

		it('navigates to external urls using window.location.assign', () => {
			const spy = jest
				.spyOn(console, 'error')
				.mockImplementation(() => undefined);

			navigate(externalUrl);

			// JSDOM does not allow to mock location.href. Thus, we verify
			// it is called by matching the error they log when an attempt
			// at setting location.href is detected

			expect(spy).toHaveBeenCalled();
			expect(spy.mock.calls[0][0]).toMatch(
				'Error: Not implemented: navigation (except hash changes)'
			);

			spy.mockRestore();
		});

		it('setups one-time-only global listeners in the Liferay object if specified', () => {
			const listenerFn = jest.fn();

			navigate(internalUrl, {
				event1: listenerFn,
				event2: listenerFn,
			});

			expect(Liferay.once).toHaveBeenCalledTimes(2);
			expect(Liferay.once).toHaveBeenNthCalledWith(
				1,
				'event1',
				listenerFn
			);
			expect(Liferay.once).toHaveBeenNthCalledWith(
				2,
				'event2',
				listenerFn
			);
		});
	});

	describe('when SPA is disabled', () => {
		beforeEach(() => {
			Liferay.SPA = undefined;
		});

		it('navigates to the given url using window.location.assign', () => {
			const spy = jest
				.spyOn(console, 'error')
				.mockImplementation(() => undefined);

			navigate(internalUrl);

			// JSDOM does not allow to mock location.href. Thus, we verify
			// it is called by matching the error they log when an attempt
			// at setting location.href is detected

			expect(spy).toHaveBeenCalled();
			expect(spy.mock.calls[0][0]).toMatch(
				'Error: Not implemented: navigation (except hash changes)'
			);

			spy.mockRestore();
		});
	});
});
