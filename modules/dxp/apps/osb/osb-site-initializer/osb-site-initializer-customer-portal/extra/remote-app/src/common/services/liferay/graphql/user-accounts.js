const getUserAccountById = (id) => {
  return {
    query: `{
            userAccount(userAccountId: ${id}) {
                id
                name
                image
                accountBriefs {
                  id
                  name
                  externalReferenceCode
                }
              }
        }`
  };
}

export { getUserAccountById };