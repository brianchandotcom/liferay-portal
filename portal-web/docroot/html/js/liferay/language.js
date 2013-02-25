Liferay.Language = {
	get: function(key, extraParams) {
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

		var value = instance._cache[url];

		if (!value) {
			AUI().use('io-base').io(
				url,
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

			instance._cache[url] = value;
		}

		return value;
	},

	_cache: {}
};