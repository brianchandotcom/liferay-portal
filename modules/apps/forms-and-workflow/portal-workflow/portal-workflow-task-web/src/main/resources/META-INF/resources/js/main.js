AUI.add(
	'liferay-workflow-tasks',
	function(A) {
		var WorkflowTasks = {
			moveFormDataFromDialog: function(form) {
				var children = form.get('children');

				var entryActionColumn;
				var updatedComments;
				var updatedContent;

				if (form && form.hasChildNodes() && children.size() >= 2) {
					updatedContent = children.item(0);
					updatedComments = children.item(1);
				}

				if (updatedContent) {
					var contentId = updatedContent.attr('id');

					var originalColumnId = contentId.substring(0, 4);

					if (contentId.search('[a-zA-Z]{4}updateDueDate') != -1) {
						originalColumnId += 'updateAsignee';
					}

					if (originalColumnId) {
						var originalColumnNode = A.one('#' + originalColumnId);

						if (originalColumnNode) {
							entryActionColumn = originalColumnNode.get('parentNode');

							entryActionColumn.append(updatedContent);

							updatedContent.attr('hidden', true);
						}
					}
				}

				if (updatedComments && entryActionColumn) {
					entryActionColumn.append(updatedComments);

					updatedComments.attr('hidden', true);
				}
			},

			onTaskClick: function(event, randomId) {
				var instance = this;

				var icon = event.currentTarget;
				var li = icon.get('parentNode');

				event.preventDefault();

				var content = null;

				var height = 310;

				if (li.hasClass('task-due-date-link')) {
					content = '#' + randomId + 'updateDueDate';

					height = 410;
				}

				var title = icon.text();

				WorkflowTasks.showPopup(icon.attr('href'), A.one(content), title, randomId, height);
			},

			showPopup: function(url, content, title, randomId, height) {
				var instance = this;

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
							destroyOnHide: true,
							height: height,
							on: {
								destroy: function() {
									instance.moveFormDataFromDialog(form);
								}
							},
							toolbars: {
								footer: [
									{
										cssClass: 'btn-lg btn-primary',
										label: Liferay.Language.get('done'),
										on: {
											click: function() {
												submitForm(form);
											}
										}
									},
									{
										cssClass: 'btn-cancel btn-lg btn-link',
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
										labelHTML: '<span> \u00D7 </span>',
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
			}
		};
		Liferay.WorkflowTasks = WorkflowTasks;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);