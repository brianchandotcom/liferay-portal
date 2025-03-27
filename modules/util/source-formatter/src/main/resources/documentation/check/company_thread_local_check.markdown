## CompanyThreadLocalCheck

Do not use `CompanyThreadLocal.setCompanyId`, use `CompanyThreadLocal.setCompanyIdWithSafeCloseable` instead.

### Examples

Incorrect:

```java
public void run(HttpSession httpSession) throws ActionException {

...

    long companyId = CompanyThreadLocal.getCompanyId();

    CompanyThreadLocal.setCompanyId(userCompanyId);

    try {
        _run(httpSession);
    } finally {
        CompanyThreadLocal.setCompanyId(companyId);
    }

...

}
```

Correct:

```java
public void run(HttpSession httpSession) throws ActionException {

...

    try (SafeCloseable safeCloseable =
            CompanyThreadLocal.setCompanyIdWithSafeCloseable(userCompanyId)) {

        _run(httpSession);
    }

...

}

```