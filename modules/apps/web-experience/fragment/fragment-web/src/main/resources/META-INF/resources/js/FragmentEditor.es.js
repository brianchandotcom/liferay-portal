import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FragmentEditor.soy';
import './FragmentPreview.es';
import './SourceEditor.es';

/**
 * Component that allows editing an existing or new Fragment
 * It integrates three <SourceEditor /> components for each part of
 * the fragment and a <FragmentPreview /> component for the preview
 */
class FragmentEditor extends Component {
	/**
	 * @inheritDoc
	 */
	shouldUpdate(changes) {
		return changes._html || changes._js || changes._css;
	}

	/**
	 * Event handler executed when any content is changed
	 * @protected
	 */
	_handleContentChanged() {
		this.emit(
			'contentChanged',
			{
				css: this._css,
				html: this._html,
				js: this._js,
			}
		);
	}

	/**
	 * Callback executed when the css editor changes
	 * @param {Event} event
	 * @protected
	 */
	_handleCSSChanged(event) {
		this._css = event.content;
		this._handleContentChanged();
	}

	/**
	 * Callback executed when the html editor changes
	 * @param {Event} event
	 * @protected
	 */
	_handleHTMLChanged(event) {
		this._html = event.content;
		this._handleContentChanged();
	}

	/**
	 * Callback executed when the js editor changes
	 * @param {Event} event
	 * @protected
	 */
	_handleJSChanged(event) {
		this._js = event.content;
		this._handleContentChanged();
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
FragmentEditor.STATE = {
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
	 * Namespace of the portlet being used.
	 * Necesary for getting the real inputs which interact with the server.
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	namespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @instance
	 * @memberOf FragmentEditor
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Property that contains the updated CSS content of
	 * the editor. This value will be propagated to the preview.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @protected
	 * @type {?string}
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
	 * @protected
	 * @type {?string}
	 */
	_html: Config.string()
		.internal()
		.value(''),

	/**
	 * Property that contains the updated JS content of
	 * the editor. This value will be propagated to the preview.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditor
	 * @protected
	 * @type {?string}
	 */
	_js: Config.string()
		.internal()
		.value(''),
};

Soy.register(FragmentEditor, templates);

export {FragmentEditor};
export default FragmentEditor;