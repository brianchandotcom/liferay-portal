const getAccountRolesByUserAccountId = (id) => {
    return `accountAccountRoles(accountId: ${id}) {
        items {
          id
          name
        }
      }`;
}

export { getAccountRolesByUserAccountId };