AUI.add(
	'liferay-calendar-message-util',
	function(A) {
		var Lang = A.Lang;
		var LString = Lang.String;

		var STR_BLANK = '';

		var TPL_MESSAGE_UPDATE_ALL_INVITED = '<p class="calendar-portlet-confirmation-text">' +
				Liferay.Language.get('invited-users-will-be-notified') +
			'</p>';

		Liferay.CalendarMessageUtil = {

			confirm: function(message, yesButtonLabel, noButtonLabel, yesFn, noFn) {
				var instance = this;

				var confirmationPanel;

				var getButtonConfig = function(label, callback) {
					return {
						label: label,
						on: {
							click: function() {
								if (callback) {
									callback.apply(this, arguments);
								}

								confirmationPanel.hide();
							}
						}
					};
				};

				confirmationPanel = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: message,
							height: 250,
							hideOn: [],
							resizable: false,
							toolbars: {
								footer: [
									getButtonConfig(yesButtonLabel, yesFn),
									getButtonConfig(noButtonLabel, noFn)
								]
							},
							width: 700
						},
						title: Liferay.Language.get('are-you-sure')
					}
				);

				return confirmationPanel.render().show();
			},

			promptSchedulerEventUpdate: function(data) {
				var instance = this;

				data.answers = {};

				var queue = new A.AsyncQueue();

				if (data.recurring) {
					queue.add(
						{
							args: [data],
							autoContinue: false,
							context: instance,
							fn: instance._queueableQuestionUpdateRecurring,
							timeout: 0
						}
					);
				}

				if (data.masterBooking) {
					if (data.hasChild) {
						queue.add(
							{
								args: [data],
								autoContinue: false,
								context: instance,
								fn: instance._queueableQuestionUpdateAllInvited,
								timeout: 0
							}
						);
					}
				}
				else {
					queue.add(
						{
							args: [data],
							autoContinue: false,
							context: instance,
							fn: instance._queueableQuestionUserCalendarOnly,
							timeout: 0
						}
					);
				}

				queue.add(
					{
						args: [data],
						autoContinue: false,
						context: instance,
						fn: data.resolver,
						timeout: 0
					}
				);

				instance.queue = queue;

				queue.run();
			},

			showAlert: function(container, message) {
				new A.Alert(
					{
						animated: true,
						bodyContent: message,
						closeable: true,
						cssClass: 'alert-success',
						destroyOnHide: true,
						duration: 1
					}
				).render(container);
			},

			_queueableQuestionUpdateAllInvited: function(data) {
				var instance = this;

				var answers = data.answers;

				var showNextQuestion = A.bind('run', instance.queue);

				if (answers.cancel) {
					A.soon(showNextQuestion);
				}
				else {
					Liferay.CalendarMessageUtil.confirm(
						TPL_MESSAGE_UPDATE_ALL_INVITED,
						Liferay.Language.get('save-changes'),
						Liferay.Language.get('do-not-change-the-event'),
						showNextQuestion,
						function() {
							answers.cancel = true;

							showNextQuestion();
						}
					);
				}
			},

			_queueableQuestionUpdateRecurring: function(data) {
				var instance = this;

				var answers = data.answers;

				var showNextQuestion = A.bind('run', instance.queue);

				if (answers.cancel) {
					A.soon(showNextQuestion);
				}
				else {
					Liferay.RecurrenceUtil.openConfirmationPanel(
						'update',
						function() {
							answers.updateInstance = true;

							showNextQuestion();
						},
						function() {
							answers.allFollowing = true;
							answers.updateInstance = true;

							showNextQuestion();
						},
						showNextQuestion,
						function() {
							answers.cancel = true;

							showNextQuestion();
						}
					);
				}
			},

			_queueableQuestionUserCalendarOnly: function(data) {
				var instance = this;

				var answers = data.answers;

				var showNextQuestion = A.bind('run', instance.queue);

				if (answers.cancel) {
					A.soon(showNextQuestion);
				}
				else {
					var content = [
						'<p class="calendar-portlet-confirmation-text">',
						Lang.sub(
							Liferay.Language.get('you-are-about-to-make-changes-that-will-only-affect-your-calendar-x'),
							[LString.escapeHTML(data.calendarName)]
						),
						'</p>'
					].join(STR_BLANK);

					Liferay.CalendarMessageUtil.confirm(
						content,
						Liferay.Language.get('save-changes'),
						Liferay.Language.get('do-not-change-the-event'),
						showNextQuestion,
						function() {
							answers.cancel = true;

							showNextQuestion();
						}
					);
				}
			}
		};
	},
	'',
	{
		requires: ['aui-alert', 'liferay-util-window']
	}
);