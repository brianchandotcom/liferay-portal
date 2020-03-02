## BNDSuiteCheck

Depreceated apps that are not published on Marketplace should be moved to the
`archived` folder.

This applies to modules, where the `app.bnd` contains the following:
```
Liferay-Releng-Deprecated: true
Liferay-Releng-Marketplace: false
```

---

If the value of property `Liferay-Releng-Suite` is not blank, it should be one
of the following value:

`collaboration`, `forms-and-workflow`, `foundation`, `static`, or
`web-experience`