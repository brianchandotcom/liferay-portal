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

	Util.uncamelize = function(value, separator) {
		separator = separator || ' ';

		value = value.replace(/([a-zA-Z][a-zA-Z])([A-Z])([a-z])/g, '$1' + separator + '$2$3');
		value = value.replace(/([a-z])([A-Z])/g, '$1' + separator + '$2');

		return value;
	};
})(AUI(), Liferay);