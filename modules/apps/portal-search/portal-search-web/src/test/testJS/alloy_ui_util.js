var withAlloyUI = function(testCase, dependencies) {
	return function(done) {
		AUI().use(
			['aui-base', 'aui-node'].concat(dependencies || []),
			function(A) {
				testCase(done, A)
			}
		);
	};
};

Liferay.namespace('Test').withAlloyUI = withAlloyUI;