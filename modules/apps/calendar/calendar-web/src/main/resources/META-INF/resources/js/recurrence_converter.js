/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-recurrence-converter',
	(A) => {
		const Lang = A.Lang;

		const LString = Lang.String;

		const EXDATE = 'EXDATE';

		const RRULE = 'RRULE';

		const STR_COLON = ':';

		const STR_COMMA = ',';

		const STR_EMPTY = '';

		const STR_EQUALS = '=';

		const STR_SEMICOLON = ';';

		const WEEKLY = 'WEEKLY';

		const padNumber = A.rbind('padNumber', LString, 2);

		const RecurrenceConverter = function () {};

		A.mix(RecurrenceConverter.prototype, {
			_encode(value) {
				const instance = this;

				let result = value;

				if (Lang.isDate(value)) {
					result = instance._encodeDate(value);
				}
				else if (Array.isArray(value)) {
					result = instance._encodeDaysOfWeek(value);
				}
				else if (value.position && value.dayOfWeek) {
					result = value.position + value.dayOfWeek;
				}

				return result;
			},

			_encodeDate(date) {
				const day = padNumber(date.getDate());
				const month = padNumber(date.getMonth() + 1);

				return [date.getFullYear(), month, day].join(STR_EMPTY);
			},

			_encodeDaysOfWeek(daysOfWeek) {
				daysOfWeek = A.Array.dedupe(daysOfWeek);

				return daysOfWeek.join(STR_COMMA);
			},

			_encodeExDate(exdate) {
				return EXDATE + STR_SEMICOLON + exdate;
			},

			_encodeRRule(rrule) {
				const instance = this;

				// eslint-disable-next-line @liferay/aui/no-object
				const components = A.Object.map(rrule, (item, index) => {
					return (
						index.toUpperCase() +
						STR_EQUALS +
						instance._encode(item)
					);
				});

				let string = '';

				if (components.length) {
					string = RRULE + STR_COLON + components.join(STR_SEMICOLON);
				}

				return string;
			},

			_parseDate(string) {
				const year = Lang.toInt(string.slice(0, 4));

				const month = Lang.toInt(string.slice(4, 6)) - 1;

				const day = Lang.toInt(string.slice(6, 8));

				return new Date(year, month, day);
			},

			_parseExDate(string) {
				let exDate = null;

				if (
					string &&
					LString.startsWith(string, EXDATE + STR_SEMICOLON)
				) {
					exDate = string.slice(7);
				}

				return exDate;
			},

			_parsePositionalByDay(string) {
				const position = string.slice(0, -2);

				const dayOfWeek = string.slice(-2);

				return {
					dayOfWeek,
					position: Lang.toInt(position),
				};
			},

			_parseRRule(string) {
				const instance = this;

				let rrule = null;

				if (string && LString.startsWith(string, RRULE + STR_COLON)) {
					string = string.slice(6);

					const params = string.split(STR_SEMICOLON);

					rrule = {};

					for (const i in params) {
						const pair = params[i].split(STR_EQUALS);

						rrule[pair[0].toLowerCase()] = pair[1];
					}

					if (rrule.interval) {
						rrule.interval = Lang.toInt(rrule.interval);
					}

					if (rrule.count) {
						rrule.count = Lang.toInt(rrule.count);
					}

					if (rrule.until) {
						rrule.until = instance._parseDate(rrule.until);
					}

					if (rrule.freq === WEEKLY) {
						rrule.byday = rrule.byday.split(STR_COMMA);
					}
					else if (rrule.byday) {
						rrule.byday = instance._parsePositionalByDay(
							rrule.byday
						);

						if (rrule.month) {
							rrule.bymonth = Lang.toInt(rrule.bymonth);
						}
					}
				}

				return rrule;
			},

			encodeRecurrence(recurrence) {
				const instance = this;

				let string = null;

				if (recurrence) {
					const components = [];

					components.push(instance._encodeRRule(recurrence.rrule));

					if (recurrence.exdate) {
						components.push(
							instance._encodeExDate(recurrence.exdate)
						);
					}

					string = components.join('\n');
				}

				return string;
			},

			parseRecurrence(string) {
				const instance = this;

				let recurrence = null;

				if (string) {
					const parts = string.split('\n');

					const rrule = instance._parseRRule(parts[0]);

					if (rrule) {
						recurrence = {
							rrule,
						};

						const exdate = instance._parseExDate(parts[1]);

						if (exdate) {
							recurrence.exdate = exdate;
						}
					}
				}

				return recurrence;
			},
		});

		Liferay.RecurrenceConverter = RecurrenceConverter;
	},
	''
);
