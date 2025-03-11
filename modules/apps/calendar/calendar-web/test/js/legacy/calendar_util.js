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
});
