## BNDImportsCheck

Wildcards are not allowed in 'bnd' files

### Example

Incorrect:

```
Export-Package:\
    com.liferay.account.*,\
    com.liferay.account.service.persistence
```

Correct:

```
Export-Package:\
    com.liferay.account.constants,\
    com.liferay.account.exception,\
    com.liferay.account.model,\
    com.liferay.account.retriever,\
    com.liferay.account.role,\
    com.liferay.account.service,\
    com.liferay.account.service.persistence
```