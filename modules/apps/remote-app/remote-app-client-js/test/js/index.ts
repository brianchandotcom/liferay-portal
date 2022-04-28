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
