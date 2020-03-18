## BNDBundleCheck

The `Liferay-Releng-Restart-Required` can only be set to `false` if a POSHI tests exists.

### Example

For `apps/portal-search-learning-to-rank/app.bnd` where `Liferay-Releng-Restart-Required` is set to `false`

Only if there is `property hot.deploy.osgi.app.includes = "portal-search-learning-to-rank";` in `*.testcase` in `https://github.com/liferay/liferay-portal/blob/master/portal-web/test/functional/com/liferay/portalweb/tests/`

```
property hot.deploy.osgi.app.includes = "portal-search-learning-to-rank";
```