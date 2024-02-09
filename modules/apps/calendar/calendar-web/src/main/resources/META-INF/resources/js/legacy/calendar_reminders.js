/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-reminders',
	(A) => {
		const Lang = A.Lang;

		const STR_BLANK = '';

		const TPL_REMINDER_SECTION =
			'<div class="calendar-portlet-reminder-section form-inline">' +
			'<input <tpl if="!disabled">checked="checked"</tpl> class="calendar-portlet-reminder-check" id="{portletNamespace}reminder{i}" name="{portletNamespace}reminder{i}" type="checkbox" />' +
			'<label class="reminder-type" for="{portletNamespace}reminder{i}">' +
			'<input id="{portletNamespace}reminderType{i}" name="{portletNamespace}reminderType{i}" type="hidden" value="email" />' +
			'{email}' +
			'</label>' +
			'<input class="input-mini reminder-value" <tpl if="disabled">disabled="disabled"</tpl> name="{portletNamespace}reminderValue{i}" size="5" type="text" value="{time.value}" /> ' +
			'<select class="reminder-duration span2" <tpl if="disabled">disabled="disabled"</tpl> name="{portletNamespace}reminderDuration{i}">' +
			'<option <tpl if="time.desc == \'minutes\'">selected="selected"</tpl> value="60">{minutes}</option>' +
			'<option <tpl if="time.desc == \'hours\'">selected="selected"</tpl> value="3600">{hours}</option>' +
			'<option <tpl if="time.desc == \'days\'">selected="selected"</tpl> value="86400">{days}</option>' +
			'<option <tpl if="time.desc == \'weeks\'">selected="selected"</tpl> value="604800">{weeks}</option>' +
			'</select>' +
			'</div>';

		const Reminders = A.Component.create({
			ATTRS: {
				portletNamespace: {
					value: '',
				},

				strings: {
					value: {
						days: Liferay.Language.get('days'),
						email: Liferay.Language.get('email'),
						hours: Liferay.Language.get('hours'),
						minutes: Liferay.Language.get('minutes'),
						weeks: Liferay.Language.get('weeks'),
					},
				},

				values: {
					validator: Lang.isArray,
					value: [
						{
							interval: 10,
							type:
								Liferay.CalendarUtil.NOTIFICATION_DEFAULT_TYPE,
						},
						{
							interval: 60,
							type:
								Liferay.CalendarUtil.NOTIFICATION_DEFAULT_TYPE,
						},
					],
				},
			},

			NAME: 'reminders',

			UI_ATTRS: ['values'],

			prototype: {
				_onChangeCheckbox(event) {
					const target = event.target;

					const checked = target.get('checked');
					const elements = target.siblings('input[type=text],select');

					elements.set('disabled', !checked);

					if (checked) {
						elements.first().select();
					}
				},

				_uiSetValues(val) {
					const instance = this;

					const boundingBox = instance.get('boundingBox');
					const portletNamespace = instance.get('portletNamespace');
					const strings = instance.get('strings');

					const buffer = [];

					const tplReminder = instance.tplReminder;

					for (let i = 0; i < val.length; i++) {
						const value = val[i];

						buffer.push(
							tplReminder.parse({
								...strings,
								disabled: !value.interval,
								i,
								portletNamespace,
								time: Liferay.Time.getDescription(
									value.interval
								),
							})
						);
					}

					boundingBox.setContent(buffer.join(STR_BLANK));
				},

				bindUI() {
					const instance = this;

					const boundingBox = instance.get('boundingBox');

					boundingBox.delegate(
						'change',
						instance._onChangeCheckbox,
						'.calendar-portlet-reminder-check',
						instance
					);
				},

				initializer() {
					const instance = this;

					instance.tplReminder = new A.Template(TPL_REMINDER_SECTION);
				},
			},
		});

		Liferay.Reminders = Reminders;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-template-deprecated',
			'liferay-calendar-util',
		],
	}
);
