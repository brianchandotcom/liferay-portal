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

import {PortletBase, debounce} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FragmentPreview.soy';

/**
 * Defined ratios for preview sizing.
 *
 * @type {Object}
 */
const SIZE_RATIO = {
	desktop: {
		height: 9,
		width: 16
	},
	'mobile-portrait': {
		height: 16,
		width: 10
	},
	'tablet-portrait': {
		height: 3,
		width: 4
	}
};

/**
 * Available preview sizes.
 *
 * @type {Array<string>}
 */
const PREVIEW_SIZES = Object.keys(SIZE_RATIO);

/**
 * Renders the preview of a fragment and allows modifications to the preview.
 */
class FragmentPreview extends PortletBase {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._updatePreview = debounce(this._updatePreview.bind(this), 500);

		this._updatePreviewSize = debounce(
			this._updatePreviewSize.bind(this),
			100
		);

		window.addEventListener('resize', this._updatePreviewSize);

		this.on('configurationChanged', this._updatePreview);
		this.on('cssChanged', this._updatePreview);
		this.on('htmlChanged', this._updatePreview);
		this.on('jsChanged', this._updatePreview);

		if (this.refs.previewFrame && this.refs.previewFrame.contentWindow) {
			this.refs.previewFrame.contentWindow.addEventListener(
				'click',
				this._handleIframeClick,
				true
			);
		}
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		window.removeEventListener('resize', this._updatePreviewSize);

		this.off('configurationChanged', this._updatePreview);
		this.off('cssChanged', this._updatePreview);
		this.off('htmlChanged', this._updatePreview);
		this.off('jsChanged', this._updatePreview);

		if (this.refs.previewFrame && this.refs.previewFrame.contentWindow) {
			this.refs.previewFrame.contentWindow.postMessage(
				JSON.stringify({data: ''}),
				'*'
			);
			this.refs.previewFrame.contentWindow.removeEventListener(
				'click',
				this._handleIframeClick
			);
		}
	}

	/**
	 * @inheritDoc
	 */
	shouldUpdate(changes) {
		return !!changes._currentPreviewSize;
	}

	/**
	 * Handle iframe clicks, preventing any click event to be executed
	 * @param {Event} event
	 * @review
	 */
	_handleIframeClick(event) {
		event.preventDefault();
		event.stopPropagation();
	}

	/**
	 * Callback executed when the preview frame content is loaded.
	 *
	 * @private
	 */
	_handlePreviewLoaded() {
		this._updatePreview();
	}

	/**
	 * Changes the preview size.
	 *
	 * @param {Event} event
	 * @protected
	 */
	_handlePreviewSizeButtonClick(event) {
		this._currentPreviewSize =
			event.delegateTarget.dataset.previewSize || null;
	}

	/**
	 * Sets the preview size property and queues an update.
	 *
	 * @param {string} previewSize
	 * @protected
	 * @return {string}
	 */
	_setPreviewSize(previewSize) {
		this._updatePreviewSize();

		return previewSize;
	}

	/**
	 * Updates the rendered preview with the given content. It encapsulates the
	 * given code inside a frame and renders it.
	 *
	 * @protected
	 */
	_updatePreview() {
		if (!this._loading) {
			this._loading = true;

			this.fetch(this.urls.render, {
				configuration: this.configuration,
				css: this.css,
				html: this.html,
				js: this.js
			})
				.then(response => response.text())
				.then(response => {
					this._loading = false;
					this.refs.previewFrame.contentWindow.postMessage(
						JSON.stringify({data: response}),
						'*'
					);
				});
		}
	}

	/**
	 * Updates the preview size using the corresponding ratio.
	 *
	 * @protected
	 */
	_updatePreviewSize() {
		const preview = this.refs.preview;

		if (preview) {
			if (this._currentPreviewSize) {
				const ratio = SIZE_RATIO[this._currentPreviewSize];
				const wrapper = this.refs.wrapper;

				if (ratio && wrapper) {
					const wrapperRect = wrapper.getBoundingClientRect();

					const scale = Math.min(
						(wrapperRect.width * 0.9) / ratio.width,
						(wrapperRect.height * 0.8) / ratio.height
					);

					preview.style.width = `${ratio.width * scale}px`;
					preview.style.height = `${ratio.height * scale}px`;
				}
			}
			else {
				preview.style.width = '';
				preview.style.height = '';
			}
		}
	}
}

/**
 * State definition.
 *
 * @type {!Object}
 * @static
 */
FragmentPreview.STATE = {
	/**
	 * Ratio of the preview being rendered. This property is modified internally
	 * with the UI buttons presented to the user, but it can be safely altered
	 * externally.
	 *
	 * @default 'full'
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {?string}
	 */
	_currentPreviewSize: Config.oneOf(PREVIEW_SIZES)
		.internal()
		.value(null)
		.setter('_setPreviewSize'),

	/**
	 * Flag that checks if the preview content is loading.
	 *
	 * @default false
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {boolean}
	 */
	_loading: Config.bool()
		.internal()
		.value(false),

	/**
	 * List of available sizes.
	 *
	 * @default PREVIEW_SIZES
	 * @instance
	 * @memberOf FragmentPreview
	 * @protected
	 * @type {?Array<string>}
	 */
	_previewSizes: Config.array()
		.internal()
		.value(PREVIEW_SIZES),

	/**
	 * Configuration content of the preview.
	 *
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	configuration: Config.string().required(),

	/**
	 * CSS content of the preview.
	 *
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	css: Config.string().required(),

	/**
	 * HTML content of the preview.
	 *
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	html: Config.string().required(),

	/**
	 * JS content of the preview.
	 *
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {!string}
	 */
	js: Config.string().required(),

	/**
	 * Path of the available icons.
	 *
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * URLs used for communicating with the back-end logic.
	 *
	 * @instance
	 * @memberOf FragmentPreview
	 * @type {{
	 *  render: !string
	 * }}
	 */
	urls: Config.shapeOf({
		render: Config.string().required()
	}).required()
};

Soy.register(FragmentPreview, templates);

export {FragmentPreview};
export default FragmentPreview;
