/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-recurrence-util',
	(A) => {
		const STR_DASH = '-';

		Liferay.RecurrenceUtil = {
			_createConfirmationPanel(title, bodyContent, footerContent) {
				return Liferay.Util.Window.getWindow({
					dialog: {
						bodyContent,
						destroyOnHide: true,
						height: 400,
						hideOn: [],
						resizable: false,
						toolbars: {
							footer: footerContent,
						},
						width: 700,
					},
					title,
				});
			},

			_createConfirmationPanelContent(description, options) {
				const instance = this;

				const contentNode = A.Node.create(
					A.Lang.sub(instance.RECURRING_EVENT_MODAL_TEMPLATE, {
						description,
					})
				);

				A.each(options, (option) => {
					const optionRow = A.Lang.sub(
						instance.RECURRING_EVENT_MODAL_ITEM_TEMPLATE,
						{
							confirmationDescription:
								option.confirmationDescription,
							confirmationDescriptionComplement:
								option.confirmationDescriptionComplement || '',
						}
					);

					const optionRowNode = A.Node.create(optionRow);

					new A.Button(option.button).render(optionRowNode.one('td'));

					optionRowNode.appendTo(contentNode.one('table'));
				});

				return contentNode;
			},

			_getConfirmationPanelButtons(
				onlyThisInstanceFn,
				allFollowingFn,
				allEventsInFn,
				cancelFn
			) {
				const instance = this;

				const getButtonConfig = function (label, callback, cssClass) {
					return {
						cssClass,
						label,
						on: {
							click() {
								if (callback) {
									callback.apply(this, arguments);
								}

								instance.confirmationPanel.hide();
							},
						},
					};
				};

				const buttons = {
					confirmations: [
						{
							button: getButtonConfig(
								Liferay.Language.get('single-event'),
								onlyThisInstanceFn,
								'btn-sm'
							),
							confirmationDescription: Liferay.Language.get(
								'only-this-event-will-be-modified-the-rest-of-the-series-will-not-change'
							),
						},
						{
							button: getButtonConfig(
								Liferay.Language.get('following-events'),
								allFollowingFn,
								'btn-sm'
							),
							confirmationDescription: Liferay.Language.get(
								'this-event-and-any-future-events-in-the-series-will-be-modified'
							),
							confirmationDescriptionComplement: Liferay.Language.get(
								'any-previous-edits-to-future-events-will-be-overwritten'
							),
						},
						{
							button: getButtonConfig(
								Liferay.Language.get('entire-series'),
								allEventsInFn,
								'btn-sm'
							),
							confirmationDescription: Liferay.Language.get(
								'the-modification-will-change-the-entire-series-of-events'
							),
							confirmationDescriptionComplement: Liferay.Language.get(
								'any-events-edited-previously-will-not-be-affected-by-this-modification'
							),
						},
					],
					dismiss: getButtonConfig(
						Liferay.Language.get('cancel'),
						cancelFn,
						'btn-link'
					),
				};

				return buttons;
			},

			FREQUENCY: {
				DAILY: 'DAILY',
				MONTHLY: 'MONTHLY',
				WEEKLY: 'WEEKLY',
				YEARLY: 'YEARLY',
			},

			INTERVAL_UNITS: {},

			MONTH_LABELS: [],

			POSITION_LABELS: {},

			RECURRENCE_SUMMARIES: {},

			RECURRING_EVENT_MODAL_ITEM_TEMPLATE:
				'<tr><td></td><td>' +
				'<p class="action-description">{ confirmationDescription }</p>' +
				'<p><small><em>{ confirmationDescriptionComplement }<em><small></p></td></tr>',

			RECURRING_EVENT_MODAL_TEMPLATE:
				'<div class="calendar-change-recurring-event-modal-content">' +
				'<p>{description}</p>' +
				'<table></table>' +
				'</div>',

			WEEKDAY_LABELS: {},

			getSummary(recurrence) {
				const instance = this;

				const params = [];
				const parts = [];

				if (Number(recurrence.interval) === 1) {
					parts.push(A.Lang.String.toLowerCase(recurrence.frequency));
				}
				else {
					parts.push(
						'every-x-' +
							instance.INTERVAL_UNITS[recurrence.frequency]
					);

					params.push(recurrence.interval);
				}

				if (recurrence.positionalWeekday) {
					if (recurrence.frequency === instance.FREQUENCY.MONTHLY) {
						parts.push('on-x-x');

						params.push(
							instance.POSITION_LABELS[
								recurrence.positionalWeekday.position
							]
						);
						params.push(
							instance.WEEKDAY_LABELS[
								recurrence.positionalWeekday.weekday
							]
						);
					}
					else {
						parts.push('on-x-x-of-x');

						params.push(
							instance.POSITION_LABELS[
								recurrence.positionalWeekday.position
							]
						);
						params.push(
							instance.WEEKDAY_LABELS[
								recurrence.positionalWeekday.weekday
							]
						);
						params.push(
							instance.MONTH_LABELS[
								recurrence.positionalWeekday.month
							]
						);
					}
				}
				else if (
					recurrence.frequency === instance.FREQUENCY.WEEKLY &&
					!!recurrence.weekdays.length
				) {
					parts.push('on-x');

					const weekdays = recurrence.weekdays.map((item) => {
						return instance.WEEKDAY_LABELS[item];
					});

					params.push(weekdays.join(', '));
				}

				if (recurrence.count && recurrence.endValue === 'after') {
					parts.push('x-times');

					params.push(recurrence.count);
				}
				else if (
					recurrence.untilDate &&
					recurrence.endValue === 'on'
				) {
					parts.push('until-x-x-x');

					const untilDate = recurrence.untilDate;

					params.push(instance.MONTH_LABELS[untilDate.getMonth()]);
					params.push(untilDate.getDate());
					params.push(untilDate.getFullYear());
				}

				const key = parts.join(STR_DASH);

				return A.Lang.sub(instance.RECURRENCE_SUMMARIES[key], params);
			},

			openConfirmationPanel(
				actionName,
				onlyThisInstanceFn,
				allFollowingFn,
				allEventsInFn,
				cancelFn
			) {
				const instance = this;

				const buttons = instance._getConfirmationPanelButtons(
					onlyThisInstanceFn,
					allFollowingFn,
					allEventsInFn,
					cancelFn
				);
				let modalDescription;
				let titleText;

				if (actionName === 'delete') {
					titleText = Liferay.Language.get('delete-recurring-event');
					modalDescription = Liferay.Language.get(
						'would-you-like-to-delete-only-this-event-all-events-in-the-series-or-this-and-all-future-events-in-the-series'
					);
				}
				else {
					titleText = Liferay.Language.get('change-recurring-event');
					modalDescription = Liferay.Language.get(
						'would-you-like-to-change-only-this-event-all-events-in-the-series-or-this-and-all-future-events-in-the-series'
					);
				}

				const bodyContent = instance._createConfirmationPanelContent(
					modalDescription,
					buttons.confirmations
				);

				const footerContent = [buttons.dismiss];

				instance.confirmationPanel = instance._createConfirmationPanel(
					titleText,
					bodyContent,
					footerContent
				);

				return instance.confirmationPanel.render().show();
			},
		};
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window'],
	}
);
