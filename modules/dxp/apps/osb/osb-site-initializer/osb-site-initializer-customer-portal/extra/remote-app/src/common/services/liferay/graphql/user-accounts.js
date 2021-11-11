const getUserAccountById = (id) => {
	return `userAccount(userAccountId: ${id}) {
                id
                name
                image
                accountBriefs {
                  id
                  name
                  externalReferenceCode
                }
                roleBriefs {
                  id
                  name
                }
              }`;
};

export {getUserAccountById};
