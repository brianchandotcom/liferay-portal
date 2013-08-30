;(function() {
	var ALLOY = YUI();

	if (ALLOY.html5shiv) {
		ALLOY.html5shiv();
	}

	window.AUI = function() {
		return ALLOY;
	};

	ALLOY.mix(AUI, YUI);
})();