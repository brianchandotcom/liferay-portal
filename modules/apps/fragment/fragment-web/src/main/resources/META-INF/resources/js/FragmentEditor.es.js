import Soy from 'metal-soy';
import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import templates from './FragmentEditor.soy';
import './FragmentPreview.es';
import './SourceEditor.es';

/**
 * Component that allows editing an existing or new Fragment
 * It integrates three <SourceEditor /> components for each part of
 * the fragment and a <FragmentPreview /> component for the preview
 * @review
 */

class FragmentEditor extends PortletBase {

	/**
	 * @inheritDoc
	 * @review
	 */

	shouldUpdate(changes) {
		return changes._html ||
			changes._js ||
			changes._css ||
			changes._saving;
	}

	/**
	 * Returns content
	 * @public
	 * @return {{
	 *   css: string,
	 *   html: string,
	 *   js: string,
	 * }}
	 * @review
	 */

	getContent() {
		return ({
			css: this._css,
			html: this._html,
			js: this._js
		});
	};

	/**
	 * Returns true when HTML content is valid, false otherwise.
	 * @public
	 * @review
	 */

	isHtmlValid() {
		return this._htmlValid;
	}

	/**
	 * Event handler executed when any content is changed
	 * @private
	 * @review
	 */

	_handleContentChanged() {
		this.emit(
			'contentChanged',
			this.getContent()
		);
	}

	/**
	 * Callback executed when the css editor changes
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleCSSChanged(event) {
		this._css = event.content;
		this._handleContentChanged();
	}

	/**
	 * Callback executed when the html editor changes
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleHTMLChanged(event) {
		this._html = event.content;
		this._htmlValid = event.valid;

		this._handleContentChanged();
	}

	/**
	 * Callback executed when the js editor changes
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleJSChanged(event) {
		this._js = event.content;
		this._handleContentChanged();
	}

	/**
	 * Save the fragment content
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleSaveButtonClick(event) {
		const content = this.getContent();
		const status = event.delegateTarget.value;

		this._saving = true;

		this.fetch(
			this.urls.edit,
			{
				cssContent: content.css,
				fragmentCollectionId: this.fragmentCollectionId,
				fragmentEntryId: this.fragmentEntryId,
				htmlContent: content.html,
				jsContent: content.js,
				name: this.name,
				status
			}
		)
			.then(
				response => response.json()
			)
			.then(
				response => {
					const redirectURL = (
						response.redirect ||
						this.urls.redirect
					);

					if (Liferay.SPA) {
						Liferay.SPA.app.navigate(redirectURL);
					}
					else {
						location.href = redirectURL;
					}
				}
			)
			.catch (
				() => {
					this._saving = false;
				}
			);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentEditor.STATE = {

	/**
	 * Fragment collection id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {!string}
	 */

	fragmentCollectionId: Config.string().required(),

	/**
	 * Fragment entry id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {!string}
	 */

	fragmentEntryId: Config.string().required(),

	/**
	 * Fragment name
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {!string}
	 */

	name: Config.string().required(),

	/**
	 * URLs used for communicating with backend
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {{
	 *  edit: !string,
	 *	redirect: !string
	 * }}
	 */

	urls: Config.shapeOf(
		{
			edit: Config.string().required(),
			redirect: Config.string().required()
		}
	).required(),

	/**
	 * Property that contains the updated CSS content of
	 * the editor. This value will be propagated to the preview.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @review
	 * @type {string}
	 */

	_css: Config.string()
		.internal()
		.value(''),

	/**
	 * Property that contains the updated HTML content of
	 * the editor. This value will be propagated to the preview.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @review
	 * @type {string}
	 */

	_html: Config.string()
		.internal()
		.value(''),

	/**
	 * Property that contains the flag if updated HTML content of
	 * the editor is valid.
	 * @default true
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @review
	 * @type {boolean}
	 */

	_htmlValid: Config.bool()
		.internal()
		.value(true),

	/**
	 * Property that contains the updated JS content of
	 * the editor. This value will be propagated to the preview.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @review
	 * @type {string}
	 */

	_js: Config.string()
		.internal()
		.value(''),

	/**
	 * If true, the fragment is being saved
	 * @default false
	 * @instance
	 * @memberOf FragmentEditor
	 * @private
	 * @review
	 * @type {bool}
	 */

	_saving: Config.bool()
		.internal()
		.value(false)
};

Soy.register(FragmentEditor, templates);

export {FragmentEditor};
export default FragmentEditor;