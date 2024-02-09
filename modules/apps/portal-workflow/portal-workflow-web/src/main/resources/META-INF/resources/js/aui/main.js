/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-workflow-web',
	(A) => {
		const WorkflowWeb = {
			_doToggleDefinitionLinkEditionMode(namespace) {
				const instance = this;

				instance._toggleElementVisibility(namespace);

				instance._switchEditMode(namespace);

				instance._removeFormGroupClass(namespace);
			},

			_duplicationDialog: null,

			_forms: {},

			_getClickedButtonName(event, namespace) {
				const button = event.target;

				const buttonId = button.get('id');

				const buttonType = buttonId.replace(namespace, '');

				return buttonType;
			},

			_getDefinitionLinkNodeNamespace(definitionLinkNode) {
				const definitionLinkNodeInput = definitionLinkNode.one(
					'input[name$=namespace]'
				);

				const definitionLinkNamespace = definitionLinkNodeInput.val();

				return definitionLinkNamespace;
			},

			_getElementsByIds() {
				const elements = [];

				let element;

				for (const index in arguments) {
					element = document.getElementById(arguments[index]);

					if (element) {
						elements.push(element);
					}
				}

				return elements;
			},

			_getOpenDefinitionLinkNode() {
				const listEditMode = A.all('input[name$=editMode][value=true]');

				let definitionLink;

				if (listEditMode.size() === 1) {
					const node = listEditMode.item(0);

					definitionLink = node.ancestor('.workflow-definition-form');
				}

				return definitionLink;
			},

			_hasDefinitionLinkChanged(definitionLinkNode) {
				const select = definitionLinkNode.one('select');

				const currentValue = select.val();

				const workflowAssignedValue = definitionLinkNode.one(
					'input[name$=workflowAssignedValue]'
				);

				const savedValue = workflowAssignedValue.val();

				let changed = false;

				if (currentValue !== savedValue) {
					changed = true;
				}

				return changed;
			},

			_removeFormGroupClass(namespace) {
				const formContainer = document.getElementById(
					namespace + 'formContainer'
				);

				const formGroup = formContainer.querySelector('.form-group');

				if (formGroup) {
					formGroup.classList.remove('form-group');
				}
			},

			_resetLastValue(namespace) {
				const formContainerNode = A.one(
					'#' + namespace + 'formContainer'
				);

				const workflowAssignedValueNode = formContainerNode.one(
					'input[name$=workflowAssignedValue]'
				);

				const selectNode = formContainerNode.one('select');

				selectNode.val(workflowAssignedValueNode.val());
			},

			_switchEditMode(namespace) {
				const formContainerNode = A.one(
					'#' + namespace + 'formContainer'
				);

				const inputEditModeNode = formContainerNode.one(
					'input[name$=editMode]'
				);

				const editMode = inputEditModeNode.val();

				const boolEditMode = editMode === 'true' || editMode === true;

				inputEditModeNode.val(!boolEditMode);
			},

			_toggleElementVisibility(namespace) {
				const instance = this;

				const saveCancelGroupId = namespace + 'saveCancelGroup';

				const editButtonId = namespace + 'editButton';

				const formContainerId = namespace + 'formContainer';

				const definitionLabelId = namespace + 'definitionLabel';

				const elementsList = instance._getElementsByIds(
					saveCancelGroupId,
					editButtonId,
					formContainerId,
					definitionLabelId
				);

				for (const index in elementsList) {
					const element = elementsList[parseInt(index, 10)];

					if (element.classList.contains('d-none')) {
						element.classList.remove('d-none');
					}
					else {
						element.classList.add('d-none');
					}
				}
			},

			confirmBeforeDuplicateDialog(
				_event,
				actionUrl,
				title,
				randomId,
				portletNamespace
			) {
				const instance = this;

				let form = A.one('#' + portletNamespace + randomId + 'form');

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

				const duplicationDialog = instance._duplicationDialog;

				if (duplicationDialog) {
					duplicationDialog.destroy();
				}

				const dialog = Liferay.Util.Window.getWindow({
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
										click() {
											if (form) {
												form.reset();
											}

											dialog.destroy();
										},
									},
								},
								{
									cssClass: 'btn btn-primary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('duplicate'),
									on: {
										click() {
											if (form) {
												submitForm(form);
											}

											dialog.hide();
										},
									},
								},
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon" focusable="false"><use href="' +
										Liferay.Icons.spritemap +
										'#times" /><title>' +
										Liferay.Language.get('close') +
										'</title></svg>',
									on: {
										click() {
											if (form) {
												form.reset();
											}

											dialog.destroy();
										},
									},
								},
							],
						},
						width: 500,
					},
					title,
				});

				instance._duplicationDialog = dialog;
			},

			openConfirmDeleteDialog(title, message, actionUrl) {
				const dialog = Liferay.Util.Window.getWindow({
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
										click() {
											window.location.assign(actionUrl);
										},
									},
								},
								{
									cssClass: 'btn btn-secondary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('cancel'),
									on: {
										click() {
											dialog.destroy();
										},
									},
								},
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon" focusable="false"><use href="' +
										Liferay.Icons.spritemap +
										'#times" /><title>' +
										Liferay.Language.get('close') +
										'</title></svg>',
									on: {
										click(event) {
											dialog.destroy();

											event.domEvent.stopPropagation();
										},
									},
								},
							],
						},
						width: 600,
					},
					title,
				});
			},

			previewBeforeRevertDialog(event, renderUrl, actionUrl, title) {
				const dialog = Liferay.Util.Window.getWindow({
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
										click() {
											dialog.destroy();
										},
									},
								},
								{
									cssClass: 'btn btn-primary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('restore'),
									on: {
										click() {
											window.location.assign(actionUrl);
										},
									},
								},
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon" focusable="false"><use href="' +
										Liferay.Icons.spritemap +
										'#times" /><title>' +
										Liferay.Language.get('close') +
										'</title></svg>',
									on: {
										click(event) {
											dialog.destroy();

											event.domEvent.stopPropagation();
										},
									},
								},
							],
						},
					},
					title,
					uri: renderUrl,
				});
			},

			saveWorkflowDefinitionLink(event, namespace) {
				const formContainer = document.getElementById(
					namespace + 'formContainer'
				);

				const form = formContainer.querySelector('.form');

				submitForm(form);
			},

			showActionUndoneSuccessMessage() {
				const successMessage = Liferay.Language.get('action-undone');

				Liferay.Util.openToast({
					container: document.querySelector('.portlet-column'),
					message: successMessage,
					type: 'success',
				});
			},

			showDefinitionImportSuccessMessage(namespace) {
				const undo = Liferay.Language.get('undo');

				const undoEvent = "'" + namespace + "undoDefinition'";

				const undoLink =
					'<a href="javascript:void(0);" onclick=Liferay.fire(' +
					undoEvent +
					'); class="alert-link">' +
					undo +
					'</a>';

				let successMessage =
					Liferay.Language.get('definition-imported-successfully') +
					'&nbsp;';

				successMessage += undoLink;

				Liferay.Util.openToast({
					container: document.querySelector('.lfr-alert-container'),
					message: successMessage,
				});
			},

			toggleDefinitionLinkEditionMode(event, namespace) {
				const instance = this;

				const buttonName = instance._getClickedButtonName(
					event,
					namespace
				);

				const openDefinitionLinkNode = instance._getOpenDefinitionLinkNode();

				let openDefinitionLinkNamespace;

				if (buttonName === 'cancelButton') {
					instance._doToggleDefinitionLinkEditionMode(namespace);

					instance._resetLastValue(namespace);
				}
				else if (!openDefinitionLinkNode) {
					instance._doToggleDefinitionLinkEditionMode(namespace);
				}
				else if (
					!instance._hasDefinitionLinkChanged(openDefinitionLinkNode)
				) {
					openDefinitionLinkNamespace = instance._getDefinitionLinkNodeNamespace(
						openDefinitionLinkNode
					);

					instance._doToggleDefinitionLinkEditionMode(
						openDefinitionLinkNamespace
					);

					instance._doToggleDefinitionLinkEditionMode(namespace);
				}
				else {
					openDefinitionLinkNamespace = instance._getDefinitionLinkNodeNamespace(
						openDefinitionLinkNode
					);

					Liferay.Util.openConfirmModal({
						message: Liferay.Language.get(
							'you-have-unsaved-changes-do-you-want-to-proceed-without-saving'
						),
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								instance._doToggleDefinitionLinkEditionMode(
									openDefinitionLinkNamespace
								);

								instance._resetLastValue(
									openDefinitionLinkNamespace
								);

								instance._doToggleDefinitionLinkEditionMode(
									namespace
								);
							}
						},
					});
				}
			},
		};

		Liferay.WorkflowWeb = WorkflowWeb;
	},
	'',
	{
		requires: ['liferay-util-window'],
	}
);
