/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-message-util',
	(A) => {
		const Lang = A.Lang;

		const STR_BLANK = '';

		const TPL_MESSAGE_UPDATE_ALL_INVITED =
			'<p class="calendar-portlet-confirmation-text">' +
			Liferay.Language.get('invited-users-will-be-notified') +
			'</p>';

		Liferay.CalendarMessageUtil = {
			_queueableQuestionUpdateAllInvited(data) {
				const instance = this;

				const answers = data.answers;

				const showNextQuestion = A.bind('run', instance.queue);

				if (answers.cancel) {
					A.soon(showNextQuestion);
				}
				else {
					Liferay.CalendarMessageUtil.confirm(
						TPL_MESSAGE_UPDATE_ALL_INVITED,
						Liferay.Language.get('save-changes'),
						Liferay.Language.get('do-not-change-the-event'),
						showNextQuestion,
						() => {
							answers.cancel = true;

							showNextQuestion();
						}
					);
				}
			},

			_queueableQuestionUpdateRecurring(data) {
				const instance = this;

				const answers = data.answers;

				const showNextQuestion = A.bind('run', instance.queue);

				if (answers.cancel) {
					A.soon(showNextQuestion);
				}
				else {
					Liferay.RecurrenceUtil.openConfirmationPanel(
						'update',
						() => {
							answers.updateInstance = true;

							showNextQuestion();
						},
						() => {
							answers.allFollowing = true;
							answers.updateInstance = true;

							showNextQuestion();
						},
						showNextQuestion,
						() => {
							answers.cancel = true;

							showNextQuestion();
						}
					);
				}
			},

			_queueableQuestionUserCalendarOnly(data) {
				const instance = this;

				const answers = data.answers;

				const showNextQuestion = A.bind('run', instance.queue);

				if (answers.cancel) {
					A.soon(showNextQuestion);
				}
				else {
					const content = [
						'<p class="calendar-portlet-confirmation-text">',
						Lang.sub(
							Liferay.Language.get(
								'you-are-about-to-make-changes-that-will-only-affect-your-calendar-x'
							),
							[Liferay.Util.escapeHTML(data.calendarName)]
						),
						'</p>',
					].join(STR_BLANK);

					Liferay.CalendarMessageUtil.confirm(
						content,
						Liferay.Language.get('save-changes'),
						Liferay.Language.get('do-not-change-the-event'),
						showNextQuestion,
						() => {
							answers.cancel = true;

							showNextQuestion();
						}
					);
				}
			},

			confirm(message, yesButtonLabel, noButtonLabel, yesFn, noFn) {
				let confirmationPanel; // eslint-disable-line prefer-const

				const getButtonConfig = function (label, callback) {
					return {
						label,
						on: {
							click() {
								if (callback) {
									callback.apply(this, arguments);
								}

								confirmationPanel.hide();
							},
						},
					};
				};

				confirmationPanel = Liferay.Util.Window.getWindow({
					dialog: {
						bodyContent: message,
						height: 250,
						hideOn: [],
						resizable: false,
						toolbars: {
							footer: [
								getButtonConfig(yesButtonLabel, yesFn),
								getButtonConfig(noButtonLabel, noFn),
							],
						},
						width: 700,
					},
					title: Liferay.Language.get('are-you-sure'),
				});

				return confirmationPanel.render().show();
			},

			promptSchedulerEventUpdate(data) {
				const instance = this;

				data.answers = {};

				const queue = new A.AsyncQueue();

				if (data.recurring) {
					queue.add({
						args: [data],
						autoContinue: false,
						context: instance,
						fn: instance._queueableQuestionUpdateRecurring,
						timeout: 0,
					});
				}

				if (data.masterBooking) {
					if (data.hasChild) {
						queue.add({
							args: [data],
							autoContinue: false,
							context: instance,
							fn: instance._queueableQuestionUpdateAllInvited,
							timeout: 0,
						});
					}
				}
				else {
					queue.add({
						args: [data],
						autoContinue: false,
						context: instance,
						fn: instance._queueableQuestionUserCalendarOnly,
						timeout: 0,
					});
				}

				queue.add({
					args: [data],
					autoContinue: false,
					context: instance,
					fn: data.resolver,
					timeout: 0,
				});

				instance.queue = queue;

				queue.run();
			},

			showAlert(containerId, message) {
				Liferay.Util.openToast({
					containerId,
					message,
					type: 'success',
				});
			},

			showErrorMessage(container, errorMessage) {
				Liferay.Util.openToast({
					container,
					message: errorMessage,
					type: 'danger',
				});
			},

			showSuccessMessage(container, message) {
				if (!message) {
					message = Liferay.Language.get(
						'your-request-completed-successfully'
					);
				}

				Liferay.Util.openToast({
					container,
					message,
					type: 'success',
				});
			},
		};
	},
	'',
	{
		requires: ['liferay-util-window'],
	}
);
