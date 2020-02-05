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

import './ImageEditorLoading.es';

import 'clay-dropdown';
import {PortletBase} from 'frontend-js-web';
import {async, core} from 'metal';
import dom from 'metal-dom';
import Soy from 'metal-soy';

import templates from './ImageEditor.soy';
import ImageEditorHistoryEntry from './ImageEditorHistoryEntry.es';

/**
 * Creates an Image Editor component.
 *
 * <p>
 * This class bootstraps all the necessary parts of an image editor. It only
 * controls the state and history of the editing process, orchestrating how the
 * different parts of the application work.
 * </p>
 *
 * <p>
 * All image processing is delegated to the different image editor capability
 * implementations. The editor provides
 * </p>
 *
 * <ul>
 * <li>
 * A common way of exposing the functionality.
 * </li>
 * <li>
 * Some registration points which can be used by the image editor capability
 * implementors to provide UI controls.
 * </li>
 * </ul>
 */
class ImageEditor extends PortletBase {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.historyIndex_ = 0;

		/**
		 * History of the different image states during editing. Every entry
		 * represents a change to the image on top of the previous one. History
		 * entries are objects with the following attributes:
		 *
		 * <ul>
		 * <li>
		 * url (optional): the URL representing the image.
		 * </li>
		 * <li>
		 * data: the image data object of the image.
		 * </li>
		 * </ul>
		 * @protected
		 * @type {Array.<Object>}
		 */
		this.history_ = [
			new ImageEditorHistoryEntry({
				url: this.image
			})
		];

		// Polyfill svg usage for lexicon icons

		svg4everybody({
			attributeName: 'data-href',
			polyfill: true
		});

		// Load the first entry imageData and render it on the app.

