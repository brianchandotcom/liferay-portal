# Lugbot Integration

The following directories contains classes that contain relevant logic to be moved to SourceFormatter

1. Liferay7x directories in [com.liferay.ide.upgrade.problems.core.internal](https://github.com/LiferayCloud/service-upgrade-bot/tree/master/lugbot/code-upgrade/providers/src/main/java/com/liferay/ide/upgrade/problems/core/internal/)

    There are over 150 classes here, but can be divided in the following categories:
    - Detecting usage of tags in `jsp` files that were removed or have changed `(.tld)`
    - Detecting usage of removed/changed API in `java` files
    - Detecting changed XML declaration `(.dtd)`
    - Detecting obsolete/moved/renamed `properties`

    Every class checks for a specific (hardcoded) removed API or tag.
    - For example: in class [WikiUtilGetEntriesInvocation](https://github.com/LiferayCloud/service-upgrade-bot/blob/master/lugbot/code-upgrade/providers/src/main/java/com/liferay/ide/upgrade/problems/core/internal/liferay70/WikiUtilGetEntriesInvocation.java) Lugbot detects calls to the method `WikiUtil.getEntries`, which was removed in version `7.0`

    SourceFormatter is not checking hardcoded values, but instead uses JSONObjects (one that holds information from the version before upgrading, and another that holds information from the version after upgrading), and finds any calls to API that is present in the old version, but no longer exists in the new version.
    Similarly, it checks usage of removed taglibs, XML declarations and properties.

    JSONObjects for each version of Liferay will be created (using logic in [PortalJSONObject](https://github.com/liferay/liferay-portal/blob/master/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/util/PortalJSONObjectUtil.java)) and Chas is working on storing these (zipped versions) so that SourceFormatter does not have to regenerate these every time.
    When these versions are all avaiable, the following method [getPortalJSONObjectByVersion](https://github.com/liferay/liferay-portal/blob/master/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/util/PortalJSONObjectUtil.java#L183-L189
) should be updated.

    ### Requirements for Upgrade Checks:
    - Checks related to Upgrade need to have the [Upgrade](https://github.com/liferay/liferay-portal/blob/master/modules/util/source-formatter/src/main/resources/documentation/upgrade_checks.markdown#upgrade-checks) category, which is set in either `sourcechecks.xml` or `checkstyle.xml`. Checks with the `Upgrade` category will be skipped by default. Only when the argument [source.check.category.names](https://github.com/liferay/liferay-portal/blob/7.4.3.4-ga4/portal-impl/build.xml#L725) is set to `Upgrade`, these checks are performed.
    - The properties `upgrade.from.version` and `upgrade.to.version` need to be set in `source-formatter.properties`:
        ```
        upgrade.from.version=7.1.3-ga4
        upgrade.to.version=7.4.3.4-ga4
        ```
     
2. [com.liferay.code.upgrade](https://github.com/LiferayCloud/service-upgrade-bot/tree/master/lugbot/code-upgrade/providers/src/main/java/com/liferay/code/upgrade/providers)

   Work in Progress: Talking to Alan and Simon to decide which way to integrate these classes in SourceFormatter