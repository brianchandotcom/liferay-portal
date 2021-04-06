## CompanyIterationCheck

It is required to use one of the following methods:
`com.liferay.portal.kernel.service.CompanyLocalService#forEachCompany`
`com.liferay.portal.kernel.service.CompanyLocalService#forEachCompanyId`

When iterating companies. In that way, we ensure that the thread locals are initialized properly.

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
	}
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