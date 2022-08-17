# Upgrade Checks

Check | File Extensions | Description
----- | --------------- | -----------
[GradleUpgradeReleaseDXPCheck](check/gradle_upgrade_release_dxp_check.markdown#gradleupgradereleasedxpcheck) | .gradle | Remove and replaced dependencies in `build.gradle` that are already in `release.dxp.api` with `released.dxp.api` dependency. |
JSPUpgradeRemovedTagsCheck | .jsp, .jspf, .jspx, .tag, .tpl or .vm | Finds removed tags when upgrading. |
[PropertiesUpgradeLiferayPluginPackageFileCheck](check/properties_upgrade_liferay_plugin_package_file_check.markdown#propertiesupgradeliferaypluginpackagefilecheck) | .eslintignore, .prettierignore or .properties | Performs several upgrade checks in `liferay-plugin-package.properties` file. |
PropertiesUpgradeLiferayPluginPackageLiferayVersionsCheck | .eslintignore, .prettierignore or .properties | Validates and upgrades the version in `liferay-plugin-package.properties` file. |
UpgradeDeprecatedAPICheck | .java | Finds calls to deprecated classes, constructors, fields or methods after an upgrade |
UpgradeJavaCheck | .java | Performs upgrade checks for `java` files |
UpgradeRemovedAPICheck | .java | Finds cases where calls are made to removed API after an upgrade. |
XMLUpgradeDTDVersionCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Checks and upgrades the DTD version in `*.xml` file. |
XMLUpgradeRemovedDefinitionsCheck | .action, .function, .jrxml, .macro, .pom, .project, .properties, .svg, .testcase, .toggle, .tpl, .wsdl, .xml or .xsd | Finds removed XML definitions when upgrading. |