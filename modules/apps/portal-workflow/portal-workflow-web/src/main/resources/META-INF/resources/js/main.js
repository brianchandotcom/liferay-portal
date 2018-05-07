AUI.add(
	'liferay-workflow-web',
	function(A) {
		var WorkflowWeb = {
			confirmBeforeDuplicateDialog: function(event, actionUrl, title, randomId, portletNamespace) {
				var instance = this;

				var form = A.one('#' + portletNamespace + randomId + 'form');

				if (form && !instance._forms[randomId]) {
					instance._forms[randomId] = form;
				}
				else if (!form && instance._forms[randomId]) {
					form = instance._forms[randomId];
				}

				if (form) {
					form.setAttribute('action', actionUrl);
					form.setAttribute('method', 'POST');
				}

				var duplicationDialog = instance._duplicationDialog;

				if (duplicationDialog) {
					duplicationDialog.destroy();
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
												if (form) {
													form.reset();
												}

												dialog.destroy();
											}
										}
									},
									{
										cssClass: 'btn btn-primary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('duplicate'),
										on: {
											click: function() {
												if (form) {
													submitForm(form);
												}

												dialog.hide();
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
												if (form) {
													form.reset();
												}

												dialog.destroy();
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

				instance._duplicationDialog = dialog;
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
												window.location.assign(actionUrl);
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

			saveWorkflowDefinitionLink: function(event, namespace) {
				var instance = this;

				var formContainer = document.getElementById(namespace + 'formContainer');

				var form = formContainer.querySelector('.form');

				submitForm(form);
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

			toggleDefinitionLinkEditionMode: function(event, namespace) {
				var instance = this;

				var saveCancelGroup = document.getElementById(namespace + 'saveCancelGroup');

				var editbutton = document.getElementById(namespace + 'editButton');

				var formContainer = document.getElementById(namespace + 'formContainer');

				var definitionLabel = document.getElementById(namespace + 'definitionLabel');

				instance._toggleElementVisibility(saveCancelGroup, editbutton, formContainer, definitionLabel);

				var formGroup = formContainer.querySelector('.form-group');

				if (formGroup) {
					formGroup.classList.remove('form-group');
				}
			},

			_toggleElementVisibility: function() {
				var instance = this;

				for (var index in arguments) {

					var element = arguments[parseInt(index, 10)];

					var hidden = element.getAttribute('hidden');

					if (hidden) {
						element.removeAttribute('hidden');
					}
					else {
						element.setAttribute('hidden', true);
					}
				}
			},

			_alert: null,
			_duplicationDialog: null,
			_forms: {}
		};

		Liferay.WorkflowWeb = WorkflowWeb;
	},
	'',
	{
		requires: ['liferay-alert', 'liferay-util-window']
	}
);