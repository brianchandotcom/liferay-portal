;(function() {
	var ALLOY = YUI();

	if (ALLOY.UA.ie && ALLOY.UA.ie < 9) {
		ALLOY.html5shiv();
	}

	window.AUI = function() {
		return ALLOY;
	};

	ALLOY.mix(AUI, YUI);
})();