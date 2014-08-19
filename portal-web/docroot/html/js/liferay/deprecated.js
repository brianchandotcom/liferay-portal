// For details about this file see: LPS-2155

;(function(A, Liferay) {
	var Util = Liferay.namespace('Util');

	var Lang = A.Lang;

	var AArray = A.Array;
	var AObject = A.Object;
	var AString = A.Lang.String;

	var htmlEscapedValues = [];
	var htmlUnescapedValues = [];

	var MAP_HTML_CHARS_ESCAPED = {
		'&': '&amp;',
		'<': '&lt;',
		'>': '&gt;',
		'"': '&#034;',
		'\'': '&#039;',
		'/': '&#047;',
		'`': '&#096;'
	};

	var MAP_HTML_CHARS_UNESCAPED = {};

	AObject.each(
		MAP_HTML_CHARS_ESCAPED,
		function(item, index) {
			MAP_HTML_CHARS_UNESCAPED[item] = index;

			htmlEscapedValues.push(item);
			htmlUnescapedValues.push(index);
		}
	);

	var STR_LEFT_SQUARE_BRACKET = '[';

	var STR_RIGHT_SQUARE_BRACKET = ']';

	var REGEX_DASH = /-([a-z])/gi;

	var REGEX_HTML_ESCAPE = new RegExp(STR_LEFT_SQUARE_BRACKET + htmlUnescapedValues.join('') + STR_RIGHT_SQUARE_BRACKET, 'g');

	var REGEX_HTML_UNESCAPE = new RegExp(htmlEscapedValues.join('|'), 'gi');

	Util.MAP_HTML_CHARS_ESCAPED = MAP_HTML_CHARS_ESCAPED;

	Util.actsAsAspect = function(object) {
		object.yield = null;
		object.rv = {};

		object.before = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				f.apply(this, arguments);

				return original.apply(this, arguments);
			};
		};

		object.after = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				this.rv[method] = original.apply(this, arguments);

				return f.apply(this, arguments);
			};
		};

		object.around = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				this.yield = original;

				return f.apply(this, arguments);
			};
		};
	};

	Util.camelize = function(value, separator) {
		var regex = REGEX_DASH;

		if (separator) {
			regex = new RegExp(separator + '([a-z])', 'gi');
		}

		value = value.replace(
			regex,
			function(match0, match1) {
				return match1.toUpperCase();
			}
		);

		return value;
	};

	Util.clamp = function(value, min, max) {
		return Math.min(Math.max(value, min), max);
	};

	Util.escapeHTML = function(str, preventDoubleEscape, entities) {
		var result;

		var regex = REGEX_HTML_ESCAPE;

		var entitiesList = [];

		var entitiesValues;

		if (Lang.isObject(entities)) {
			entitiesValues = [];

			AObject.each(
				entities,
				function(item, index) {
					entitiesList.push(index);

					entitiesValues.push(item);
				}
			);

			regex = new RegExp(STR_LEFT_SQUARE_BRACKET + AString.escapeRegEx(entitiesList.join('')) + STR_RIGHT_SQUARE_BRACKET, 'g');
		}
		else {
			entities = MAP_HTML_CHARS_ESCAPED;

			entitiesValues = htmlEscapedValues;
		}

		return str.replace(regex, A.bind('_escapeHTML', Util, !!preventDoubleEscape, entities, entitiesValues));
	};

	Util.isEditorPresent = function(editorImpl) {
		return Liferay.EDITORS && Liferay.EDITORS[editorImpl];
	};

	Util.randomMinMax = function(min, max) {
		return (Math.round(Math.random() * (max - min))) + min;
	};

	Util.textareaTabs = function(event) {
		var el = event.currentTarget.getDOM();
		var pressedKey = event.keyCode;

		if (event.isKey('TAB')) {
			event.halt();

			var oldscroll = el.scrollTop;

			if (el.setSelectionRange) {
				var caretPos = el.selectionStart + 1;
				var elValue = el.value;

				el.value = elValue.substring(0, el.selectionStart) + '\t' + elValue.substring(el.selectionEnd, elValue.length);

				setTimeout(
					function() {
						el.focus();
						el.setSelectionRange(caretPos, caretPos);
					},
					0
				);

			}
			else {
				document.selection.createRange().text = '\t';
			}

			el.scrollTop = oldscroll;

			return false;
		}
	};

	Util.uncamelize = function(value, separator) {
		separator = separator || ' ';

		value = value.replace(/([a-zA-Z][a-zA-Z])([A-Z])([a-z])/g, '$1' + separator + '$2$3');
		value = value.replace(/([a-z])([A-Z])/g, '$1' + separator + '$2');

		return value;
	};

	Util.unescapeHTML = function(str, entities) {
		var regex = REGEX_HTML_UNESCAPE;

		var entitiesMap = MAP_HTML_CHARS_UNESCAPED;

		if (entities) {
			var entitiesValues = [];

			entitiesMap = {};

			AObject.each(
				entities,
				function(item, index) {
					entitiesMap[item] = index;

					entitiesValues.push(item);
				}
			);

			regex = new RegExp(entitiesValues.join('|'), 'gi');
		}

		return str.replace(regex, A.bind('_unescapeHTML', Util, entitiesMap));
	};

	Util._escapeHTML = function(preventDoubleEscape, entities, entitiesValues, match) {
		var result;

		if (preventDoubleEscape) {
			var arrayArgs = AArray(arguments);

			var length = arrayArgs.length;

			var string = arrayArgs[length - 1];
			var offset = arrayArgs[length - 2];

			var nextSemicolonIndex = string.indexOf(';', offset);

			if (nextSemicolonIndex >= 0) {
				var entity = string.substring(offset, nextSemicolonIndex + 1);

				if (AArray.indexOf(entitiesValues, entity) >= 0) {
					result = match;
				}
			}
		}

		if (!result) {
			result = entities[match];
		}

		return result;
	};

	Util._unescapeHTML = function(entities, match) {
		return entities[match];
	};

	Liferay.provide(
		Util,
		'disableTextareaTabs',
		function(textarea) {
			textarea = A.one(textarea);

			if (textarea && textarea.attr('textareatabs') != 'enabled') {
				textarea.attr('textareatabs', 'disabled');

				textarea.detach('keydown', Util.textareaTabs);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'enableTextareaTabs',
		function(textarea) {
			textarea = A.one(textarea);

			if (textarea && textarea.attr('textareatabs') != 'enabled') {
				textarea.attr('textareatabs', 'disabled');

				textarea.on('keydown', Util.textareaTabs);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'switchEditor',
		function(options) {
			var uri = options.uri;

			var windowName = Liferay.Util.getWindowName();

			var dialog = Liferay.Util.getWindow(windowName);

			if (dialog) {
				dialog.iframe.set('uri', uri);
			}
		},
		['aui-io']
	);
})(AUI(), Liferay);