# Liferay Modules

The goal of this document is to explain how to use Liferay's build system and
its recommended best practices.

## Marker Files

### Build

File Name | Description
--------- | -----------
`.lfrbuild-portal-pre` | During `ant compile`, builds the module in the `tmp/lib-pre` directory even before building `portal-kernel`, `portal-impl`, etc.
`.lfrbuild-portal` | Deploys the module during a regular `ant all`. `-test` modules never have this file.
`.lfrbuild-slim` | Deploys the module during `ant all` if building a Liferay Slim Runtime.

### Continuous Integration

File Name | Description
--------- | -----------
`.lfrbuild-ci` | Deploys the module during `ant all`, but only if running in Jenkins.
`.lfrbuild-semantic-versioning` | Enables the semantic versioning check of the module on CI. `apps` and `core` modules are already checked, so they don't need this marker file.

### Deploy Directory

File Name | Description
--------- | -----------
`.lfrbuild-app-server-lib` | Deploys module to `${app.server.portal.dir}/WEB-INF/lib`.
`.lfrbuild-static` | Deploys module to `${liferay home}/osgi/static`.
`.lfrbuild-tool` | Deploys module to `${liferay.home}/tools/${module.dir.name}`.

### Release

File Name | Description
--------- | -----------
`.lfrbuild-releng-ignore` | Never considers the module as *stale*, in order to avoid its release.
`.lfrrelease-src` | Added to the root of an app, includes the app's source code in the DXP release.

### Themes

File Name | Description
--------- | -----------
`.lfrbuild-missing-resources-importer` | Added only on the `master `branch, prevents the theme from theme from being published in case it doesn't contain the *Resources Importer* files.