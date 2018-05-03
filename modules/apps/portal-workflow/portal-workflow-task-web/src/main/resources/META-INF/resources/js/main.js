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

				WorkflowTasks._showPopup(icon.attr('href'), A.one(content), title, randomId, height);
			},

			_showPopup: function(url, content, title, randomId, height) {
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
				else if (!content && title && title.trim().indexOf('Update Due Date') !== -1) {
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
										cssClass: 'btn-primary mr-2',
										label: Liferay.Language.get('done'),
										on: {
											click: function() {
												submitForm(form);
											}
										}
									},
									{
										cssClass: 'btn-cancel',
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												dialog.hide();
											}
										}
									}
								],
								header: [
									{
										cssClass: 'close',
										discardDefaultButtonCssClasses: true,
										labelHTML: '<span aria-hidden="true">&times;</span>',
										on: {
											click: function(event) {
												dialog.hide();
											}
										}
									}
								]
							},
							width: 720
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