		this.history_[0].getImageData().then(imageData => {
			async.nextTick(() => {
				this.imageEditorReady = true;

				this.syncImageData_(imageData);
			});
		});
	}

	/**
	 * Accepts the current changes applied by the active control and creates
	 * a new entry in the history stack. This wipes out any stale redo states.
	 */
	accept() {
		const selectedControl = this.components[
			this.id + '_selected_control_' + this.selectedControl.variant
		];

		this.history_[this.historyIndex_]
			.getImageData()
			.then(imageData => selectedControl.process(imageData))
			.then(imageData => this.createHistoryEntry_(imageData))
			.then(() => this.syncHistory_())
			.then(() => {
				this.selectedControl = null;
				this.selectedTool = null;
			});
	}

	/**
	 * Notifies the opener app that the user wants to close the editor without
	 * saving the changes.
	 *
	 * @protected
	 */
	close_() {
		Liferay.Util.getWindow().hide();
	}

	/**
	 * Creates a new history entry state.
	 *
	 * @param  {ImageData} imageData The image data of the new image.
	 * @protected
	 */
	createHistoryEntry_(imageData) {
		this.historyIndex_++;
		this.history_.length = this.historyIndex_ + 1;
		this.history_[this.historyIndex_] = new ImageEditorHistoryEntry({
			data: imageData
		});

		return Promise.resolve();
	}

	/**
	 * Discards the current changes applied by the active control and reverts
	 * the image to its state before the control activation.
	 */
	discard() {
		this.selectedControl = null;
		this.selectedTool = null;
		this.syncHistory_();
	}

	/**
	 * Retrieves the editor canvas DOM node.
	 *
	 * @return {Element} The canvas element
	 */
	getImageEditorCanvas() {
		return this.element.querySelector(
			'.lfr-image-editor-image-container canvas'
		);
	}

	/**
	 * Retrieves the blob representation of the current image.
	 *
	 * @return {Promise} A promise that resolves with the image blob.
	 */
	getImageEditorImageBlob() {
		return new Promise(resolve => {
			this.getImageEditorImageData().then(imageData => {
				const canvas = document.createElement('canvas');
				canvas.width = imageData.width;
				canvas.height = imageData.height;

				canvas.getContext('2d').putImageData(imageData, 0, 0);

				if (canvas.toBlob) {
					canvas.toBlob(resolve, this.saveMimeType);
				}
				else {
					const data = atob(
						canvas.toDataURL(this.saveMimeType).split(',')[1]
					);
					const length = data.length;
					const bytes = new Uint8Array(length);

					for (let i = 0; i < length; i++) {
						bytes[i] = data.charCodeAt(i);
					}

					resolve(new Blob([bytes], {type: this.saveMimeType}));
				}
			});
		});
	}

	/**
	 * Retrieves the image data representation of the current image.
	 *
	 * @return {Promise} A promise that resolves with the image data.
	 */
	getImageEditorImageData() {
		return this.history_[this.historyIndex_].getImageData();
	}

	/**
	 * Returns a list of all possible image editor capabilities.
	 *
	 * @return {Array<{Object}>}
	 */
	getPossibleControls() {
		return this.imageEditorCapabilities.tools.reduce(
			(prev, curr) => prev.concat(curr.controls),
			[]
		);
	}

	/**
	 * Normalizes different MIME types to the most similar MIME type available
	 * to canvas implementations.
	 *
	 * @param  {String} mimeType The original MIME type.
	 * @return {String} The normalized MIME type.
	 * @see http://kangax.github.io/jstests/toDataUrl_mime_type_test/
	 */
	normalizeCanvasMimeType_(mimeType) {
		mimeType = mimeType.toLowerCase();

		return mimeType.replace('jpg', 'jpeg');
	}

	/**
	 * Notifies the opener app of the result of the save action.
	 *
	 * @param  {Object} result The server response to the save action.
	 * @protected
	 */
	notifySaveResult_(result) {
		this.components.loading.show = false;

		if (result && result.success) {
			Liferay.Util.getOpener().Liferay.fire(this.saveEventName, {
				data: result
			});

			Liferay.Util.getWindow().hide();
		}
		else if (result.error) {
			this.showError_(result.error.message);
		}
	}

	/**
	 * Updates the image back to a previously undone state in the history.
	 * Redoing an action recovers the undone image changes and enables the
	 * undo stack in case the user wants to undo the changes again.
	 */
	redo() {
		this.historyIndex_++;
		this.syncHistory_();
	}

	/**
	 * Selects a control and starts its editing phase with filters.
	 *
	 * @param  {MouseEvent} event The mouse event.
	 */
	requestImageEditorEditFilters(event) {
		const controls = this.getPossibleControls();

		const target = event.delegateTarget || event.currentTarget;
		const targetControl = target.getAttribute('data-control');
		const targetTool = target.getAttribute('data-tool');

		this.syncHistory_().then(() => {
			this.selectedControl = controls.filter(
				tool => tool.variant === targetControl
			)[0];
			this.selectedTool = targetTool;
		});
	}

	/**
	 * Select a control and starts its editing phase.
	 *
	 * @param {MouseEvent} event The mouse event.
	 */
	requestImageEditorEdit(event) {
		const controls = this.getPossibleControls();

		const target = event.target.element;
		const targetControl = event.data.item.variant;
		const targetTool = target.getAttribute('data-tool');

		this.syncHistory_().then(() => {
			this.selectedControl = controls.filter(
				tool => tool.variant === targetControl
			)[0];
			this.selectedTool = targetTool;
		});
	}

	/**
	 * Queues a request for a preview process of the current image by the
	 * currently selected control.
	 */
	requestImageEditorPreview() {
		const selectedControl = this.components[
			this.id + '_selected_control_' + this.selectedControl.variant
		];

		this.history_[this.historyIndex_]
			.getImageData()
			.then(imageData => selectedControl.preview(imageData))
			.then(imageData => this.syncImageData_(imageData));

		this.components.loading.show = true;
	}

	/**
	 * Discards all changes and restores the original state of the image.
	 * Unlike the {@link ImageEditor#undo|undo} and
	 * {@link ImageEditor#redo|redo} methods, this method wipes out the entire
	 * history.
	 */
	reset() {
		this.historyIndex_ = 0;
		this.history_.length = 1;
		this.syncHistory_();
	}

	/**
	 * Tries to save the current image using the provided save URL.
	 * @param {MouseEvent} event The mouse event that triggers the save action.
	 * @protected
	 */
	save_(event) {
		if (!event.delegateTarget.disabled) {
			this.getImageEditorImageBlob()
				.then(imageBlob => this.submitBlob_(imageBlob))
				.then(result => this.notifySaveResult_(result))
				.catch(error => this.showError_(error));
		}
	}

	/**
	 * Setter function for the <code>saveMimeType</code> state key.
	 * @param  {!String} saveMimeType The optional value for the attribute.
	 * @protected
	 * @return {String} The computed value for the attribute.
	 */
	setterSaveMimeTypeFn_(saveMimeType) {
		if (!saveMimeType) {
			const imageExtensionRegex = /\.(\w+)\/[^?/]+/;
			const imageExtension = this.image.match(imageExtensionRegex)[1];

			saveMimeType = `image/${imageExtension}`;
		}

		return this.normalizeCanvasMimeType_(saveMimeType);
	}

	/**
	 * Displays an error message in the editor.
	 * @param  {String} message The error message to display.
	 * @protected
	 */
	showError_(message) {
		this.components.loading.show = false;

		AUI().use('liferay-alert', () => {
			new Liferay.Alert({
				delay: {
					hide: 2000,
					show: 0
				},
				duration: 3000,
				icon: 'exclamation-circle',
				message: message.message,
				type: 'danger'
			}).render(this.element);
		});
	}

	/**
	 * Sends a given image blob to the server for processing and storing.
	 * @param  {Blob} imageBlob The image blob to send to the server.
	 * @protected
	 * @return {Promise} A promise that follows the XHR submission
	 * process.
	 */
	submitBlob_(imageBlob) {
		const saveFileName = this.saveFileName;
		const saveParamName = this.saveParamName;

		const promise = new Promise((resolve, reject) => {
			const formData = new FormData();

			formData.append(saveParamName, imageBlob, saveFileName);

			this.fetch(this.saveURL, formData)
				.then(response => response.json())
				.then(resolve)
				.catch(error => reject(error));
		});

		this.components.loading.show = true;

		return promise;
	}

	/**
	 * Syncs the image and history values after changes to the history stack.
	 * @protected
	 */
	syncHistory_() {
		return new Promise(resolve => {
			this.history_[this.historyIndex_].getImageData().then(imageData => {
				this.syncImageData_(imageData);

				this.history = {
					canRedo: this.historyIndex_ < this.history_.length - 1,
					canReset: this.history_.length > 1,
					canUndo: this.historyIndex_ > 0
				};

				resolve();
			});
		});
	}

	/**
	 * Updates the image data displayed in the editable area.
	 * @param  {ImageData} imageData The new image data value to display in the
	 * editor.
	 * @protected
	 */
	syncImageData_(imageData) {
		const width = imageData.width;
		const height = imageData.height;

		const aspectRatio = width / height;

		const offscreenCanvas = document.createElement('canvas');
		offscreenCanvas.width = width;
		offscreenCanvas.height = height;

		const offscreenContext = offscreenCanvas.getContext('2d');
		offscreenContext.clearRect(0, 0, width, height);
		offscreenContext.putImageData(imageData, 0, 0);

		const canvas = this.getImageEditorCanvas();

		const boundingBox = dom.closest(this.element, '.portlet-layout');
		const availableWidth = boundingBox.offsetWidth;

		let dialogFooterHeight = 0;
		const dialogFooter = this.element.querySelector('.dialog-footer');

		if (dialogFooter) {
			dialogFooterHeight = dialogFooter.offsetHeight;
		}

		const availableHeight =
			boundingBox.offsetHeight - 142 - 40 - dialogFooterHeight;
		const availableAspectRatio = availableWidth / availableHeight;

		if (availableAspectRatio > 1) {
			canvas.height = availableHeight;
			canvas.width = aspectRatio * availableHeight;
		}
		else {
			canvas.width = availableWidth;
			canvas.height = availableWidth / aspectRatio;
		}

		const context = canvas.getContext('2d');
		context.clearRect(0, 0, canvas.width, canvas.height);
		context.drawImage(
			offscreenCanvas,
			0,
			0,
			width,
			height,
			0,
			0,
			canvas.width,
			canvas.height
		);

		canvas.style.width = canvas.width + 'px';
		canvas.style.height = canvas.height + 'px';

		this.components.loading.show = false;
	}

	/**
	 * Reverts the image to the previous state in the history. Undoing an action
	 * brings back the previous version of the image and enables the redo stack
	 * in case the user wants to reapply the change again.
	 */
	undo() {
		this.historyIndex_--;
		this.syncHistory_();
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
ImageEditor.STATE = {
	/**
	 * Whether the editor is ready for user interaction.
	 * @type {Object}
	 */
	imageEditorReady: {
		validator: core.isBoolean,
		value: false
	},

	/**
	 * Event to dispatch when editing is complete.
	 * @type {String}
	 */
	saveEventName: {
		validator: core.isString
	},

	/**
	 * Name of the saved image to send to the server for the save action.
	 * @type {String}
	 */
	saveFileName: {
		validator: core.isString
	},

	/**
	 * MIME type of the saved image. If not explicitly set, the image MIME type
	 * is inferred from the image URL.
	 * @type {String}
	 */
	saveMimeType: {
		setter: 'setterSaveMimeTypeFn_',
		validator: core.isString
	},

	/**
	 * Name of the param that specifies where to send the image to the server
	 * for the save action.
	 * @type {String}
	 */
	saveParamName: {
		validator: core.isString
	},

	/**
	 * URL to save the image changes.
	 * @type {String}
	 */
	saveURL: {
		validator: core.isString
	}
};

Soy.register(ImageEditor, templates);

export default ImageEditor;
