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

				if (li.hasClass('task-due-date-link')) {
					content = '#' + randomId + 'updateDueDate';
				}
				else if (li.hasClass('task-assign-to-me-link')) {
					content = '#' + randomId + 'updateAsigneeToMe';
				}
				else if (li.hasClass('task-assign-link')) {
					content = '#' + randomId + 'updateAsignee';
				}

				title = icon.text();

				WorkflowTasks.showPopup(icon.attr('href'), A.one(content), title, randomId);
			},

			showPopup: function(url, content, title, randomId) {
				var form = A.Node.create('<form />');

				form.setAttribute('action', url);
				form.setAttribute('method', 'POST');

				var comments = A.one('#' + randomId + 'updateComments');

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
							toolbars: {
								footer: [
									{
										label: Liferay.Language.get('ok'),
										on: {
											click: function() {
												submitForm(form);
											}
										}
									},
									{
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												this.close();
											}
										}
									}
								]
							}
						},
						title: title
					}
				);
			}
		};

		Liferay.WorkflowTasks = WorkflowTasks;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);