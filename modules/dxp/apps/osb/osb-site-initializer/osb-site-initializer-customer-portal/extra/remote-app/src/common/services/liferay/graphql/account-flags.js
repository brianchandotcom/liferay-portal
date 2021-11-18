const getAccountFlagByFilter = (filter) => {
	let filters = '';

	if (filter) {
		const filterKeys = Object.keys(filter);

		filterKeys.forEach((key) => {
			let value = '';

			if (key === 'value') {
				value = filter[key];
			}
			else {
				value = `'${filter[key]}'`;
			}

			filters += filters
				? `${filters ? ' and ' : ''}${key} eq ${value}`
				: `${key} eq ${value}`;
		});

		if (filters) {
			return `c {
                    accountFlags(filter: "${filters}") {
                      items {
                        accountKey
                        name
                        userUuid
                      }
                    }
                  }`;
		}
	}
};

export {getAccountFlagByFilter};
