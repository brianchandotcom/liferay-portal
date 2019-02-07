AUI.add(
	'liferay-workflow-tasks',
	function(A) {
		var WorkflowTasks = {
			onTaskClick: function(event, randomId) {
				var instance = this;

				var icon = event.currentTarget;
				var li = icon.get('parentNode');

				event.preventDefault();

				var content = null;

				var height = 400;

				if (li.hasClass('task-due-date-link')) {
					content = '#' + randomId + 'updateDueDate';

					height = 480;
				}

				var title = icon.text();

				WorkflowTasks._showPopup(icon.attr('href'), A.one(content), title, randomId, height, icon._node.id);
			},

			_showPopup: function(url, content, title, randomId, height, targetId) {
				var instance = this;

				var form = A.Node.create('<form />');

				form.setAttribute('action', url);
				form.setAttribute('method', 'POST');

				var comments = A.one('#' + randomId + 'updateComments');

				if (comments && !instance._comments[randomId]) {
					instance._comments[randomId] = comments;
				}
				else if (!comments && instance._comments[randomId]) {
					comments = instance._comments[randomId];
				}

				if (content && !instance._content[randomId]) {
					instance._content[randomId] = content;
				}
				else if (!content && targetId.endsWith('taskDueDateLink')) {
					content = instance._content[randomId];
				}

				if (content) {
					form.append(content);
					content.show();
				}

				if (comments) {
					form.append(comments);
					comments.show();
				}

				var dialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: form,
							destroyOnHide: true,
							height: height,
							resizable: false,
							toolbars: {
								footer: [
									{
										cssClass: 'btn btn-secondary task-action-button',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												dialog.hide();
											}
										}
									},
									{
										cssClass: 'btn btn-primary task-action-button',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('done'),
										on: {
											click: function() {
												submitForm(form);
											}
										}
									}
								],
								header: [
									{
										cssClass: 'close',
										discardDefaultButtonCssClasses: true,
										labelHTML: '<svg class="lexicon-icon lexicon-icon-times" focusable="false" role="presentation" viewBox="0 0 512 512"><path class="lexicon-icon-outline" d="M295.781 256l205.205-205.205c10.998-10.998 10.998-28.814 0-39.781-10.998-10.998-28.815-10.998-39.781 0l-205.205 205.205-205.205-205.238c-10.966-10.998-28.814-10.998-39.781 0-10.998 10.998-10.998 28.814 0 39.781l205.205 205.238-205.205 205.205c-10.998 10.998-10.998 28.815 0 39.781 5.467 5.531 12.671 8.265 19.874 8.265s14.407-2.734 19.907-8.233l205.205-205.238 205.205 205.205c5.5 5.5 12.703 8.233 19.906 8.233s14.407-2.734 19.906-8.233c10.998-10.998 10.998-28.815 0-39.781l-205.238-205.205z"></path></svg>',
										on: {
											click: function(event) {
												dialog.hide();
											}
										}
									}
								]
							},
							width: 896
						},
						title: A.Lang.String.escapeHTML(title)
					}
				);
			},

			_comments: {},
			_content: {}
		};
		Liferay.WorkflowTasks = WorkflowTasks;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);