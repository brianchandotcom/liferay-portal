/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Cookie from '../../../../src/main/resources/META-INF/resources/liferay/util/cookie/cookie';

describe('Liferay.Util.Cookie', () => {
	const necessaryCookie = 'any-cookie';
	const unnecessaryCookie = 'any-other-cookie';
	const anyCookieValue = 'any-value';

	const defaultCookieMap = {
		[Cookie.TYPES.NECESSARY]: true,
		[Cookie.TYPES.PERFORMANCE]: false,
		[Cookie.TYPES.PERSONALIZATION]: false,
		[Cookie.TYPES.FUNCTIONAL]: false,
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
				Cookie.TYPES.NECESSARY
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(
				necessaryCookie,
				Cookie.TYPES.NECESSARY
			);

			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it('Allows setting a performance cookie if enabled', () => {
			Cookie.set(
				Cookie.TYPES.PERFORMANCE,
				'true',
				Cookie.TYPES.NECESSARY
			);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.PERFORMANCE
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(
				unnecessaryCookie,
				Cookie.TYPES.PERFORMANCE
			);

			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it("Doesn't allow setting a performance cookie if disabled", () => {
			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.PERFORMANCE
			);

			expect(cookieIsSet).toBe(false);
			expect(
				Cookie.get(unnecessaryCookie, Cookie.TYPES.PERFORMANCE)
			).toBeUndefined();
		});

		it('Allows setting a personalization cookie if enabled', () => {
			Cookie.set(
				Cookie.TYPES.PERSONALIZATION,
				'true',
				Cookie.TYPES.NECESSARY
			);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.PERSONALIZATION
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(
				unnecessaryCookie,
				Cookie.TYPES.PERSONALIZATION
			);

			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it("Doesn't allow setting a personalization cookie if disabled", () => {
			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.PERSONALIZATION
			);

			expect(cookieIsSet).toBe(false);
			expect(
				Cookie.get(unnecessaryCookie, Cookie.TYPES.PERSONALIZATION)
			).toBe(undefined);
		});

		it('Allows setting a functional cookie if enabled', () => {
			Cookie.set(Cookie.TYPES.FUNCTIONAL, 'true', Cookie.TYPES.NECESSARY);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.FUNCTIONAL
			);

			expect(cookieIsSet).toBe(true);

			const setCookieValue = Cookie.get(
				unnecessaryCookie,
				Cookie.TYPES.FUNCTIONAL
			);

			expect(setCookieValue).not.toBeUndefined();
			expect(setCookieValue).toBe(anyCookieValue);
		});

		it("Doesn't allow setting a functional cookie if disabled", () => {
			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.FUNCTIONAL
			);

			expect(cookieIsSet).toBe(false);
			expect(Cookie.get(unnecessaryCookie, Cookie.TYPES.FUNCTIONAL)).toBe(
				undefined
			);
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
				Cookie.TYPES.NECESSARY,
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

		it('Allows setting cookies if preference cookies are not set', () => {
			for (const type in Object.keys(Cookie.TYPES)) {
				document.cookie += `${type}=false; max-age=0`;

				Cookie.set(necessaryCookie, anyCookieValue, type);

				expect(
					Cookie.get(type, Cookie.TYPES.NECESSARY)
				).toBeUndefined();
				expect(Cookie.get(necessaryCookie, type)).toBe(anyCookieValue);
			}
		});
	});

	describe('Liferay.Util.Cookie.get', () => {
		it("Returns undefined if the cookie isn't set", () => {
			expect(
				Cookie.get(unnecessaryCookie, Cookie.TYPES.PERFORMANCE)
			).toBeUndefined();
		});

		it('Returns consent value as string if the cookie is set', () => {
			expect(
				Cookie.get(Cookie.TYPES.FUNCTIONAL, Cookie.TYPES.NECESSARY)
			).toBe('false');
			expect(
				Cookie.get(Cookie.TYPES.NECESSARY, Cookie.TYPES.NECESSARY)
			).toBe('true');
		});

		it('Returns value as string if cookie is set and type consented', () => {
			Cookie.set(
				Cookie.TYPES.PERFORMANCE,
				'true',
				Cookie.TYPES.NECESSARY
			);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.PERFORMANCE
			);

			expect(cookieIsSet).toBe(true);
			expect(
				Cookie.get(unnecessaryCookie, Cookie.TYPES.PERFORMANCE)
			).not.toBeUndefined();
		});

		it("Doesn't return value if the cookie is set but type not consented", () => {
			Cookie.set(
				Cookie.TYPES.PERFORMANCE,
				'true',
				Cookie.TYPES.NECESSARY
			);

			const cookieIsSet = Cookie.set(
				unnecessaryCookie,
				anyCookieValue,
				Cookie.TYPES.PERFORMANCE
			);

			expect(cookieIsSet).toBe(true);

			Cookie.set(
				Cookie.TYPES.PERFORMANCE,
				'false',
				Cookie.TYPES.NECESSARY
			);

			expect(
				Cookie.get(unnecessaryCookie, Cookie.TYPES.PERFORMANCE)
			).toBeUndefined();
		});
	});

	describe('Liferay.Util.Cookie.remove', () => {
		it('Removes cookie if it exists', () => {
			expect(
				Cookie.get(Cookie.TYPES.FUNCTIONAL, Cookie.TYPES.NECESSARY)
			).not.toBeUndefined();

			Cookie.remove(Cookie.TYPES.FUNCTIONAL);

			expect(
				Cookie.get(Cookie.TYPES.FUNCTIONAL, Cookie.TYPES.NECESSARY)
			).toBeUndefined();
		});

		it("Cookie still doesn't exist if it didn't exist before removal", () => {
			Cookie.set(Cookie.TYPES.FUNCTIONAL, 'true', Cookie.TYPES.NECESSARY);

			Cookie.remove(unnecessaryCookie);

			expect(
				Cookie.get(unnecessaryCookie, Cookie.TYPES.FUNCTIONAL)
			).toBeUndefined();
		});
	});

	describe('Liferay.Util.Cookie.TYPES', () => {
		it('Exists', () => {
			expect(Cookie.TYPES).not.toBeUndefined();
		});
	});
});
