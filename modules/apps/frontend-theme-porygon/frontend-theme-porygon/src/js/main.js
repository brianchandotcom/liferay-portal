(function() {
	AUI().ready(
		'liferay-sign-in-modal',
		function(A) {
			var signIn = A.one('.sign-in > a');

			if (signIn && signIn.getData('redirect') !== 'true') {
				signIn.plug(Liferay.SignInModal);
			}
		}
	);

	require(
		'metal/src/async/async',
		'metal/src/core',
		'metal-dom/src/dom',
		'metal-state/src/State',
		'porygon-theme/js/top_search.es',
		function(async, core, dom, State, TopSearch) {
			new TopSearch.default();
		}
	);
})();