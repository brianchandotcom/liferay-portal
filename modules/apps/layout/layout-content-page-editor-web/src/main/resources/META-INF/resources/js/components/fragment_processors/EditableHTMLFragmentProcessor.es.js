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

import {getFloatingToolbarButtons} from './EditableRichTextFragmentProcessor.es';

let _changedCallback = null;
let _destroyedCallback = null;
let _dialog;
let _editableElement;
let _editor;

/**
 * Destroys, if any, an existing instance of LiferayFullScreenSourceEditor.
 */

function destroy() {
	if (_dialog) {
		_editableElement.removeAttribute('style');

		_dialog.destroy();
		_editor.destroy();

		_dialog = null;
		_editableElement = null;
		_editor = null;

		_destroyedCallback();
		_destroyedCallback = null;

		_changedCallback = null;
	}
}

/**
 * Creates an instance of LiferayFullScreenSourceEditor and destroys the existing one if any.
 * @param {HTMLElement} editableElement
 * @param {string} fragmentEntryLinkId
 * @param {string} portletNamespace
 * @param {Object} options
 * @param {function} changedCallback
 * @param {function} destroyedCallback
 */
function init(
	editableElement,
	fragmentEntryLinkId,
	portletNamespace,
	options,
	changedCallback,
	destroyedCallback
) {
	const _destroy = destroy;

	_editableElement = editableElement;

	_changedCallback = changedCallback;
	_destroyedCallback = destroyedCallback;

	Liferay.Util.openWindow(
		{
			dialog: {
				after: {
					destroy() {
						_destroy();
					}
				},
				constrain: true,
				cssClass:
					'lfr-fulscreen-source-editor-dialog modal-full-screen',
				destroyOnHide: true,
				modal: true,
				'toolbars.footer': [
					{
						label: Liferay.Language.get('cancel'),
						on: {
							click() {
								_dialog.hide();
							}
						}
					},
					{
						cssClass: 'btn-primary',
						label: Liferay.Language.get('save'),
						on: {
							click() {
								_dialog.hide();

								_changedCallback(_editor.get('value'));
							}
						}
					}
				]
			},

			title: Liferay.Language.get('edit-content')
		},
		dialog => {
			_dialog = dialog;

			Liferay.Util.getTop()
				.AUI()
				.use('liferay-fullscreen-source-editor', A => {
					_editor = new A.LiferayFullScreenSourceEditor({
						boundingBox: dialog
							.getStdModNode(A.WidgetStdMod.BODY)
							.appendChild('<div></div>'),
						previewCssClass:
							'alloy-editor alloy-editor-placeholder',
						value: editableElement.innerHTML
					}).render();
				});
		}
	);
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @return {string} Transformed content
 */
function render(content, value) {
	return value;
}

export default {
	destroy,
	getFloatingToolbarButtons,
	init,
	render
};
