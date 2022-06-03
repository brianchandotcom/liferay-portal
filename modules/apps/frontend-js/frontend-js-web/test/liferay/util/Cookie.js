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

import Cookie from '../../../src/main/resources/META-INF/resources/liferay/util/Cookie';

describe('Liferay.Util.Cookie', () => {
	const necessaryCookie = 'any-cookie';
	const unnecessaryCookie = 'any-other-cookie';
	const anyCookieValue = 'any-value';

	const defaultCookieMap = {
		[Cookie.types.NECESSARY]: true,
		[Cookie.types.PERFORMANCE]: false,
		[Cookie.types.PERSONALIZATION]: false,
		[Cookie.types.FUNCTIONAL]: false,
	};

	beforeEach(() => {
		Object.entries(defaultCookieMap).forEach(([name, value]) => {
			document.cookie = `${name}=${value}`;
		});
	});

	afterEach(() => {
		jest.restoreAllMocks();
		document.cookie = `${necessaryCookie}=; max-age=0`;
		document.cookie = `${unnecessaryCookie}=; max-age=0`;
	});

	describe('Liferay.Util.Cookie.set', () => {
		it('Always allows setting a necessary cookie', () => {
			const cookieIsSet = Cookie.set(
				necessaryCookie,
				anyCookieValue,
				Cookie.types.NECESSARY
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(necessaryCookie);
			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it('Allows setting a performance cookie if enabled', () => {
			Cookie.set(
				Cookie.types.PERFORMANCE,
				'true',
				Cookie.types.NECESSARY
			);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.PERFORMANCE
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(unnecessaryCookie);
			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it("Doesn't allow setting a performance cookie if disabled", () => {
			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.PERFORMANCE
			);

			expect(cookieIsSet).toBe(false);
			expect(Cookie.get(unnecessaryCookie)).toBeUndefined();
		});

		it('Allows setting a personalization cookie if enabled', () => {
			Cookie.set(
				Cookie.types.PERSONALIZATION,
				'true',
				Cookie.types.NECESSARY
			);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.PERSONALIZATION
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(unnecessaryCookie);
			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it("Doesn't allow setting a personalization cookie if disabled", () => {
			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.PERSONALIZATION
			);

			expect(cookieIsSet).toBe(false);
			expect(Cookie.get(necessaryCookie)).toBe(undefined);
		});

		it('Allows setting a functional cookie if enabled', () => {
			Cookie.set(Cookie.types.FUNCTIONAL, 'true', Cookie.types.NECESSARY);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.FUNCTIONAL
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(unnecessaryCookie);
			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it("Doesn't allow setting a functional cookie if disabled", () => {
			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.FUNCTIONAL
			);

			expect(cookieIsSet).toBe(false);
			expect(Cookie.get(necessaryCookie)).toBe(undefined);
		});

		it('Allows setting optional cookie settings', () => {
			let cookieValue;
			const cookieSetterMock = jest
				.spyOn(document, 'cookie', 'set')
				.mockImplementation((value) => {
					cookieValue = value;
				});

			const maxAgeValue = 5;
			const samesiteValue = 'lax';
			const pathValue = '/any-path';
			const domainValue = 'any-domain.com';

			Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.types.NECESSARY,
				{
					'domain': domainValue,
					'max-age': maxAgeValue,
					'path': pathValue,
					'samesite': samesiteValue,
					'secure': true,
				}
			);

			expect(cookieSetterMock).toHaveBeenCalled();
			expect(cookieValue).not.toBeUndefined();
			expect(cookieValue.includes(`domain=${domainValue}`)).toBe(true);
			expect(cookieValue.includes(`max-age=${maxAgeValue}`)).toBe(true);
			expect(cookieValue.includes(`path=${pathValue}`)).toBe(true);
			expect(cookieValue.includes('secure')).toBe(true);
			expect(cookieValue.includes(`samesite=${samesiteValue}`)).toBe(
				true
			);
		});
	});

	describe('Liferay.Util.Cookie.get', () => {
		it("Returns undefined if the cookie isn't set", () => {
			expect(Cookie.get(unnecessaryCookie)).toBeUndefined();
		});

		it('Returns value as string if the cookie is set', () => {
			expect(Cookie.get(Cookie.types.FUNCTIONAL)).toBe('false');
			expect(Cookie.get(Cookie.types.NECESSARY)).toBe('true');
		});
	});

	describe('Liferay.Util.Cookie.remove', () => {
		it('Removes cookie if it exists', () => {
			expect(Cookie.get(Cookie.types.FUNCTIONAL)).not.toBeUndefined();

			Cookie.remove(Cookie.types.FUNCTIONAL);
			expect(Cookie.get(Cookie.types.FUNCTIONAL)).toBeUndefined();
		});

		it("Cookie still doesn't exist if it didn't exist before removal", () => {
			Cookie.remove(unnecessaryCookie);
			expect(Cookie.get(unnecessaryCookie)).toBeUndefined();
		});
	});

	describe('Liferay.Util.Cookie.types', () => {
		it('Exists', () => {
			expect(Cookie.types).not.toBeUndefined();
		});
	});
});
