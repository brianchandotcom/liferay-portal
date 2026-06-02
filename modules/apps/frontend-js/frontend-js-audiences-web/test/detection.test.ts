/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as audiences from '../src/main/resources/META-INF/resources/implementation';

import type {
	Attribute,
	AudiencesDefinition,
	Conjunction,
	Operator,
	Rule,
} from '../src/main/resources/META-INF/resources/index';

const URL = 'https://example.com/audiences.json';

function mockAudiencesDefinition(conjunction: Conjunction, rules: Rule[]) {
	const audiencesDefinition: AudiencesDefinition = {
		audiences: [
			{
				conjunction,
				id: 'the_audience',
				retentionType: 'BROWSER',
				rules,
			},
		],
	};

	(global as any).fetch = jest.fn(() =>
		Promise.resolve({
			json: () => Promise.resolve(audiencesDefinition),
			ok: true,
			status: 200,
			statusText: 'OK',
		})
	);
}

function mockAudiencesDefinitionWithAttribute(
	attribute: Attribute,
	operator: Operator,
	value: any
) {
	return mockAudiencesDefinition('AND', [
		{
			attribute,
			operator,
			value,
		},
	]);
}

describe('detection', () => {
	afterEach(() => {
		jest.useRealTimers();

		jest.restoreAllMocks();

		delete (document as any).cookie;
		delete (document as any).referrer;
		delete (navigator as any).userAgent;
		delete (global as any).Analytics;

		window.history.replaceState({}, '', '/');

		audiences.clear();
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

	it('detects attribute browser_name', async () => {
		mockAudiencesDefinitionWithAttribute('browser_name', 'eq', 'Firefox');

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute browser_version', async () => {
		mockAudiencesDefinitionWithAttribute('browser_version', 'eq', '151.0');

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute cookies', async () => {
		mockAudiencesDefinitionWithAttribute(
			'cookies',
			'includes',
			'REMEMBER_ME=true'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute hostname', async () => {
		mockAudiencesDefinitionWithAttribute('hostname', 'eq', 'localhost');

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute language', async () => {
		mockAudiencesDefinitionWithAttribute('language', 'eq', 'en-US');

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute local_date', async () => {
		mockAudiencesDefinitionWithAttribute('local_date', 'eq', '2009-04-23');

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute local_hour', async () => {
		mockAudiencesDefinitionWithAttribute('local_hour', 'eq', 10);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute pathname', async () => {
		mockAudiencesDefinitionWithAttribute('pathname', 'eq', '/home');

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute referrer', async () => {
		mockAudiencesDefinitionWithAttribute(
			'referrer',
			'eq',
			'https://www.wikipedia.org/'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute request_parameters', async () => {
		mockAudiencesDefinitionWithAttribute(
			'request_parameters',
			'includes',
			'utm_source=newsletter'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute segments', async () => {
		mockAudiencesDefinitionWithAttribute(
			'segments',
			'includes',
			'SEGMENT_REAL_TIME'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute timezone', async () => {
		mockAudiencesDefinitionWithAttribute(
			'timezone',
			'eq',
			'America/New_York'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute url', async () => {
		mockAudiencesDefinitionWithAttribute(
			'url',
			'eq',
			'http://localhost/home?utm_source=newsletter&language=es'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('detects attribute user_agent', async () => {
		mockAudiencesDefinitionWithAttribute(
			'user_agent',
			'eq',
			'Mozilla/5.0 (X11; Linux x86_64; rv:151.0) Gecko/20100101 Firefox/151.0'
		);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));
	});

	it('matches an AND audience only when every rule matches', async () => {
		mockAudiencesDefinition('AND', [
			{attribute: 'hostname', operator: 'eq', value: 'localhost'},
			{attribute: 'language', operator: 'eq', value: 'en-US'},
		]);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));

		audiences.clear();

		mockAudiencesDefinition('AND', [
			{attribute: 'hostname', operator: 'eq', value: 'localhost'},
			{attribute: 'browser_name', operator: 'eq', value: 'Chrome'},
		]);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set());
	});

	it('matches an OR audience when any rule matches', async () => {
		mockAudiencesDefinition('OR', [
			{attribute: 'hostname', operator: 'eq', value: 'example.com'},
			{attribute: 'language', operator: 'eq', value: 'en-US'},
		]);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set(['the_audience']));

		audiences.clear();

		mockAudiencesDefinition('OR', [
			{attribute: 'hostname', operator: 'eq', value: 'example.com'},
			{attribute: 'browser_name', operator: 'eq', value: 'Chrome'},
		]);

		await audiences.runDetection(URL);

		expect(audiences.get()).toEqual(new Set());
	});
});
