const getKoroneikiAccountsByFilter = (filter) => {
	if (filter) {
		let filters = '';

		if (filter.accountKeys) {
			const totalKeys = filter.accountKeys.length;

			if (totalKeys) {
				filter.accountKeys.forEach((key, index) => {
					filters += `accountKey eq '${key}'${
						index + 1 < totalKeys ? ' or ' : ''
					}`;
				});
			}
		}

		if (filters.length > 0) {
			return `c {
                koroneikiAccounts(filter: "${filters}") {
                  items {
                    accountKey
                    code
                    slaCurrent
                    slaExpired
                    slaFuture
                    slaCurrentEndDate
                    region
                    liferayContactName
                    liferayContactRole
                    liferayContactEmailAddress
                  }
                }
              }`;
		}
	}
};

export {getKoroneikiAccountsByFilter};
