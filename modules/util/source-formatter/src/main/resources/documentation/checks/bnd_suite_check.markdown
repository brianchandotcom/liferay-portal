## BNDSuiteCheck

Deprecated apps that are not published on Marketplace should be moved to the
`archived` folder.

This applies to modules, where the `app.bnd` contains the following:
```
Liferay-Releng-Deprecated: true
Liferay-Releng-Marketplace: false
```

---

If the value of property `Liferay-Releng-Suite` is not blank, it should be one
of the values specified in

`source-formatter.properties#source.check.BNDSuiteCheck.allowedLiferayRelengSuiteNames`