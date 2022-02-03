## BNDLiferayRelengBundleCheck

The following rule applies to any `app.bnd` file that matches a path name
specified in `source-formatter.properties#allowedLiferayRelengBundleNames`:

DXP modules (modules located in `/modules/dxp/apps`) with
`Liferay-Releng-Bundle: true` in `app.bnd` should always have a
.lfrbuild-release-src` file.