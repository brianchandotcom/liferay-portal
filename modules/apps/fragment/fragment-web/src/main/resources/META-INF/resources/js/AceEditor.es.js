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

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './AceEditor.soy';

/**
 * @param  {...any} args
 */
const FragmentAutocompleteProcessor = function(...args) {
	FragmentAutocompleteProcessor.superclass.constructor.apply(this, args);
};

const MATCH_TAG = 'tag';
const MATCH_TAGLIB = 'taglib';
const MATCH_VARIABLE = 'variable';

/**
 * Creates an Ace Editor component to use for code editing.
 */
class AceEditor extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this._editorDocument = null;
		this._editorSession = null;

		this._getAutocompleteSuggestion = this._getAutocompleteSuggestion.bind(
			this
		);

		this._getAutocompleteResults = this._getAutocompleteResults.bind(this);

		this._handleDocumentChanged = this._handleDocumentChanged.bind(this);

		AUI().use(
			'aui-ace-editor',
			'aui-ace-autocomplete-plugin',
			'aui-ace-autocomplete-templateprocessor',

			A => {
				this._editor = new A.AceEditor({
					boundingBox: this.refs.wrapper,
					highlightActiveLine: false,
					mode: this.syntax,
					tabSize: 2
				});

				this._editor.set('readOnly', this.readOnly);

				this._editorDocument = this._editor.getSession().getDocument();
				this._editorSession = this._editor.getSession();

				this._overrideSetAnnotations(this._editorSession);

				this.refs.wrapper.style.height = '';
				this.refs.wrapper.style.width = '';

				this._editorDocument.on('change', this._handleDocumentChanged);

				this._editorSession.on(
					'changeAnnotation',
					this._handleDocumentChanged
				);

				if (this.initialContent) {
					this._editorDocument.setValue(this.initialContent);
				}

				this._initAutocomplete(A, this._editor);
			}
		);
	}

	/**
	 * @inheritDoc
	 */
	disposed() {
		if (this._editor) {
			this._editor.destroy();
		}
	}

	/**
	 * @inheritDoc
	 */
	shouldUpdate() {
		return false;
	}

	/**
	 * @param {object} A
	 * @param {object} editor
	 * @private
	 * @review
	 */
	_initAutocomplete(A, editor) {
		if (this.syntax !== 'html') {
			return;
		}

		A.extend(
			FragmentAutocompleteProcessor,
			A.AceEditor.TemplateProcessor,

			{
				getMatch: this._getAutocompleteMatch,
				getResults: this._getAutocompleteResults,
				getSuggestion: this._getAutocompleteSuggestion
			}
		);

		const autocompleteProcessor = new FragmentAutocompleteProcessor({});

		editor.plug(A.Plugin.AceAutoComplete, {
			processor: autocompleteProcessor,
			render: true,
			visible: false,
			zIndex: 10000
		});
	}

	/**
	 * Returns a match object (if any) for <code>lfr-</code> tags inside the
	 * given content.
	 *
	 * @param {string} content The given content.
	 * @private
	 * @return {object} The matching result.
	 */
	_getAutocompleteMatch(content) {
		let match = null;
		let matchContent = content;

		if (
			matchContent.lastIndexOf('<') >= 0 ||
			matchContent.lastIndexOf('${') >= 0 ||
			matchContent.lastIndexOf('[@') >= 0
		) {
			matchContent = matchContent.trim();

			if (/<lfr[\w]*[^<lfr]*$/.test(matchContent)) {
				match = {
					content: matchContent.substring(1),
					type: MATCH_TAG
				};
			}
			else if (/\[@[^\]]+$/.test(matchContent)) {
				match = {
					content: matchContent.substring(2),
					type: MATCH_TAGLIB
				};
			}
			else if (/\${[^}]+$/.test(matchContent)) {
				match = {
					content: matchContent.substring(2),
					type: MATCH_VARIABLE
				};
			}
		}

		return match;
	}

	/**
	 * Returns a list of available auto-complete suggestions for the given
	 * match.
	 *
	 *
	 * @param {object} match The match.
	 * @param {function} callbackSuccess Success callback.
	 * @param {function} callbackError Errir callback.
	 * @private
	 * @return {array} The list of available suggestions.
	 */
	_getAutocompleteResults(match, callbackSuccess, callbackError) {
		let matchDirectives = null;

		const regex = new RegExp(match.content || '', 'gi');

		if (match.type === MATCH_TAG) {
			matchDirectives = this.autocompleteTags.map(tag => tag.name);
		}
		else if (match.type === MATCH_TAGLIB) {
			matchDirectives = this.freeMarkerTaglibs;
		}
		else if (match.type === MATCH_VARIABLE) {
			matchDirectives = this.freeMarkerVariables;
		}
		else {
			callbackError();
		}

		if (matchDirectives) {
			matchDirectives = matchDirectives.filter(directive =>
				regex.test(directive)
			);

			callbackSuccess(matchDirectives);
		}
	}

	/**
	 * Returns a tag completion suggestion for the given match and selected
	 * suggestion.
	 *
	 * @param {object} match The match.
	 * @param {string} selectedSuggestion The selected suggestion.
	 * @private
	 * @return {string} The suggested tag autocompletion.
	 */
	_getAutocompleteSuggestion(match, selectedSuggestion) {
		let result = this.autocompleteTags.find(
			_tag => _tag.name === selectedSuggestion
		);

		if (result) {
			result = result.content.substring(1);
		}

		if (!result) {
			result = [
				...this.freeMarkerTaglibs,
				...this.freeMarkerVariables
			].find(variable => variable === selectedSuggestion);
		}

		return result || '';
	}

	/**
	 * Callback executed when the internal Ace Editor is modified; this
	 * propagates the <code>contentChanged</code> event.
	 *
	 * @private
	 */
	_handleDocumentChanged() {
		const valid = this._editorSession
			.getAnnotations()
			.reduce((acc, annotation) => {
				return !acc || annotation.type === 'error' ? false : acc;
			}, true);

		this.emit('contentChanged', {
			content: this._editorDocument.getValue(),
			valid
		});
	}

	/**
	 * Overrides Ace Editor's session <code>setAnnotations</code> method to avoid
	 * showing misleading messages.
	 *
	 * @param {Object} session AceEditor session
	 * @private
	 */
	_overrideSetAnnotations(session) {
		const setAnnotations = session.setAnnotations.bind(session);

		session.setAnnotations = () => {
			setAnnotations([]);
		};
	}
}

