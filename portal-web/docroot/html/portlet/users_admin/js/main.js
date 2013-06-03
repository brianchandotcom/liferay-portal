AUI.add(
	'liferay-users-admin',
	function(A) {
		var Addresses = {
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
						active: true,
						countryId: Number(selectKey)
					},
					callback
				);
			}
		};

		Liferay.UsersAdmin = {
			Addresses: Addresses
		};
	}
);