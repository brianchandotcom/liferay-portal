// For details about this file see: LPS-2155

;(function(A, Liferay) {
	var Util = Liferay.namespace('Util');

	var REGEX_DASH = /-([a-z])/gi;

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