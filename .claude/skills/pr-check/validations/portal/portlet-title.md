# Portlet Title

## Trigger

A new portlet is registered through an added `jakarta.portlet.name=` property in a `@Component`. Each portlet needs a matching `jakarta.portlet.title.<portlet name>` key, in the global `portal-language-lang` `Language.properties` or in the module's own `content/Language.properties`. A missing key fails `PortletTitleTest`, which lives in a separate test module the PR never touches, so nothing else here catches it.

## Match

`(^|/)[^/]+Portlet\.java$`

## Command

This check is static and needs no build. List the added `jakarta.portlet.name=` lines:

```bash
git diff "$(git merge-base HEAD master)...HEAD" -- '*.java'
```

For each line, work out the portlet name from a string literal or from the value of a referenced `*PortletKeys` constant, reading that constant and joining its string fragments when needed. Then confirm that a `jakarta.portlet.title.<name>=` line exists in the global `portal-language-lang` `Language.properties` or in the module's own `content/Language.properties`, and report any missing key as a FAIL.

Like `PortletTitleTest`, skip `ObjectDefinitionsPortlet` names, whose title comes from the object definition rather than a key.

## Time Estimate

~10-30 sec (static; no build).