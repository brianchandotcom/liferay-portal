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
		return changes._html || changes._js || changes._css;
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
		const status = event.delegateTarget.value;
		const content = this.getContent();

		this.fetch(
			this.editFragmentEntryURL, 
			Object.assign(
				this.fragmentEntryData, 
				{
					status,
					cssContent: content.css,
					htmlContent: content.html,
					jsContent: content.js
				}
			)
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
	 * Url for save draft or publish
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	editFragmentEntryURL: Config.string().required(),

	/**
	 * Default FragmentEditor params for save draft or publish
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {{
	 *  fragmentEntryId: !string,
	 *  fragmentCollectionId: !string,
	 *  fragmentEntryId: !string,
	 *  redirect: !string,
	 *  name: !string
	 * }}
	 */

	fragmentEntryData: Config.shapeOf({
		fragmentCollectionId: Config.string().required(),
		fragmentEntryId: Config.string().required(),
		redirect: Config.string().required(),
		name: Config.string().required()
	}).required(),

	/**
	 * Initial HTML sent to the editor
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	initialHTML: Config.string().required(),

	/**
	 * Initial CSS sent to the editor
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	initialCSS: Config.string().required(),

	/**
	 * Initial JS sent to the editor
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */

	initialJS: Config.string().required(),

	/**
	 * Preview fragment entry URL
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {!string}
	 */

	previewFragmentEntryURL: Config.string().required(),

	/**
	 * Render fragment entry URL
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {!string}
	 */

	renderFragmentEntryURL: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditor
	 * @review
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

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
		.value('')
};

Soy.register(FragmentEditor, templates);

export {FragmentEditor};
export default FragmentEditor;