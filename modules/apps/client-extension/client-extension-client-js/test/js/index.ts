/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {__LIFERAY_REMOTE_APP_SDK__ as SDK} from '../../src/main/resources/META-INF/resources/js/index';

const {Client} = SDK;

describe('SDK.Client()', () => {
	let client: ReturnType<typeof Client>;

	beforeEach(() => {

		// Cast to avoid TS7009 (TS wants `new` used only with `class` values).

		client = new (SDK.Client as any)();
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('can be instantiated', () => {
		expect(client).not.toBe(null);
		expect(typeof client).toBe('object');
	});

	describe('debug', () => {
		it('defaults to off', () => {
			expect(client.debug).toBe(false);
		});

		it('can be overridden at construction time', () => {
			client = new (SDK.Client as any)({debug: true});

			expect(client.debug).toBe(true);
		});

		it('can be updated after construction', () => {
			expect(client.debug).toBe(false);

			client.debug = true;

			expect(client.debug).toBe(true);
		});

		it('logs when true', () => {
			client.debug = true;

			const spy = jest.spyOn(console, 'log');

			client.openToast({message: 'hello'});

			expect(spy).toHaveBeenCalledWith(
				expect.stringMatching(/^\[CLIENT: [a-f0-9]+\.\.\.\]/),
				expect.stringContaining('enqueuing message'),
				expect.objectContaining({command: 'openToast'})
			);
		});

		it('logs nothing when false', () => {
			const spy = jest.spyOn(console, 'log');

			client.openToast({message: 'hello'});

			expect(spy).not.toHaveBeenCalled();
		});
	});

	describe('state', () => {
		it('initiates registration as soon as it is constructed', () => {
			expect(client.state).toBe('registering');
		});
	});
});
