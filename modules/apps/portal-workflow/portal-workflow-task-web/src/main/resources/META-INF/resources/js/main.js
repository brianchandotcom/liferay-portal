/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-workflow-tasks',
	A => {
		var WorkflowTasks = {
			_comments: {},
			_content: {},
			_forms: {},

			_showPopup(form, height, title) {
				var dialog = Liferay.Util.Window.getWindow({
					dialog: {
						bodyContent: form,
						destroyOnHide: true,
						height,
						resizable: false,
						toolbars: {
							footer: [
								{
									cssClass:
										'btn btn-secondary task-action-button',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('cancel'),
									on: {
										click() {
											if (form) {
												form.reset();
											}

											dialog.destroy();
										}
									}
								},
								{
									cssClass:
										'btn btn-primary task-action-button',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('done'),
									on: {
										click() {
											if (form) {
												var hasErrors = false;

												var liferayForm = Liferay.Form.get(
													form.attr('id')
												);

												if (liferayForm) {
													var validator =
														liferayForm.formValidator;

													if (validator) {
														validator.validate();

														hasErrors = validator.hasErrors();
													}
												}

												if (!hasErrors) {
													submitForm(form);

													dialog.hide();
												}
											}
										}
									}
								}
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon lexicon-icon-times" focusable="false" role="presentation" viewBox="0 0 512 512"><path class="lexicon-icon-outline" d="M295.781 256l205.205-205.205c10.998-10.998 10.998-28.814 0-39.781-10.998-10.998-28.815-10.998-39.781 0l-205.205 205.205-205.205-205.238c-10.966-10.998-28.814-10.998-39.781 0-10.998 10.998-10.998 28.814 0 39.781l205.205 205.238-205.205 205.205c-10.998 10.998-10.998 28.815 0 39.781 5.467 5.531 12.671 8.265 19.874 8.265s14.407-2.734 19.907-8.233l205.205-205.238 205.205 205.205c5.5 5.5 12.703 8.233 19.906 8.233s14.407-2.734 19.906-8.233c10.998-10.998 10.998-28.815 0-39.781l-205.238-205.205z"></path></svg>',
									on: {
										click() {
											if (form) {
												form.reset();
											}

											dialog.destroy();
										}
									}
								}
							]
						},
						width: 896
					},
					title: A.Lang.String.escapeHTML(title)
				});
			},

			onTaskClick(event, randomId) {
				var instance = this;

				event.preventDefault();

				var icon = event.currentTarget;

				var form = A.Node.create('<form />');

				form.setAttribute('action', icon.attr('href'));
				form.setAttribute('method', 'POST');

				var comments = A.one('#' + randomId + 'updateComments');

				if (comments && !instance._comments[randomId]) {
					instance._comments[randomId] = comments;
				}
				else if (!comments && instance._comments[randomId]) {
					comments = instance._comments[randomId];
				}

				if (comments) {
					form.append(comments);
					comments.show();
				}

				WorkflowTasks._showPopup(form, 400, icon.text());
			}
		};
		Liferay.WorkflowTasks = WorkflowTasks;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);
