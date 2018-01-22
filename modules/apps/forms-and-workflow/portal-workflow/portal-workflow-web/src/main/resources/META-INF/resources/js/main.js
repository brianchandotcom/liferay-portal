AUI.add(
	'liferay-workflow-web',
	function(A) {
		var WorkflowWeb = {
			confirmBeforeDuplicateDialog: function(event, actionUrl, title, randomId, content) {
				var instance = this;

				var form = A.Node.create('<form />');

				form.setAttribute('action', actionUrl);
				form.setAttribute('method', 'POST');

				var titleInput = A.one('#' + randomId + 'titleInputLocalized');

				if (titleInput && !instance._titles[randomId]) {
					instance._titles[randomId] = titleInput;
				}
				else if (!titleInput && instance._titles[randomId]) {
					titleInput = instance._titles[randomId];
				}

				if (titleInput) {
					form.append(titleInput);
					titleInput.show();
				}

				var contentInput = A.one('#' + randomId + 'contentInput');

				if (contentInput && !instance._contents[randomId]) {
					instance._contents[randomId] = contentInput;
				}
				else if (!contentInput && instance._contents[randomId]) {
					contentInput = instance._contents[randomId];
				}

				if (contentInput) {
					form.append(contentInput);
				}

				var dialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: form,
							height: 325,
							toolbars: {
								footer: [
									{
										cssClass: 'btn btn-secondary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												dialog.hide();
											}
										}
									},
									{
										cssClass: 'btn btn-primary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('duplicate'),
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
										labelHTML: '<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>' + Liferay.Language.get('close') + '</title></svg>',
										on: {
											click: function(event) {
												dialog.hide();
											}
										}
									}
								]
							},
							width: 500
						},
						title: title
					}
				);
			},

			openConfirmDeleteDialog: function(title, message, actionUrl) {
				var instance = this;

				var dialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: message,
							destroyOnHide: true,
							height: 200,
							resizable: false,
							toolbars: {
								footer: [
									{
										cssClass: 'btn btn-primary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('delete'),
										on: {
											click: function() {
												window.location.assign(actionUrl);
											}
										}
									},
									{
										cssClass: 'btn btn-secondary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												dialog.destroy();
											}
										}
									}
								],
								header: [
									{
										cssClass: 'close',
										discardDefaultButtonCssClasses: true,
										labelHTML: '<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>' + Liferay.Language.get('close') + '</title></svg>',
										on: {
											click: function(event) {
												dialog.destroy();

												event.domEvent.stopPropagation();
											}
										}
									}
								]
							},
							width: 600
						},
						title: title
					}
				);
			},

			previewBeforeRevertDialog: function(event, renderUrl, actionUrl, title) {
				var instance = this;

				var form = A.Node.create('<form />');

				form.setAttribute('action', actionUrl);
				form.setAttribute('method', 'POST');

				var dialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							destroyOnHide: true,
							modal: true,
							toolbars: {
								footer: [
									{
										cssClass: 'btn btn-secondary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												dialog.destroy();
											}
										}
									},
									{
										cssClass: 'btn btn-primary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('restore'),
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
										labelHTML: '<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>' + Liferay.Language.get('close') + '</title></svg>',
										on: {
											click: function(event) {
												dialog.destroy();

												event.domEvent.stopPropagation();
											}
										}
									}
								]
							}
						},
						title: title,
						uri: renderUrl
					}
				);
			},

			showActionUndoneSuccessMessage: function(namespace) {
				var instance = this;

				var successMessage = Liferay.Language.get('action-undone');

				var alert = instance._alert;

				if (alert) {
					alert.destroy();
				}

				alert = new Liferay.Alert(
					{
						closeable: true,
						delay: {
							hide: 5000,
							show: 0
						},
						message: successMessage,
						type: 'success'
					}
				);

				if (!alert.get('rendered')) {
					alert.render('.portlet-column');
				}

				alert.show();

				instance._alert = alert;
			},

			showDefinitionImportSuccessMessage: function(namespace) {
				var instance = this;

				var undo = Liferay.Language.get('undo');

				var undoEvent = '\'' + namespace + 'undoDefinition\'';

				var undoLink = '<a href="javascript:;" onclick=Liferay.fire(' + undoEvent + '); class="alert-link">' + undo + '</a>';

				var successMessage = Liferay.Language.get('definition-imported-sucessfully');

				successMessage += undoLink;

				var alert = instance._alert;

				if (alert) {
					alert.destroy();
				}

				alert = new Liferay.Alert(
					{
						closeable: true,
						delay: {
							hide: 10000,
							show: 0
						},
						message: successMessage,
						type: 'success'
					}
				);

				if (!alert.get('rendered')) {
					alert.render('.portlet-column');
				}

				alert.show();

				instance._alert = alert;
			},

			_alert: null,
			_contents: {},
			_titles: {}
		};

		Liferay.WorkflowWeb = WorkflowWeb;
	},
	'',
	{
		requires: ['liferay-alert', 'liferay-util-window']
	}
);