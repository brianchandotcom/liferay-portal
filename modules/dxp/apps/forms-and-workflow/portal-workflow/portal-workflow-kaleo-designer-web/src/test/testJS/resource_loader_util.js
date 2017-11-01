AUI().use(
	'aui-io',
	function(A) {
		Liferay.Test = Liferay.Test || {};

		var loadResource = function(resource) {
			return new Promise(
				function(resolve, reject) {
					A.io.request(
						'/base/src/test/resources/' + resource,
						{
							dataType: 'text',
							on: {
								failure: function(err) {
									reject(err);
								},
								success: function() {
									resolve(this.get('responseData'));
								}
							}
						}
					);
				}
			);
		};

		Liferay.Test.loadResource = loadResource;
	}
);