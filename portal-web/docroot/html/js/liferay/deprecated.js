// For details about this file see: LPS-2155

Liferay.namespace('Util');

Liferay.Util.actsAsAspect = function(object) {
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

Liferay.Util.clamp = function(value, min, max) {
	return Math.min(Math.max(value, min), max);
};