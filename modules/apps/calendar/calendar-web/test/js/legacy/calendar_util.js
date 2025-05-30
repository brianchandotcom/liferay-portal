/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

describe('liferay-calendar-util', () => {
	let calendarUtil;

	beforeEach((done) => {
		const add = AUI.add;

		AUI.add = function (name, callback, version, metadata) {
			add.call(AUI, name, callback, version, {
				...metadata,
				requires: metadata.requires.filter(
					(name) =>
						name !== 'aui-scheduler' &&
						name !== 'aui-toolbar' &&
						name !== 'autocomplete'
				),
			});
		};

		require('../../../src/main/resources/META-INF/resources/js/legacy/calendar_util');

		AUI().use(['liferay-calendar-util'], () => {
			calendarUtil = global.Liferay.CalendarUtil;

			done();
		});
	});

	it('ensures the doAsUserId parameter is encoded', () => {
		const url = calendarUtil.fillURLParameters(
			'https://example.com/page?doAsUserId=userId',
			{}
		);

		expect(url).toContain('doAsUserId=userIdEncoded');
	});

	test.each`
		utc                       | timeZone              | expectedHour
		${'2025-05-30T00:30:00Z'} | ${'UTC'}              | ${0}
		${'2025-05-30T01:59:00Z'} | ${'UTC'}              | ${0}
		${'2025-05-30T02:00:00Z'} | ${'UTC'}              | ${0}
		${'2025-05-30T03:00:00Z'} | ${'UTC'}              | ${1}
		${'2025-05-30T10:00:00Z'} | ${'Europe/Paris'}     | ${10}
		${'2025-05-30T04:00:00Z'} | ${'America/New_York'} | ${0}
		${'2025-05-30T06:00:00Z'} | ${'America/New_York'} | ${0}
		${'2025-05-30T08:00:00Z'} | ${'America/New_York'} | ${2}
	`(
		'sets initial scroll hour to $expectedHour for UTC time $utc in timeZone $timeZone',
		({expectedHour, timeZone, utc}) => {
			const utcDate = new Date(utc);

			const scrollDate = calendarUtil.getInitialScroll(utcDate, timeZone);

			expect(scrollDate.getUTCHours()).toBe(expectedHour);
			expect(scrollDate.getUTCMinutes()).toBe(0);
		}
	);
});
