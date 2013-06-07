Liferay.Address = {
	getCountries: function(callback) {
		Liferay.Service(
			'/country/get-countries',
			{
				active: true
			},
			callback
		);
	},

	getRegions: function(callback, selectKey) {
		Liferay.Service(
			'/region/get-regions',
			{
				countryId: Number(selectKey),
				active: true
			},
			callback
		);
	}
};