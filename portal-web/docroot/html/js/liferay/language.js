;(function(A, Liferay) {
	var Language = {};

	Language.get = function(key){
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
						else if (Liferay.Util.isArray(extraParams)) {
							url += extraParams.join('/');
						}
					}

					var authUrl = url;

					var authToken = Liferay.authToken;

					if (authToken) {
						authUrl = Liferay.Util.addParams('p_auth=' + authToken, url);
					}

					A.io(
						authUrl,
						{
							on: {
								complete: function(i, o) {
									value = o.responseText;
								}
							},
							sync: true,
							type: 'GET'
						}
					);

					return value;
				}
			);
		}
	);

	Liferay.Language = Language;
})(AUI(), Liferay);