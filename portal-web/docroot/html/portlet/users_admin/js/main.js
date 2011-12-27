AUI.add(
	'liferay-users-admin',
	function(A) {
		var Addresses = {
			getCountries: function(callback) {
				Liferay.Service.Portal.Country.getCountries(
					{
						active: true
					},
					callback
				);
			},

			getRegions: function(callback, selectKey) {
				Liferay.Service.Portal.Region.getRegions(
					{
						countryId: Number(selectKey),
						active: true
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