/**
 * Available Ace Editor syntax.
 *
 * @static
 * @type {Object}
 */
AceEditor.SYNTAX = {
	css: 'css',
	html: 'html',
	javascript: 'javascript',
	json: 'json'
};

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
AceEditor.STATE = {
	/**
	 * Ace editor plugin instance
	 * @default null
	 * @instance
	 * @memberof AceEditor
	 * @type object
	 */
	_editor: Config.object()
		.internal()
		.value(null),

	/**
	 * Ace editor plugin document instance
	 * @default null
	 * @instance
	 * @memberof AceEditor
	 * @type object
	 */
	_editorDocument: Config.object()
		.internal()
		.value(null),

	/**
	 * Ace editor plugin session instance
	 * @default null
	 * @instance
	 * @memberof AceEditor
	 * @type object
	 */
	_editorSession: Config.object()
		.internal()
		.value(null),

	/**
	 * List of tags for custom autocompletion in the HTML editor.
	 *
	 * @default []
	 * @instance
	 * @memberOf AceEditor
	 * @type Array
	 */
	autocompleteTags: Config.arrayOf(
		Config.shapeOf({
			attributes: Config.arrayOf(Config.string()),
			name: Config.string()
		})
	),

	/**
	 * List of FreeMarker tags for custom autocompletion in the HTML editor.
	 *
	 * @default []
	 * @instance
	 * @memberOf AceEditor
	 * @type Array
	 */
	freeMarkerTaglibs: Config.arrayOf(Config.string()),

	/**
	 * List of FreeMarker variables for custom autocompletion in the HTML
	 * editor.
	 *
	 * @default []
	 * @instance
	 * @memberOf AceEditor
	 * @type Array
	 */
	freeMarkerVariables: Config.arrayOf(Config.string()),

	/**
	 * Initial content sent to the editor.
	 *
	 * @default ''
	 * @instance
	 * @memberOf AceEditor
	 * @type {string}
	 */
	initialContent: Config.string().value(''),

	/**
	 * Sets the editor in readOnly mode preventing any input from the user.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf AceEditor
	 * @type {boolean}
	 */
	readOnly: Config.bool().required(),

	/**
	 * Syntax used for the Ace Editor that is rendered on the interface.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf AceEditor
	 * @see {@link AceEditor.SYNTAX|SYNTAX}
	 * @type {!string}
	 */
	syntax: Config.oneOf(Object.values(AceEditor.SYNTAX)).required()
};

Soy.register(AceEditor, templates);

export {AceEditor};
export default AceEditor;
