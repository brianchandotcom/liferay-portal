## CompanyIterationCheck

It is required to use one of the following methods when iterating companies:
- `com.liferay.portal.kernel.service.CompanyLocalService#forEachCompany`
- `com.liferay.portal.kernel.service.CompanyLocalService#forEachCompanyId`

This way, we process companies in the correct order and ensure that the thread
locals are initialized properly.

This applies to SQL queries as well.

#### Example 1

Instead of:
```java
List<Company> companies = _companyLocalService.getCompanies();

for (Company company : companies) {
	_commerceAccountGroupLocalService.
		checkGuestCommerceAccountGroup(company.getCompanyId());
}
```

We should do:
```java
_companyLocalService.forEachCompanyId(
	companyId ->
		_commerceAccountGroupLocalService.
			checkGuestCommerceAccountGroup(companyId));
```
#### Example 2

Instead of:
```java
public void cleanUp(String... companyIds) {
	for (long companyId : companyIds) {
		_cleanUp(companyId);
	}
}
```

We should do:
```java
public void cleanUp(String... companyIds) {
	_companyLocalService.forEachCompanyId(
		companyId -> _cleanUp(companyId), companyIds);
}
```

#### Example 3

Instead of:
```java
PreparedStatement preparedStatement = connection.prepareStatement(
	"select companyId, userId from Company");

ResultSet resultSet = preparedStatement.executeQuery();

while (resultSet.next()) {
	processCompany(resultSet.getLong("companyId"), resultSet.getLong("userId"));
}
```

We should do:
```java
_companyLocalService.forEachCompany(
	company -> processCompany(company.getCompanyId(), company.getUserId()));
```