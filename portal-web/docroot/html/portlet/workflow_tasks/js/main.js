AUI().add(
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

				var dialog = new A.Dialog(
					{
						bodyContent: form,
						buttons: [
							{
								handler: function() {
									submitForm(form);
								},
								label: Liferay.Language.get('ok')
							},
							{
								handler: function() {
									this.close();
								},
								label: Liferay.Language.get('cancel')
							}
						],
						centered: true,
						modal: true,
						title: title,
						width: 400
					}
				).render();
			}
		};

		Liferay.WorkflowTasks = WorkflowTasks;
	},
	'',
	{
		requires: ['aui-dialog']
	}
);