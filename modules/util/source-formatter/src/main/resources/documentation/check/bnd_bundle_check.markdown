## BNDBundleCheck

The `Liferay-Releng-Restart-Required` can only be set to `false` if a POSHI
tests exists.

### Example

If a `*.testcase` file exists in directory
`https://github.com/liferay/liferay-portal/blob/master/portal-web/test/functional/com/liferay/portalweb/tests/`
containing:
```
property hot.deploy.osgi.app.includes = "portal-search-learning-to-rank";
```
Then the value of `Liferay-Releng-Restart-Required` in
`apps/portal-search-learning-to-rank/app.bnd` can be set to `false`