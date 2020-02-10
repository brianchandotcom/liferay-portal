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

import {PortletBase, openToast} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FragmentEditor.soy';

import './FragmentPreview.es';

import './SourceEditor.es';

/**
 * Creates a Fragment Editor component that lets you create a new fragment or
 * edit an existing fragment. This component integrates three
 * <code>&lt;SourceEditor /></code> components for each part of the fragment and
 * a <code>&lt;FragmentPreview /></code> component for the preview.
 */
class FragmentEditor extends PortletBase {
	/**
	 * @inheritDoc
	 */
	shouldUpdate(changes) {
		return (
			changes.cacheable ||
			changes._html ||
			changes._js ||
			changes._configuration ||
			changes._css ||
			changes._saving
		);
	}

	/**
	 * Returns content for the fragment.
	 *
	 * @public
	 * @return {{
	 *   configuration: string,
	 *   css: string,
	 *   html: string,
	 *   js: string
	 * }}
	 */
	getContent() {
		return {
			cacheable: this.cacheable,
			configuration: this._configuration,
			css: this._css,
			html: this._html,
			js: this._js
		};
	}

	/**
	 * Returns <code>true</code> when HTML content is valid.
	 *
	 * @public
	 * @return {boolean} <code>true</code> when HTML is valid; <code>false</code>
	 * otherwise.
	 */
	isHtmlValid() {
		return this._htmlValid;
	}

	/**
	 * Callback executed when the Cacheable property changes.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleCacheableChanged(event) {
		this.cacheable = event.delegateTarget.checked;

		this._handleContentChanged();
	}

	/**
	 * Callback that propagates the <code>contentChanged</code> event when
	 * content is modified.
	 *
	 * @private
	 */
	_handleContentChanged() {
		this.emit('contentChanged', this.getContent());
	}

	/**
	 * Callback executed when the Configuration editor changes.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleConfigurationChanged(event) {
		this._configuration = event.content;
		this._handleContentChanged();
	}

	/**
	 * Callback executed when the CSS editor changes.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleCSSChanged(event) {
		this._css = event.content;
		this._handleContentChanged();
	}

	/**
	 * Callback executed when the HTML editor changes.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleHTMLChanged(event) {
		this._html = event.content;
		this._htmlValid = event.valid;

		this._handleContentChanged();
	}

	/**
	 * Callback executed when the JS editor changes.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleJSChanged(event) {
		this._js = event.content;
		this._handleContentChanged();
	}

	/**
	 * Saves the fragment content when the Save button is clicked.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleSaveButtonClick(event) {
		const content = this.getContent();
		const status = event.delegateTarget.value;

		if (this.isHtmlValid()) {
			this._saving = true;

			this.fetch(this.urls.edit, {
				cacheable: content.cacheable,
				configurationContent: content.configuration,
				cssContent: content.css,
				fragmentCollectionId: this.fragmentCollectionId,
				fragmentEntryId: this.fragmentEntryId,
				htmlContent: content.html,
				jsContent: content.js,
				name: this.name,
				status
			})
				.then(response => response.json())
				.then(response => {
					if (response.error) {
						throw response.error;
					}

					return response;
				})
				.then(response => {
					const redirectURL = response.redirect || this.urls.redirect;

					Liferay.Util.navigate(redirectURL);
				})
				.catch(error => {
					this._saving = false;

					const message =
						typeof error === 'string'
							? error
							: Liferay.Language.get('error');

					openToast({
						message,
						title: Liferay.Language.get('error'),
						type: 'danger'
					});
				});
		}
		else {
			alert(Liferay.Language.get('fragment-html-is-invalid'));
		}
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
FragmentEditor.STATE = {
	/**
	 * Updated Configuration content of the editor. This value is propagated to the
	 * preview pane.
	 *
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @type {string}
	 */
	_configuration: Config.string()
		.internal()
		.value(''),

	/**
	 * Updated CSS content of the editor. This value is propagated to the
	 * preview pane.
	 *
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @type {string}
	 */
	_css: Config.string()
		.internal()
		.value(''),

	/**
	 * Updated HTML content of the editor. This value is propagated to the
	 * preview pane.
	 *
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @type {string}
	 */
	_html: Config.string()
		.internal()
		.value(''),

	/**
	 * Flag to specify if the editor's updated HTML content is valid.
	 *
	 * @default true
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @type {boolean}
	 */
	_htmlValid: Config.bool()
		.internal()
		.value(true),

	/**
	 * Updated JS content of the editor. This value is propagated to the preview
	 * pane.
	 *
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @type {string}
	 */
	_js: Config.string()
		.internal()
		.value(''),

	/**
	 * If <code>true</code>, the fragment is saved.
	 *
	 * @default false
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @type {bool}
	 */
	_saving: Config.bool()
		.internal()
		.value(false),

	/**
	 * List of tags for custom autocompletion in the HTML editor.
	 *
	 * @default []
	 * @instance
	 * @memberOf FragmentEditor
	 * @type Array
	 */
	autocompleteTags: Config.arrayOf(
		Config.shapeOf({
			content: Config.string(),
			name: Config.string()
		})
	),

	/**
	 * Cacheable property of the fragment.
	 *
	 * @default false
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {boolean}
	 */
	cacheable: Config.bool()
		.internal()
		.value(false),

	/**
	 * Fragment collection ID.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	fragmentCollectionId: Config.string().required(),

	/**
	 * Fragment entry ID.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	fragmentEntryId: Config.string().required(),

	/**
	 * Fragment name.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	name: Config.string().required(),

	/**
	 * URLs used for communicating with back-end logic.
	 *
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {{
	 *  edit: !string,
	 *	redirect: !string
	 * }}
	 */
	urls: Config.shapeOf({
		edit: Config.string().required(),
		redirect: Config.string().required()
	}).required()
};

Soy.register(FragmentEditor, templates);

export {FragmentEditor};
export default FragmentEditor;
