;(function() {
	var ALLOY = YUI();

	if (ALLOY.html5shiv) {
		ALLOY.html5shiv();
	}

	var originalUse = ALLOY.use;

	ALLOY.use = function() {
		var args = Array.prototype.slice.call(arguments, 0);

		var currentURL = Liferay.currentURL;

		var originalCallback = args[args.length - 1];

		if (typeof originalCallback === 'function') {
			args[args.length - 1] = function() {
				if (Liferay.currentURL === currentURL) {
					originalCallback.apply(this, arguments);
				}
			};
		}

		return originalUse.apply(this, args);
	};

	window.AUI = function() {
		return ALLOY;
	};

	ALLOY.mix(AUI, YUI);

	AUI.$ = window.jQuery;
	AUI._ = window._;
})();