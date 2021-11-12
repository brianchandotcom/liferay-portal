const getAccountFlagByFilter = (filter) => {
	let filters = '';

	if (filter) {
		const filterKeys = Object.keys(filter);

		for (const key in filterKeys) {
			const value = filter[key];

			filters += filters
				? `${filters ? ' and ' : ''}${key} eq '${value}'`
				: `${key} eq '${value}'`;
		}

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
