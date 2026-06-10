/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import UAParser from 'ua-parser-js';

import {getBrowserName} from '../src/main/resources/META-INF/resources/detection/attributes/browser_name';
import {getBrowserVersion} from '../src/main/resources/META-INF/resources/detection/attributes/browser_version';
import {getCookies} from '../src/main/resources/META-INF/resources/detection/attributes/cookies';
import {getHostname} from '../src/main/resources/META-INF/resources/detection/attributes/hostname';
import {getLanguage} from '../src/main/resources/META-INF/resources/detection/attributes/language';
import {getLocalDate} from '../src/main/resources/META-INF/resources/detection/attributes/local_date';
import {getLocalHour} from '../src/main/resources/META-INF/resources/detection/attributes/local_hour';
import {getPathname} from '../src/main/resources/META-INF/resources/detection/attributes/pathname';
import {getReferrer} from '../src/main/resources/META-INF/resources/detection/attributes/referrer';
import {getRequestParameters} from '../src/main/resources/META-INF/resources/detection/attributes/request_parameters';
import {getSegments} from '../src/main/resources/META-INF/resources/detection/attributes/segments';
import {getTimezone} from '../src/main/resources/META-INF/resources/detection/attributes/timezone';
import {getUrl} from '../src/main/resources/META-INF/resources/detection/attributes/url';
import {getUserAgent} from '../src/main/resources/META-INF/resources/detection/attributes/user_agent';

describe('attributes', () => {
	afterEach(() => {
		jest.useRealTimers();

		jest.restoreAllMocks();

		delete (document as any).cookie;
		delete (document as any).referrer;
		delete (navigator as any).userAgent;
		delete (global as any).Analytics;

		window.history.replaceState({}, '', '/');
	});

	beforeEach(() => {
		jest.useFakeTimers();
		jest.setSystemTime(new Date('2009-04-23T10:30:00'));

		jest.spyOn(
			Intl.DateTimeFormat.prototype,
			'resolvedOptions'
		).mockReturnValue({timeZone: 'America/New_York'} as any);

		Object.defineProperty(document, 'cookie', {
			configurable: true,
			value: 'REMEMBER_ME=true; JSESSIONID=ba8e4d1c',
		});
		Object.defineProperty(document, 'referrer', {
			configurable: true,
			value: 'https://www.wikipedia.org/',
		});

		Object.defineProperty(global, 'Analytics', {
			configurable: true,
			value: {
				segment: {
					getBatchSegmentExternalReferenceCodes: () =>
						Promise.resolve(['SEGMENT_BATCH']),
					getRealTimeSegmentExternalReferenceCodes: () =>
						Promise.resolve(['SEGMENT_REAL_TIME']),
				},
			},
		});

		Object.defineProperty(navigator, 'userAgent', {
			configurable: true,
			value: 'Mozilla/5.0 (X11; Linux x86_64; rv:151.0) Gecko/20100101 Firefox/151.0',
		});

		window.history.replaceState(
			{},
			'',
			'/home?utm_source=newsletter&language=es'
		);
	});

	it('attribute browser_name works and returns a string', async () => {
		const value = getBrowserName(new UAParser(navigator.userAgent));

		expect(typeof value).toBe('string');
		expect(value).toBe('Firefox');
	});

	it('attribute browser_version and returns a string', async () => {
		const value = getBrowserVersion(new UAParser(navigator.userAgent));

		expect(typeof value).toBe('string');
		expect(value).toBe('151.0');
	});

	describe('attribute cookies', () => {
		it('works and returns a Set<string>', async () => {
			const value = getCookies();

			expect(value).toBeInstanceOf(Set);
			expect(value).toEqual(
				new Set(['REMEMBER_ME=true', 'JSESSIONID=ba8e4d1c'])
			);
		});

		it('decodes percent-encoded cookies', async () => {
			Object.defineProperty(document, 'cookie', {
				configurable: true,
				value: 'greeting=hello%20world; city=S%C3%A3o%20Paulo',
			});

			expect(getCookies()).toEqual(
				new Set(['greeting=hello world', 'city=São Paulo'])
			);
		});

		it('passes cookies that are not encoded unchanged', async () => {
			Object.defineProperty(document, 'cookie', {
				configurable: true,
				value: 'REMEMBER_ME=true; JSESSIONID=ba8e4d1c',
			});

			expect(getCookies()).toEqual(
				new Set(['REMEMBER_ME=true', 'JSESSIONID=ba8e4d1c'])
			);
		});

		it('passes cookies that are badly encoded unchanged', async () => {
			Object.defineProperty(document, 'cookie', {
				configurable: true,
				value: 'discount=50%; valid=a%20b',
			});

			expect(getCookies()).toEqual(
				new Set(['discount=50%', 'valid=a b'])
			);
		});
	});

	it('attribute hostname works and returns a string', async () => {
		const value = getHostname();

		expect(typeof value).toBe('string');
		expect(value).toBe('localhost');
	});

	it('attribute language works and returns a string', async () => {
		const value = getLanguage();

		expect(typeof value).toBe('string');
		expect(value).toBe('en-US');
	});

	it('attribute local_date works and returns a string', async () => {
		const value = getLocalDate();

		expect(typeof value).toBe('string');
		expect(value).toBe('2009-04-23');
	});

	it('attribute local_hour works and returns a number', async () => {
		const value = getLocalHour();

		expect(typeof value).toBe('number');
		expect(value).toBe(10);
	});

	it('attribute pathname works and returns a string', async () => {
		const value = getPathname();

		expect(typeof value).toBe('string');
		expect(value).toBe('/home');
	});

	it('attribute referrer works and returns a string', async () => {
		const value = getReferrer();

		expect(typeof value).toBe('string');
		expect(value).toBe('https://www.wikipedia.org/');
	});

	it('attribute request_parameters works and returns a Set<string>', async () => {
		const value = getRequestParameters();

		expect(value).toBeInstanceOf(Set);
		expect(value).toEqual(
			new Set(['utm_source=newsletter', 'language=es'])
		);
	});

	it('attribute segments works and returns a Set<string>', async () => {
		const value = await getSegments();

		expect(value).toBeInstanceOf(Set);
		expect(value).toEqual(new Set(['SEGMENT_BATCH', 'SEGMENT_REAL_TIME']));
	});

	it('attribute timezone works and returns a string', async () => {
		const value = getTimezone();

		expect(typeof value).toBe('string');
		expect(value).toBe('America/New_York');
	});

	it('attribute url works and returns a string', async () => {
		const value = getUrl();

		expect(typeof value).toBe('string');
		expect(value).toBe(
			'http://localhost/home?utm_source=newsletter&language=es'
		);
	});

	it('attribute user_agent works and returns a string', async () => {
		const value = getUserAgent();

		expect(typeof value).toBe('string');
		expect(value).toBe(
			'Mozilla/5.0 (X11; Linux x86_64; rv:151.0) Gecko/20100101 Firefox/151.0'
		);
	});
});
