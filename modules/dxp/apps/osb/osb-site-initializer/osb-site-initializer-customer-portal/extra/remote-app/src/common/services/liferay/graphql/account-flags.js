const getAccountFlagByFilter = (filter) => {
    if (filter) {
        let filters = "";

        if (filter.accountKey) {
            filters += `accountKey eq '${filter.accountKey}'`;
        }

        if (filter.userUuid) {
            filters += `${filters.length > 0 ? " and " : ""}userUuid eq '${filter.userUuid}'`;
        }

        if (filter.value) {
            filters += `${filters.length > 0 ? " and " : ""}value eq ${filter.value}`;
        }

        if (filter.name) {
            filters += `${filters.length > 0 ? " and " : ""}name eq '${filter.name}'`;
        }

        if (filters.length > 0) {
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
}

export { getAccountFlagByFilter }