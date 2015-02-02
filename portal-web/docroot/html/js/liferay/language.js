;(function(A, Liferay) {
	var Language = {};

	Language.get = function(key) {
		return key;
	};

	A.use(
		'io-base',
		function(A) {
			Language.get = A.cached(
				function(key, extraParams) {
					var instance = this;

					var url = themeDisplay.getPathContext() + '/language/' + themeDisplay.getLanguageId() + '/' + key + '/';

					if (extraParams) {
						if (typeof extraParams == 'string') {
							url += extraParams;
						}
						else if (A.Lang.isArray(extraParams)) {
							url += extraParams.join('/');
						}
					}

					var headers = {
						"X-CSRF-Token": Liferay.authToken
					};

					var value = '';

					A.io(
						url,
						{
							headers: headers,
							method: 'GET',
							on: {
								complete: function(i, o) {
									value = o.responseText;
								}
							},
							sync: true
						}
					);

					return value;
				}
			);
		}
	);

	Liferay.Language = Language;
})(AUI(), Liferay);