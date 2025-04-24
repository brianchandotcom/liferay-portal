## BNDLiferayEnterpriseAppCheck

The following rules apply to any `bnd.bnd` file that matches a path name
specified in `source-formatter.properties#enterpriseAppModulePathNames`:

- `Liferay-Enterprise-App` has to be defined
- If the file is located in `/modules/dxp/apps/`, the `Liferay-Enterprise-App`
value should contain `dxp.only=true`
- If the file is **not** located in `/modules/dxp/apps/`, the
`Liferay-Enterprise-App` value should **not** contain `dxp.only`