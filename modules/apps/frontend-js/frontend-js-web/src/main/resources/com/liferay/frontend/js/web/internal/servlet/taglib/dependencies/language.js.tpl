Language: {
	_cache:
		window?.Liferay?.Language?._cache
			? Liferay.Language._cache
			: {},
	available: {
[$AVAILABLE$]
	},
	direction: {
[$DIRECTION$]
	},
	get:
		(key) => {
			let value = Liferay.Language._cache[key];

			if (value === undefined) {
				value = key;
			}

			return value;
		},
},