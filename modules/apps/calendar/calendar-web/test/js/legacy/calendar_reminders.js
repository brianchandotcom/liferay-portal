/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

describe('liferay-calendar-reminders', () => {
	let Reminders;
	let Time;

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
		require('../../../src/main/resources/META-INF/resources/js/legacy/calendar_reminders');

		AUI().use(['liferay-calendar-util'], (A) => {
			Time = global.Liferay.Time;

			// Stub for "aui-component", which refuses to load in test env.

			A.Component = {
				create({prototype}) {
					const constructor = function (config) {
						this.initializer(config);
					};

					Object.assign(constructor.prototype, prototype);

					return constructor;
				},
			};

			AUI().use(['liferay-calendar-reminders'], () => {
				Reminders = global.Liferay.Reminders;

				done();
			});
		});
	});

	test.each`
		unit         | interval
		${'minutes'} | ${'MINUTE'}
		${'hours'}   | ${'HOUR'}
		${'days'}    | ${'DAY'}
		${'weeks'}   | ${'WEEK'}
	`(
		'preselects the $unit duration for a reminder stored in $unit',
		({interval, unit}) => {
			const reminders = new Reminders();

			const node = document.createElement('div');

			reminders.get = (key) =>
				({
					boundingBox: {
						setContent(content) {
							node.innerHTML = content;
						},
					},
					portletNamespace: '_test_',
					strings: {
						days: 'days',
						email: 'email',
						hours: 'hours',
						minutes: 'minutes',
						weeks: 'weeks',
					},
				})[key];

			reminders._uiSetValues([{interval: Time[interval]}]);

			expect(
				node.querySelector('.reminder-duration option[selected]')?.text
			).toBe(unit);
		}
	);
});
